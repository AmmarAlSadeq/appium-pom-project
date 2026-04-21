package org.automation.testUtils;

import org.automation.utils.ConfigReader;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Retry analyzer that retries failed tests up to a configurable max count.
 * Reads retryCount from config.properties via ConfigReader.
 */
public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private static final int MAX_RETRY = loadMaxRetry();

    private static int loadMaxRetry() {
        try {
            return Integer.parseInt(ConfigReader.get("retryCount"));
        } catch (Exception e) {
            return 2;
        }
    }

    /**
     * Determines whether a failed test should be retried.
     *
     * @param result The test result of the failed test.
     * @return true if retry count has not reached MAX_RETRY, false otherwise.
     */
    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < MAX_RETRY) {
            retryCount++;
            return true;
        }
        return false;
    }
}
