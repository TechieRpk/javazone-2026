package javazone_demo.catalog

import io.micronaut.serde.annotation.Serdeable
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.Instant

@Serdeable
data class DatasetDTO(
    val id: Long? = null,
    @field:NotBlank val name: String,
    @field:NotBlank val owningTeam: String,
    @field:NotNull val tags: List<String>,
    @field:NotNull val sensitivity: DatasetSensitivity,
    @field:NotNull val retentionDays: Int,
    @field:NotNull val refreshIntervalHours: Int,
    @field:NotNull val schemaFields: List<FieldDTO>,
    val updatedAt: Instant? = null
)
