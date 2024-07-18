package pages;

import org.openqa.selenium.By;

public class HomePage {
	public static By obj_HomePage_Title = By.xpath("//td[@class='pagetitle']");
	public static By obj_ShortCut_Window = By.xpath("//div[@style='display: block;']");
	public static By obj_ShortBy_Close_Button = By.id("gettingStartedShortcutsMenuCloseId");
	public static By obj_Logout_Link = By.xpath("//a[@id='logoutLink']");
	public static By obj_SaveChanges_Button = By.xpath("//input[@id='SubmitTTButton']");
}
