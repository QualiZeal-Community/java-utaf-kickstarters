package com.opencart.pages;

import com.google.inject.Inject;
import com.opencart.components.ProductThumbnail;
import com.opencart.components.SearchField;
import com.qualizeal.components.NavBar;
import com.qualizeal.components.bootstrap.BootstrapNavBar;
import com.qualizeal.configurations.TestDrivers;
import com.qualizeal.pages.web.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class LandingPage implements Page {

	private final TestDrivers drivers;
	private static final String PAGE_URL = "/";

	private final By navbarMenu = By.cssSelector("#menu");
	private final By fieldSearch = By.cssSelector("div[id=search]");
	private final By componentFeaturedProducts = By.cssSelector("#content form .product-thumb");

	@Inject
	SearchField searchField;

	@Inject
	ProductThumbnail productThumbnail;

	@Inject
	BootstrapNavBar navBar;

	@Inject
	public LandingPage(TestDrivers drivers) {
		this.drivers = drivers;
	}

	public SearchField getSearchField() {
		return searchField.newInstance(fieldSearch);
	}

	public NavBar getNavBar() {
		return navBar.newInstance(navbarMenu);
	}

	public List<ProductThumbnail> getFeaturedProducts() {
		return getDriver().findElements(componentFeaturedProducts).stream().map(cmp -> productThumbnail.newInstance(cmp)).collect(Collectors.toList());
	}

	public ProductThumbnail getFeaturedProductByName(String productName) {
		return getFeaturedProducts().stream().filter(p -> p.getProductName().getText().equals(productName)).findFirst().orElse(null);
	}

	@Override
	public TestDrivers getAutomationDrivers() {
		return drivers;
	}

	@Override
	public Page navigate() {
		getDriver().get(getConfiguration().getProperty("baseUrl") + PAGE_URL);
		new WebDriverWait(getDriver(), Duration.ofSeconds(90)).until(ExpectedConditions.presenceOfElementLocated(fieldSearch));
		return this;
	}
}