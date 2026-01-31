package selenium;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPageTest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
           //  driver.quit();
        }
    }

    @Test
    void testLogin() {

        driver.get("http://localhost:8080/JPDomain2/login");

        driver.findElement(By.name("providerNo")).sendKeys("001");
        driver.findElement(By.name("userId")).sendKeys("test");
        driver.findElement(By.name("password")).sendKeys("pass");

        driver.findElement(By.cssSelector("input[type='submit'][value='ログイン']")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.titleIs("メニュー"));

        assertEquals("メニュー", driver.getTitle());
    }
}