package DriverFactory;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import CommonFunctionLibrary.FunctionLibrary;
import Utils.ExcelFileUtils;

public class DriverScript {
	WebDriver driver;
	ExtentReports report;
	ExtentTest  test;
	
	@Test
	public void startTest() throws Throwable
	{
		ExcelFileUtils excel=new ExcelFileUtils();
		String ModuleStatus=" ";
		int r=excel.rowCount("MasterTestCases");
		System.out.println(r);
		for(int i=1;i<=excel.rowCount("MasterTestCases");i++)
		{
			if(excel.getData("MasterTestCases", i, 2).equalsIgnoreCase("Y"))
			{
				String ModuleName=excel.getData("MasterTestCases", i, 1);
				//Generate html report
				report=new ExtentReports("./Reports/"+ModuleName+FunctionLibrary.generateDate()+".html");
				int RC=excel.rowCount(ModuleName);
				test=report.startTest(ModuleName);
				for(int j=1;j<=RC;j++)
				{
					String Test_Description=excel.getData(ModuleName, j, 0);
					String Method_Name=excel.getData(ModuleName, j, 1);
					String Locator_Type=excel.getData(ModuleName, j, 2);
					String Locator_Value=excel.getData(ModuleName, j, 3);
					String Test_Data=excel.getData(ModuleName, j, 4);
					
					try {
						if (Method_Name.equalsIgnoreCase("openBrowser")) {
							driver=FunctionLibrary.openBrowser(driver);
							test.log(LogStatus.INFO, Test_Description);
						}
						else if (Method_Name.equalsIgnoreCase("openApp")) {
							FunctionLibrary.openApp(driver);
							test.log(LogStatus.INFO, Test_Description);
						}
						else if (Method_Name.equalsIgnoreCase("waitForElement")) {
							FunctionLibrary.waitForElement(driver, Locator_Type, Locator_Value, Test_Data);
							test.log(LogStatus.INFO, Test_Description);
						}
						else if (Method_Name.equalsIgnoreCase("mouseClick")) {
							FunctionLibrary.mouseClick(driver);
							test.log(LogStatus.INFO, Test_Description);
						}
						else if (Method_Name.equalsIgnoreCase("typeAction")) {
							FunctionLibrary.typeAction(driver, Locator_Type, Locator_Value, Test_Data);
							test.log(LogStatus.INFO, Test_Description);
						}
						else if (Method_Name.equalsIgnoreCase("clickAction")) {
							FunctionLibrary.clickAction(driver, Locator_Type, Locator_Value);
							test.log(LogStatus.INFO, Test_Description);
						}
						else if (Method_Name.equalsIgnoreCase("titleValidation")) {
							FunctionLibrary.titleValidation(driver, Test_Data);
							test.log(LogStatus.INFO, Test_Description);
						}
						excel.setData(ModuleName, j, 5, "Pass");
						test.log(LogStatus.PASS, Test_Description);
						ModuleStatus="true";
					} catch (Exception e) {
						excel.setData(ModuleName, j, 5, "Fail");
						test.log(LogStatus.FAIL, Test_Description);
						ModuleStatus="false";
					}
					if(ModuleStatus.equalsIgnoreCase("true"))
					{
						excel.setData("MasterTestCases", i, 3, "Pass");
					}
					else if(ModuleStatus.equalsIgnoreCase("false"))
					{
						excel.setData("MasterTestCases", i, 3, "Fail");
					}
					report.flush();
					report.endTest(test);
				}
				
			}
			if(excel.getData("MasterTestCases", i, 2).equalsIgnoreCase("N"))
			{
				excel.setData("MasterTestCases", i, 3, "Not Executed");
			}
		}
	}
}
