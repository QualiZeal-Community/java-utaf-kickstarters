package com.opencart.pages;

import com.google.inject.Inject;
import com.opencart.exceptions.OperationNotAllowed;
import com.qualizeal.configurations.TestDrivers;
import com.qualizeal.pages.web.Page;
import org.openqa.selenium.By;

public class ProductPage implements Page {

	private final TestDrivers drivers;
	private final By textProductName = By.cssSelector("h1");
	private final By textPrice = By.cssSelector("h2 .price-new");
	private final By textTax = By.xpath("//*[@class='price-new']/ancestor::li/following-sibling::li");
	private final By textDescription = By.cssSelector("p.intro");

	@Inject
	public ProductPage(TestDrivers drivers) {
		this.drivers = drivers;
	}

	public String getProductName() {
		return getDriver().findElement(textProductName).getText();
	}

	public String getProductPrice() {
		return getDriver().findElement(textPrice).getText();
	}

	public String getProductTax() {
		return find(textTax).getText();
	}

	public String getProductDescription() {
		return find(textDescription).getText();
	}

	@Override
	public TestDrivers getAutomationDrivers() {
		return drivers;
	}

	@Override
	public Page navigate() {
		throw new OperationNotAllowed();
	}
}