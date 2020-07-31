package com.github.flockcommunity.reactivestreaming.middleware.wordcloud.service

import com.github.flockcommunity.reactivestreaming.middleware.wordcloud.domain.WordCloud
import com.github.flockcommunity.reactivestreaming.middleware.wordcloud.repository.WordRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux


@Service
internal class WordService(private val repo: WordRepository) {
    companion object{
        private const  val WORDCLOUD_ID: Long = 1
    }

    private val log = LoggerFactory.getLogger(javaClass)
    private val words = startWordRetrieval().cache(10)
    private val wordDistributions = startWordDistributionDerivation().cache(1)

    private val wordCounter = mutableMapOf<String, Long>()

    fun getWords(): Flux<String> = words
    fun getWordDistribution(): Flux<WordCloud> = wordDistributions



    private fun startWordRetrieval(): Flux<String> =
            repo
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

