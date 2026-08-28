package javazone_demo.catalog

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

@MicronautTest
class DatasetControllerTest {

    @Inject
    @Client("/")
    lateinit var client: HttpClient

    @Test
    fun listReturnsSeededDatasets() {
        val response = client.toBlocking().exchange(HttpRequest.GET<Any>("/datasets"), Array<DatasetDTO>::class.java)
        assertEquals(HttpStatus.OK, response.status)
        assertEquals(3, response.body()?.size)
    }

    @Test
    fun createWithoutAuthIsRejected() {
        val request = HttpRequest.POST("/datasets", DatasetDTO(name = "test_ds", ownerTeam = "team-x",
            tags = emptyList(), sensitivity = DatasetSensitivity.PUBLIC, schemaFields = emptyList()))
        val ex = assertThrows(HttpClientResponseException::class.java) { client.toBlocking().exchange<DatasetDTO, Any>(request) }
        assertEquals(HttpStatus.UNAUTHORIZED, ex.status)
    }
}
