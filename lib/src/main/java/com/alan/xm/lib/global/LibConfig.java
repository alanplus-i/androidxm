package com.alan.xm.lib.global;

/**
 * @author alan ye
 * created on  2022/11/18
 */
public class LibConfig {

    private boolean isLog;
    private InitHttpClient initHttpClient;

    public boolean isLog() {
        return isLog;
    }

    public InitHttpClient getInitHttpClient() {
        return initHttpClient;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Builder() {
        }

        private boolean isLog = true;
        private InitHttpClient initHttpClient;

        public Builder setLog(boolean log) {
            isLog = log;
            return this;
        }

        public Builder setInitHttpClient(InitHttpClient initHttpClient) {
            this.initHttpClient = initHttpClient;
            return this;
        }

        public LibConfig build() {
            LibConfig config = new LibConfig();
            config.isLog = isLog;
            config.initHttpClient = initHttpClient;
            return config;
        }
    }
}
