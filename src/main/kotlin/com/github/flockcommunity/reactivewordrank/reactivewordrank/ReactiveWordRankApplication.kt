package com.github.flockcommunity.reactivewordrank.reactivewordrank

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ReactiveWordRankApplication

fun main(args: Array<String>) {
	runApplication<ReactiveWordRankApplication>(*args)
}
