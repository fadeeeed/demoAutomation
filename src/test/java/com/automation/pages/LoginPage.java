package com.automation.pages;



import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.automation.base.BaseTest;
import com.automation.config.ConfigFileReader;

public class LoginPage {
	
	static Logger log = Logger.getLogger(BaseTest.class.getName());
	
	private WebDriver driver;
	
	@FindBy(xpath="//*[@id=\"root\"]/div[1]/div/div/div[2]/button")
	WebElement loginToEngage;
	
	@FindBy(id="identifierId")
	WebElement loginEmail;
	
	@FindBy(id="identifierNext")
	WebElement emailNextButton;
	
	@FindBy(name="password")
	WebElement password;
	
	@FindBy(id="passwordNext")
	WebElement passwordNext;
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	ConfigFileReader config = new ConfigFileReader();
	
	protected Properties properties = config.initialize();
	
	public void login() {
		log.info("Started Loggin In");
		
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS); 
		
		loginToEngage.click();
		
		String mainWindowHandle = driver.getWindowHandle();
	    Set<String> allWindowHandles = driver.getWindowHandles();
	    
	    Iterator<String> iterator = allWindowHandles.iterator();
	 
	    
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
	    
		
		while (iterator.hasNext()) {
	    	String ChildWindow = iterator.next();
	        
	    	if (!mainWindowHandle.equalsIgnoreCase(ChildWindow)) {
	        	driver.switchTo().window(ChildWindow);
	      
	        	loginEmail.sendKeys(properties.getProperty("email"));
	     
	        	emailNextButton.click();
	        	
	        	driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
	        	
	 
	        	new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(password)).sendKeys(properties.getProperty("password"));
	        	new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(passwordNext)).click();

	     
	        }
	    }
	    log.info("Login Successfull");
		driver.switchTo().window(mainWindowHandle);
	 }
}
