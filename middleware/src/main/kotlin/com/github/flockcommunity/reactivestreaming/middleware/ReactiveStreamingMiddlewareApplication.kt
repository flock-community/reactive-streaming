package com.github.flockcommunity.reactivestreaming.middleware

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class ReactiveStreamingMiddlewareApplication

fun main(args: Array<String>) {
	runApplication<ReactiveStreamingMiddlewareApplication>(*args)
}
