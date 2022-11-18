package com.alan.xm.lib.global;

/**
 * @author alan ye
 * created on  2022/11/18
 */
public class LibConfig {

    private boolean isLog;

    public boolean isLog() {
        return isLog;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Builder() {
        }

        private boolean isLog = true;

        public Builder setLog(boolean log) {
            isLog = log;
            return this;
        }

        public LibConfig build() {
            LibConfig config = new LibConfig();
            config.isLog = isLog;
            return config;
        }
    }
}
