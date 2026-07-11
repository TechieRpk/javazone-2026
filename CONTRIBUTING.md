# Contributing

This is a talk demo, not a production service — but it's meant to keep
working and be easy to extend as the talk evolves. This doc covers local
setup, how the pieces fit together, and how to add to it.

## Prerequisites

- JDK 25
- Docker or a compatible runtime ([Colima](https://github.com/abiosoft/colima)
  works well on macOS: `brew install colima docker && colima start`) —
  needed for `generate*Client` tasks, not for building/running the app itself
- Python 3.9+ — for the patch script and integration tests
- Optional, only if you want to compile the TypeScript/Go clients locally
  (CI does this for you either way): Node 22+, Go 1.23+

## Build and test loop

```bash
./gradlew build          # compile, run unit tests, generate the OpenAPI spec
./gradlew test            # unit tests only
./gradlew run              # run the app on :8080
```

Unit tests live in `src/test/kotlin` and use `@MicronautTest` with an injected
`HttpClient` — see `DatasetControllerTest.kt` for the pattern (including the
"POST without auth returns 401" case).

## Project layout

| Path | Purpose |
|---|---|
| `src/main/kotlin/javazone_demo/catalog/` | Domain: DTOs, in-memory repository, controller |
| `src/main/kotlin/javazone_demo/security/` | `ApiTokenFilter` — bearer-auth on mutating verbs |
| `build.gradle.kts` | App deps, `micronaut-openapi`/KSP wiring, Docker-based `generate*Client` tasks |
| `scripts/patch_python_client.py` | Post-generation fix for the Python client's auth handling |
| `tests/integration/` | pytest suite driving the generated+patched Python client against a live app |
| `.github/workflows/api-client-pipeline.yml` | CI: build → spec → generate → patch → integration test |

## Adding an endpoint or field

1. Add/change the field on `DatasetDTO` (or a new DTO) in
   `src/main/kotlin/javazone_demo/catalog/`. Use `@field:NotBlank` /
   `@field:NotNull` (not bare `@NotBlank`) — Kotlin needs the use-site target
   for Jakarta Bean Validation to see the annotation on the backing field.
2. Wire it into `DatasetController` with an explicit
   `@Operation(operationId = "...")` — this becomes the generated client's
   method name in every language, so keep it descriptive
   (`listDatasets`, not `list`).
3. If the operation should require auth, add
   `security = [SecurityRequirement(name = "bearerAuth")]` to its `@Operation`
   and make sure `ApiTokenFilter`'s path pattern still covers it.
4. **If you add `@Valid`-validated parameters to a controller method, mark
   both the class and that method `open`.** Kotlin classes/methods are final
   by default, which breaks Micronaut's AOP proxying for bean validation —
   KSP will fail the build with a clear error if you forget
   (`DatasetController` already demonstrates this).
5. Run `./gradlew build` and check `build/openapi/openapi.yaml` reflects the
   change, then `./gradlew generateAllClients` to confirm all three clients
   still generate cleanly.
6. Update `tests/integration/test_catalog_api.py` if the change affects the
   Python client's shape (it's a real generated client, not a mock — class
   and method names come directly from the spec).

## Rehearsing the "this catches breaking changes" demo

```bash
# Make a breaking change, e.g. rename a field in DatasetDTO.kt
./gradlew generatePythonClient
python3 scripts/patch_python_client.py clients/python/generated
pip install --force-reinstall --no-deps ./clients/python/generated
pytest tests/integration -v   # should now fail
```

Revert the change afterward — don't leave it broken on `main`.

## Adding another target language

`build.gradle.kts` has a `registerClientTask(name, generator, outDir, extra)`
helper. To add a fourth language, call it with the
[openapi-generator generator name](https://openapi-generator.tech/docs/generators)
you want (e.g. `"kotlin"`, `"rust"`), an output dir under `clients/`, and any
`--additional-properties`. Add it to `generateAllClients`'s `dependsOn`, and
if you want it exercised in CI, add a job to
`.github/workflows/api-client-pipeline.yml` mirroring `typescript-client` or
`go-client`.

## Known gotchas

- **Docker bind-mount ownership**: `openapi-generator-cli`'s image runs as
  root. `registerClientTask` passes `--user <uid>:<gid>` to avoid root-owned
  output files breaking later steps (this bit the `go-client` CI job before
  the fix — `go mod tidy` couldn't write `go.sum` into a root-owned
  directory). Keep this if you touch that function.
- **Python version**: the patch script's generated `auth.py` uses
  `str | None` syntax behind `from __future__ import annotations` so it still
  works on Python 3.9. Don't drop that import.
- **KSP output path**: the generated spec lands at
  `build/generated/ksp/main/resources/META-INF/swagger/javazone-catalog-api-1.0.yml`,
  not the Java annotation-processor path — `copyOpenApiSpec` depends on this.
  If you rename the app's `title`/`version` in `@OpenAPIDefinition`, update
  that `from(...)` path too (the filename pattern is `${title}-${version}.yml`).

## Commit style

Small, logical commits over one big diff — see `git log` for the shape this
repo's history takes (scaffold → OpenAPI/build wiring → domain →
patch script/tests → CI, with fixes as their own follow-up commits).
