package com.swaglab.tests;

import com.google.inject.Inject;
import com.qualizeal.configurations.Reporter;
import com.qualizeal.configurations.TestDrivers;
import com.qualizeal.dao.Data;
import com.qualizeal.dao.DataFilter;
import com.qualizeal.dao.FileType;
import com.swaglab.modules.DataModule;
import com.swaglab.modules.SwagLabAutomationModule;
import com.swaglab.pages.ProductsPage;
import com.swaglab.pages.SignInPage;
import com.swaglab.util.users.UserFactory;
import lombok.SneakyThrows;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.MDC;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.logging.Level;

@Guice(modules = {SwagLabAutomationModule.class, DataModule.class})
public class TestConfiguration {

	@Inject
	protected TestDrivers testDrivers;

	@Inject
	Reporter reporter;

	@Inject
	public Data csvData;

	@Inject
	public UserFactory userFactory;

	@Inject
	public SignInPage signInPage;

	@Inject
	public ProductsPage productsPage;

	@BeforeClass(alwaysRun = true)
	public void initDriver() {
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setHeadless(true);
		chromeOptions.addArguments("start-maximized");
		testDrivers.setWebDriverInstance(new ChromeDriver(chromeOptions));
	}

	@BeforeMethod(alwaysRun = true)
	public void startTest(Method method) {
		reporter.startTest(method.getName(), this.getClass().getName());
		System.setProperty("webdriver.chrome.silentOutput", "true");
		java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
	}

	@AfterMethod(alwaysRun = true)
	public void clearTestParameters(ITestResult iTestResult) {
		MDC.remove("test.name");
		reporter.endTest();
	}

	@AfterClass(alwaysRun = true)
	public void tearDownDrivers() {
		testDrivers.quitWebDriver();
	}

	@SneakyThrows
	public Map<String, String> getDataByTestId(String file, String testId) {
		DataFilter filter = new DataFilter(FileType.CSV, file).addFilter("TestID", testId);
		return csvData.readData().filter(filter);
	}
}
