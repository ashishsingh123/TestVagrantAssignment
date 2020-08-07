package ui.report;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryFailedTCs implements IRetryAnalyzer {

    private int retryCount = 0;

  // Set Maximum count to run Failed Test cases
    private int maxRetryCount = 1;

   /* This method will be called everytime a test fails.
     It will return True if a test fails and need to be retried,
     else it returns False */

    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {
            retryCount++;
            System.out.println("Retrying to run Failed tcs: " + retryCount);
            return true;
        }
        return false;
    }

}