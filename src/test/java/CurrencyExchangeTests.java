import com.fasterxml.jackson.core.type.TypeReference;
import constants.BasePath;
import constants.ServicePath;
import extentions.CallContext;
import extentions.JsonSource;
import helpers.RequestHelper;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import testdata.currencytests.AvailableCurrencyResponse;
import testdata.currencytests.Currency;
import testdata.currencytests.CurrencyConversionResponse;
import testdata.currencytests.CurrencyTestData;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class CurrencyExchangeTests extends BaseExchangeMockTest {

    private Logger log = LogManager.getLogger(CurrencyExchangeTests.class);
    private CallContext context;

    @BeforeClass
    void login() {
        context = new CallContext("dig", "password", BasePath.LOCAL_HOST);
    }

    @Test
    public void canGetAvailableCurrencies() {
        log.info("Test Description: Retrieve all available currencies");

        List<AvailableCurrencyResponse> response = RequestHelper.get(context, ServicePath.GET_AVAILABLE_CURRENCY,
                new TypeReference<List<AvailableCurrencyResponse>>() {
                }.getType(),
                HttpStatus.SC_OK);

        Arrays.stream(Currency.values())
                .forEach(currency -> {
                    AvailableCurrencyResponse expectedCurrency = response.stream()
                            .filter(t -> t.getId().equals(currency.name()))
                            .findAny()
                            .orElse(null);
                    assertThat(expectedCurrency, is(notNullValue()));
                    assertThat(expectedCurrency.getDescription(), is(equalTo(currency.getDescription())));
                });
    }

    @JsonSource(resource = "data/currency_test_data.json", type = CurrencyTestData.class)
    @Test(dataProvider = "getData")
    public void canConvertCurrency(CurrencyTestData data) {
        log.info("Test Description: {}", data.getTestDescription());
        CurrencyConversionResponse response = RequestHelper
                .get(context, ServicePath.GET_CONVERTED_CURRENCY, CurrencyConversionResponse.class, HttpStatus.SC_OK,
                        "from", data.getFrom(),
                        "to", data.getTo(),
                        "from_amount", data.getFromAmount()
                );
        assertThat(response.getFrom(), is(equalTo(data.getFrom())));
        assertThat(response.getTo(), is(equalTo(data.getTo())));
        assertThat(response.getFromAmount(), is(equalTo(data.getFromAmount())));
        assertThat("Converted amount is incorrect", response.getToAmount(), is(equalTo(10000)));
    }
}
