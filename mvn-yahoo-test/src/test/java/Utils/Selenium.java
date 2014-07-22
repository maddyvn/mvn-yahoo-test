package Utils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.log4testng.Logger;

/**
 *
 * Common web base actions which using selenium2.
 *
 */
public class Selenium {

	private Logger log = Logger.getLogger(Selenium.class);
	private WebDriver driver;
	private EventFiringWebDriver eventDriver;
	
	private long DEFAULT_TIMEOUT = 10;

	/*********************************************************
	 * init Web driver and set up implicitlyWait time
	 * 
	 * @param - implicitlyWait in seconds. If not have parameter, default wait
	 *        time is 10
	 *********************************************************/
	public void initWebDriver() {
		initWebDriver(DEFAULT_TIMEOUT);
	}

	public void initWebDriver(long implicitlyWaitSecond) {
		DEFAULT_TIMEOUT = implicitlyWaitSecond;
		driver = new FirefoxDriver();
		log.info("Initialized " + driver.toString());
		driver.manage().timeouts().implicitlyWait(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
		log.info("ImplicitlyWait set to " + DEFAULT_TIMEOUT);
		driver.manage().window().maximize();
		log.info("Web instance is maximized");
		eventDriver = new EventFiringWebDriver(driver);
		eventDriver.register(new EventListener());
		
	}

	/*********************************************************
	 * open an URL
	 * 
	 * @param - url link
	 *********************************************************/
	public void openURL(String url) {
		eventDriver.get(url);
		log.info("Went to URL: " + eventDriver.getCurrentUrl());
	}

	/*********************************************************
	 * refresh web page
	 *********************************************************/
	public void refresh() {
		eventDriver.navigate().refresh();
		log.info("Refreshed the current page");
	}

	/*********************************************************
	 * close and quit webdriver
	 *********************************************************/
	public void quit() {
		eventDriver.close();
		eventDriver.quit();
		log.info("Quit " + driver.toString());
	}

	/*********************************************************
	 * go to previous page
	 *********************************************************/
	public void back() {
		eventDriver.navigate().back();
	}

	/*********************************************************
	 * check a web element is exists or not
	 * 
	 * @param - By: locator of element
	 * @overrideparam - the xpath of element
	 * @return true if element is exists
	 *********************************************************/
	public boolean isElementExists(By locator) {
		if (eventDriver.findElements(locator).size() > 0) {
			return true;
		} else {
			log.info("no element found: " + locator.toString());
			return false;
		}
	}

	public boolean isElementExists(String locator) {
		return isElementExists(By.xpath(locator));
	}

	/*********************************************************
	 * check a web element is displayed on screen or not
	 * 
	 * @param - By: locator of element
	 * @overrideparam - the xpath of element
	 * @return true if element exists and display on screen
	 *********************************************************/
	public boolean isDisplayed(By locator) {
		return isElementExists(locator) ? eventDriver.findElement(locator)
				.isDisplayed() : false;
	}

	public boolean isDisplayed(String locator) {
		return isDisplayed(By.xpath(locator));
	}

	/*********************************************************
	 * check a web element is enabled on screen or not
	 * 
	 * @param - By: locator of element
	 * @overrideparam - the xpath of element
	 * @return true if element is enabled
	 *********************************************************/
	public boolean isEnabled(By locator) {
		return isElementExists(locator) ? eventDriver.findElement(locator)
				.isEnabled() : false;
	}

	public boolean isEnabled(String locator) {
		return isEnabled(By.xpath(locator));
	}

	/*********************************************************
	 * check a web element is selected on screen or not
	 * 
	 * @param - By: locator of element
	 * @overrideparam - the xpath of element
	 * @return true if element is selected
	 *********************************************************/
	public boolean isSelected(By locator) {
		return isElementExists(locator) ? eventDriver.findElement(locator)
				.isSelected() : false;
	}

	public boolean isSelected(String locator) {
		return isSelected(By.xpath(locator));
	}

	/*********************************************************
	 * check / uncheck a web element toggle button if the element exists
	 * 
	 * @param - By: locator of element
	 * @overrideparam - the xpath of element
	 * @overrideparam - the target web element
	 *********************************************************/
	public boolean check(By locator, boolean checked) {
		return isElementExists(locator) ? check(
				eventDriver.findElement(locator), checked) : false;
	}

	public boolean check(String xpath, boolean checked) {
		return check(By.xpath(xpath), checked);
	}

	public boolean check(WebElement element, boolean checked) {
		if ((element.isSelected() && checked == false)
				|| (!element.isSelected() && checked == true)) {
//			element.sendKeys(Keys.SPACE);
			element.click();
		}
		log.info("Try to check the checkbox " + element.getTagName() + ". Selected status now: "  + element.isSelected());
		return element.isSelected();
	}

	/*********************************************************
	 * click on a web element if the element exists
	 * 
	 * @param - By: locator of element
	 * @overrideparam - the xpath of element
	 *********************************************************/
	public void click(String locator) {
		click(By.xpath(locator));
	}

	public void click(By locator) {
		if (isElementExists(locator))
			eventDriver.findElement(locator).click();
	}

	/*********************************************************
	 * get attribute of a web element if the element exists
	 * 
	 * @param - By: locator of element
	 * @overrideparam - the xpath of element
	 * @return value of the attribute
	 *********************************************************/
	public String getAttribute(By locator, String attribute) {
		return (isElementExists(locator)) ? eventDriver.findElement(locator)
				.getAttribute(attribute) : null;
	}

	public String getAttribute(String locator, String attribute) {
		return getAttribute(By.xpath(locator), attribute);
	}

	/*********************************************************
	 * get css value of a web element if the element exists
	 * 
	 * @param - By: locator of element
	 * @overrideparam - the xpath of element
	 * @return css value
	 *********************************************************/
	public String getCssValue(By locator, String property) {
		return (isElementExists(locator)) ? eventDriver.findElement(locator)
				.getCssValue(property) : null;
	}

	public String getCssValue(String locator, String property) {
		return getCssValue(By.xpath(locator), property);
	}

	/*********************************************************
	 * get element count
	 * 
	 * @param - By: locator of element
	 * @overrideparam - the xpath of element
	 * @return number of elements
	 *********************************************************/
	public int getElementCount(By locator) {
		return eventDriver.findElements(locator).size();
	}

	public int getElementCount(String locator) {
		return eventDriver.findElements(By.xpath(locator)).size();
	}

	/*********************************************************
	 * get element
	 * 
	 * @param - By: locator of element
	 * @overrideparam - the xpath of element
	 * @return web elements
	 *********************************************************/
	public WebElement getElement(By locator) {
		return eventDriver.findElement(locator);
	}

	public WebElement getElement(String locator) {
		return eventDriver.findElement(By.xpath(locator));
	}

	/*********************************************************
	 * get page title
	 * 
	 * @return title of page
	 *********************************************************/
	public String getPageTitle() {
		return eventDriver.getTitle();
	}

	/*********************************************************
	 * get page url
	 * 
	 * @return url of page
	 *********************************************************/
	public String getPageUrl() {
		return eventDriver.getCurrentUrl();
	}

	/*********************************************************
	 * get the selected text of an element
	 * 
	 * @param - By: locator of element
	 * @overrideparam - the xpath of element
	 * @overrideparam - the target web element
	 * @return - the selected text
	 *********************************************************/
	public String getSelectedText(By locator) {
		return (isElementExists(locator)) ? getSelectedText(eventDriver
				.findElement(locator)) : null;
	}

	public String getSelectedText(String xpath) {
		return getSelectedText(By.xpath(xpath));
	}

	public String getSelectedText(WebElement element) {
		Select select = new Select(element);
		return select.getFirstSelectedOption().getText();
	}

	/*********************************************************
	 * get the text of an element
	 * 
	 * @param - By: locator of element
	 * @overrideparam - the xpath of element
	 * @return - text value of an element
	 *********************************************************/
	public String getText(By locator) {
		return (isElementExists(locator)) ? eventDriver.findElement(locator)
				.getText() : null;
	}

	public String getText(String locator) {
		return getText(By.xpath(locator));
	}

	/*********************************************************
	 * select value in combo box
	 * 
	 * @param - By: locator of element
	 * @overrideparam - the xpath of element
	 * @overrideparam - the target web element
	 *********************************************************/
	public void select(By locator, String value) {
		if (value != null && isElementExists(locator))
			try {
				select(eventDriver.findElement(locator), value);
			} catch (StaleElementReferenceException e) {
				log.info("cannot select value-" + e);
			}
	}

	public void select(String locator, String value) {
		select(By.xpath(locator), value);
	}

	public void select(WebElement element, String value) {
		Select select = new Select(element);
		waitForElementPresent(By.xpath("option[text()='" + value + "']"), true,
				DEFAULT_TIMEOUT);
		select.selectByVisibleText(value);
	}

	/*********************************************************
	 * type a text to an input element
	 * 
	 * @param - By: locator of element
	 * @overrideparam - the xpath of element
	 * @overrideparam - the target web element
	 *********************************************************/
	public void type(By locator, String text) {
		if (isElementExists(locator))
			type(eventDriver.findElement(locator), text);
	}

	public void type(String locator, String text) {
		type(By.xpath(locator), text);
	}

	public void type(WebElement element, String text) {
		if (text != null) {
			element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			element.sendKeys(text);
		}
	}

	/*********************************************************
	 * send a Key an input element
	 * 
	 * @param - By: locator of element
	 * @overrideparam - the xpath of element
	 * @overrideparam - the target web element
	 *********************************************************/
	public void type(By locator, Keys keys) {
		if (isElementExists(locator))
			eventDriver.findElement(locator).sendKeys(keys);
	}

	public void type(String locator, Keys keys) {
		type(By.xpath(locator), keys);
	}

	public void type(WebElement element, String text, Keys keys) {
		element.clear();
		element.sendKeys(text);
		element.sendKeys(keys);
	}

	/*********************************************************
	 * wait for an element is displayed
	 * 
	 * @param - By: locator of element
	 * @overrideparam - the xpath of element
	 * @param - Expected: true if displayed, false if not displayed
	 * @param - wait time in seconds
	 *********************************************************/
	public void waitForElementDisplayed(By locator, boolean expected,
			long second) {
		WebDriverWait wait = new WebDriverWait(eventDriver, second);
		if (expected)
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		else
			wait.until(ExpectedConditions.not(ExpectedConditions
					.visibilityOfElementLocated(locator)));
	}

	public void waitForElementDisplayed(String locator, boolean expected,
			long second) {
		waitForElementDisplayed(By.xpath(locator), expected, second);
	}

	/*********************************************************
	 * wait for an element is presented
	 * 
	 * @param - By: locator of element
	 * @overrideparam - the xpath of element
	 * @param - Expected: true if presented, false if not presented
	 * @param - wait time in seconds
	 *********************************************************/
	public void waitForElementPresent(By locator, boolean expected, long second) {
		WebDriverWait wait = new WebDriverWait(eventDriver, second);
		if (expected)
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		else
			wait.until(ExpectedConditions.not(ExpectedConditions
					.presenceOfElementLocated(locator)));
	}

	public void waitForElementPresent(String locator, boolean expected,
			long second) {
		waitForElementPresent(By.xpath(locator), expected, second);
	}

	/*********************************************************
	 * wait for list item change when the dependent value change
	 * 
	 * @param - By: locator of element
	 * @overrideparam - the xpath of element
	 * @param - wait time in seconds
	 *********************************************************/
	public void waitForListElementChanged(By locator, long second) {
		if (isElementExists(locator)) {
			List<WebElement> elementCount = eventDriver.findElements(locator);
			long end = System.currentTimeMillis() + second * 1000;
			while (System.currentTimeMillis() < end) {
				if (!elementCount.equals(eventDriver.findElements(locator)))
					break;
			}
		}
	}

	public void waitForListElementChanged(String locator, long second) {
		waitForListElementChanged(By.xpath(locator), second);
	}

	/*********************************************************
	 * wait for element attribute change
	 * 
	 * @param - By: locator of element
	 * @overrideparam - the xpath of element
	 * @param - wait time in seconds
	 *********************************************************/
	public void waitForAttributeChanged(By locator, String attribute,
			long second) {
		if (isElementExists(locator)) {
			String old = getAttribute(locator, attribute);
			long end = System.currentTimeMillis() + second * 1000;
			while (System.currentTimeMillis() < end) {
				if (!getAttribute(locator, attribute).equals(old))
					break;
			}
		}
	}

	public void waitForAttributeChanged(String locator, String attribute,
			long second) {
		waitForAttributeChanged(By.xpath(locator), attribute, second);
	}

}