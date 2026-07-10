package javazone_demo.catalog

import io.micronaut.serde.annotation.Serdeable
import jakarta.validation.constraints.NotBlank

@Serdeable
data class FieldDTO(
    @field:NotBlank val name: String,
    @field:NotBlank val type: String
)
