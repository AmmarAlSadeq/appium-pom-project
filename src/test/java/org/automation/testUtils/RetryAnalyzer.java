package org.automation.testUtils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Retry analyzer that retries failed tests up to 2 times before marking as failed.
 */
public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private static final int MAX_RETRY = 2;

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
