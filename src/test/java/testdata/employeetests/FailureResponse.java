package testdata.employeetests;

public class FailureResponse {
    private Error error;

    public Error getError() {
        return error;
    }

    public static class Error {
        private String text;

        public String getText() {
            return text;
        }
    }
}
