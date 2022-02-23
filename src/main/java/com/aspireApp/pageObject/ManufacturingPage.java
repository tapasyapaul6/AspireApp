package com.aspireApp.pageObject;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.aspireApp.utilities.Constants;
import com.aspireApp.utilities.Utils;

public class ManufacturingPage 
{
	/* For Logging */
	private final static Logger logger = Logger.getLogger(ManufacturingPage.class.getName());
	
	public void addManufacturingItem(String prodName,String quantity)
	{
		try 
		{
			
			new AppPage().goToManuFacturing();
			WebElement create = Utils.waitForElementByXpathToBeClickable(Constants.MANUFACTUREPAGE, "createManufacture", 10);
			create.click();

			WebElement qty = Utils.waitForElementByXpath(Constants.MANUFACTUREPAGE, "manufactureQuantity", 20);

			String s = Keys.chord(Keys.CONTROL, "a");
			qty.sendKeys(s);

			qty.sendKeys(Keys.DELETE);
			qty.sendKeys(quantity);
			
			WebElement prod = Utils.waitForElementByXpath(Constants.MANUFACTUREPAGE, "manufactureProduct", 10);
			prod.clear();
			prod.sendKeys(prodName);

			TimeUnit.SECONDS.sleep(1);
			WebElement prodN = Utils.getDriver().findElement(By.xpath("//*[. = '"+prodName+"']"));
			Actions actions = new Actions(Utils.getDriver());
			actions.moveToElement(prodN).click().build().perform();
			
			

			WebElement confirm = Utils.waitForElementByXpathToBeClickable(Constants.MANUFACTUREPAGE, "confirm", 10);
			confirm.click();
			WebElement markAsDone = Utils.waitForElementByXpathToBeClickable(Constants.MANUFACTUREPAGE, "markAsDone", 10);
			markAsDone.click();
			
			WebElement alertOk = Utils.waitForElementByXpathToBeClickable(Constants.MANUFACTUREPAGE, "alertOk", 10);
			alertOk.click();
			
			WebElement alertApply = Utils.waitForElementByXpathToBeClickable(Constants.MANUFACTUREPAGE, "alertApply", 10);
			alertApply.click();
			
			WebElement save = Utils.waitForElementByXpathToBeClickable(Constants.MANUFACTUREPAGE, "save", 10);
			save.click();

			TimeUnit.SECONDS.sleep(2);
			
			WebElement app = Utils.waitForElementByXpathToBeClickable(Constants.MANUFACTUREPAGE, "appLocator", 30);
			app.click();
			TimeUnit.SECONDS.sleep(1);
	
		} catch (Exception e) {

			logger.error("Error in addManufacturingItem()", e);
		}

	

	}
}
