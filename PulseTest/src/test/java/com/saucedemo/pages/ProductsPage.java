package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.saucedemo.utilities.Helper;


public class ProductsPage  {
	WebDriver driver;
	Helper helper = new Helper();
	
	public ProductsPage(WebDriver ldriver) {
		this.driver = ldriver;
		PageFactory.initElements(driver, this);
		
		if (!ProductsPagelogo.isDisplayed())
			throw new AssertionError("Not landed on Products page");
	}
	
	@FindBy(xpath = "//span[@class= 'title']")
	protected WebElement ProductsPagelogo;
	
	@FindBy(id = "react-burger-menu-btn")
	protected WebElement menu;
	
	@FindBy(id = "react-burger-cross-btn")
	protected WebElement closeMenu;
	
	
	@FindBy(id = "reset_sidebar_link")
	protected WebElement resetApp;
	
	String addElement = "add-to-cart-%s";
	
	
	@FindBy(xpath = "//a[@class= 'shopping_cart_link']")
	protected WebElement cart; 
	
   public YourCart navigateToCart() {
	   cart.click();
	   return new YourCart(driver);
   }
	
   public void resetAppState() {
	   menu.click();
	   resetApp.click();
	   closeMenu.click();
   }
	
	
	public void addToCart(String productName) {
		updateLocator(driver, productName.toLowerCase().replace(" ", "-")).click();;
		//addElement.click();
		
	}
	
	public WebElement updateLocator( WebDriver driver ,String text) {
		return driver.findElement(By.id(String.format(addElement.toString(), text)));
	}
	
	//Assert.assertEquals(ProductsPagelogo.getText(), "Products")
}


