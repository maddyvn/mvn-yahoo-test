package testcases;

import interfaces.LoginPage;
import models.User;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import utils.SeleniumTestBase;

public class loginTest extends SeleniumTestBase {
	LoginPage loginPage;
	User validUser;

	/************************************************
	 * Testcase 01: Login with valid user
	 * 
	 * Steps 1 - Open Yahoo login page "https://login.yahoo.com"
	 * Steps 2 - Enter valid username, password
	 * Steps 3 - Uncheck the keepMeSignIn checkbox
	 * Steps 4 - Verify yahoo mailbox is navigated
	 ************************************************/
	@Test
	public void login_01() {
		loginPage.openURL("https://login.yahoo.com/config/mail?.intl=us");
		loginPage.login(validUser, false);
		assertTrue(loginPage.waitForPageTitleContains("Yahoo Mail", loginPage.getExplicitlyWaitSecond()), "Verify yahoo mailbox is navigated");
	}

	@BeforeClass
	public void beforeClass() {
		WebDriver wd = new FirefoxDriver();
		loginPage = new LoginPage(wd);
		
		validUser = new User();
		validUser.setUsername("thongkh86@yahoo.com");
		validUser.setPassword("Teka1986");
	}

	@AfterClass
	public void afterClass() {
		loginPage.close();
	}

}
