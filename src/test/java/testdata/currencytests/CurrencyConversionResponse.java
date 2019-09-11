package testdata.currencytests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CurrencyConversionResponse {

    private String from;
    private String to;

    @JsonProperty("from_amount") // No messing around with Java naming convention
    private Integer fromAmount;

    @JsonProperty("to_amount")
    private Integer toAmount;

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public Integer getFromAmount() { // Should be Long instead
        return fromAmount;
    }

    public Integer getToAmount() {
        return toAmount;
    }
}
