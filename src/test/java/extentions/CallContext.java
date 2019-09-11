package extentions;

import constants.BasePath;
import io.restassured.authentication.PreemptiveBasicAuthScheme;

public class CallContext {

    private PreemptiveBasicAuthScheme authScheme;
    private BasePath basePath; // Should allow overriding via environment variables

    public CallContext(String username, String password, BasePath basePath) {
        PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
        authScheme.setUserName(username);
        authScheme.setPassword(password);
        this.authScheme = authScheme;
        this.basePath = basePath;
    }

    public CallContext(PreemptiveBasicAuthScheme authScheme, BasePath basePath) {
        this.authScheme = authScheme;
        this.basePath = basePath;
    }

    public CallContext(BasePath basePath) { // If you don't need auth
        this.basePath = basePath;
    }


    public PreemptiveBasicAuthScheme getAuthScheme() {
        return authScheme;
    }

    public CallContext setAuthScheme(PreemptiveBasicAuthScheme authScheme) {
        this.authScheme = authScheme;
        return this;
    }

    public BasePath getBasePath() {
        return basePath;
    }

    public CallContext setBasePath(BasePath basePath) {
        this.basePath = basePath;
        return this;
    }
}
