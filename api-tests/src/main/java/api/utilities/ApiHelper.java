package api.utilities;

import common.config.ConfigReader;


public class ApiHelper {
    public static String getBaseApiUrl() {
        return ConfigReader.getProperty("api.base.url.test");
    }

    public static String getApiAuthKey() {
        return ConfigReader.getProperty("api.auth.key");
    }
}
