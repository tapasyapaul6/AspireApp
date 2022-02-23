package com.aspireApp.pageObject;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

import com.aspireApp.utilities.Constants;
import com.aspireApp.utilities.Utils;

public class LoginPage 
{
	/* For Logging */
	private final static Logger logger = Logger.getLogger(LoginPage.class.getName());
	
	public void loginAspireApp(String link,String user,String pwd)
	{
		try
		{
			Utils.getDriver().get(link);
			WebElement email = Utils.waitForElementByXpath(Constants.LOGINPAGE, "email", 10);
			email.clear();
			email.sendKeys(user);
			WebElement password = Utils.waitForElementByXpath(Constants.LOGINPAGE, "password", 10);
			password.clear();
			password.sendKeys(Utils.passworProtect(Utils.loadConfig(Constants.CONFIG).getProperty("aspire_link_password")));
			WebElement submit = Utils.waitForElementByXpath(Constants.LOGINPAGE, "loginId", 10);
			submit.submit();
		}
		catch(Exception e)
		{
			logger.error("Error in loginAspireApp()", e);
		}

	}
}
