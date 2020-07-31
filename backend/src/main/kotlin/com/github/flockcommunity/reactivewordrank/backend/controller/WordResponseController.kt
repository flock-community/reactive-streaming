package com.github.flockcommunity.reactivewordrank.backend.controller

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.springframework.http.MediaType
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class WordResponseController {

    @MessageMapping("words")
    suspend fun getMessagesRScocket() = getMessagesFlow()

    @RequestMapping("words", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    suspend fun getMessagesRest() = getMessagesFlow()

    private fun getMessagesFlow(): Flow<WordResponse> = flow {
        while (true) {
            delay(100)
            emit(WordResponse("Random word: ${(0..10).random()}"))
        }
    }
}

class WordResponse(
        val word: String
)