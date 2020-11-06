package com.github.flockcommunity.reactivestreaming.middleware.wordcloud.service

import com.github.flockcommunity.reactivestreaming.middleware.wordcloud.domain.Word
import com.github.flockcommunity.reactivestreaming.middleware.wordcloud.domain.WordCloud
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

private const val WORDCLOUD_ID: Long = 1

@Service
internal class WordDistributionService(
        private val wordService: WordService) {

    private val log = LoggerFactory.getLogger(javaClass)

    fun getWordDistribution(): Flux<WordCloud> {
        val wordCounter = mutableMapOf<String, Long>()
        return wordService.getWords()
                .map {
                    wordCounter.updateWith(it.word)
                    WordCloud(WORDCLOUD_ID, wordCounter)
                }
                .doOnError { log.info("Issue updating wordDistribution. Stopping", it) }
                .onErrorStop()
    }

    private fun MutableMap<String, Long>.updateWith(word: String): MutableMap<String, Long> {
        val currentCount = this[word] ?: 0
        this[word] = currentCount + 1
        return this
    }


    fun getWordDistributionWithRetry(): Flux<WordCloud> {
        val wordCounter = mutableMapOf<String, Long>()
        return wordService.getWordsWithRetry()
                .onErrorReturn(Word(-1, "error"))
                .map {
                    wordCounter.updateWith(it.word)
                    WordCloud(WORDCLOUD_ID, wordCounter)
                }
                .doOnError { log.info("Issue updating wordDistribution. Trying again", it) }

    }





    private val wordDistributions = startWordDistributionDerivation().cache(1)


    private fun startWordDistributionDerivation(): Flux<WordCloud> {
        val wordCounter = mutableMapOf<String, Long>()
        return wordService.getWords()
                .onErrorReturn(Word(-1, "error"))
                .map {
                    wordCounter.updateWith(it.word)
                    WordCloud(WORDCLOUD_ID, wordCounter)
                }
                .doOnError { log.info("Issue updating wordDistribution. Trying again", it) }
                .retry()
    }


    fun getWordDistributionCached(): Flux<WordCloud> = wordDistributions


}

