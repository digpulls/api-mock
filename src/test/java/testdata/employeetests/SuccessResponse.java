package testdata.employeetests;

public class SuccessResponse {

    private Success success;

    public Success getSuccess() {
        return success;
    }

    public static class Success {
        private String text;

        public String getText() {
            return text;
        }
    }
}
