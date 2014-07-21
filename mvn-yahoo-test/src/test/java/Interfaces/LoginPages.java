package Interfaces;

import Models.User;
import Utils.Selenium;

public class LoginPages {
	String uri;
	
	private long DEFAULT_TIMEOUT = 10;
	
	// Controls
	private static String txtUsername = "//*[@id='username']";
	private static String txtPassword = "//*[@id='passwd']";
	private static String btnSignIn = "//*[@id='.save']";
	private static String chkKeepMeSignedIn = "//*[@id='pLabelC']";
	
	// Init
	public LoginPages() {
		uri = "https://login.yahoo.com";
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public void login(Selenium selenium, User user, boolean keepMeSignIn) {
		selenium.waitForElementPresent(txtUsername, true, DEFAULT_TIMEOUT);
		selenium.type(txtUsername, user.getUsername());
		selenium.waitForElementPresent(txtPassword, true, DEFAULT_TIMEOUT);
		selenium.type(txtPassword, user.getPassword());
		selenium.check(chkKeepMeSignedIn, keepMeSignIn);
		selenium.click(btnSignIn);
	}
}
