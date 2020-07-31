package com.github.flockcommunity.reactivestreaming.middleware.wordcloud.repository

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import java.time.Duration
import kotlin.random.Random


@ConstructorBinding
@ConfigurationProperties(prefix = "app.wordgeneration")
data class WordGenerationConfig(
        val interval: Duration,
    val words: List<String>
)


@Repository
internal class WordRepository(
        private val wordGenerationConfig: WordGenerationConfig
)
{
    private val log = LoggerFactory.getLogger(javaClass)

    private val dictionary: List<String> = wordGenerationConfig.words

    private val wordPool: List<String>

    init {
        wordPool = List(dictionary.size) { dictionary[Random.nextInt(0, dictionary.size)] }
    }

    fun getWords(): Flux<String> {
        log.info("I'm gonna generate words with interval ${wordGenerationConfig.interval}")
        return Flux.interval(wordGenerationConfig.interval).map { wordPool[Random.nextInt(0, wordPool.size)] }
    }
}