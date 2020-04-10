package com.github.flockcommunity.reactivewordrank.reactivewordrank.wordcloud

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class WordService (private val wordRepository: WordRepository) {

    fun getWords(): Flux<String> = wordRepository.getWords()

    fun getWordDistribution(): Flux<WordCloud>{

        return getWords().map { WordCloud(1, mutableMapOf("Hello" to 1L, "World" to 2L)) }
    }
}


data class WordCloud (val id: Long, val wordCounter: MutableMap<String, Long>)