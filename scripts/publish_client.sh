#!/usr/bin/env bash
# Publish a freshly generated client to its own repo and open a PR for review.
#
# Usage: scripts/publish_client.sh <python|typescript|go>
#
# Clones the target catalog-client-<lang> repo, replaces its tracked contents
# with the current clients/<lang>/generated output, and pushes a PR on a
# fixed branch (re-running this updates the same PR instead of piling up
# duplicates).
set -euo pipefail

if [ $# -ne 1 ]; then
  echo "usage: $0 <python|typescript|go>" >&2
  exit 1
fi

LANG_NAME="$1"
REPO_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
SRC_DIR="$REPO_ROOT/clients/$LANG_NAME/generated"
TARGET_REPO="TechieRpk/catalog-client-$LANG_NAME"
BRANCH="sync/openapi-client"
SRC_SHA="$(git -C "$REPO_ROOT" rev-parse --short HEAD)"

if [ ! -d "$SRC_DIR" ]; then
  echo "[$LANG_NAME] $SRC_DIR does not exist -- run ./gradlew generateAllClients first" >&2
  exit 1
fi

WORKDIR="$(mktemp -d)"
trap 'rm -rf "$WORKDIR"' EXIT

gh repo clone "$TARGET_REPO" "$WORKDIR" -- --quiet
cd "$WORKDIR"
git checkout -B "$BRANCH"
git rm -rq --ignore-unmatch -- . >/dev/null
rsync -a --exclude='.git' "$SRC_DIR/" ./
git add -A

if git diff --cached --quiet; then
  echo "[$LANG_NAME] no changes to publish"
  exit 0
fi

git commit -q -m "Sync generated client from javazone-2026@$SRC_SHA"
git push -q -u origin "$BRANCH" --force

OPEN_COUNT="$(gh pr list --repo "$TARGET_REPO" --head "$BRANCH" --state open --json number --jq length)"
if [ "$OPEN_COUNT" != "0" ]; then
  echo "[$LANG_NAME] existing PR updated: $(gh pr list --repo "$TARGET_REPO" --head "$BRANCH" --state open --json url --jq '.[0].url')"
else
  gh pr create --repo "$TARGET_REPO" --base main --head "$BRANCH" \
    --title "Sync generated $LANG_NAME client" \
    --body "Regenerated from the Dataset Catalog OpenAPI spec at javazone-2026@${SRC_SHA}."
fi
