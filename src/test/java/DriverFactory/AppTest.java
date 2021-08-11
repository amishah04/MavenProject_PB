package DriverFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import Utility.ExcelFileUtil;


public class AppTest {

	WebDriver driver;
	String inputpath ="/Users/gauravsalkar/eclipse-workspace/Maven_Project/TestInput/LoginData.xlsx";
	String outputpath ="/Users/gauravsalkar/eclipse-workspace/Maven_Project/TestOutput/Results.xlsx";
	ExtentReports report;
	ExtentTest test;
	
	
	@BeforeTest
	public void setUp()
	{
		//extent report path u have to set
		 report= new ExtentReports("./Reports/Login.html");
		System.setProperty("webdriver.chrome.driver", "/Users/gauravsalkar/eclipse-workspace/Maven_Project/Drivers/chromedriver");
		driver = new ChromeDriver();
	}
	@Test
	public void verifyLogin()throws Throwable
	{
		ExcelFileUtil xl = new ExcelFileUtil(inputpath);
		int rc= xl.rowCount("Login");
		Reporter.log("No of rows are::"+rc,true);
		for(int i=1;i<=rc;i++)
		{
			driver.get("http://orangehrm.qedgetech.com/");
			driver.manage().window().maximize();
			// for extents report
			
			test=report.startTest("Validate Login");
			String username =xl.getCellData("Login", i, 0);
			String password = xl.getCellData("Login", i, 1);
			driver.findElement(By.cssSelector("#txtUsername")).sendKeys(username);
			driver.findElement(By.cssSelector("#txtPassword")).sendKeys(password);
			driver.findElement(By.cssSelector("#btnLogin")).click();
			Thread.sleep(5000);
			String expected ="dashboard";
			String actual = driver.getCurrentUrl();
			if(actual.contains(expected))
			{
				Reporter.log("Login success::"+expected+"  "+actual,true);
				xl.setCellData("Login", i, 2, "Login Success", outputpath);
				xl.setCellData("Login", i, 3, "Pass", outputpath);
				// test.log(LogStatus.PASS, "Login success::"+expected+"   "+actual);
			}
			else
			{
				Reporter.log("Login Fail::"+expected+"  "+actual,true);
				xl.setCellData("Login", i, 2, "Login Fail", outputpath);
				xl.setCellData("Login", i, 3, "Fail", outputpath);
				// test.log(LogStatus.FAIL, "Login Fail::"+expected+"   "+actual);
			}
			report.endTest(test);
			report.flush();
		}
	}
	@AfterTest
	public void tearDown()
	{
		driver.close();
	}
	
	
	
}
