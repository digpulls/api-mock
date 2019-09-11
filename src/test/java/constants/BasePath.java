package constants;

public enum BasePath {

    LOCAL_HOST("http://localhost:8080"),
    DUMMY_REST_HOST("http://dummy.restapiexample.com");

    private String host;

    BasePath(String host) {
        this.host = host;
    }

    public String getHost() {
        return host;
    }
}
