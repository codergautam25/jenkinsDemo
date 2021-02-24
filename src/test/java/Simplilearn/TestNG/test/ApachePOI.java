package Simplilearn.TestNG.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ApachePOI {
	WebDriver driver;
	SoftAssert assertObj;

	XSSFWorkbook wb;
	XSSFSheet sheet1;

	@BeforeTest
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "/home/gautamdas251998/Downloads/chromedriver");
		// System.setProperty("webdriver.gecko.driver","/home/gautamdas251998/Downloads/geckodriver");
		driver = new ChromeDriver();
		// driver=new FirefoxDriver();

		driver.get("https://www.simplilearn.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30000, TimeUnit.MILLISECONDS);

	}

	@Test
	public void Testcase1() throws IOException {

		FileInputStream fis = new FileInputStream("exceldata.xlsx");
		wb = new XSSFWorkbook(fis);
		sheet1 = wb.getSheet("data");
		
		String username=sheet1.getRow(0).getCell(0).getStringCellValue();
		String password=sheet1.getRow(0).getCell(1).getStringCellValue();
		System.out.println(username);
		System.out.println(password);
				

		WebElement lnkLogin = driver.findElement(By.className("login"));
		lnkLogin.click();

		WebElement editUsername = driver.findElement(By.name("user_login"));
		editUsername.sendKeys(username);

		WebElement editPassword = driver.findElement(By.name("user_pwd"));
		editPassword.sendKeys(password);

		WebElement btnLogin = driver.findElement(By.name("btn_login"));
		btnLogin.click();

		WebElement error = driver.findElement(By.id("msg_box"));

		String actMsg = error.getText();
		String expMsg = "The email or password you have entered is invalid.";

		// Assert.assertEquals(actMsg,expMsg);

		assertObj = new SoftAssert();

		assertObj.assertEquals(actMsg, expMsg);

		System.out.println("After Assertion");

	}

	@AfterMethod
	public void tearDown() {
		try {
			assertObj.assertAll();
		} catch (Exception e) {
			System.out.println("Exception caught" + e);
		} finally {
			driver.quit();
		}

	}

}
