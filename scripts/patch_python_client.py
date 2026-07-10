#!/usr/bin/env python3
"""Patch the generated Python catalog client so bearer-auth actually works.

openapi-generator's Python client wires bearerAuth into Configuration.access_token /
auth_settings(), but that path doesn't reliably attach the Authorization header to
outgoing requests (a known upstream gap). ApiClient.set_default_header(...) *is*
honored on every request, so this adds a small auth.py helper that uses it instead.

Idempotent: safe to re-run after every regeneration of the client.
"""
import argparse
import sys
from pathlib import Path

MARKER = "# added by scripts/patch_python_client.py"

AUTH_MODULE = '''"""Auth helper patched in by scripts/patch_python_client.py.

openapi-generator's Configuration.access_token / auth_settings() does not reliably
attach the Authorization header to outgoing requests. set_default_header is honored
on every request, so we use that instead.
"""
from __future__ import annotations

import os

from {package}.api_client import ApiClient
from {package}.configuration import Configuration


def authenticated_client(base_url: str | None = None, token: str | None = None) -> ApiClient:
    token = token or os.environ.get("CATALOG_API_TOKEN")
    if not token:
        raise RuntimeError("Set CATALOG_API_TOKEN env var (or pass token=) to authenticate.")
    config = Configuration(host=base_url or os.environ.get("CATALOG_API_BASE_URL", "http://localhost:8080"))
    client = ApiClient(config)
    client.set_default_header("Authorization", f"Bearer {{token}}")
    return client
'''

EXPORT_LINE = "from {package}.auth import authenticated_client  {marker}\n"


def patch(client_dir: Path, package_name: str) -> None:
    package_dir = client_dir / package_name
    if not package_dir.is_dir():
        sys.exit(f"Could not find generated package '{package_name}' under {client_dir}")

    auth_file = package_dir / "auth.py"
    auth_file.write_text(AUTH_MODULE.format(package=package_name))
    print(f"wrote {auth_file}")

    init_file = package_dir / "__init__.py"
    contents = init_file.read_text()
    if MARKER not in contents:
        export_line = EXPORT_LINE.format(package=package_name, marker=MARKER)
        init_file.write_text(contents.rstrip("\n") + "\n\n" + export_line)
        print(f"patched {init_file} (added authenticated_client export)")
    else:
        print(f"{init_file} already patched, skipping")


def main() -> None:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("client_dir", nargs="?", default="clients/python/generated",
                         help="Path to the openapi-generator output directory")
    parser.add_argument("--package", default="catalog_client",
                         help="Generated Python package name (packageName additional-property)")
    args = parser.parse_args()

    patch(Path(args.client_dir), args.package)


if __name__ == "__main__":
    main()
