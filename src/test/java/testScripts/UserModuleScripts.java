package testScripts;

import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import driver.DriverScript;

public class UserModuleScripts extends DriverScript{
	/*************************************
	 * method Name	: TS_loginLogout()
	 * purpose		: automated Test case TS_101
	 * 
	 *********************************/
	public void TS_loginLogout() {
		WebDriver oBrowser = null;
		Map<String, String> data = null;
		test = extent.startTest("TS_loginLogout");
		try {
			data = datatable.getExcelData(System.getProperty("user.dir") + "\\TestData\\UserModule.xlsx", "userData", "TS_101");
			oBrowser = appInd.launchBrowser(data.get("browserName"));
			if(oBrowser != null) {
				Assert.assertTrue(appDep.navigateURL(oBrowser, data.get("appURL"), "actiTIME - Login"));
				Assert.assertTrue(appDep.loginToActiTime(oBrowser, data.get("userName"), data.get("password")));
				Assert.assertTrue(appDep.logoutFromActiTime(oBrowser));
			}else {
				report.writeReport(null, "Fail", "Failed to launch the browser");
			}
		}catch(Exception e) {
			report.writeReport(oBrowser, "Exception", "Exception in 'TS_loginLogout()' testScript. " + e);
		}
		finally {
			oBrowser.close();
			oBrowser = null;
			report.endExtentReport(test);
		}
	}
	
	
	/*************************************
	 * method Name	: TS_createAndDeleteUser()
	 * purpose		: automated Test case TS_102
	 * 
	 *********************************/
	public void TS_createAndDeleteUser() {
		WebDriver oBrowser = null;
		String userName = null;
		Map<String, String> data = null;
		test = extent.startTest("TS_createAndDeleteUser");
		try {	
			data = datatable.getExcelData(System.getProperty("user.dir") + "\\TestData\\UserModule.xlsx", "userData", "TS_102");
			oBrowser = appInd.launchBrowser(data.get("browserName"));
			if(oBrowser != null) {
				Assert.assertTrue(appDep.navigateURL(oBrowser, data.get("appURL"), "actiTIME - Login"));
				Assert.assertTrue(appDep.loginToActiTime(oBrowser, data.get("userName"), data.get("password")));
				userName = userMethods.createUser(oBrowser, data);
				Assert.assertTrue(userMethods.deleteUser(oBrowser, userName));
				Assert.assertTrue(appDep.logoutFromActiTime(oBrowser));
			}else {
				report.writeReport(null, "Fail", "Failed to launch the browser");
			}
		}catch(Exception e) {
			report.writeReport(oBrowser, "Exception", "Exception in 'TS_createAndDeleteUser()' testScript. " + e);
		}
		finally {
			oBrowser.close();
			oBrowser = null;
			userName = null;
			report.endExtentReport(test);
		}
	}
}
