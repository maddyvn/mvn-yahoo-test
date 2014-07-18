package Testcase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

import Interfaces.LoginPages;
import Models.User;

public class loginTest {
	WebDriver driver;
	LoginPages loginPage;
	User testUser1;

	@Test
	public void login() {
		loginPage = new LoginPages(driver);
		driver.get(loginPage.getUri());
		loginPage.login(testUser1, false);
	}

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		
		testUser1 = new User();
		testUser1.setUsername("thongkh86@yahoo.com");
		testUser1.setPassword("Teka1986");
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
