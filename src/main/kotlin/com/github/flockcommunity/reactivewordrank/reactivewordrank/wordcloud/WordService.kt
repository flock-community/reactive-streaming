package com.github.flockcommunity.reactivewordrank.reactivewordrank.wordcloud

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

private const val WORDCLOUD_ID: Long = 1

@Service
class WordService(private val wordRepository: WordRepository) {

    private val log = LoggerFactory.getLogger(javaClass)
    private val words = startWordRetrieval().cache(10)
    private val wordDistributions = startWordDistributionDerivation().cache(1)

    private val wordCounter = mutableMapOf<String, Long>()

    fun getWords(): Flux<String> = words
    fun getWordDistribution(): Flux<WordCloud> = wordDistributions


    private fun startWordRetrieval(): Flux<String> =
            wordRepository
                    .getWords()
                    .doOnError { log.info("Issue retrieving words. Starting over ... ", it) }
                    .retry()

    private fun startWordDistributionDerivation(): Flux<WordCloud> = words
            .onErrorReturn("error")
            .map {
                wordCounter.updateWith(it)
                WordCloud(WORDCLOUD_ID, wordCounter)
            }
            .doOnError { log.info("Issue updating wordDistribution. Trying again", it) }


    private fun MutableMap<String, Long>.updateWith(word: String): MutableMap<String, Long> {
        val currentCount = this[word] ?: 0
        this[word] = currentCount + 1
        return this
    }
}

