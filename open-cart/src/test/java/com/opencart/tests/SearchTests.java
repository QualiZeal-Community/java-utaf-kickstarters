package com.opencart.tests;

import com.opencart.TestConfiguration;
import com.opencart.modules.DataModule;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Map;

@Guice(modules = DataModule.class)
public class SearchTests extends TestConfiguration {
	public static final String TEST_DATA_FILE = "opencart.csv";

	@BeforeClass(alwaysRun = true)
	public void navigateToOpenMartApp() {
		landingPage.navigate();
	}

	@Test(groups = "SMOKE")
	public void shouldBeAbleToSearchForProducts() {
		SoftAssert softAssert = new SoftAssert();
		Map<String, String> testData = getDataByTestId(TEST_DATA_FILE, "shouldOpenProductPage");
		String productName = testData.get("Product");

		landingPage.getSearchField().searchText(productName);
		softAssert.assertNotNull(landingPage.getFeaturedProductByName(productName), "Search is not yielding results");
		softAssert.assertAll();
	}
}