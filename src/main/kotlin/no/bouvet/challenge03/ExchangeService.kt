package no.bouvet.challenge03

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

abstract class ExchangeService(val baseUrl: String, val exchangeId:String) {
    private val client by lazy { WebClient.create(baseUrl) }

    /**
     * Challenge 3 - Part 1 - Exercise B
     * Take a look at the reactive @see JExchangeService#getStockQuote Java implementation,
     * which makes use of Mono<T> as reactive abstraction for a single result fetched by a remote service.
     * In this exercise you have to implement the getStockQuote method in a reactive way
     * using Coroutines. So you should create a suspend method that does not return a Mono<T> but a T right away.
     * Tip: use the WebClient to do the REST call. Convert the resulting Mono<T> with the
     * glue method awaitBody<T> into a suspended method call.
     * Make the corresponding test in @see Coroutines01ServiceRepositoryTest pass.
     */
    //TODO: implement the getStockQuote method

}

@Component
class ExchangeServiceNasdaq(@Value("\${remote.service.url}") baseUrl: String) : ExchangeService(baseUrl, "nasdaq")

@Component
class ExchangeServiceEuronext(@Value("\${remote.service.url}") baseUrl: String) : ExchangeService(baseUrl, "euronext")

@Component
class ExchangeServiceSix(@Value("\${remote.service.url}") baseUrl: String) : ExchangeService(baseUrl, "six")