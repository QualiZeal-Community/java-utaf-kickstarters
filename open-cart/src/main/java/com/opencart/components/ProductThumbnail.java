package com.opencart.components;

import com.google.inject.Inject;
import com.qualizeal.components.ButtonGroup;
import com.qualizeal.components.bootstrap.BootstrapButtonGroup;
import com.qualizeal.components.utils.Events;
import com.qualizeal.configurations.TestDrivers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Objects;
import java.util.function.Supplier;

public class ProductThumbnail implements Events {

	private final TestDrivers drivers;
	private Supplier<Boolean> presence;
	private WebElement thumbnailContainer;
	private BootstrapButtonGroup buttonGroup;

	private static final By imageProduct = By.xpath(".//img");
	private static final By linkProductName = By.xpath(".//div[contains(@class, 'description')]//h4/a");
	private static final By textDescription = By.xpath(".//div[contains(@class, 'description')]//p");
	private static final By textPrice = By.xpath(".//div[contains(@class, 'description')]//span[@class='price-new']");
	private static final By textTax = By.xpath(".//div[contains(@class, 'description')]//span[@class='price-tax']");
	private static final By buttonGroupProductButtons = By.xpath(".//div[@class='button-group']");

	@Inject
	public ProductThumbnail(TestDrivers drivers, BootstrapButtonGroup buttonGroup) {
		this.drivers = drivers;
		this.buttonGroup = buttonGroup;
	}

	public ProductThumbnail newInstance(By locator) {
		return newInstance(getDriver().findElement(locator));
	}

	public ProductThumbnail newInstance(WebElement element) {
		return new ProductThumbnail(element, buttonGroup, drivers);
	}

	private ProductThumbnail(WebElement element, BootstrapButtonGroup buttonGroup, TestDrivers drivers) {
		this.drivers = drivers;
		if(Objects.nonNull(element)) {
			presence = () -> !element.findElements(By.xpath(".")).isEmpty();
			scrollTo(element);
			this.thumbnailContainer = element;
			this.buttonGroup = buttonGroup;
		}
	}

	public WebElement getImage() {
		return thumbnailContainer.findElement(imageProduct);
	}

	public WebElement getProductName() {
		return thumbnailContainer.findElement(linkProductName);
	}

	public WebElement getProductDescription() {
		return thumbnailContainer.findElement(textDescription);
	}

	public WebElement getProductPrice() {
		return thumbnailContainer.findElement(textPrice);
	}

	public WebElement getProductTax() {
		return thumbnailContainer.findElement(textTax);
	}

	public void openProductPage() {
		getProductName().click();
	}

	public void addToCart() {
		getButtonGroup().getButtons().get(0).click();
	}

	public void addToWishList() {
		getButtonGroup().getButtons().get(1).click();
	}

	public ButtonGroup getButtonGroup() {
		return buttonGroup.newInstance(buttonGroupProductButtons);
	}

	@Override
	public boolean isPresent() {
		return presence.get();
	}

	@Override
	public WebDriver getDriver() {
		return drivers.getWebDriverInstance();
	}
}