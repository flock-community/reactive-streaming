package com.github.flockcommunity.reactivewordrank.reactivewordrank.utils

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import reactor.netty.tcp.TcpClient
import java.time.Duration
import java.util.concurrent.TimeUnit

internal fun createWebClient(baseUrl: String, connectTimeout: Duration, readTimeout: Duration): WebClient =
        WebClient.builder()
                .baseUrl(baseUrl)
                .clientConnector(
                        ReactorClientHttpConnector(
                                HttpClient.from(
                                        TcpClient.create()
                                                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeout.toMillis().toInt())
                                                .doOnConnected { connection -> connection.addHandlerLast(ReadTimeoutHandler(readTimeout.toMillis(), TimeUnit.MILLISECONDS)) }
                                )
                        )
                )
                .build()