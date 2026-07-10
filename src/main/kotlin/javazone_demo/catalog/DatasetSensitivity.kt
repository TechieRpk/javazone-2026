package javazone_demo.catalog

import io.micronaut.serde.annotation.Serdeable

@Serdeable
enum class DatasetSensitivity {
    PUBLIC,
    INTERNAL,
    RESTRICTED
}
