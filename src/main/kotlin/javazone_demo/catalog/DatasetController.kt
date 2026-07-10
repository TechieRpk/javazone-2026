package javazone_demo.catalog

import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.http.annotation.Status
import io.micronaut.http.exceptions.HttpStatusException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid

@Controller("/datasets")
@Tag(name = "datasets")
open class DatasetController(private val repository: DatasetRepository) {

    @Get
    @Operation(operationId = "listDatasets")
    fun list(): Collection<DatasetDTO> = repository.findAll()

    @Get("/{id}")
    @Operation(operationId = "getDataset")
    fun get(id: Long): DatasetDTO =
        repository.findById(id) ?: throw HttpStatusException(HttpStatus.NOT_FOUND, "Dataset $id not found")

    @Post
    @Status(HttpStatus.CREATED)
    @Operation(operationId = "createDataset", security = [SecurityRequirement(name = "bearerAuth")])
    open fun create(@Valid @Body dataset: DatasetDTO): DatasetDTO = repository.save(dataset)

    @Put("/{id}")
    @Operation(operationId = "updateDataset", security = [SecurityRequirement(name = "bearerAuth")])
    open fun update(id: Long, @Valid @Body dataset: DatasetDTO): DatasetDTO =
        repository.update(id, dataset) ?: throw HttpStatusException(HttpStatus.NOT_FOUND, "Dataset $id not found")

    @Delete("/{id}")
    @Status(HttpStatus.NO_CONTENT)
    @Operation(operationId = "deleteDataset", security = [SecurityRequirement(name = "bearerAuth")])
    fun delete(id: Long) {
        if (!repository.delete(id)) {
            throw HttpStatusException(HttpStatus.NOT_FOUND, "Dataset $id not found")
        }
    }
}
