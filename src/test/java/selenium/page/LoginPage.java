package selenium.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    private WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public LoginPage setProviderNo(String no) {
        driver.findElement(By.name("providerNo")).sendKeys(no);
        return this;
    }

    public LoginPage setUserId(String id) {
        driver.findElement(By.name("userId")).sendKeys(id);
        return this;
    }

    public LoginPage setPassword(String pass) {
        driver.findElement(By.name("password")).sendKeys(pass);
        return this;
    }

    public MenuPage clickLogin() {
        driver.findElement(By.cssSelector("input[type='submit']")).click();
        return new MenuPage(driver);
    }
}