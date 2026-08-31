#!/usr/bin/env bash
# Regenerate all three clients (generatePythonClient/generateTypeScriptClient patch
# their known generator gaps automatically) and publish each to its own
# catalog-client-<lang> repo as a PR for the owning team to review.
set -euo pipefail
REPO_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$REPO_ROOT"

./gradlew generateAllClients --no-daemon

for lang in python typescript go; do
  scripts/publish_client.sh "$lang"
done
