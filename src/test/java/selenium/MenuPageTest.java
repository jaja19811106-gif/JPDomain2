package selenium;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import selenium.page.DomainRegisterPage;
import selenium.page.LoginPage;
import selenium.page.MenuPage;

public class MenuPageTest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();

        // ★ パスワードマネージャー完全無効化
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("autofill.profile_enabled", false);
        prefs.put("autofill.credit_card_enabled", false);
        prefs.put("autofill.password_enabled", false);
        options.setExperimentalOption("prefs", prefs);

        // ★ 自動入力ポップアップを抑止
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--disable-autofill");
        options.addArguments("--disable-password-manager-reauthentication");

        // ★ ブラウザを閉じない
        options.setExperimentalOption("detach", true);
        options.addArguments("--start-maximized");

        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        // ★ quit() を呼ばないことでブラウザが閉じない
        // driver.quit();
    }

    @Test
    void testMenuPageTitle() {

        // ログイン画面へ
        driver.get("http://localhost:8080/JPDomain2/login");

        // ログイン処理
        LoginPage login = new LoginPage(driver);
        MenuPage menu = login
                .setProviderNo("001")
                .setUserId("test")
                .setPassword("pass")
                .clickLogin();

        // タイトルが表示されるまで待機
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.titleIs("メニュー"));

        // タイトル確認
        assertEquals("メニュー", menu.getTitle());
    }
    
    @Test
    void testClickHome() {

        driver.get("http://localhost:8080/JPDomain2/login");

        // ログイン
        LoginPage login = new LoginPage(driver);
        MenuPage menu = login
                .setProviderNo("001")
                .setUserId("test")
                .setPassword("pass")
                .clickLogin();

        // ホームリンクをクリック
        menu.clickHome();

        // タイトルが変わらないことを確認
        assertEquals("メニュー", menu.getTitle());
    }
    
    @Test
    void testClickDomainRegister() {

        driver.get("http://localhost:8080/JPDomain2/login");

        // ログイン
        LoginPage login = new LoginPage(driver);
        MenuPage menu = login
                .setProviderNo("001")
                .setUserId("test")
                .setPassword("pass")
                .clickLogin();

        // ドメイン登録リンクをクリック
        menu.clickDomainRegister();

        // 遷移先のタイトルを確認（domainRegister.jsp の <title> に合わせる）
        assertEquals("ドメイン登録", driver.getTitle());
    }
    
    @Test
    void testDomainRegister() {
        driver.get("http://localhost:8080/JPDomain2/login");

        MenuPage menu = new LoginPage(driver)
                .setProviderNo("001")
                .setUserId("test")
                .setPassword("pass")
                .clickLogin();

        menu.clickDomainRegister();

        DomainRegisterPage register = new DomainRegisterPage(driver);
    	System.out.println(
    		    "Delegator = " +
    		    net.sf.log4jdbc.log.SpyLogFactory.getSpyLogDelegator().getClass().getName()
    		);
        register.setDomainName("example.jp")
                .setRegistrant("山田太郎")
                .selectPeriod("1")
                .setNs1("ns1.example.jp")
                .setNs2("ns2.example.jp")
                .clickRegister();

        // 遷移先のタイトルを確認（domainRegisterResult.jsp の <title> に合わせる）
        assertEquals("登録結果", driver.getTitle());
    }
}