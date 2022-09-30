package org.tyss.universalUtility;

import java.net.MalformedURLException;
import java.net.URL;

import org.objectRepository.AddCartPage;
import org.objectRepository.CommonPage;
import org.objectRepository.ProductPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class MainClass {
	AppiumDriverLocalService service;
	public AndroidDriver<WebElement> driver;
	public MobileUtility mobileUtility;
	public FileUtility fileUtility;
	JavaUtility javaUtility;
	public CommonPage homePage;
	public ProductPage productPage;
	public AddCartPage addToCartPage;

	public ExcelUtility excelUtility;


	@BeforeSuite
	public void suiteSetUp()
	{
		
	}

	@BeforeClass
	public void classSetUp() {
		
	}
	@BeforeMethod
	public void methodSetUp() throws MalformedURLException {
		mobileUtility=new MobileUtility();
		fileUtility=new FileUtility();
		javaUtility=new JavaUtility();
		excelUtility=new ExcelUtility();

		ThreadSafeClass.setFileUtility(fileUtility);
		ThreadSafeClass.getFileUtility().initializePropertyFile(IConstants.PROPERTYFILEPATH);
		excelUtility.initializeExcelFile(IConstants.EXCELFILEPATH);


		//fetch the data from property file
		String platformVersion=ThreadSafeClass.getFileUtility().getDataFromProperties("platformVersion");
		String appPackage=ThreadSafeClass.getFileUtility().getDataFromProperties("appPackage");
		String appActivity=ThreadSafeClass.getFileUtility().getDataFromProperties("appActivity");
		String deviceId=ThreadSafeClass.getFileUtility().getDataFromProperties("deviceId");

		DesiredCapabilities cap = mobileUtility.initializeDesiredCapabilities(IConstants.PLATFORMNAME, platformVersion, deviceId,appPackage,appActivity);
		URL url = mobileUtility.initializeUrl(fileUtility.getDataFromProperties("urlAddress"));
		driver = mobileUtility.initializeAndroidDriver(url, cap);

		homePage=new CommonPage(driver);
		productPage = new ProductPage(driver);

		addToCartPage=new AddCartPage(driver);
		//Thread safe class
		ThreadSafeClass.setDriver(driver);
		ThreadSafeClass.setJavaUtility(javaUtility);
		ThreadSafeClass.setMobileUtility(mobileUtility);

		ListenerImpClass.testLog.info("Application Opened");


		ThreadSafeClass.getMobileUtility().initializeExplicitWait(10);
		ThreadSafeClass.getMobileUtility().implicitWait(10);


	}

	@AfterMethod
	public void methodTearDown() {
		excelUtility.closeWorkbook();
		driver.closeApp();
		ListenerImpClass.testLog.info("Application closed");
	}
	@AfterClass
	public void classTearDown() {
		
	}

	@AfterSuite
	public void suiteTearDown() {
		
	}

}
