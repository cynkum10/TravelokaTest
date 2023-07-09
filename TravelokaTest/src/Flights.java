import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class Flights {
	public static void main(String[] args) throws InterruptedException {
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.traveloka.com/en-id/flight");
		Thread.sleep(3000);

		inputDepartureLocation(driver);
		inputDestinationLocation(driver);
		selectCurrentDate(driver);
		selectReturnDate(driver);
		verifyInfantPassengers(driver);
		searchFlights(driver);
	}
	
	public static void inputDepartureLocation(WebDriver driver) {
		WebElement inputDeparture = driver.findElement(By.xpath("//input[@data-testid='airport-input-departure']"));
		inputDeparture.clear();
		inputDeparture.sendKeys("Yogyakarta");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

		WebElement selectedDepartureLocation = driver.findElement(By.xpath("(//div[@data-cell-content='Yogyakarta, Indonesia'])[1]"));
		selectedDepartureLocation.getText().contains("Yogyakarta, Indonesia");
		selectedDepartureLocation.click();
		driver.findElement(By.xpath("//input[@data-testid='airport-input-departure']")).getText().contains("Yogyakarta (YKIA)");
	}
	
	public static void inputDestinationLocation(WebDriver driver) {
		WebElement inputDestination = driver.findElement(By.xpath("//input[@data-testid='airport-input-destination']"));
		inputDestination.clear();
		inputDestination.sendKeys("Jakarta");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

		WebElement selectedDestinationLocation = driver.findElement(By.xpath("(//div[@data-cell-content='Jakarta, Indonesia'])[1]"));
		selectedDestinationLocation.getText().contains("Jakarta, Indonesia");
		selectedDestinationLocation.click();
		driver.findElement(By.xpath("//input[@data-testid='airport-input-destination']")).getText().contains("Jakarta (JKTA)");
	}
	
	public static void selectCurrentDate(WebDriver driver) {
		driver.findElement(By.xpath("//input[@data-testid='departure-date-input']")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//div[@data-testid='departure-date-calendar-container']")).isDisplayed());
	    List<WebElement> currentMonthDate = driver.findElements(By.xpath("(//div[@class='css-1dbjc4n']/div[@class='css-1dbjc4n r-14lw9ot r-6413gk r-14hr28s r-c0cves'])[1]/div[@class='css-1dbjc4n']"));

		DateUtil.clickDate(currentMonthDate, DateUtil.getCurrentDay());
	}
	
	public static void selectReturnDate(WebDriver driver) {
		driver.findElement(By.xpath("//div[@class='css-1dbjc4n r-1loqt21 r-1otgn73 r-1i6wzkk r-lrvibr']")).click();
		driver.findElement(By.xpath("//input[@data-testid='return-date-input']")).click();
		Assert.assertTrue(driver.findElement(By.id("returnDate")).isDisplayed());

	    List<WebElement> currentMonthDate = driver.findElements(By.xpath("(//div[@class='css-1dbjc4n']/div[@class='css-1dbjc4n r-14lw9ot r-6413gk r-14hr28s r-c0cves'])[4]/div[@class='css-1dbjc4n']"));

		DateUtil.clickDate(currentMonthDate, Integer.toString(15));
	}
	
	public static void verifyInfantPassengers(WebDriver driver) {
		driver.findElement(By.xpath("//input[@data-testid='passengers-input']")).click();
		
		for (int i = 1; i < 3; i++) {
			driver.findElement(By.xpath("//div[@data-testid='passengers-stepper-plus-child']")).click();
			driver.findElement(By.xpath("//div[@data-testid='passengers-stepper-plus-infant']")).click();
			
			int totalInfant = Integer.parseInt(driver.findElement(By.xpath("//div[@data-testid='passengers-stepper-value-infant']")).getText());
			int totalAdult = Integer.parseInt(driver.findElement(By.xpath("//div[@data-testid='passengers-stepper-value-adult']")).getText());
			if(totalInfant > totalAdult){
				driver.findElement(By.xpath("(//div[@class='css-901oao r-jwli3a r-t1w4ow r-ubezar r-majxgm r-135wba7 r-q4m81j'])[1]")).isDisplayed();
				String infantText = driver.findElement(By.xpath("(//div[@class='css-901oao r-jwli3a r-t1w4ow r-ubezar r-majxgm r-135wba7 r-q4m81j'])[1]")).getText();
				Assert.assertEquals(infantText, "Number of infant passengers can't be more than adult passengers.");
				Assert.assertEquals(totalInfant, totalAdult);
			}
		}
		
		driver.findElement(By.xpath("//div[@class='css-901oao r-t1w4ow r-1o4mh9l r-b88u0q r-f0eezp r-q4m81j']")).click();
	}
	
	public static void selectSeatClass(WebDriver driver) {
		driver.findElement(By.xpath("//div[@aria-labelledby='flight_seat']")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//div[@role='listbox']")).isDisplayed());
		driver.findElement(By.xpath("(//div[@class='css-901oao css-bfa6kz r-13awgt0 r-t1w4ow r-ubezar r-majxgm r-135wba7 r-fdjqy7'])[3]")).click();
		Assert.assertEquals(driver.findElement(By.xpath("//div[@aria-labelledby='flight_seat']")).getText(), "Premium Economy");
	}
	
	public static void searchFlights(WebDriver driver) {
		driver.findElement(By.xpath("//div[@class='css-901oao r-1awozwy r-jwli3a r-6koalj r-1d4mawv']")).click();
		Assert.assertTrue(driver.getCurrentUrl().contains("YKIA.JKTA"));
		
	}
}
	
