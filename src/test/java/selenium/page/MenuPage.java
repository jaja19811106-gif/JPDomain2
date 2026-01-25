package selenium.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MenuPage {

    private WebDriver driver;

    public MenuPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getTitle() {
        return driver.getTitle();
    }
    
    public MenuPage clickHome() {
        driver.findElement(By.linkText("ホーム")).click();
        return this; // 同じ画面なので this を返す
    }
    
    public MenuPage clickDomainRegister() {
        driver.findElement(By.linkText("ドメイン登録")).click();
        return this; // まだ DomainRegisterPage が無いので this でOK
    }
}