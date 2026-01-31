package selenium.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class DomainRegisterPage {

    private WebDriver driver;

    public DomainRegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    // タイトル取得
    public String getTitle() {
        return driver.getTitle();
    }

    // ドメイン名
    public DomainRegisterPage setDomainName(String name) {
        driver.findElement(By.name("domainName")).sendKeys(name);
        return this;
    }

    // 登録者名
    public DomainRegisterPage setRegistrant(String registrant) {
        driver.findElement(By.name("registrant")).sendKeys(registrant);
        return this;
    }

    // 登録年数（Select）
    public DomainRegisterPage selectPeriod(String value) {
        Select select = new Select(driver.findElement(By.name("period")));
        select.selectByValue(value); // "1", "2", "3"
        return this;
    }

    // ネームサーバー1
    public DomainRegisterPage setNs1(String ns1) {
        driver.findElement(By.name("ns1")).sendKeys(ns1);
        return this;
    }

    // ネームサーバー2
    public DomainRegisterPage setNs2(String ns2) {
        driver.findElement(By.name("ns2")).sendKeys(ns2);
        return this;
    }

    // 登録ボタン
    public void clickRegister() {
        driver.findElement(By.cssSelector("input[type='submit'][value='登録']")).click();
    }
}