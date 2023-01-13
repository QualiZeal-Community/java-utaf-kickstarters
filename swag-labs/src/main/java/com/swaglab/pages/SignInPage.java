package com.swaglab.pages;

import com.google.inject.Inject;
import com.qualizeal.configurations.TestDrivers;
import com.qualizeal.pages.web.Page;
import com.swaglab.util.users.SwagUserType;
import com.swaglab.util.users.User;
import com.swaglab.util.users.UserFactory;
import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SignInPage implements Page {

	private final TestDrivers drivers;
	UserFactory userFactory;

	public static final String PAGE_URL = "/";
	private final By fieldUserName = By.cssSelector("#user-name");
	private final By fieldPassword = By.cssSelector("#password");
	private final By buttonLogIn = By.cssSelector("#login-button");
	private final By textErrorMessage = By.cssSelector("h3[data-test='error']");

	@Inject
	public SignInPage(TestDrivers drivers, UserFactory userFactory) {
		this.drivers = drivers;
		this.userFactory = userFactory;
	}

	public void login(SwagUserType swagUserType) {
		User user = userFactory.getUserByType(swagUserType);
		login(user.getUserName(), user.getPassword());
	}

	public void login(String username, String password) {
		clearAndType(fieldUserName, username);
		clearAndType(fieldPassword, password);
		click(buttonLogIn);
		delayABit();
	}

	public boolean isErrorMessagePresent() {
		try {
			waitUntil(ExpectedConditions.presenceOfElementLocated(textErrorMessage));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public String getErrorMessageText() {
		return find(textErrorMessage).getText();
	}

	@Override
	public TestDrivers getAutomationDrivers() {
		return drivers;
	}

	@Override
	public Page navigate() {
		getDriver().get(getConfiguration().getProperty("baseUrl") + PAGE_URL);
		waitUntil(ExpectedConditions.presenceOfElementLocated(fieldUserName));
		return this;
	}

	private <T> T waitUntil(ExpectedCondition<T> condition) {
		return new WebDriverWait(getDriver(), Duration.ofSeconds(90)).until(condition);
	}

	@SneakyThrows
	private void delayABit() {
		Thread.sleep(2000);
	}
}