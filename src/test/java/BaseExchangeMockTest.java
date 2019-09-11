import com.github.tomakehurst.wiremock.WireMockServer;
import constants.ServicePath;
import extentions.BaseTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.matching;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public abstract class BaseExchangeMockTest extends BaseTest {

    private WireMockServer wireMockServer;

    @BeforeClass
    public void setup() {
        wireMockServer = new WireMockServer(
                wireMockConfig()
                        .extensions(new CurrencyExchangeTransformer()));
        wireMockServer.start();
        setupStub();
    }

    @AfterClass
    public void teardown() {
        wireMockServer.stop();
    }

    private void setupStub() {
        stubFor(get(urlEqualTo(ServicePath.GET_AVAILABLE_CURRENCY))
                .withBasicAuth("dig", "password")
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBodyFile("server/available_currencies.json")));

        stubFor(get(urlPathEqualTo(ServicePath.GET_CONVERTED_CURRENCY))
                .withBasicAuth("dig", "password")
                .withQueryParam("from", matching("^(.*[A-Z]){3}.*$")) // Only uppercase character with 3 letter
                /*
                    Let's block this at the Transformer layer for fun!
                */
//                .withQueryParam("to", matching("^(.*[A-Z]){3}.*$"))
                .withQueryParam("from_amount", matching("^\\d+$")) //Only Integers
                .willReturn(aResponse()
                        .withTransformers("CurrencyExchangeTransformer")));
    }
}
