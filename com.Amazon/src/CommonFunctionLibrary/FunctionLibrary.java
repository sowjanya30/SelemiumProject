package CommonFunctionLibrary;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import Utils.PropertyFileUtils;

public class FunctionLibrary {

	public static WebDriver openBrowser(WebDriver driver) throws FileNotFoundException, IOException
	{
		if (PropertyFileUtils.getValueForKey("Browser").equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", "./CommonJars/chromedriver.exe");
			driver=new ChromeDriver();
		}
		else if (PropertyFileUtils.getValueForKey("Browser").equalsIgnoreCase("Firefox")) 
		{
			driver=new FirefoxDriver();
		}
		else if (PropertyFileUtils.getValueForKey("Browser").equalsIgnoreCase("ie"))
		{
			driver=new InternetExplorerDriver();
		}
		
		return driver;
		
	}
	
	public static void openApp(WebDriver driver) throws FileNotFoundException, IOException
	{
		driver.get(PropertyFileUtils.getValueForKey("URL"));
		driver.manage().window().maximize();
	}
	
	public static void waitForElement(WebDriver driver,String Locator_Type,String Locator_Value,String Test_Data)
	{
		WebDriverWait wait=new WebDriverWait(driver, Integer.parseInt(Test_Data));
				if(Locator_Type.equalsIgnoreCase("id"))
				{
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(Locator_Value)));
				}
				else if(Locator_Type.equalsIgnoreCase("name"))
				{
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(Locator_Value)));
				}
				else if(Locator_Type.equalsIgnoreCase("xpath"))
				{
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locator_Value)));
				}
	}
	
	public static void mouseClick(WebDriver driver)
	{
		Actions act= new Actions(driver);
		act.moveToElement(driver.findElement(By.xpath("//*[@id='nav-link-accountList']/span[2]/span"))).build().perform();
		act.click(driver.findElement(By.xpath("//*[@id='nav-flyout-ya-signin']/a/span"))).build().perform();
	}
	
	public static void typeAction(WebDriver driver,String Locator_Type,String Locator_Value,String Test_Data)
	{
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(Locator_Value)).clear();
			driver.findElement(By.id(Locator_Value)).sendKeys(Test_Data);
		}
		else if (Locator_Type.equalsIgnoreCase("name")) 
		{
			driver.findElement(By.name(Locator_Value)).clear();
			driver.findElement(By.name(Locator_Value)).sendKeys(Test_Data);
		} 
		else if (Locator_Type.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(Locator_Value)).clear();
			driver.findElement(By.xpath(Locator_Value)).sendKeys(Test_Data);
		}
	}
	
	public static void clickAction(WebDriver driver,String Locator_Type,String Locator_Value)
	{
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(Locator_Value)).click();
		}
		else if (Locator_Type.equalsIgnoreCase("name")) 
		{
			driver.findElement(By.name(Locator_Value)).click();
		} 
		else if (Locator_Type.equalsIgnoreCase("xpath")) 
		{
			driver.findElement(By.xpath(Locator_Value)).click();
		} 
	}
	
	public static void titleValidation(WebDriver driver,String Test_Data) throws InterruptedException
	{
		String act_title=driver.getTitle();
		Assert.assertEquals(act_title, Test_Data);
	}
	
	public static String generateDate()
	{
		DateFormat sdf=new SimpleDateFormat("YYYY_MM_DD_ss");
		Date date=new Date();
		return sdf.format(date);
	}
	
}
