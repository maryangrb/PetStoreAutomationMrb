package pages;

import interfaces.ButtonClick;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage implements ButtonClick {

    private final By signInButton = By.xpath("//*[@id=\"MenuContent\"]/a[2]");
    private final By menuInHomePage = By.xpath("//*[@id=\"SidebarContent\"]");
    private final By imageDogsInHomePage = By.xpath("//*[@id=\"SidebarContent\"]/a[3]/img");

    // Localizadores de los botones tras iniciar sesión
    private final By signOutButton = By.linkText("Sign Out");
    private final By myAccountButton = By.linkText("My Account");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void accURL(String url) {
        navigateTo(url);
    }

    @Override
    public void darClick() {
        click(signInButton);
    }

    public String obtenerUrlActual() {
        return driver.getCurrentUrl();
    }

    // Métodos para verificar visibilidad
    public boolean esVisibleMenuIzquierdo() {
        return isDisplayed(menuInHomePage);
    }
    public boolean esVisibleImagenDogsEnElMenuIzq() {
        return isDisplayed(imageDogsInHomePage);
    }

    public boolean esVisibleSignOut() {
        return isDisplayed(signOutButton);
    }
    public boolean esVisibleMyAccount() {
        return isDisplayed(myAccountButton);
    }
}
