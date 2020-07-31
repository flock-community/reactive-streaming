package com.github.flockcommunity.reactivewordrank.backend.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.core.DatabaseClient

@Configuration
class DbConfig(
        val db: DatabaseClient
) {

    init {
        val initDb = db.execute {
            """ CREATE TABLE IF NOT EXISTS TEST_DATA (
                    id SERIAL PRIMARY KEY,
                    name VARCHAR(255)
                );
            """
        }
        initDb.then().subscribe()
    }
}