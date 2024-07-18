package common;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import driver.DriverScript;

public class AppIndependentMethods extends DriverScript{
	/*****************************
	 * method Name	: getDataTime
	 * purpose		: it provides the timestamp with current date and time
	 *
	 ******************************/
	public String getDateTime(String format) {
		Date dt = null;
		SimpleDateFormat sdf = null;
		try {
			dt = new Date();
			sdf = new SimpleDateFormat(format);
			return sdf.format(dt);
		}catch(Exception e) {
			report.writeReport(null, "Exception", "Exception in 'getDateTime()' method. " + e);
			return null;
		}
		finally {
			dt = null;
			sdf = null;
		}
	}
	
	
	/*****************************
	 * method Name	: getPropData
	 * purpose		: it reads prop data and stores as a Map object
	 *
	 ******************************/
	public Map<String, String> getPropData(String filePath){
		FileInputStream fin = null;
		Properties prop = null;
		Map<String, String> objData = null;
		try {
			fin = new FileInputStream(filePath);
			prop = new Properties();
			prop.load(fin);
			objData = new HashMap<String, String>();
			
			Set<Map.Entry<Object, Object>> both = prop.entrySet();
			Iterator<Map.Entry<Object, Object>> it = both.iterator();
			while(it.hasNext() == true) {
				Map.Entry<Object, Object> mp = it.next();
				objData.put(mp.getKey().toString(), mp.getValue().toString());
			}
			return objData;
		}catch(Exception e) {
			report.writeReport(null, "Exception", "Exception in 'getPropData()' method. " + e);
			return null;
		}
		finally
		{
			try {
				fin.close();
				fin = null;
				prop = null;
				objData = null;
			}catch(Exception e) {
			}
		}
	}
	
	
	/***************************************
	 * Method Name	: clickObject()
	 * purpose		: to click the elements in UI
	 * 
	 * 
	 *****************************************/
	public boolean clickObject(WebDriver oBrowser, By objBy) {
		WebElement oEle = null;
		try {
			oEle = oBrowser.findElement(objBy);
			if(oEle.isDisplayed() == true) {
				oEle.click();
				report.writeReport(oBrowser, "Pass", "The element '"+String.valueOf(objBy)+"' clicked successful");
				return true;
			}else return false;
		}catch(Exception e) {
			System.out.println();
			report.writeReport(oBrowser, "Exception", "Exception in 'clickObject()' method. " + e);
			return false;
		}finally {
			oEle = null;
		}
	}
	
	
	/***************************************
	 * Method Name	: setObject()
	 * purpose		: to set the elements value in UI
	 * 
	 * 
	 *****************************************/
	public boolean setObject(WebDriver oBrowser, By objBy, String strData) {
		WebElement oEle = null;
		try {
			oEle = oBrowser.findElement(objBy);
			if(oEle.isDisplayed() == true) {
				oEle.sendKeys(strData);
				report.writeReport(oBrowser, "Pass", "The data '"+strData+"' was enterd succeful in the element '"+objBy+"'");
				return true;
			}else return false;
		}catch(Exception e) {
			report.writeReport(oBrowser, "Exception", "Exception in 'setObject()' method. " + e);
			return false;
		}finally {
			oEle = null;
		}
	}
	
	
	
	/***************************************
	 * Method Name	: compareExactValue()
	 * purpose		: to compare the exact values
	 * 
	 * 
	 *****************************************/
	public boolean compareExactValue(WebDriver oBrowser, String expectedValue, String actualValue) {
		try {
			if(actualValue.equals(expectedValue)) {
				report.writeReport(oBrowser, "Pass", "The actual '"+actualValue+"' & expected '"+expectedValue+"' values are matched");
				return true;
			}else {
				report.writeReport(oBrowser, "Fail", "Mis-match in the actual '"+actualValue+"' & expected '"+expectedValue+"' values");
				return false;
			}
		}catch(Exception e) {
			report.writeReport(oBrowser, "Exception", "Exception in 'compareExactValue()' method. " + e);
			return false;
		}
	}
	
	
	
	/***************************************
	 * Method Name	: comparePartialValue()
	 * purpose		: to compare the exact values
	 * 
	 * 
	 *****************************************/
	public boolean comparePartialValue(WebDriver oBrowser, String expectedValue, String actualValue) {
		try {
			if(actualValue.contains(expectedValue)) {
				report.writeReport(oBrowser, "Pass", "The actual '"+actualValue+"' & expected '"+expectedValue+"' values are matched");
				System.out.println();
				return true;
			}else {
				report.writeReport(oBrowser, "Fail", "Mis-match in the actual '"+actualValue+"' & expected '"+expectedValue+"' values");
				return false;
			}
		}catch(Exception e) {
			report.writeReport(oBrowser, "Exception", "Exception in 'comparePartialValue()' method. " + e);
			return false;
		}
	}
	
	
	
