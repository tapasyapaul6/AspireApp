package com.aspireApp.utilities;

import java.io.IOException;
import java.security.Key;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;


public class Utils 
{
	/* For Logging */
	private final static Logger logger = Logger.getLogger(Utils.class.getName());
	/* Web Driver, only single instance is available */
	private static WebDriver driver = null;

	private static ChromeOptions options;
	public static Properties loadConfig(String filePath) 
	{
		Properties props = null;
		try {
			props = new Properties();
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath));
		} catch (IOException t) {
			logger.error("Exception while loading configuration file:" + filePath, t);
		}
		return props;
	}

	public static synchronized WebDriver getDriver() {

		try {
			if (driver == null) 
			{
				System.setProperty("webdriver.chrome.silentOutput", "true");
				WebDriverManager.chromedriver().setup();

				Map<String, Object> preferences = new Hashtable<String, Object>();

				preferences.put("profile.default_content_settings.popups", 0);
				preferences.put("download.prompt_for_download", "false");
				preferences.put("safebrowsing.enabled", "true"); 

				options = new ChromeOptions();
				//options.addArguments("headless");

				options.addArguments("--safebrowsing-disable-download-protection");
				options.addArguments("--disable-extensions");
				options.addArguments("disable-infobars");
				options.addArguments("test-type");
				options.addArguments("start-maximized");
				options.addArguments("--ignore-ssl-errors=yes");
				options.addArguments("--ignore-certificate-errors");
				options.addArguments("--disable-gpu");
				options.addArguments("--disable-dev-shm-usage"); 
				options.addArguments("--no-sandbox");			

				options.setExperimentalOption("prefs", preferences);

				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);

				driver = new ChromeDriver(capabilities);

			}
		}

		catch (Exception e) {
			logger.error("Excption while getting Web Driver", e);
		}
		return driver;
	}

	public static void setDriver(WebDriver driver) 
	{
		Utils.driver = driver;
	}

	public static WebElement waitForElementByXpath(String fileName, String element, long time) 
	{
		WebDriverWait wait = new WebDriverWait(driver, time);
		WebElement elem = null;
		try {
			elem = wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath(loadConfig(Constants.UI_RESOURCE_LOC + fileName).getProperty(element))));
		} catch (Exception e) {
			logger.error("Error while waiting for the expected web element", e);
		}
		return elem;
	}

	public static WebElement waitForElementByXpathToBeClickable(String fileName, String element, long time) 
	{
		WebDriverWait wait = new WebDriverWait(driver, time);
		WebElement elem = null;
		try {
			elem = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath(loadConfig(Constants.UI_RESOURCE_LOC + fileName).getProperty(element))));
		} catch (Exception e) {
			logger.error("Error while waiting for the expected web element", e);
		}
		return elem;
	}

	public static List<WebElement> waitForElementsByXpath(String fileName, String element, long time) 
	{
		WebDriverWait wait = new WebDriverWait(driver, time);
		List<WebElement> elem = null;
		try {
			elem = (List<WebElement>) wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
					By.xpath(loadConfig(Constants.UI_RESOURCE_LOC + fileName).getProperty(element))));
		} catch (Exception e) {
			logger.error("Error while waiting for the expected web element", e);
		}
		return elem;
	}

	public static void closeDriver()
	{
		driver.quit();
		driver = null;
		Utils.setDriver(null);

	}

	public static  String passworProtect(String pwd) 
	{
		String decrypt = "";
		try 
		{
			String key = "Bar12345Bar12345"; // 128 bit key
			
			Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES");

			cipher.init(Cipher.ENCRYPT_MODE, aesKey);
			cipher.init(Cipher.DECRYPT_MODE, aesKey);
			decrypt = new String(cipher.doFinal(pwd.getBytes()));

		}
		catch(Exception e) 
		{
			logger.error("Error while decrypting password",e);
		}
		return decrypt;
	}
}
