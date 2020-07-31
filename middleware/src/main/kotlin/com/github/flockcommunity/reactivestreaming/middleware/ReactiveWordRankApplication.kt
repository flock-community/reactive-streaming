package com.github.flockcommunity.reactivestreaming.middleware

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ReactiveWordRankApplication

fun main(args: Array<String>) {
	runApplication<ReactiveWordRankApplication>(*args)
}
