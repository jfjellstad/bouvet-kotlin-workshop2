package no.bouvet.challenge03

import kotlinx.coroutines.flow.Flow
import org.springframework.web.bind.annotation.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerSentEvent

@RestController
class StockTraderController(
    val stockRepository: StockRepository,
    exchangeServiceNasdaq: ExchangeServiceNasdaq,
    exchangeServiceEuronext: ExchangeServiceEuronext,
    exchangeServiceSix: ExchangeServiceSix
) {

    val exchanges = listOf(exchangeServiceEuronext,exchangeServiceNasdaq, exchangeServiceSix)

    /**
     * Challenge 3 - Part 2 - Exercise A
     * Take a look at the reactive @see [JStockTraderController.getStock] Java implementation,
     * which makes use of the reactive [JStockRepository] to fetch a stock by id from the database.
     * In this exercise you have to convert the Java implementation to its Coroutine counterpart
     * by using the injected [stockRepository]. Remember not to use the Mono abstraction at all.
     * Make the corresponding test in @see [no.bouvet.challenge03.Coroutines02ControllerTest] pass.
     */
    @GetMapping("/stocks/{stock-id}")
    @ResponseBody
    suspend fun getStock(@PathVariable("stock-id") id: Long = 0): Stock? = TODO("implement")


    /**
     * Challenge 3 - Part 2 - Exercise B
     * Take a look at the reactive @see [JStockTraderController.getStock] Java implementation,
     * which makes use of the reactive [JStockRepository] to fetch a stock by symbol from the database.
     * In this exercise you have to convert the Java implementation to its Coroutine counterpart
     * by using the injected [stockRepository]. Remember not to use the Mono abstraction at all.
     * Make the corresponding test in @see [no.bouvet.challenge03.Coroutines02ControllerTest] pass.
     */
    @GetMapping("/stock")
    @ResponseBody
    suspend fun stockBySymbol(@RequestParam("symbol") symbol: String): Stock? = TODO("implement")


    /**
     * Challenge 3 - Part 2 - Exercise C
     * Take a look at the reactive @see [JStockTraderController.upsertStock] Java implementation,
     * which makes use of the reactive [JStockRepository] to upsert a stock in the database.
     * In this exercise you have to convert the Java implementation to its Coroutine counterpart
     * by using the injected [stockRepository]. Remember not to use the Mono abstraction at all.
     * Make the corresponding test in @see [no.bouvet.challenge03.Coroutines02ControllerTest] pass.
     *
     * (In Challenge 3 - Part 3 - Exercise C you will have to extend this method)
     */
    @PostMapping("/stocks")
    @ResponseBody
    suspend fun upsertStock(@RequestBody stock: Stock): Stock {
        return TODO("implement")
    }

    /**
     * Challenge 3 - Part 2 - Exercise D
     * Take a look at the reactive @see [JStockTraderController.bestQuote] Java implementation,
     * which makes use of the reactive JExchangeServices to fetch several quotes for a stock and
     * filters out the one with the lowest price.
     * In this exercise you have to convert the Java implementation to its Coroutine counterpart
     * by using the injected exchanges. Remember not to use the Mono abstraction at all.
     * Tip: to call the different exchanges in parallel make use of [kotlinx.coroutines.async] / [kotlinx.coroutines.Deferred.await]. Don't forget
     * to use the helper method [kotlinx.coroutines.coroutineScope] for async to work.
     * Make the corresponding test in @see [no.bouvet.challenge03.Coroutines02ControllerTest] pass.
     */
    @GetMapping("/stocks/quote")
    @ResponseBody
    suspend fun bestQuote(@RequestParam("symbol") symbol: String): StockQuoteDto? = TODO("implement")

    //========================================================================================================

    /**
     * Challenge 3 - Part 3 - Exercise A
     * Take a look at the reactive @see [JStockTraderController.getStocks] Java implementation,
     * which makes use of the reactive [JStockRepository] to fetch all stocks as a Flux.
     * In this exercise you have to convert the Java implementation to its Coroutine counterpart
     * by using the injected [stockRepository]. Remember to use a Flow and not the Flux abstraction at all.
     * Make the corresponding test in @see [no.bouvet.challenge03.Coroutines03FlowControllerTest] pass.
     */
    @GetMapping("/stocks")
    @ResponseBody
    fun getStocks(): Flow<Stock> = TODO("implement")


    /**
     * Challenge 3 - Part 3 - Exercise B
     * In this exercise you will apply (almost) the same logic as in Challenge 4 Exercise C, where you implemented
     * a flow that was polling a resources in intervals of 200ms and returned newly added items. Now
     * you will take this implementation to the next level: you will poll a 'real' database resource and
     * return newly added [Stock]s in a [ServerSentEvent]<Stock> stream, so that potentially the whole world
     * can consume your flow.
     * Therefore, implement an infinite flow that initially fetches all Stocks via [StockRepository.findById_GreaterThan]
     * and then keeps on polling for new Stocks in intervals of 200ms
     * Hint 0: re-visit @see [no.bouvet.challenge02.NewsFeedService.pollingNewsFeedFlow]. The implementation is almost identical
     * Hint 1: Since [StockRepository.findById_GreaterThan] will return a Flow that you have to collect
     * in the flow that will serve the SSE items. So you will get a Flow within a Flow which is perfectly fine.
     * Also use a var latestId in the flow builder to keep track of the latestId that was fetched, which is perfectly thread-safe.
     * Hint 2: It might help defining a nested method (e.g.: tailrec suspend fun fetch()) that recursively fetches
     * [Stock] based on the latestId it accesses in the surrounding scope. Once fetched it also delays for 200ms before recursing.
     */
    @GetMapping("/stocks/sse-polling", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    @ResponseBody
    suspend fun pollingStocksFlow(@RequestParam("offset") offsetId: Long = 0): Flow<ServerSentEvent<Stock>> {
        var latestId = offsetId
        tailrec suspend fun fetch() {
            TODO("implement")
        }
        fetch()
        return  TODO("implement return flow")

    }



    data class StockChangedNotification(val stock: Stock)
    private val sharedFlow = MutableSharedFlow<StockChangedNotification>()

    /**
     * Challenge 3 - Part 3 - Exercise C
     * In this exercise you will apply (almost) the same logic as in Challenge 2 Exercise E, where you implemented
     * a flow that was first fetching all items of a resources and then waited for a notification being emitted by the SharedFlow to fetch and return newly added items.
     * Also, here the implementation is almost identical to the previous one with the following differences:
     * - First extend the above @see [StockTraderController.upsertStock] method that will emit a [StockChangedNotification] to the predefined [MutableSharedFlow].
     * - Second instead of polling for new items, let the flow wait for a [StockChangedNotification] (via the [Flow.collect] method ) and then initiate a new fetch
     * Hint 1: re-visit @see [no.bouvet.challenge02.NewsFeedService.sharedFlowTriggeredNewsFeedFlow]. The implementation is almost identical.
     */
    @GetMapping("/stocks/sse-shared-flow-triggered", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    @ResponseBody
    fun sharedFlowTriggeredNewsFeedFlow(@RequestParam("offset") offsetId: Long = 0): Flow<ServerSentEvent<Stock>> = TODO("implement")

}
