package com.swaglab.tests.login;

import com.swaglab.annotations.IssueLink;
import com.swaglab.tests.TestConfiguration;
import com.swaglab.util.users.SwagUserType;
import com.swaglab.util.users.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

public class InvalidCredentialsTest extends TestConfiguration {

	public static final String TEST_DATA_FILE = "swag.csv";

	@BeforeClass
	public void navigateToSwagLabs() {
		signInPage.navigate();
	}

	@Test
	@IssueLink("SWAG-17")
	public void shouldNotBeAbleToLoginWithValidUserNameAndInvalidPassword() {
		Map<String, String> testData = getDataByTestId(TEST_DATA_FILE, "shouldNotBeAbleToLoginWithValidUserNameAndInvalidPassword");
		User user = userFactory.getUserByType(SwagUserType.STANDARD);
		signInPage.login(user.getUserName(), RandomStringUtils.randomAlphabetic(10));

		Assert.assertTrue(signInPage.isErrorMessagePresent(), "Error Message is not present");
		Assert.assertEquals(signInPage.getErrorMessageText(), testData.get("ErrorMessage"), "Error message is not as expected");
	}

	@Test
	@IssueLink("SWAG-18")
	public void shouldNotBeAbleToLoginWithInValidUserNameAndValidPassword() {
		Map<String, String> testData = getDataByTestId(TEST_DATA_FILE, "shouldNotBeAbleToLoginWithInValidUserNameAndValidPassword");
		User user = userFactory.getUserByType(SwagUserType.STANDARD);
		signInPage.login(RandomStringUtils.randomAlphabetic(10), user.getPassword());

		Assert.assertTrue(signInPage.isErrorMessagePresent(), "Error Message is not present");
		Assert.assertEquals(signInPage.getErrorMessageText(), testData.get("ErrorMessage"), "Error message is not as expected");
	}

	@Test
	@IssueLink("SWAG-19")
	public void shouldNotBeAbleToLoginWithLockedOutUser() {
		Map<String, String> testData = getDataByTestId(TEST_DATA_FILE, "shouldNotBeAbleToLoginWithLockedOutUser");
		signInPage.login(SwagUserType.LOCKED_OUT);

		Assert.assertTrue(signInPage.isErrorMessagePresent(), "Error Message is not present");
		Assert.assertEquals(signInPage.getErrorMessageText(), testData.get("ErrorMessage"), "Error message is not as expected");
	}

	@Test
	@IssueLink("SWAG-20")
	public void shouldNotBeAbleToLoginWithoutUsernameAndPassword() {
		Map<String, String> testData = getDataByTestId(TEST_DATA_FILE, "shouldNotBeAbleToLoginWithoutUsernameAndPassword");
		signInPage.login("", "");
		Assert.assertTrue(signInPage.isErrorMessagePresent(), "Error Message is not present");
		Assert.assertEquals(signInPage.getErrorMessageText(), testData.get("ErrorMessage"), "Error message is not as expected");
	}
}