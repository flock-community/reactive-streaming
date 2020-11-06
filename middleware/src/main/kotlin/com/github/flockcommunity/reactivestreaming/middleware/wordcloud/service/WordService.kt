package com.github.flockcommunity.reactivestreaming.middleware.wordcloud.service

import com.github.flockcommunity.reactivestreaming.middleware.wordcloud.domain.Word
import com.github.flockcommunity.reactivestreaming.middleware.wordcloud.repository.WordRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
internal class WordService(private val repo: WordRepository) {

    private val log = LoggerFactory.getLogger(javaClass)

    fun getWords(): Flux<Word> = repo.getWords()

    fun getWordsWithRetry(): Flux<Word> = getWords()
            .doOnError { log.info("Issue retrieving words. Starting over ... ", it) }
            .retry()





    /**
     * Expose a streamed cache
     * Caching
     *
     */

    private val words = startWordRetrieval().cache(2).also { it.subscribe() }
//    private val words = Flux.empty<Word>()


    private fun startWordRetrieval() = repo
            .getWords()
//            .doOnNext{ log.info("Another word: $it") }
            .doOnError { log.info("Issue retrieving words. Starting over ... ", it) }

    fun getWordsCached(): Flux<Word> = words
}

