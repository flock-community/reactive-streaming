package com.github.flockcommunity.reactivewordrank.reactivewordrank.wordcloud

import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import java.time.Duration
import kotlin.random.Random

@Repository
class WordRepository {

    private val wordList: List<String> = listOf("Hello", "World")

    fun getWords(): Flux<String> {
        return Flux.interval(Duration.ofSeconds(2)).map { it -> getWord() }
    }

    private fun getWord(): String = wordList.get(Random.nextInt(0,2))

}