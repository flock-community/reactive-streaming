package com.github.flockcommunity.reactivewordrank.backend.repository

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.reactive.ReactiveCrudRepository


interface R2DBCRepostitory : ReactiveCrudRepository<TestData, Long> {

}

@Table
data class TestData(
        @Id
        val id: Long?,
        val name: String
)