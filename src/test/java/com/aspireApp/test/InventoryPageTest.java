package com.aspireApp.test;

import static org.testng.Assert.assertEquals;

import java.util.Properties;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.aspireApp.pageObject.InventoryPage;
import com.aspireApp.pageObject.LoginPage;
import com.aspireApp.pageObject.ManufacturingPage;
import com.aspireApp.utilities.Constants;
import com.aspireApp.utilities.Utils;


public class InventoryPageTest 
{

	private static InventoryPage inPage = null;
	private Properties props = null;
	private final static Logger logger = Logger.getLogger(InventoryPageTest.class.getName());
	@BeforeMethod
	public void setUp() {
		try 
		{
			props = Utils.loadConfig(Constants.CONFIG);
			new LoginPage().loginAspireApp(props.getProperty("aspire_link"),props.getProperty("aspire_link_userId"),
					props.getProperty("aspire_link_password"));

			inPage = new InventoryPage();

		} catch (Exception e) 
		{
			logger.error("Error in set up of InventoryPageTest", e);
		}
	}

	@Test
	public void addInventoryTest()
	{

		inPage.addUpdateInventory(props.getProperty("prodName"),props.getProperty("invQuantity"));
		float qty1 = inPage.viewOnHandProductQty(props.getProperty("prodName"));
		//verify product added to inventory- aspireApp__VerifyproductAddedToInventory
		assertEquals(qty1, 0.0);
		
		new ManufacturingPage().addManufacturingItem(props.getProperty("prodName"),props.getProperty("mafQty"));
		
		float qty2 = inPage.viewOnHandProductQty(props.getProperty("prodName"));
		//verify product is manufactured and on hand count increased to the qty provided - aspireApp__VerifyproductManufactured
		assertEquals(qty1, Float.parseFloat(props.getProperty("mafQty")));
		
		float  actualResult = qty2 - qty1;
		float expectedResult = Float.parseFloat(props.getProperty("mafQty"));
		
		//verify on hand increases after manufactured - aspireApp__VerifyonHandCount
		assertEquals(actualResult,expectedResult,"On Hand Quantity Updated by "+props.getProperty("quantity")+" unit");
	}

	@AfterMethod
	public void cleanUp() 
	{
		Utils.closeDriver();
	}


}
