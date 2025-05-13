package no.bouvet.challenge03

import kotlinx.coroutines.runBlocking
import no.bouvet.uitls.prepareTestData
import no.bouvet.uitls.reAssignVal
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.junit.jupiter.SpringExtension


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
class Coroutines01ServiceRepositoryTest @Autowired constructor(val stockRepository: StockRepository,
                                                               val nasdaqService: ExchangeServiceNasdaq,
                                                               @LocalServerPort val localPort: Int) {

    @BeforeEach
    fun setup():Unit = runBlocking {
        stockRepository.prepareTestData()
        nasdaqService.reAssignVal(ExchangeServiceNasdaq::baseUrl) { it.replace("8081", localPort.toString()) }
    }

    /**
     * Exercise A:
     * For instructions go to @see [StockRepository]
     */
    @Test
    fun `Exercise A should get stock by symbol from Coroutine enabled StocksRepository`(): Unit = runBlocking {
        TODO("uncomment")
//        val foundStock = stocksRepository.findBySymbol(GOOG)
//        foundStock?.symbol shouldBe GOOG

    }

    /**
     * Exercise B:
     * For instructions go to @see [ExchangeService]
     */
    @Test
    fun `Exercise B should get stock quote from ExchangeService`(): Unit = runBlocking {
        TODO("uncomment")
//        val stockQuote = nasdaqService.getStockQuote(GOOG)
//        stockQuote.symbol shouldBe GOOG
    }


    companion object {
        const val GOOG = "GOOG"
    }
}