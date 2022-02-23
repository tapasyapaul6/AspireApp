package com.aspireApp.pageObject;

import org.openqa.selenium.WebElement;

import com.aspireApp.utilities.Constants;
import com.aspireApp.utilities.Utils;

public class AppPage 
{

	public void goToInventory()
	{
		WebElement  inv = Utils.waitForElementByXpathToBeClickable(Constants.APPPAGE, "inventory", 30);
		inv.click();
	}

	public void goToManuFacturing()
	{
		WebElement  man = Utils.waitForElementByXpathToBeClickable(Constants.APPPAGE, "manufacturing", 30);
		man.click();
	}
}
