package pages;

import interfaces.ButtonClick;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class InformationPage extends BasePage implements ButtonClick {

    // Definir los localizadores
    // User Information
    private final By userIdInput = By.name("username");
    private final By passwordInput = By.name("password");
    private final By repeatedPasswordInput = By.name("repeatedPassword");

    // Account Information
    private final By firstNameInput = By.name("firstName");
    private final By lastNameInput = By.name("lastName");
    private final By emailInput = By.name("email");
    private final By phoneInput = By.name("phone");
    private final By address1Input = By.name("address1");
    private final By address2Input = By.name("address2");
    private final By cityInput = By.name("city");
    private final By stateInput = By.name("state");
    private final By zipInput = By.name("zip");
    private final By countryInput = By.name("country");

    // Profile Information (Preferencias)
    private final By languageSelect = By.name("languagePreference");
    private final By categorySelect = By.name("favouriteCategoryId");
    private final By enableMyListCheckbox = By.name("listOption");
    private final By enableMyBannerCheckbox = By.name("bannerOption");

    // Botón de Guardado
    private final By saveAccountButton = By.name("newAccount");

    public InformationPage(WebDriver driver) {
        super(driver);
    }

    //Métodos
    public String ingresarUserId(String userId) {
        // Si viene la instrucción o contiene espacios, generamos un User ID válido de exactamente 10 caracteres
        if (userId != null && (userId.contains("generados") || userId.contains(" "))) {
            // Ejemplo: "U" + 9 dígitos únicos del timestamp actual -> ej: "U823948192"
            userId = "U" + String.valueOf(System.currentTimeMillis()).substring(4);
        }
        type(userIdInput, userId);
        return userId; //  Retorna el userId
    }
    public String ingresarNewPassword(String password) {
        type(passwordInput, password);
        return password;
    }

    public void ingresarRepeatPassword(String repeatPassword) {
        type(repeatedPasswordInput, repeatPassword);
    }

    public void ingresarFirstName(String firstName) {
        type(firstNameInput, firstName);
    }
    public void ingresarLastName(String lastName) {
        type(lastNameInput, lastName);
    }
    public void ingresarEmail(String email) {
        // Reemplaza el comodín {timestamp} con la hora actual en milisegundos para que el email sea siempre único
        if (email != null && email.contains("{timestamp}")) {
            email = email.replace("{timestamp}", String.valueOf(System.currentTimeMillis()));
        }
        type(emailInput, email);
    }
    public void ingresarPhone(String phone) {
        type(phoneInput, phone);
    }
    public void ingresarAddress1(String address1) {
        type(address1Input, address1);
    }
    public void ingresarAddress2(String address2) {
        type(address2Input, address2);
    }
    public void ingresarCity(String city) {
        type(cityInput, city);
    }
    public void ingresarState(String state) {
        type(stateInput, state);
    }
    public void ingresarZip(String zip) {
        type(zipInput, zip);
    }
    public void ingresarCountry(String country) {
        type(countryInput, country);
    }

    //Verifica que el formulario de registro haya cargado exitosamente.
    public boolean estaEnPaginaDeRegistro() {
        return isDisplayed(userIdInput);
    }

    //Alternativa opcional: Obtener la URL actual del navegador.
    public String obtenerUrlActual() {
        return driver.getCurrentUrl();
    }

    public boolean verificarPreferenciasPorDefecto() {
        Select selectLang = new Select(find(languageSelect));
        Select selectCat = new Select(find(categorySelect));
        boolean idiomaCorrecto = selectLang.getFirstSelectedOption().getText().trim().equalsIgnoreCase("english");
        boolean categoriaCorrecta = selectCat.getFirstSelectedOption().getText().trim().equalsIgnoreCase("FISH");
        boolean myListDesmarcado = !find(enableMyListCheckbox).isSelected();
        boolean myBannerDesmarcado = !find(enableMyBannerCheckbox).isSelected();
        return idiomaCorrecto && categoriaCorrecta && myListDesmarcado && myBannerDesmarcado;
    }

    @Override
    public void darClick() {
        click(saveAccountButton);
    }
}
