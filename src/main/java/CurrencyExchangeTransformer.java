import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformer;
import com.github.tomakehurst.wiremock.http.QueryParameter;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;
import org.apache.http.HttpStatus;

import java.util.stream.Stream;

public class CurrencyExchangeTransformer extends ResponseDefinitionTransformer {

    @Override
    public String getName() {
        return "CurrencyExchangeTransformer";
    }

    @Override
    public boolean applyGlobally() {
        return false;
    }

    @Override
    public ResponseDefinition transform(Request request, ResponseDefinition responseDefinition, FileSource files, Parameters parameters) {
        return request.getUrl().contains("/service/convert") ? buildResponse(request) : responseDefinition;
    }

    private ResponseDefinition buildResponse(Request request) {
        QueryParameter fromCurrency = request.queryParameter("from");
        QueryParameter toCurrency = request.queryParameter("to");
        QueryParameter fromAmount = request.queryParameter("from_amount");

        if (Stream.of(fromCurrency, toCurrency, fromAmount)
                .anyMatch(t -> !t.isPresent())) { //For fun
            return new ResponseDefinitionBuilder()
                    .withStatus(HttpStatus.SC_BAD_REQUEST)
                    .withBody("{\"error\":\"err_missing_parameter\"," +
                            "\"message\":\"Required parameter is missing! Please check message again\"}")
                    .build();
        }

        Integer toAmount = 10000; // will always return 100
        String mockResponse = String.format("{\"from\":\"%s\",\"to\":\"%s\",\"from_amount\":%d,\"to_amount\":%d}",
                fromCurrency.firstValue(),
                toCurrency.firstValue(),
                Integer.valueOf(fromAmount.firstValue()),
                toAmount);

        return new ResponseDefinitionBuilder()
                .withStatus(HttpStatus.SC_OK)
                .withBody(mockResponse)
                .build();
    }
}
