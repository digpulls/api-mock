package testdata.currencytests;

public enum Currency {

    SEK("SEK Sweden, kronor"),
    ATS("ATS Austria, shilling"),
    AUD("AUD Australian, dollar");

    private String description;

    Currency(String description) {
        this.description = description;

    }

    public String getDescription() {
        return description;
    }
}
