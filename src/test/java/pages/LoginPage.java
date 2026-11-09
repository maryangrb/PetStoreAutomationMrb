package pages;

import interfaces.ButtonClick;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage implements ButtonClick {

    private By registerNowButton = By.xpath("//*[@id=\"Catalog\"]/a");
    private final By usernameInput = By.name("username");
    private final By passwordInput = By.name("password");
    private final By loginButton = By.name("signon");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void darClick() {
        click(registerNowButton);
    }

    public void ingresarUsername(String username) {
        type(usernameInput, username);
    }
    public void ingresarPassword(String password) {
        type(passwordInput, password);
    }

    public void clickLogin() {
        click(loginButton);
    }
}
