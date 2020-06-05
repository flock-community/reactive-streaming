package com.github.flockcommunity.reactivewordrank.reactivewordrank.hackernews

import org.slf4j.LoggerFactory.getLogger
import org.springframework.stereotype.Repository

@Repository
class HackNewsRepository(
        private val hackerNewsWebClient: HackerNewsWebClient
) {
    private val log = getLogger(javaClass)

    private val comments = hackerNewsWebClient.getComments()
            .doOnNext { log.info(it) }
            .doOnError { log.error("Got error receiving comments from hackernews ", it) }
            .doOnComplete { log.warn("Hackernews comments Flux has ended. Oh boy?") }
            .retry()
            .doOnComplete { log.error("Hackernews comments Flux has bypassed retry")}
            .cache(1)
}