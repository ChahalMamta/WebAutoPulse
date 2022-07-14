package com.saucedemo.testcases;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.saucedemo.pages.BaseClass;
import com.saucedemo.pages.CheckoutComplete;
import com.saucedemo.pages.CheckoutOverview;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import com.saucedemo.pages.YourCart;
import com.saucedemo.pages.YourInformation;

public class LoginTest extends BaseClass {
	LoginPage loginPage;
	ProductsPage productPage;
	YourCart yourCart;
	YourInformation yourInformation;
	CheckoutOverview checkoutOverview;
	CheckoutComplete checkoutComplete;

	@Test(priority = 1)
	public void loginApp() throws InterruptedException {

		loginPage = new LoginPage(driver); // PageFactory.initElements(driver, LoginPage.class);

		loginPage.InvalidLoginToApp(excel.getStringData("Login", 1, 0), excel.getStringData("Login", 1, 1));

		Assert.assertEquals(driver.findElement(By.xpath("//div[@class= 'error-message-container error']")).getText(),
				"Epic sadface: Sorry, this user has been locked out.");

	}

	@Test(priority = 2)
	public void verifyCheckout() throws InterruptedException {

		loginPage = new LoginPage(driver);

		productPage = loginPage.loginToApp(excel.getStringData("Login", 0, 0), excel.getStringData("Login", 0, 1));
		
		productPage.addToCart("Sauce Labs Backpack");
		productPage.resetAppState();
		productPage.addToCart("Sauce Labs Bike Light");
		productPage.addToCart("Sauce Labs Fleece Jacket");
		productPage.addToCart("Sauce Labs Onesie");
		yourCart = productPage.navigateToCart();
		yourCart.removeSecondElement();
		yourInformation = yourCart.checkout();
		checkoutOverview = yourInformation.continueCheckout();
		Assert.assertEquals(checkoutOverview.getPaymentInformation(), "SauceCard #31337");
		Assert.assertEquals(checkoutOverview.getTotal(), checkoutOverview.totalPrice());
		checkoutComplete = checkoutOverview.finishCheckout();
		Assert.assertEquals(checkoutComplete.getThankyouNote(), "THANK YOU FOR YOUR ORDER");

		Assert.assertEquals(checkoutComplete.getFinalNote(),
				"Your order has been dispatched, and will arrive just as fast as the pony can get there!");

		Assert.assertTrue(checkoutComplete.getImage(), "No image present");
	}

}
