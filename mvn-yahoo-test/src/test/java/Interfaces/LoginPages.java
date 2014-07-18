package Interfaces;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.thoughtworks.selenium.webdriven.commands.Check;

import Models.User;

public class LoginPages {
	WebDriver currentDriver;
	String uri;
	
//	// Controls
//	public static String txtUsername = "//*[@id='username']";
//	public static String txtPassword = "//*[@id='passwd']";
//	public static String btnSignIn = "//*[@id='.save']";
//	public static String chkKeepMeSignedIn = "//*[@id='pLabelC']";
	
	// Init
	public LoginPages(WebDriver driver) {
		currentDriver = driver;
		uri = "https://login.yahoo.com";
	}
	
	public WebDriver getCurrentDriver() {
		return currentDriver;
	}

	public void setCurrentDriver(WebDriver currentDriver) {
		this.currentDriver = currentDriver;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public void login(User user, boolean keepMeSignIn) {
		WebElement txtUsername;
		WebElement txtPassword;
		WebElement chkKeepMeSignedIn;
		WebElement btnSignIn;

		txtUsername = currentDriver.findElement(By.xpath("//*[@id='username']"));
		txtUsername.sendKeys(user.getUsername());
		txtPassword = currentDriver.findElement(By.xpath("//*[@id='passwd']"));
		txtPassword.sendKeys(user.getPassword());
		chkKeepMeSignedIn = currentDriver.findElement(By.xpath("//*[@id='pLabelC']"));
		
		if (keepMeSignIn) {
			if (!chkKeepMeSignedIn.isSelected())
				chkKeepMeSignedIn.click();
		} else {
			if (chkKeepMeSignedIn.isSelected())
				chkKeepMeSignedIn.click();
		}
		btnSignIn = currentDriver.findElement(By.xpath("//*[@id='.save']"));
		btnSignIn.click();
	}
}
