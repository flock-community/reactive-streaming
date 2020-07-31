package com.github.flockcommunity.reactivewordrank.backend.service

import com.github.flockcommunity.reactivewordrank.backend.repository.R2DBCRepostitory
import com.github.flockcommunity.reactivewordrank.backend.repository.TestData
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class R2JDBCService(
        val r2DBCRepostitory: R2DBCRepostitory
) {
    val logger = LoggerFactory.getLogger(this.javaClass)

    init {
        saveTestData()
    }

    private final fun saveTestData() = TestData(null, "Name: ${UUID.randomUUID()}")
            .also {
                r2DBCRepostitory.save(it).subscribe()
                logger.info("Saved: $it")
            }


}