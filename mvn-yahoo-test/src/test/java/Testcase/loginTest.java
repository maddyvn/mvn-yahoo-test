package Testcase;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

import Interfaces.LoginPages;
import Models.User;
import Utils.Selenium;

public class loginTest {
	Selenium selenium;
	LoginPages loginPage;
	User validUser;

	@Test
	public void login() {
		selenium.initWebDriver();
		selenium.openURL(loginPage.getUri());
		loginPage.login(selenium, validUser, true);
	}

	@BeforeClass
	public void beforeClass() {
		selenium = new Selenium();
		loginPage = new LoginPages();
		
		validUser = new User();
		validUser.setUsername("thongkh86@yahoo.com");
		validUser.setPassword("Teka1986");
	}

	@AfterClass
	public void afterClass() {
		selenium.quit();
	}

}
