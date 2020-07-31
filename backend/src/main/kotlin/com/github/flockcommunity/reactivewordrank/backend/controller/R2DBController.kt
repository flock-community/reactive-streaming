package com.github.flockcommunity.reactivewordrank.backend.controller

import com.github.flockcommunity.reactivewordrank.backend.repository.R2DBCRepostitory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class R2DBController(
        val r2DBCRepostitory: R2DBCRepostitory
) {

    @GetMapping(produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    suspend fun findAll() = r2DBCRepostitory.findAll()

}