#!/usr/bin/env python3
"""Patch the generated TypeScript catalog client so it actually compiles.

openapi-generator's typescript-axios template leaves the axios dependency as an
unpinned "^1.16.0" range. Newer axios patch releases (seen with 1.20.0) changed
their exported types such that common.ts's createRequestFunction infers a return
type referencing an inaccessible `unique symbol`, which fails under
`declaration: true` with TS2527. Pinning axios to the known-good 1.16.0 avoids it.

Idempotent: safe to re-run after every regeneration of the client.
"""
import argparse
import json
import sys
from pathlib import Path

KNOWN_GOOD_AXIOS_VERSION = "1.16.0"


def patch(client_dir: Path) -> None:
    package_json = client_dir / "package.json"
    if not package_json.is_file():
        sys.exit(f"Could not find {package_json}")

    data = json.loads(package_json.read_text())
    current = data.get("dependencies", {}).get("axios")
    if current == KNOWN_GOOD_AXIOS_VERSION:
        print(f"{package_json} already patched, skipping")
        return

    data["dependencies"]["axios"] = KNOWN_GOOD_AXIOS_VERSION
    package_json.write_text(json.dumps(data, indent=2) + "\n")
    print(f"patched {package_json} (pinned axios {current} -> {KNOWN_GOOD_AXIOS_VERSION})")


def main() -> None:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("client_dir", nargs="?", default="clients/typescript/generated",
                         help="Path to the openapi-generator output directory")
    args = parser.parse_args()

    patch(Path(args.client_dir))


if __name__ == "__main__":
    main()
