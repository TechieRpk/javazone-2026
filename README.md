# javazone-2026

Demo project for a JavaZone 2026 lightning talk: **killing API-client drift with
OpenAPI + openapi-generator**.

Every team has that one API client someone wrote 18 months ago that nobody
remembers to update when the backend changes. This repo shows the full
pipeline that fixes that: a Micronaut app generates its OpenAPI spec at build
time, `openapi-generator` turns that spec into typed clients in Python,
TypeScript, and Go, small post-processing scripts patch real gaps in the
generated Python and TypeScript clients, and a GitHub Actions pipeline wires
it all together so a breaking API change fails CI instead of silently
breaking a consumer.

## What's here

A small **Dataset Catalog API** (`/datasets`) built with Micronaut + Kotlin:
list, get, create, update, and delete dataset records (name, owner, tags,
sensitivity level, schema fields). Reads are public; writes require a bearer
token, via `ApiTokenFilter`.

```
src/main/kotlin/javazone_demo/
├── Application.kt              # @OpenAPIDefinition + @SecurityScheme
├── catalog/                    # DatasetDTO, FieldDTO, DatasetSensitivity,
│                                # DatasetRepository (in-memory), DatasetController
└── security/ApiTokenFilter.kt  # guards POST/PUT/DELETE with a bearer token

scripts/patch_python_client.py      # post-processing fix for the generated Python client
scripts/patch_typescript_client.py  # post-processing fix for the generated TypeScript client
tests/integration/              # pytest suite exercising the generated+patched client
.github/workflows/              # build → spec → generate clients → test, in CI
```

## Quickstart

Requires JDK 25 and Docker (or a Docker-compatible runtime like
[Colima](https://github.com/abiosoft/colima)) running locally.

```bash
# Build the app — this also generates the OpenAPI spec
./gradlew build

# Run it
./gradlew run
# → http://localhost:8080/datasets
# → http://localhost:8080/swagger-ui  (spec browser)
```

The spec is generated at compile time by `micronaut-openapi` and copied to
`build/openapi/openapi.yaml` by the `copyOpenApiSpec` Gradle task.

### Generate clients

```bash
./gradlew generatePythonClient      # clients/python/generated
./gradlew generateTypeScriptClient  # clients/typescript/generated
./gradlew generateGoClient          # clients/go/generated
./gradlew generateAllClients        # all three
```

These shell out to the official `openapitools/openapi-generator-cli` Docker
image, so the same command produces identical output locally and in CI.

### Patch the TypeScript client

openapi-generator's typescript-axios template leaves the `axios` dependency as
an unpinned `^1.16.0` range. Newer axios patch releases changed their exported
types such that `common.ts`'s `createRequestFunction` infers a return type
referencing an inaccessible `unique symbol`, which fails to compile
(`TS2527`) under `declaration: true`. `scripts/patch_typescript_client.py`
pins `axios` to the known-good `1.16.0`. Idempotent — safe to re-run after
every regeneration.

```bash
./gradlew generateTypeScriptClient
python3 scripts/patch_typescript_client.py clients/typescript/generated
cd clients/typescript/generated && npm install && npm run build
```

### Patch the Python client and run the integration suite

openapi-generator's Python client doesn't reliably attach the bearer token to
outgoing requests (`Configuration.access_token` / `auth_settings()` is
unreliable). `scripts/patch_python_client.py` fixes this with
`ApiClient.set_default_header(...)`, which is honored on every request. It's
idempotent — safe to re-run after every regeneration.

```bash
python3 scripts/patch_python_client.py clients/python/generated

python3 -m venv .venv
source .venv/bin/activate
pip install ./clients/python/generated -r tests/integration/requirements.txt

./gradlew run &                     # in one terminal, or backgrounded
CATALOG_API_BASE_URL=http://localhost:8080 \
CATALOG_API_TOKEN=javazone-secret \
pytest tests/integration -v
```

### CI

`.github/workflows/api-client-pipeline.yml` runs on every push/PR to `main`:
builds the app, generates the spec, generates all three clients, patches the
Python one, starts the app, and runs the integration suite against it — a
failing test here means the API changed in a way that breaks its clients.

## Micronaut documentation

- [User Guide](https://docs.micronaut.io/5.0.3/guide/index.html)
- [API Reference](https://docs.micronaut.io/5.0.3/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/5.0.3/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)
- [Micronaut Gradle Plugin documentation](https://micronaut-projects.github.io/micronaut-gradle-plugin/latest/)
- [Micronaut AOT documentation](https://micronaut-projects.github.io/micronaut-aot/latest/guide/)
- [Micronaut Serialization Jackson Core documentation](https://micronaut-projects.github.io/micronaut-serialization/latest/guide/)
