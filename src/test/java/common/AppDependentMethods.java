package common;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import driver.DriverScript;
import pages.HomePage;
import pages.LoginPage;

public class AppDependentMethods extends DriverScript{
	/*************************************
	 * method Name	: navigateURL()
	 * purpose		: it will navigate the URL
	 * 
	 *********************************/
	public boolean navigateURL(WebDriver oBrowser, String strURL, String pageTitle) {
		try {
			oBrowser.navigate().to(strURL);
			appInd.waitForElement(oBrowser, LoginPage.obj_Login_Button, "Clickable", "", 10);
			report.writeReport(oBrowser, "Screenshot", "The URL '"+strURL+"' is navigated successful");
			return appInd.compareExactValue(oBrowser, oBrowser.getTitle(), pageTitle);
		}catch(Exception e) {
			report.writeReport(oBrowser, "Exception", "Exception in 'navigateURL() method'. " + e);
			return false;
		}
	}
	
	
	
	/*************************************
	 * method Name	: loginToActiTime()
	 * purpose		: it will login to actiTime application
	 * 
	 *********************************/
	public boolean loginToActiTime(WebDriver oBrowser, String userName, String password) {
		try {
			Assert.assertTrue(appInd.setObject(oBrowser, LoginPage.obj_UserName_Edit, userName));
			Assert.assertTrue(appInd.setObject(oBrowser, LoginPage.obj_Password_Edit, password));
			Assert.assertTrue(appInd.clickObject(oBrowser, LoginPage.obj_Login_Button));
			appInd.waitForElement(oBrowser, HomePage.obj_SaveChanges_Button, "Clickable", "", 10);
			report.writeReport(oBrowser, "Screenshot", "Login to actiTime successful");
			boolean blnRes = appInd.verifyText(oBrowser, HomePage.obj_HomePage_Title, "Text", "Enter Time-Track");
			if(blnRes) {
				//Handle the shortcut window
				if(appInd.verifyOptionalElementPresent(oBrowser, HomePage.obj_ShortCut_Window)){
					Assert.assertTrue(appInd.clickObject(oBrowser, HomePage.obj_ShortBy_Close_Button));
				}
				return true;
			}else return false;
			
		}catch(Exception e) {
			report.writeReport(oBrowser, "Exception", "Exception in 'loginToActiTime() method'. " + e);
			return false;
		}catch(AssertionError e) {
			report.writeReport(oBrowser, "Exception", "Assert Error in 'loginToActiTime() method'. " + e);
			return false;
		}
	}
	
	
	
	/**********************************************
	 * Method Name		: logoutFromActiTime()
	 * Purpose			: it is used to logout from the actiTime
	 * Author			: tester1
	 * Date created		:
	 * Modified By		:
	 * Date modified	:
	 **********************************************/
	public boolean logoutFromActiTime(WebDriver oBrowser) {
		try {
			report.writeReport(oBrowser, "Screenshot", "Before logout from actiTime");
			Assert.assertTrue(appInd.clickObject(oBrowser, HomePage.obj_Logout_Link));
			appInd.waitForElement(oBrowser, LoginPage.obj_LoginPage_Header, "Text", "Please identify yourself", 10);
			report.writeReport(oBrowser, "Screenshot", "After logout from actiTime");
			return appInd.verifyText(oBrowser, LoginPage.obj_LoginPage_Header, "Text", "Please identify yourself");
		}catch(Exception e) {
			report.writeReport(oBrowser, "Exception", "Exception in 'logoutFromActiTime() method'. " + e);
			return false;
		}catch(AssertionError e) {
			report.writeReport(oBrowser, "Exception", "Assert Error in 'logoutFromActiTime() method'. " + e);
			return false;
		}
	}
	
}
