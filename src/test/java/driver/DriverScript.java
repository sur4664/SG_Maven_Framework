package driver;

import java.lang.reflect.Method;
import java.util.Map;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import common.AppDependentMethods;
import common.AppIndependentMethods;
import common.Datatable;
import common.UserModuleMethods;
import reports.ReportUtility;

public class DriverScript {
	public static AppIndependentMethods appInd = null;
	public static AppDependentMethods appDep = null;
	public static Datatable datatable = null;
	public static UserModuleMethods userMethods = null;
	public static ReportUtility report = null;
	public static Map<String, String> propData = null;
	public static ExtentReports extent = null;
	public static ExtentTest test = null;
	public static String controllerfilePath = null;
	
	
	
	@BeforeSuite
	public void loadClasses() {
		try {
			appInd = new AppIndependentMethods();
			appDep = new AppDependentMethods();
			datatable = new Datatable();
			userMethods = new UserModuleMethods();
			report = new ReportUtility();
			propData = appInd.getPropData(System.getProperty("user.dir") + "\\src\\main\\resources\\Config.properties");
			extent = report.startExtentReport("TestReports");
			controllerfilePath = System.getProperty("user.dir") + "\\Runner\\ExecutionController.xlsx";
		}catch(Exception e) {
			System.out.println("Exception in 'loadClasses()' method. " + e);
		}
	}
	
	@Test
	public void test() {
		Class cls = null;
		Object obj = null;
		Method script = null;
		try {
			int rows = datatable.getRowCount(controllerfilePath, "Runner");
			for(int r=1; r<=rows; r++) {
				String scriptName = datatable.getCellData(controllerfilePath, "Runner", "ScriptName", r);
				String className = datatable.getCellData(controllerfilePath, "Runner", "ClassName", r);
				String executeTest = datatable.getCellData(controllerfilePath, "Runner", "ExecuteTest", r);
				
				if(executeTest.equalsIgnoreCase("Yes")) {
					cls = Class.forName(className);
					obj = cls.getDeclaredConstructor().newInstance();
					script = obj.getClass().getMethod(scriptName);
					script.invoke(obj);
				}
			}
		}catch(Exception e) {
			System.out.println("Exception in 'test()' method. " + e);
		}
		finally {
			cls = null;
			obj = null;
			script = null;
		}
	}
}
