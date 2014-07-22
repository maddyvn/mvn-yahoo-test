package interfaces;

import models.User;

import org.openqa.selenium.WebDriver;
import org.testng.log4testng.Logger;

public class LoginPage extends iPage {
	private Logger log = Logger.getLogger(LoginPage.class);
	
	public LoginPage(WebDriver wdriver) {
		super(wdriver);
	}

	// Controls
	private static String txtUsername = "//*[@id='username']";
	private static String txtPassword = "//*[@id='passwd']";
	private static String btnSignIn = "//*[@id='.save']";
	private static String chkKeepMeSignedIn = "//*[@id='pLabelC']";
	
	// Methods
	/***********************************************************
	 * Get the current status of keepMeSignIn checkbox
	 * 
	 * @return boolean
	 ***********************************************************/
	public boolean getKeepMeSignInStatus() {
		waitForElementPresent(chkKeepMeSignedIn, true, DEFAULT_TIMEOUT);
		return (getAttribute(chkKeepMeSignedIn, "class").contains("checked")) ?  true : false;			
	}
	
	/************************************************************
	 * Login with assigned account
	 * 
	 * @param selenium - Current Web driver
	 ***********************************************************/
	public void toggleKeepMeSignInStatus(boolean status) {
		if ((getKeepMeSignInStatus() && !status) || (!getKeepMeSignInStatus() && status)){
			click(chkKeepMeSignedIn);
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
		waitForElementPresent(txtUsername, true, DEFAULT_TIMEOUT);
		type(txtUsername, user.getUsername());
		waitForElementPresent(txtPassword, true, DEFAULT_TIMEOUT);
		type(txtPassword, user.getPassword());
		toggleKeepMeSignInStatus(keepMeSignIn);
		click(btnSignIn);
	}
}
