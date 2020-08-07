package ui.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.*;
import org.testng.annotations.Parameters;
import org.testng.xml.XmlSuite;
import ui.utils.CommonDriverTest;

import java.util.*;


public class ExtentReport implements IReporter {

	static String projectPath = System.getProperty("user.dir");
	private static final String OUTPUT_FOLDER = "\\Report\\";
	private static final String FILE_NAME = "ExecutionReport.html";

	private ExtentReports extent;
	static ExtentTest test;

	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		try {
			init();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		for (ISuite suite : suites) {
			Map<String, ISuiteResult> result = suite.getResults();

			for (ISuiteResult r : result.values()) {
				ITestContext context = r.getTestContext();

				try {
					buildTest(context.getPassedTests(), Status.PASS);
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					buildTest(context.getFailedTests(), Status.FAIL);
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					buildTest(context.getSkippedTests(), Status.SKIP);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}

		for (String s : Reporter.getOutput()) {
			extent.setTestRunnerOutput(s);
		}
		extent.flush();

	}

	private void init() throws Exception {

		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(projectPath + OUTPUT_FOLDER + FILE_NAME);

		// ExtentSparkReporter htmlReporter = new ExtentSparkReporter (projectPath +
		// OUTPUT_FOLDER + FILE_NAME); ////This code works for V4

		htmlReporter.config().setDocumentTitle("Execution  Report");
		htmlReporter.config().setReportName("Execution Report");
		htmlReporter.config().setTimeStampFormat("MMM dd, yyyy hh:mm:ss a");

		// htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM); //This

		htmlReporter.config().setTheme(Theme.DARK);
		htmlReporter.config().setProtocol(Protocol.HTTPS);

		// Remove Timeline
		String css = "div#timeline-chart { display: none; }";
		String css1 = "div#charts-row>div:last-child { display: none; }";
		htmlReporter.config().setCSS(css);
		htmlReporter.config().setCSS(css1);
		htmlReporter.config().setJS("document.querySelector('a[view=dashboard-view]').click()");


		extent = new ExtentReports();
		extent.setSystemInfo("Author", "QA");
		extent.setSystemInfo("Platform", System.getProperty("os.name"));
		extent.setSystemInfo("Machine's User", System.getProperty("user.name"));
		extent.setSystemInfo("Java Version", System.getProperty("java.version"));
		extent.setSystemInfo("Environment", " QA ");

		extent.attachReporter(htmlReporter);
		extent.setReportUsesManualConfiguration(true);

	}

	private void buildTest(IResultMap tests, Status status) throws Exception {

		if (tests.size() > 0) {

			List<ITestResult> resultList = new LinkedList<ITestResult>(tests.getAllResults());

			class ResultComparator implements Comparator<ITestResult> {
				public int compare(ITestResult r1, ITestResult r2) {
					return getTime(r1.getStartMillis()).compareTo(getTime(r2.getStartMillis()));
				}
			}

			Collections.sort(resultList, new ResultComparator());

			for (ITestResult result : resultList) {


				String value = result.getTestContext().getCurrentXmlTest().getParameter("browserType");
				Object[] value1=result.getParameters();
				if(value1.length>0){
					for(Object ob:value1){
						test = extent.createTest(result.getMethod().getMethodName()+":Data Set: "+ob.toString()
								+":: Browser : "+value);
					}
				}else {
					test = extent.createTest(result.getMethod().getMethodName()+":: Browser : "+value);
				}


				for (String group : result.getMethod().getGroups()) {

					test.assignCategory(group);

				}

				if (result.getStatus() == ITestResult.FAILURE) {
					test.fail(MarkupHelper.createLabel(result.getName() + " Test Case is FAILED", ExtentColor.RED));
					test.getModel().setStartTime(getTime(result.getStartMillis()));
					test.getModel().setEndTime(getTime(result.getEndMillis()));

					String image="..\\FailedTestsScreenshots\\"+result.getName()+".png";

                    test.fail(result.getThrowable());

                    test.fail("To view, please click on failed Screenshot:", MediaEntityBuilder.createScreenCaptureFromPath(image).build());
					//test.getModel().getLogContext().get(stepNum++).setTimestamp(step.getEndTime());

				} else if (result.getStatus() == ITestResult.SKIP) {
					test.skip(MarkupHelper.createLabel(result.getName() + " Test Case is SKIPPED", ExtentColor.YELLOW));
					test.getModel().setStartTime(getTime(result.getStartMillis()));
					test.getModel().setEndTime(getTime(result.getEndMillis()));
					test.skip(result.getThrowable());
				} else if (result.getStatus() == ITestResult.SUCCESS) {
					test.pass(MarkupHelper.createLabel(result.getMethod().getDescription() + " got "
							+ status.toString().toLowerCase() + "ed", ExtentColor.GREEN));
					test.getModel().setStartTime(getTime(result.getStartMillis()));
					test.getModel().setEndTime(getTime(result.getEndMillis()));

				}

			}
		}

	}

	private Date getTime(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}

}