package typractice;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CreatingDoctorAndBookingAppointment {

	public static void main(String[] args) throws Throwable {
		
				
				FileInputStream fis = new FileInputStream("./src/test/resources/comman.properties");
				Properties p = new Properties();
				p.load(fis);
				String URL = p.getProperty("url");
				String BROWSER = p.getProperty("browser");
				String ADMINLOGINID = p.getProperty("adminLoginID");
				String ADMINPASSWORD = p.getProperty("adminPassword");
				String PTLOGINID = p.getProperty("ptLoginID");
				String PTPASSWORD = p.getProperty("ptPassword");
				
				FileInputStream fis1 = new FileInputStream("C:\\Users\\RAVISHANKAR\\OneDrive\\Desktop\\hmsTestData.xlsx");
				Workbook wb = WorkbookFactory.create(fis1);
				 Row row = wb.getSheet("CreatingDoctorAndBookingAppoint").getRow(1);
				String dcSp = row.getCell(0).toString();
				String doctor = row.getCell(1).toString();
				String cliAdd = row.getCell(2).toString();
				String docFee = row.getCell(3).toString();
				String docContact = row.getCell(4).toString();
				String docEmail = row.getCell(5).toString();
				String nPass = row.getCell(6).toString();
				String cPass = row.getCell(7).toString();
				
				Random ran = new Random();
				int r = ran.nextInt(1000);
				
				WebDriver driver = null;
				
				if(BROWSER.equalsIgnoreCase("chrome")) {
					driver = WebDriverManager.chromedriver().create();
				}
				else if(BROWSER.equalsIgnoreCase("edge")) {
					driver = WebDriverManager.edgedriver().create();
				}
				else if(BROWSER.equalsIgnoreCase("firefox")) {
					driver = WebDriverManager.firefoxdriver().create();
				}
				else {
					driver = WebDriverManager.chromedriver().create();
				}
				
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

				driver.get(URL);

				String homePageUrl = driver.getCurrentUrl();
				if (homePageUrl.contains("Hospital_Management_System")) {
					System.out.println("Hospital_Management_System web applications home page is displayed.");
				}

				driver.findElement(
						By.xpath("//h3[text()='Admin Login']/following-sibling::div[@class='button']//a[text()='Click Here']"))
						.click();

				String adminLoginPageUrl = driver.getCurrentUrl();
				if (adminLoginPageUrl.contains("Hospital_Management_System/hms/admin")) {
					System.out.println("Admin Login page is displayed.");
				}

				driver.findElement(By.name("username")).sendKeys(ADMINLOGINID);///////////////////
				driver.findElement(By.name("password")).sendKeys(ADMINPASSWORD);////////////////////////
				driver.findElement(By.name("submit")).click();

				String adminDashBoardPageUrl = driver.getCurrentUrl();

				if (adminDashBoardPageUrl.contains("Hospital_Management_System/hms/admin/dashboard")) {
					System.out.println("Admin dashboard page is displayed.");
				}

				driver.findElement(By.xpath("//span[text()=' Doctors ']")).click();
				driver.findElement(By.xpath("//span[text()=' Doctor Specialization ']")).click();
				String doctorSpecializationUrl = driver.getCurrentUrl();

				if (doctorSpecializationUrl.contains("doctor-specilization")) {
					System.out.println("Doctors specialization page is displayed.");
				}

				dcSp = dcSp+r;
				driver.findElement(By.name("doctorspecilization")).sendKeys(dcSp);////////////////
				driver.findElement(By.name("submit")).click();
				String dSMsg = driver
						.findElement(By.xpath("//p[normalize-space(text()='Doctor Specialization added successfully !!')]"))
						.getText();
				dSMsg = dSMsg.trim();
				 List<WebElement> allSpecialization = driver
						.findElements(By.xpath("//td[@class='center']/following-sibling::td[@class='hidden-xs']"));
				 List<String> allSpecializationText = allSpecialization.stream().map(i -> i.getText())
						.collect(Collectors.toList());

				if (dSMsg.equalsIgnoreCase("Doctor Specialization added successfully !!")) {
					System.out.println("Doctor Specialization added successfully. and message have came.");
				}

				for (String i : allSpecializationText) {
					if (i.equalsIgnoreCase(dcSp)) {
						System.out.println("Doctor Specialization added successfully. Showing On the List of Specialization.");
						break;
					}
				}

				driver.findElement(By.xpath("//span[text()=' Doctors ']")).click();
				driver.findElement(By.xpath("//span[text()=' Add Doctor']")).click();

				String addDoctorpageUrl = driver.getCurrentUrl();
				if (addDoctorpageUrl.contains("add-doctor")) {
					System.out.println("Add doctor page is displayed.");
				}

				Select s = new Select(driver.findElement(By.name("Doctorspecialization")));

				s.selectByVisibleText(dcSp);
				
				driver.findElement(By.name("docname")).sendKeys(doctor+r);

				driver.findElement(By.name("clinicaddress")).sendKeys(cliAdd+r);

				driver.findElement(By.name("docfees")).sendKeys(docFee+r);

				driver.findElement(By.name("doccontact")).sendKeys(docContact+r);
				String[] temp = docEmail.split("@");
				docEmail = temp[0]+r+"@"+temp[1];
				driver.findElement(By.name("docemail")).sendKeys(docEmail);

				driver.findElement(By.name("npass")).sendKeys(nPass);

				driver.findElement(By.name("cfpass")).sendKeys(cPass);
				
				driver.findElement(By.name("submit")).click();
				
				String addDoctorAlertText = driver.switchTo().alert().getText();
				driver.switchTo().alert().accept();

				if (addDoctorAlertText.contains("Doctor info added Successfully")) {
					System.out.println("Alert has came. Doctor Added successfully.");
				}

				String manageDoctorHeading = driver.findElement(By.xpath("//section[@id='page-title']//h1")).getText();

				if (manageDoctorHeading.contains("Admin | Manage Doctors")) {
					System.out.println("Manage doctor page is displayed.");
				}

				driver.findElement(By.xpath("//span[@class='username']")).click();

				driver.findElement(By.xpath("//li/a[contains(text(),'Log Out')]")).click();

				String homePageUrl1 = driver.getCurrentUrl();

				if (homePageUrl1.contains("Hospital_Management_System")) {
					System.out.println("Hospital_Management_System web applications home page is displayed.");
				}

				driver.findElement(
						By.xpath("//h3[text()='Patients']/following-sibling::div[@class='button']//a[text()='Click Here']"))
						.click();
				String patientLoginPageUrl = driver.getCurrentUrl();

				if (patientLoginPageUrl.contains("Hospital_Management_System/hms/user-login")) {
					System.out.println("Patient login page is displayed.");
				}

				driver.findElement(By.name("username")).sendKeys(PTLOGINID);
				driver.findElement(By.name("password")).sendKeys(PTPASSWORD);
				driver.findElement(By.name("submit")).click();

				String patientDashboardPageUrl = driver.getCurrentUrl();

				if (patientDashboardPageUrl.contains("Hospital_Management_System/hms/dashboard")) {
					System.out.println("Patient Dashboard page is displayed.");
				}

				driver.findElement(By.xpath("//span[text()=' Book Appointment ']")).click();
				
				String bookAppointmentUrl = driver.getCurrentUrl();
				
				if(bookAppointmentUrl.contains("Hospital_Management_System/hms/book-appointment")) {
					System.out.println("Book appointment page is displayed in patient module");
				}

				Select s1 = new Select(driver.findElement(By.name("Doctorspecialization")));
				List<WebElement> allSp = s1.getOptions();

				for (WebElement i : allSp) {
					if (i.getText().contains(dcSp)) {
						System.out.println(
								"Specialization is present in the text field of Doctor specialization of Book appointment page of Patient module.");
						break;
					}
				}

				Select s2 = new Select(driver.findElement(By.name("doctor")));
				List<WebElement> allDoc = s2.getOptions();

				for (WebElement i : allDoc) {
					if (i.getText().contains(doctor)) {
						System.out.println(
								"Doctor is present in the text field of Doctor of Book appointment page of Patient module.");
						break;
					}
				}

				driver.findElement(By.xpath("//span[@class='username']")).click();

				driver.findElement(By.xpath("//li/a[contains(text(),'Log Out')]")).click();

			}
		}
	


