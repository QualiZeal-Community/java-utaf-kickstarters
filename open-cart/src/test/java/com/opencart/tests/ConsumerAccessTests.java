package com.opencart.tests;

import com.opencart.TestConfiguration;
import com.opencart.components.ProductThumbnail;
import com.opencart.modules.DataModule;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Guice(modules = DataModule.class)
public class ConsumerAccessTests extends TestConfiguration {

	public static final String TEST_DATA_FILE = "opencart.csv";

	@BeforeClass(alwaysRun = true)
	public void navigateToOpenMartApp() {
		landingPage.navigate();
	}

	@Test(groups = "SMOKE")
	public void shouldDisplayTheTopNavbar() {
		Map<String, String> testData = getDataByTestId(TEST_DATA_FILE, "shouldDisplayTheTopNavbar");

		List<String> navLinks = landingPage.getNavBar().getNavLinks().stream().map(WebElement::getText).filter(t -> !t.equalsIgnoreCase("")).collect(Collectors.toList());
		List<String> expectedNavLinks = Arrays.stream(testData.get("NavbarItems").split(",")).collect(Collectors.toList());
		Assert.assertTrue(navLinks.containsAll(expectedNavLinks), "All Nav links are not present in the Navbar");
	}

	@Test(groups = "SMOKE")
	public void shouldDisplayedFeaturedProducts() {
		SoftAssert softAssert = new SoftAssert();
		Map<String, String> testData = getDataByTestId(TEST_DATA_FILE, "shouldDisplayedFeaturedProducts");

		List<ProductThumbnail> featuredProducts = landingPage.getFeaturedProducts();
		softAssert.assertEquals(featuredProducts.size(), 4, "Four products are not featured in the app");
		softAssert.assertNotNull(landingPage.getFeaturedProductByName(testData.get("Product")), "IPhone is not present in the featured products");
		softAssert.assertAll();
	}

	@Test(groups = "SMOKE")
	public void shouldOpenProductPageWithMatchingDetails() {
		SoftAssert softAssert = new SoftAssert();
		Map<String, String> testData = getDataByTestId(TEST_DATA_FILE, "shouldOpenProductPage");
		String productName = testData.get("Product");

		ProductThumbnail product = landingPage.getFeaturedProductByName(productName);
		String price = product.getProductPrice().getText();
		String tax = product.getProductTax().getText();

		product.openProductPage();
		softAssert.assertEquals(productPage.getProductName(), productName, "Name is not matching in the product page");
		softAssert.assertEquals(productPage.getProductTax(), tax, "Tax is not matching in the product page");
		softAssert.assertEquals(productPage.getProductPrice(), price, "Price is not matching in the product page");
		softAssert.assertAll();
	}
}