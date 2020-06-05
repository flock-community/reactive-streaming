package com.github.flockcommunity.reactivewordrank.reactivewordrank.utils

import org.springframework.web.reactive.function.client.ClientResponse
import java.lang.RuntimeException

data class HttpClientException(override val message: String, val response: ClientResponse?) : RuntimeException(message)
data class HttpServerException(override val message: String, val response: ClientResponse?) : RuntimeException(message)

