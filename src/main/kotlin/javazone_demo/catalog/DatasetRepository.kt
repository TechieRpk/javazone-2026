package javazone_demo.catalog

import jakarta.annotation.PostConstruct
import jakarta.inject.Singleton
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

@Singleton
class DatasetRepository {

    private val datasets = ConcurrentHashMap<Long, DatasetDTO>()
    private val ids = AtomicLong()

    @PostConstruct
    fun seed() {
        save(DatasetDTO(name = "orders_daily", owner = "team-payments",
            tags = listOf("orders", "daily"), sensitivity = DatasetSensitivity.INTERNAL,
            schemaFields = listOf(FieldDTO("order_id", "STRING"), FieldDTO("total_amount", "DECIMAL"))))
        save(DatasetDTO(name = "clickstream_raw", owner = "team-web",
            tags = listOf("clickstream", "raw"), sensitivity = DatasetSensitivity.PUBLIC,
            schemaFields = listOf(FieldDTO("session_id", "STRING"), FieldDTO("event_ts", "TIMESTAMP"))))
        save(DatasetDTO(name = "customer_pii", owner = "team-identity",
            tags = listOf("customers", "pii"), sensitivity = DatasetSensitivity.RESTRICTED,
            schemaFields = listOf(FieldDTO("customer_id", "STRING"), FieldDTO("email", "STRING"))))
    }

    fun findAll(): Collection<DatasetDTO> = datasets.values

    fun findById(id: Long): DatasetDTO? = datasets[id]

    fun save(dataset: DatasetDTO): DatasetDTO {
        val id = ids.incrementAndGet()
        val saved = dataset.copy(id = id, updatedAt = Instant.now())
        datasets[id] = saved
        return saved
    }

    fun update(id: Long, dataset: DatasetDTO): DatasetDTO? {
        if (!datasets.containsKey(id)) {
            return null
        }
        val updated = dataset.copy(id = id, updatedAt = Instant.now())
        datasets[id] = updated
        return updated
    }

    fun delete(id: Long): Boolean = datasets.remove(id) != null
}
