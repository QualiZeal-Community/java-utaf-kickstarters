package com.opencart.components;

import com.google.inject.Inject;
import com.qualizeal.components.utils.Events;
import com.qualizeal.configurations.TestDrivers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Objects;
import java.util.function.Supplier;

public class SearchField implements Events {

	private Supplier<Boolean> presence;
	private final TestDrivers drivers;
	private WebElement searchContainer;

	private final By fieldSearchInput = By.xpath(".//input");
	private final By buttonSearch = By.xpath(".//button");

	@Inject
	public SearchField(TestDrivers drivers) {
		this.drivers = drivers;
	}

	public SearchField newInstance(WebElement element) {
		return new SearchField(element, drivers);
	}

	public SearchField newInstance(By locator) {
		return newInstance(getDriver().findElement(locator));
	}

	private SearchField(WebElement searchContainer, TestDrivers drivers) {
		this.drivers = drivers;
		presence = ()-> !searchContainer.findElements(By.xpath(".")).isEmpty();
		if(Objects.nonNull(searchContainer)) {
			scrollTo(searchContainer);
			this.searchContainer = searchContainer;
		}
	}

	public void searchText(String text) {
		WebElement input = searchContainer.findElement(fieldSearchInput);
		input.sendKeys("");
		input.sendKeys(text);
		searchContainer.findElement(buttonSearch).click();
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