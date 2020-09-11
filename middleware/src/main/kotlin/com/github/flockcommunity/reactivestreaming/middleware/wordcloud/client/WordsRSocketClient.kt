package com.github.flockcommunity.reactivestreaming.middleware.wordcloud.client

import io.rsocket.RSocket
import io.rsocket.RSocketFactory
import io.rsocket.frame.decoder.PayloadDecoder
import io.rsocket.metadata.WellKnownMimeType
import io.rsocket.transport.netty.client.TcpClientTransport
import org.slf4j.LoggerFactory.getLogger
import org.springframework.boot.rsocket.server.ServerRSocketFactoryProcessor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.messaging.rsocket.RSocketStrategies
import org.springframework.stereotype.Component
import org.springframework.util.MimeTypeUtils
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.extra.retry.retryExponentialBackoff
import java.time.Duration


@Component
internal class WordsRSocketClient(
        private val rSocketRequester: Mono<RSocketRequester>,
        rSocketRequesterBuilder: RSocketRequester.Builder

) {

//    private val rSocketRequesterQuickAndDirty: RSocketRequester = rSocketRequesterBuilder
//            .connectTcp("192.168.1.54", 8083)
//            .block()!!

//    fun getWordss(): Flux<WordDTO> {
//        return rSocketRequesterQuickAndDirty
//                .route("words")
//                .retrieveFlux(WordDTO::class.java)
//                .log()
//    }

    fun getWords(): Flux<WordDTO> {
        return rSocketRequester.flatMapMany {
            it.route("words").retrieveFlux(WordDTO::class.java).log()
        }
    }

}


@Configuration
class ClientConfiguration {

    private val log = getLogger(javaClass)

//    val rSocket: Mono<RSocket> =
//            RSocketFactory
//                    .connect()
//                    .dataMimeType(MimeTypeUtils.APPLICATION_JSON_VALUE)
//                    .metadataMimeType(WellKnownMimeType.MESSAGE_RSOCKET_COMPOSITE_METADATA.string)
//                    .frameDecoder(PayloadDecoder.ZERO_COPY)
//                    .transport(TcpClientTransport.create(
//                            "192.168.1.54",
//                            8083
//                    ))
//                    .start()
//                    .log()
//                    .retryBackoff(100, Duration.ofSeconds(1), Duration.ofSeconds(180))
//

//    @Bean
//    fun rSocketRequester(rSocketStrategies: RSocketStrategies): Mono<RSocketRequester> =
//            rSocket.map {
//                RSocketRequester.wrap(
//                        it,
//                        MimeTypeUtils.APPLICATION_JSON,
//                        MimeTypeUtils.parseMimeType(WellKnownMimeType.MESSAGE_RSOCKET_COMPOSITE_METADATA.string),
//                        rSocketStrategies
//                )
//            }
//            .cache().also{it.subscribe()}

    @Bean
    fun rSocketRequester(rSocketStrategies: RSocketStrategies): Mono<RSocketRequester> = Mono.empty()


    @Bean
    fun serverRSocketFactoryProcessor(): ServerRSocketFactoryProcessor? {
        return ServerRSocketFactoryProcessor { it.resume() }
    }
}
