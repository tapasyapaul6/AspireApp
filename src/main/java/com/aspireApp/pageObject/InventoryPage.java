package com.aspireApp.pageObject;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import com.aspireApp.utilities.Constants;
import com.aspireApp.utilities.Utils;

public class InventoryPage 
{
	/* For Logging */
	private final static Logger logger = Logger.getLogger(InventoryPage.class.getName());
	public void addUpdateInventory(String name,String quantity)
	{
		try
		{
			new AppPage().goToInventory();

			WebElement prod = Utils.waitForElementByXpathToBeClickable(Constants.INVENTORYPAGE, "productButton", 10);
			prod.click();
			WebElement prods = Utils.waitForElementByXpath(Constants.INVENTORYPAGE, "productsOption", 20);
			prods.click();
			WebElement create = Utils.waitForElementByXpathToBeClickable(Constants.INVENTORYPAGE, "createProduct", 10);
			create.click();
			WebElement prodName = Utils.waitForElementByXpath(Constants.INVENTORYPAGE, "productName", 20);
			prodName.clear();
			prodName.sendKeys(name);

			WebElement updateQty = Utils.waitForElementByXpathToBeClickable(Constants.INVENTORYPAGE, "updateQuantity", 10);
			updateQty.click();
			WebElement createUpdateQty = Utils.waitForElementByXpathToBeClickable(Constants.INVENTORYPAGE, "createUpdateQty", 10);
			createUpdateQty.click();



			WebElement qty = Utils.waitForElementByXpath(Constants.INVENTORYPAGE, "countedQty", 20);
			qty.clear();
			qty.sendKeys(quantity);
			WebElement saveUpdateQty = Utils.waitForElementByXpathToBeClickable(Constants.INVENTORYPAGE, "saveQty", 10);
			saveUpdateQty.click();

			WebElement app = Utils.waitForElementByXpathToBeClickable(Constants.INVENTORYPAGE, "appLocator", 10);
			app.click();
		}
		catch(Exception e)
		{
			logger.error("Error in addUpdateInventory()",e);
		}

	}

	public float viewOnHandProductQty(String prodName) 
	{
		float qty = 0;
		try
		{
			new AppPage().goToInventory();
			WebElement prod = Utils.waitForElementByXpathToBeClickable(Constants.INVENTORYPAGE, "productButton", 10);
			prod.click();
			WebElement prods = Utils.waitForElementByXpath(Constants.INVENTORYPAGE, "productsOption", 20);
			prods.click();

			TimeUnit.SECONDS.sleep(2);
			WebElement inventorySearch = Utils.waitForElementByXpath(Constants.INVENTORYPAGE, "inventorySearch", 30);
			inventorySearch.sendKeys(prodName);
			List<WebElement> prodSugg = Utils.waitForElementsByXpath(Constants.INVENTORYPAGE, "searchSuggestion", 10);
			for(WebElement e : prodSugg )
			{
				e.click();
				break;
			}
			TimeUnit.SECONDS.sleep(2);


			String p = Utils.waitForElementByXpath(Constants.INVENTORYPAGE, "inHandProdQty", 20).getAttribute("innerHTML");

			qty = Float.parseFloat(p);

			WebElement app = Utils.waitForElementByXpathToBeClickable(Constants.INVENTORYPAGE, "appLocator", 30);
			app.click();
			TimeUnit.SECONDS.sleep(1);
		}
		catch(Exception e)
		{
			logger.error("Error in viewOnHandProductQty()", e);
		}


		return qty;
	}


}
