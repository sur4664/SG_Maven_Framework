package reports;

import java.io.File;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import driver.DriverScript;

public class ReportUtility extends DriverScript{
	/*************************************
	 * method Name	: startExtentReport()
	 * purpose		: it will create the extent report .html file
	 * 
	 *********************************/
	public ExtentReports startExtentReport(String fileName) {
		String reportPath = null;
		File objReportPath = null;
		String screenshot = null;
		File objScreenshot = null;
		try {
			reportPath = System.getProperty("user.dir").replace("\\", "/") + "/target/extent-reports";
			screenshot = System.getProperty("user.dir").replace("\\", "/") + "/target/extent-reports/screenshot";
			objReportPath = new File(reportPath);
			if(!objReportPath.exists()) {
				objReportPath.mkdirs();
			}
			
			objScreenshot = new File(screenshot);
			if(!objScreenshot.exists()) {
				objScreenshot.mkdirs();
			}
			
			extent = new ExtentReports(reportPath +"\\"+ fileName +".html", false);
			extent.addSystemInfo("Host Name", System.getProperty("os.name"));
			extent.addSystemInfo("User Name", System.getProperty("user.name"));
			extent.addSystemInfo("Environemnt", propData.get("environment"));
			extent.loadConfig(new File(System.getProperty("user.dir") + "\\\\extent-config.xml"));
			return extent;
		}catch(Exception e) {
			System.out.println("Exception in 'startExtentReport()' method. " + e);
			return null;
		}
		finally {
			reportPath = null;
			objReportPath = null;
		}
	}
	
	
	/*************************************
	 * method Name	: endExtentReport()
	 * purpose		: it will write the extent report .html file
	 * 
	 *********************************/
	public void endExtentReport(ExtentTest test) {
		try {
			extent.endTest(test);
			extent.flush();
		}catch(Exception e) {
			System.out.println("Exception in 'endExtentReport()' method. " + e);
		}
	}
	
	
	/*************************************
	 * method Name	: captureScreenshot()
	 * purpose		: it will capture the screenshot
	 * 
	 *********************************/
	public String captureScreenshot(WebDriver oBrowser) {
		String filePath = null;
		File objSrc = null;
		File objDest = null;
		try {
			filePath = System.getProperty("user.dir") + "\\target\\extent-reports\\screenshot\\screenshot_"+appInd.getDateTime("hhmmss")+".png";
			TakesScreenshot ts = (TakesScreenshot) oBrowser;
			objSrc = ts.getScreenshotAs(OutputType.FILE);
			objDest = new File(filePath);
			FileHandler.copy(objSrc, objDest);
			return filePath;
		}catch(Exception e) {
			System.out.println("Exception in 'captureScreenshot()' method. " + e);
			return null;
		}
		finally {
			filePath = null;
			objSrc = null;
			objDest = null;
		}
	}
	
	
	/*************************************
	 * method Name	: writeReport()
	 * purpose		: it will write the report
	 * 
	 *********************************/
	public void writeReport(WebDriver oBrowser, String status, String description) {
		try {
			switch(status.toLowerCase()) {
				case "pass":
					test.log(LogStatus.PASS, description);
					break;
				case "fail":
					test.log(LogStatus.FAIL, description +" "+test.addScreenCapture(captureScreenshot(oBrowser)));
					break;
				case "info":
					test.log(LogStatus.INFO, description);
					break;
				case "warning":
					test.log(LogStatus.WARNING, description);
					break;
				case "exception":
					test.log(LogStatus.FATAL, description +" "+test.addScreenCapture(captureScreenshot(oBrowser)));
					break;
				case "screenshot":
					test.log(LogStatus.PASS, description +" "+test.addScreenCapture(captureScreenshot(oBrowser)));
					break;
				default:
					System.out.println("Invalid status '"+status+"' for writing the report");	
			}
		}catch(Exception e) {
			System.out.println("Exception in 'writeReport()' method. " + e);
		}
	}
}
