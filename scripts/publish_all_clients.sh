#!/usr/bin/env bash
# Regenerate all three clients, patch the known generator gaps, and publish
# each to its own catalog-client-<lang> repo as a PR for the owning team to review.
set -euo pipefail
REPO_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$REPO_ROOT"

./gradlew generateAllClients --no-daemon

python3 scripts/patch_python_client.py clients/python/generated
python3 scripts/patch_typescript_client.py clients/typescript/generated

for lang in python typescript go; do
  scripts/publish_client.sh "$lang"
done
