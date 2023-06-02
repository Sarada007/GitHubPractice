package typractice;

import java.sql.Driver;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class JenkinsPractice {
	@Test
public void parameter() {
		WebDriver driver;
	String Browser = System.getProperty("browser");
	String Url = System.getProperty("url");
	if(Browser.equals("chrome")) {
		driver=WebDriverManager.chromedriver().create();
		
	}
	else if(Browser.equals("edge")) {
		driver=WebDriverManager.edgedriver().create();
	}
	else if(Browser.equals("firefox")) {
		driver=WebDriverManager.firefoxdriver().create();
	}
	else
		driver=WebDriverManager.chromedriver().create();
	driver.manage().window().maximize();
	driver.get(Url);
	
}
	
}
