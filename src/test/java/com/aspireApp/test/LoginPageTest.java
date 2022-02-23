package com.aspireApp.test;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.Properties;
import org.apache.commons.validator.UrlValidator;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aspireApp.pageObject.LoginPage;
import com.aspireApp.utilities.Constants;
import com.aspireApp.utilities.Utils;


public class LoginPageTest 
{

	private static LoginPage lPage = null;
	private Properties props = null;
	
	private final static Logger logger = Logger.getLogger(LoginPageTest.class.getName());
	
	@BeforeTest
	public void setUp() {
		try 
		{
			props = Utils.loadConfig(Constants.CONFIG);
			lPage = new LoginPage();
			

		} catch (Exception e) 
		{
			logger.error("Error in setup of LoginPageTest", e);
		}
	}

	@SuppressWarnings("deprecation")
	
	@Test(priority = 1)
	public void aspireAPP_verifyValidURL()
	{
		UrlValidator urlValidator = new UrlValidator();

		assertTrue(urlValidator.isValid(props.getProperty("aspire_link")));
	}
	@Test(priority = 2)
	public void aspireApp_VerifyLoginWithInvalidUserIdPassword()
	{
		lPage.loginAspireApp(props.getProperty("aspire_link"),props.getProperty("aspire_link_invalid_userId"),
				props.getProperty("aspire_link_valid_password"));

		assertNull(Utils.waitForElementByXpath(Constants.APPPAGE, "inventory", 40), "Element Not Found, Login unsuccessfull");
	
	}
	
	@Test(priority = 3)
	public void aspireApp_VerifyLoginWithValidUserIdPassword()
	{
		lPage.loginAspireApp(props.getProperty("aspire_link"),props.getProperty("aspire_link_userId"),
				props.getProperty("aspire_link_password"));
		assertNotNull(Utils.waitForElementByXpath(Constants.APPPAGE, "inventory", 40), "Element Found, Login successfull");
		
	}
	@AfterTest
	public void cleanUp() 
	{
		Utils.closeDriver();
	}

	
	
}
