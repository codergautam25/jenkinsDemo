package Simplilearn.TestNG.test;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.annotations.BeforeMethod;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


public class LoginTest {
	
	WebDriver driver;
	SoftAssert assertObj;
	
	@BeforeTest
	public  void setUp() {
		System.setProperty("webdriver.chrome.driver","chromedriver");
		//System.setProperty("webdriver.gecko.driver","/home/gautamdas251998/Downloads/geckodriver");
		  driver=new ChromeDriver();
       // driver=new FirefoxDriver();
		
		driver.get("https://www.simplilearn.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30000, TimeUnit.MILLISECONDS);
		
	}
	
	
	@Parameters({"UserName","Password"})
	@Test
	public void Testcase1(String UserName, String Password) {
		WebElement lnkLogin= driver.findElement(By.className("login"));
		lnkLogin.click();
		
		WebElement editUsername=driver.findElement(By.name("user_login"));
		editUsername.sendKeys(UserName);
		
		WebElement editPassword=driver.findElement(By.name("user_pwd"));
		editPassword.sendKeys(Password);
		
		WebElement btnLogin=driver.findElement(By.name("btn_login"));
		btnLogin.click();
		
		
		WebElement error=driver.findElement(By.id("msg_box"));
		
		String actMsg=error.getText();
		String expMsg="The email or password you have entered is invalid.";
		
	//	Assert.assertEquals(actMsg,expMsg);		
		
		assertObj=new SoftAssert();
		
		assertObj.assertEquals(actMsg, expMsg);
		
		
		System.out.println("After Assertion");
		
		
	}
	
	

	@AfterMethod
	public  void tearDown() {
		try {
			assertObj.assertAll();
		}catch (Exception e) {
			System.out.println("Exception caught"+e);
		}finally {
			driver.quit();
		}
		
		
	}

}
