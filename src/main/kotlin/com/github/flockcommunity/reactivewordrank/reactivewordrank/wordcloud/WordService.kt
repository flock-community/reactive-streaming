package com.github.flockcommunity.reactivewordrank.reactivewordrank.wordcloud

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

private const val ID: Long = 1

@Service
class WordService (private val wordRepository: WordRepository) {

    private val words = wordRepository.getWords().cache(10)
    private val wordCounter = mutableMapOf<String, Long>()
    private val wordDistributions = words
                .map { word ->
                    wordCounter.updateWith(word)
                    WordCloud(ID, wordCounter)
                }
                .cache(1)


    fun getWords(): Flux<String> = words
    fun getWordDistribution(): Flux<WordCloud> = wordDistributions

    private fun MutableMap<String, Long>.updateWith(word: String): MutableMap<String, Long> {
        val currentCount = this[word] ?: 0
        this[word] = currentCount + 1
        return this
    }
}

    data class WordCloud (val id: Long, val wordCounter: MutableMap<String, Long>)