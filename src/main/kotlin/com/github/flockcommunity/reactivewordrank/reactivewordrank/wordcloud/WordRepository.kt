package com.github.flockcommunity.reactivewordrank.reactivewordrank.wordcloud

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import java.time.Duration
import kotlin.random.Random

@Repository
class WordRepository {

    private val log = LoggerFactory.getLogger(javaClass)

    private val dictionary: List<String> = listOf(
            "Poooo hee",
            "Flock.",
            "Software",
            "Skillz",
            "Yo mamma",
            "Borrel",
            "🍺 Beer",
            "🕶 privacy",
            "hackerbois",
            "不客气"
    )

    fun getWords(): Flux<String> {
        log.info("I'm gonna generate words every 2 seconds")
        return Flux.interval(Duration.ofSeconds(2)).map { dictionary[Random.nextInt(0, dictionary.size)] }
    }

}