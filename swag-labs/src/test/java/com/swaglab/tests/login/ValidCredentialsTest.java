package com.swaglab.tests.login;

import com.swaglab.annotations.IssueLink;
import com.swaglab.tests.TestConfiguration;
import com.swaglab.util.users.SwagUserType;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ValidCredentialsTest extends TestConfiguration {

	@BeforeClass
	public void navigateToSwagLabs() {
		signInPage.navigate();
	}

	@Test
	@IssueLink("SWAG-16")
	public void shouldBeAbleToLoginToSwagLabSite() {
		signInPage.login(SwagUserType.STANDARD);
		Assert.assertTrue(productsPage.isDisplayed(), "Product Page is not displayed");
	}

}