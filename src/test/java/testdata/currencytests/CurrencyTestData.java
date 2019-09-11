package testdata.currencytests;

public class CurrencyTestData {

    private String testDescription;
    private Integer fromAmount;
    private String from;
    private String to;

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public Integer getFromAmount() {
        return fromAmount;
    }

    public String getTestDescription() {
        return testDescription;
    }

    @Override
    public String toString() {
        return testDescription;
    } // This will be used to describe the parameterized tests

}
