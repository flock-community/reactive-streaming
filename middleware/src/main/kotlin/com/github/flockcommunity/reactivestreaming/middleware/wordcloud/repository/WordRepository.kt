package com.github.flockcommunity.reactivestreaming.middleware.wordcloud.repository

import com.github.flockcommunity.reactivestreaming.middleware.wordcloud.client.WordsRSocketClient
import com.github.flockcommunity.reactivestreaming.middleware.wordcloud.domain.Word
import org.slf4j.LoggerFactory
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
        val words: List<String>,
        val useUpstream: Boolean = true
)

@Repository
internal class WordRepository(
        private val client: WordsRSocketClient,
        private val config: WordGenerationConfig
) {
    private val log = LoggerFactory.getLogger(javaClass)

    private val dictionary: List<String> = config.words

    private val wordPool: List<String>

    init {
        wordPool = List(dictionary.size) { dictionary[Random.nextInt(0, dictionary.size)] }
    }


    fun getWords(): Flux<Word> = if (config.useUpstream) getWordsUpstream() else getWordsInternal()

    private fun getWordsUpstream(): Flux<Word> {
        log.info("Requesting words upstream")
        var idx = 0L;
        return client.getWords()
                .map { Word(idx = idx++, word = it.word) }
    }

    private fun getWordsInternal(): Flux<Word> {

        log.info("I'm gonna generate words with interval ${config.interval}")
        var idx = 0L;
        return Flux.interval(config.interval).map { wordPool[Random.nextInt(0, wordPool.size)] }
                .map {
                    Word(idx = idx++, word = it)
                }
    }


}