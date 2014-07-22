package Interfaces;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.log4testng.Logger;

import Utils.EventListener;

public class iPage {

	private WebDriver driver;
	private EventFiringWebDriver eventDriver;
	private long implicitlyWaitSecond;
	private long explicitlyWaitSecond;
	
	protected static long DEFAULT_TIMEOUT = 10;
	private Logger log = Logger.getLogger(iPage.class);

	/*********************************************************
	 * Constructors
	 *********************************************************/
	public iPage(WebDriver wdriver) {
		driver = wdriver;
		setImplicitlyWaitSecond(DEFAULT_TIMEOUT);
		setExplicitlyWaitSecond(DEFAULT_TIMEOUT);
		setupEventDriver();
	}
	
	/*********************************************************
	 * Set/Get ExplicitlyWaitSecond
	 *********************************************************/
	public long getExplicitlyWaitSecond() {
		return explicitlyWaitSecond;
	}

	public void setExplicitlyWaitSecond(long explicitlyWaitSecond) {
		this.explicitlyWaitSecond = explicitlyWaitSecond;
	}

	/*********************************************************
	 * Set/Get ImplicitlyWaitSecond
	 *********************************************************/
	public long getImplicitlyWaitSecond() {
		return implicitlyWaitSecond;
	}

	public void setImplicitlyWaitSecond(long second) {
		implicitlyWaitSecond = second;
		try {
			driver.manage().timeouts().implicitlyWait(implicitlyWaitSecond, TimeUnit.SECONDS);
			log.trace("ImplicitlyWait set to " + implicitlyWaitSecond);
		} catch (Throwable t) {
			log.error("Driver is null.", t);
		}
	}

	/*********************************************************
	 * Setup the EventDriver for capturing event
	 *********************************************************/
	public void setupEventDriver() {
		eventDriver = new EventFiringWebDriver(this.driver);
		eventDriver.register(new EventListener());
	}
	
	/*********************************************************
	 * Maximize windows
	 *********************************************************/
	public void maximize() {
		eventDriver.manage().window().maximize();
		log.debug("Maximized window instance.");
	}
	
	/*********************************************************
	 * open an URL
	 * 
	 * @param - url link
	 *********************************************************/
	public void openURL(String url) {
		eventDriver.get(url);
		log.debug("Went to URL: " + eventDriver.getCurrentUrl());
	}

	/*********************************************************
	 * refresh web page
	 *********************************************************/
	public void refresh() {
		eventDriver.navigate().refresh();
		log.debug("Refreshed the current page");
	}

	/*********************************************************
	 * close webdriver
	 *********************************************************/
	public void close() {
		eventDriver.close();
		eventDriver.quit();
		log.trace("Closed " + driver.toString());
	}

	/*********************************************************
	 * go to previous page
	 *********************************************************/
	public void back() {
		eventDriver.navigate().back();
		log.debug("Page has just moved back");
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
			log.trace("Element found: " + locator.toString() + "(" + eventDriver.findElements(locator).size() + ")");
			return true;
		} else {
			log.trace("No element found: " + locator.toString());
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
		boolean flag = isElementExists(locator) ? eventDriver.findElement(locator).isDisplayed() : false;
		log.trace("Element " + locator.toString() + ". IsDisplayed() status found: " + flag);
		return flag;
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
		boolean flag = isElementExists(locator) ? eventDriver.findElement(locator).isEnabled() : false;
		log.trace("Element " + locator.toString() + ". IsEnabled() status found: " + flag);
		return flag;
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
		boolean flag = isElementExists(locator) ? eventDriver.findElement(locator).isSelected() : false;
		log.trace("Element " + locator.toString() + ". IsSelected() status found: " + flag);
		return flag;
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
		boolean flag = isElementExists(locator) ? check(eventDriver.findElement(locator), checked) : false;
		log.debug("Performed check action on element " + locator.toString() + ". IsSelected() status now becomes: "  + flag);
		return flag;
	}

	public boolean check(String xpath, boolean checked) {
		return check(By.xpath(xpath), checked);
	}

	public boolean check(WebElement element, boolean checked) {
		if ((element.isSelected() && checked == false) || (!element.isSelected() && checked == true)) {
			element.click();
		}
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
		if (isElementExists(locator)) {
			eventDriver.findElement(locator).click();
			log.debug("Performed click action on element " + locator.toString());
		}
	}

	/*********************************************************
	 * get attribute of a web element if the element exists
	 * 
	 * @param - By: locator of element
	 * @overrideparam - the xpath of element
	 * @return value of the attribute
	 *********************************************************/
	public String getAttribute(By locator, String attribute) {
		return (isElementExists(locator)) ? eventDriver.findElement(locator).getAttribute(attribute) : null;
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
		return (isElementExists(locator)) ? eventDriver.findElement(locator).getCssValue(property) : null;
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
		return (isElementExists(locator)) ? getSelectedText(eventDriver.findElement(locator)) : null;
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
		return (isElementExists(locator)) ? eventDriver.findElement(locator).getText() : null;
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
				log.debug("Seleted value of element" + locator.toString() + "now becomes: " + value);
			} catch (StaleElementReferenceException e) {
				log.debug("Cannot select value-" + e);
			}
	}

	public void select(String locator, String value) {
		select(By.xpath(locator), value);
	}

	public void select(WebElement element, String value) {
		Select select = new Select(element);
		waitForElementPresent(By.xpath("option[text()='" + value + "']"), true, DEFAULT_TIMEOUT);
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
	 * Wait for element attribute change
	 * 
	 * @param - By: locator of element
	 * @overrideparam - the xpath of element
	 * @param - wait time in seconds
	 *********************************************************/
	public void waitForAttributeChanged(By locator, String attribute, long second) {
		if (isElementExists(locator)) {
			String old = getAttribute(locator, attribute);
			long end = System.currentTimeMillis() + second * 1000;
			while (System.currentTimeMillis() < end) {
				if (!getAttribute(locator, attribute).equals(old))
					break;
			}
		}
	}

	public void waitForAttributeChanged(String locator, String attribute, long second) {
		waitForAttributeChanged(By.xpath(locator), attribute, second);
	}

	/*********************************************************
	 * Wait for page title equals assigned string
	 * 
	 * @param - String: page title
	 * @param - wait time in seconds
	 *********************************************************/
	public boolean waitForPageTitle(String title, long second) {
		log.trace("Wait for page title become '" + title.toString() + "' for " + second + "seconds");
		long end = System.currentTimeMillis() + second * 1000;
		while (System.currentTimeMillis() < end) {
			if (getPageTitle().contentEquals(title))
				return true;
		}
		return false;
	}
	
	/*********************************************************
	 * Wait for page title contains assigned string
	 * 
	 * @param - String: subtitle to contain
	 * @param - wait time in seconds
	 *********************************************************/
	public boolean waitForPageTitleContains(String subTitle, long second) {
		log.trace("Wait for page title contains '" + subTitle.toString() + "' for " + second + "seconds");
		long end = System.currentTimeMillis() + second * 1000;
		while (System.currentTimeMillis() < end) {
			if (getPageTitle().contains(subTitle))
				return true;
		}
		return false;
	}

}
