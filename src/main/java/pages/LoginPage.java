package pages;

import org.openqa.selenium.By;

public class LoginPage {
	public static By obj_UserName_Edit = By.xpath("//input[@id='username']");
	public static By obj_Password_Edit = By.xpath("//input[@name='pwd']");
	public static By obj_Login_Button = By.xpath("//a[@id='loginButton']");
	public static By obj_LoginPage_Header = By.id("headerContainer");
}
