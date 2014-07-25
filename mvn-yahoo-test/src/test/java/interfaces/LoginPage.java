package interfaces;

import models.User;

import org.openqa.selenium.WebDriver;
import org.testng.log4testng.Logger;

import utils.ControlFactory;

public class LoginPage extends ControlFactory {
	public Logger log = Logger.getLogger(LoginPage.class);
	
	public LoginPage(WebDriver driver) {
		setUp(driver);
		loadControls("loginPage");
	}

	// Methods
	/***********************************************************
	 * Get the current status of keepMeSignIn checkbox
	 * 
	 * @return boolean
	 ***********************************************************/
	public boolean getKeepMeSignInStatus() {
		waitForElementPresent(getControl("chkKeepMeSignedIn"), true, DEFAULT_TIMEOUT);
		return (getAttribute(getControl("chkKeepMeSignedIn"), "class").contains("checked")) ?  true : false;			
	}
	
	/************************************************************
	 * Login with assigned account
	 * 
	 * @param selenium - Current Web driver
	 ***********************************************************/
	public void toggleKeepMeSignInStatus(boolean status) {
		if ((getKeepMeSignInStatus() && !status) || (!getKeepMeSignInStatus() && status)){
			click(getControl("chkKeepMeSignedIn"));
			log.info("Toggle KeepMeSignIn status to " + status);
		}
	}
	
	/************************************************************
	 * Login with assigned account
	 * 
	 * @param selenium - Current Web driver
	 * @param user - assigned user
	 * @param keepMeSignIn - toggle check/uncheck the keepMeSignin checkbox
	 ************************************************************/
	public void login(User user, boolean keepMeSignIn) {
		log.info("Login with user account: " + user.getUsername().toString() + "/" + user.getPassword().toString());
		waitForElementPresent(getControl("txtUsername"), true, DEFAULT_TIMEOUT);
		type(getControl("txtUsername"), user.getUsername());
		waitForElementPresent(getControl("txtPassword"), true, DEFAULT_TIMEOUT);
		type(getControl("txtPassword"), user.getPassword());
		toggleKeepMeSignInStatus(keepMeSignIn);
		click(getControl("btnSignIn"));
	}
}
