package com.swaglab.pages;

import com.google.inject.Inject;
import com.qualizeal.configurations.TestDrivers;
import com.qualizeal.exceptions.NotImplementedException;
import com.qualizeal.pages.web.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductsPage implements Page {
	private final TestDrivers drivers;
	private final By inventoryItems = By.cssSelector(".inventory_item");

	@Inject
	public ProductsPage(TestDrivers drivers) {
		this.drivers = drivers;
	}

	public boolean isDisplayed() {
		try {
			new WebDriverWait(getDriver(), Duration.ofSeconds(90)).until(ExpectedConditions.presenceOfElementLocated(inventoryItems));
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public TestDrivers getAutomationDrivers() {
		return this.drivers;
	}

	@Override
	public Page navigate() {
		throw new NotImplementedException();
	}
}