	/***************************************
	 * Method Name	: verifyText()
	 * purpose		: to compare the value from the application against expected value
	 * 
	 * 
	 *****************************************/
	public boolean verifyText(WebDriver oBrowser, By objBy, String elementType, String expectedValue) {
		WebElement oEle = null;
		String actualValue = null;
		Select oSelect = null;
		try {
			oEle = oBrowser.findElement(objBy);
			if(oEle.isDisplayed() == true) {
				switch(elementType.toLowerCase()) {
				case "text":
					actualValue = oEle.getText();
					break;
				case "value":
					actualValue = oEle.getAttribute("value");
					break;
				case "dropdown":
					oSelect = new Select(oEle);
					actualValue = oSelect.getFirstSelectedOption().getText();
					break;
				default:
					report.writeReport(oBrowser, "Fail", "Invalid elementType '"+elementType+"' was specified.");
				}
				
				if(compareExactValue(oBrowser, actualValue, expectedValue)) {
					return true;
				}else return false;
				
			}else return false;
		}catch(Exception e) {
			report.writeReport(oBrowser, "Exception", "Exception in 'verifyText()' method. " + e);
			return false;
		}finally {
			oEle = null;
			actualValue = null;
			oSelect = null;
		}
	}
	
	
	/***************************************
	 * Method Name	: verifyElementPresent()
	 * purpose		: to verify that the element should exist in the DOM
	 * 
	 * 
	 *****************************************/
	public boolean verifyElementPresent(WebDriver oBrowser, By objBy) {
		WebElement oEle = null;
		try {
			oEle = oBrowser.findElement(objBy);
			if(oEle.isDisplayed()) {
				report.writeReport(oBrowser, "Pass", "The element '"+objBy+"' present in the DOM");
				return true;
			}else return false;
		}catch(Exception e) {
			report.writeReport(oBrowser, "Exception", "Exception in 'verifyElementPresent()' method. " + e);
			return false;
		}
	}
	
	
	
	/***************************************
	 * Method Name	: verifyElementNotPresent()
	 * purpose		: to verify that the element should not exist in the DOM
	 * 
	 * 
	 *****************************************/
	public boolean verifyElementNotPresent(WebDriver oBrowser, By objBy) {
		List<WebElement> oEle = null;
		try {
			oEle = oBrowser.findElements(objBy);
			if(oEle.size() > 0) {
				report.writeReport(oBrowser, "Fail", "The element '"+objBy+"' still present in the DOM");
				return false;
			}else {
				report.writeReport(oBrowser, "Pass", "The element '"+objBy+"' is NOT present in the DOM");
				return true;
			}
		}catch(Exception e) {
			report.writeReport(oBrowser, "Exception", "Exception in 'verifyElementNotPresent()' method. " + e);
			return false;
		}
	}
	
	
	
	/***************************************
	 * Method Name	: verifyOptionalElementPresent()
	 * purpose		: to verify the optional element is present/not
	 * 
	 * 
	 *****************************************/
	public boolean verifyOptionalElementPresent(WebDriver oBrowser, By objBy) {
		List<WebElement> oEle = null;
		try {
			oEle = oBrowser.findElements(objBy);
			if(oEle.size() > 0) {
				return true;
			}else {
				return false;
			}
		}catch(Exception e) {
			report.writeReport(oBrowser, "Exception", "Exception in 'verifyOptionalElementPresent()' method. " + e);
			return false;
		}
	}
	
	
	/***************************************
	 * Method Name	: launchBrowser()
	 * purpose		: to launch the different browsers viz., Chrome, Edge and Firefox
	 * 
	 * 
	 *****************************************/
	public WebDriver launchBrowser(String browserName) {
		WebDriver oDriver = null;
		try {
			switch(browserName.toLowerCase()) {
				case "chrome":
					oDriver = new ChromeDriver();
					break;
				case "firefox":
					oDriver = new FirefoxDriver();
					break;
				case "edge":
					oDriver = new EdgeDriver();
					break;
				default:
					report.writeReport(null, "Fail", "Invalid browser name '"+browserName+"' is specified.");
			}
			
			if(oDriver!=null) {
				oDriver.manage().window().maximize();
				report.writeReport(oDriver, "Pass", "The '"+browserName+"' browser was launched successful");
				return oDriver;
			}else {
				report.writeReport(null, "Fail", "Faield to launch the '"+browserName+"' browser");
				return null;
			}
		}catch(Exception e) {
			report.writeReport(null, "Exception", "Exception in 'launchBrowser() method'. " + e);
			return null;
		}
	}
	
	
	/***************************************
	 * Method Name	: waitForElement()
	 * purpose		: to wait for the specific situation for the specific element
	 * 
	 * 
	 *****************************************/
	public boolean waitForElement(WebDriver oBrowser, By objBy, String waitCondition, String expectedText, int timeout) {
		WebDriverWait oWait = null;
		try {
			oWait = new WebDriverWait(oBrowser, Duration.ofSeconds(timeout));
			switch(waitCondition.toLowerCase()) {
				case "clickable":
					oWait.until(ExpectedConditions.elementToBeClickable(objBy));
					break;
				case "visible":
					oWait.until(ExpectedConditions.visibilityOfElementLocated(objBy));
					break;
				case "invisible":
					oWait.until(ExpectedConditions.invisibilityOfElementLocated(objBy));
					break;
				case "text":
					oWait.until(ExpectedConditions.textToBePresentInElementLocated(objBy, expectedText));
					break;
				case "value":
					oWait.until(ExpectedConditions.textToBePresentInElementValue(objBy, expectedText));
					break;
				case "alert":
					oWait.until(ExpectedConditions.alertIsPresent());
					break;
				default:
					report.writeReport(oBrowser, "Fail", "Invalid wait condition '"+waitCondition+"' was specified");
					return false;
			}
			return true;
		}catch(Exception e) {
			report.writeReport(null, "Exception", "Exception in 'waitForElement() method'. " + e);
			return false;
		}
	}
	
}
