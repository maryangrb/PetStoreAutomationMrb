package steps;

import driver.DriverManager;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import pages.HomePage;
import pages.InformationPage;
import pages.LoginPage;

import java.util.Map;

public class CreateUserStep {

    HomePage homePage = new HomePage(DriverManager.getDriver());
    LoginPage loginPage = new LoginPage(DriverManager.getDriver());
    InformationPage informationPage = new InformationPage(DriverManager.getDriver());

    // Variables para guardar las credenciales del usuario recién creado
    private String registeredUserId;
    private String registeredPassword;

    @Given("ingresa en esta URL {string}")
    public void accederURL(String url) {
        homePage.accURL(url);
    }

    @And("da click en {string}")
    public void daClickEn(String boton) {
        switch (boton){
            case "Sign in":
                homePage.darClick();
                break;
            case "Register now":
                loginPage.darClick();
                break;
            case "Save account information":
                informationPage.darClick();
                break;
            default:
                throw new IllegalArgumentException("Botón no reconocido en el step: '" + boton + "'");
        }
    }

    @Then("ingresa a la página de registro")
    public void ingresaALaPáginaDeRegistro() {
        //validar que la página cargó (por ejemplo, verificando la presencia del formulario de registro o la URL).
        Assertions.assertTrue(
                informationPage.estaEnPaginaDeRegistro(),
                "Error: No se visualiza el formulario de registro de usuario."
        );
        Assertions.assertTrue(
                informationPage.obtenerUrlActual().contains("/account/new"),
                "La URL actual no corresponde a la página de registro."
        );
    }

    @When("completa el formulario con los siguientes datos:")
    public void completaElFormularioConLosSiguientesDatos(DataTable dataTable) {
        Map<String, String> datos = dataTable.asMap(String.class, String.class);

        // Guardamos el User ID generado y la contraseña
        this.registeredUserId = informationPage.ingresarUserId(datos.get("User ID"));
        this.registeredPassword = informationPage.ingresarNewPassword(datos.get("New password"));

        System.out.println("Usuario a registrar: " + registeredUserId);
        System.out.println("Contraseña a registrar: " + registeredPassword);

        informationPage.ingresarRepeatPassword(datos.get("Repeat password"));
        informationPage.ingresarFirstName(datos.get("First name"));
        informationPage.ingresarLastName(datos.get("Last name"));
        informationPage.ingresarEmail(datos.get("Email"));
        informationPage.ingresarPhone(datos.get("Phone"));
        informationPage.ingresarAddress1(datos.get("Address 1"));
        informationPage.ingresarCity(datos.get("City"));
        informationPage.ingresarState(datos.get("State"));
        informationPage.ingresarZip(datos.get("Zip"));
        informationPage.ingresarCountry(datos.get("Country"));

    }

    @And("deja las preferencias de perfil con sus valores por defecto")
    public void dejaLasPreferenciasDePerfilConSusValoresPorDefecto() {
        //asegurar q los valores por defecto sean los correctos (según el documento funcional: english, FISH, y checkboxes desactivados)
        Assertions.assertTrue(
                informationPage.verificarPreferenciasPorDefecto(),
                "Las preferencias de perfil no coinciden con los valores por defecto esperados (english, FISH, desmarcados)."
        );
    }

    //Revisar a partir de aquí, está dando error 500 ahora
    @Then("el sistema redirige a la página de inicio {string}")
    public void elSistemaRedirigeALaPaginaDeInicio(String urlEsperada) {
        //validar que la página redirigió bien (por ejemplo, verificando la presencia de algo o la URL).
        Assertions.assertTrue(homePage.obtenerUrlActual().contains(urlEsperada));
        //System.out.println("URL actual: " + homePage.obtenerUrlActual());
        //System.out.println("the urlEsperada is: " + urlEsperada);
    }

    @And("la página de inicio muestra la imagen {string}")
    public void laPáginaDeInicioMuestraLaImagen(String Dogs) {
        //Validar que la imagen esperada (ej. "Dogs") esté visible
        Assertions.assertTrue(
                homePage.esVisibleImagenDogsEnElMenuIzq(),
                "Error: La imagen de '" + Dogs + "' no se muestra en la página de inicio."
        );
        //Validar que el menú izquierdo esté visible
        Assertions.assertTrue(
                homePage.esVisibleMenuIzquierdo(),
                "Error: El menú izquierdo no se visualiza en la página de inicio."
        );
    }

    @And("ingresa el User ID registrado")
    public void ingresaElUserIDRegistrado() {
        loginPage.ingresarUsername(this.registeredUserId);
    }

    @And("ingresa la contraseña registrada")
    public void ingresaLaContraseñaRegistrada() {
        loginPage.ingresarPassword(this.registeredPassword);
    }

    @And("envía el formulario de inicio de sesión")
    public void envíaElFormularioDeInicioDeSesión() {
        loginPage.clickLogin();
    }

    @And("la página de inicio muestra My Account and Sign Out buttons")
    public void laPáginaDeInicioMuestraMyAccountAndSignOutButtons() {
        //Validar que el botón 'My Account' esté visible
        Assertions.assertTrue(
                homePage.esVisibleMyAccount(),
                "Error: El botón 'My Account' no se muestra en la página de inicio tras iniciar sesión."
        );
        //Validar que el botón 'Sign Out' esté visible
        Assertions.assertTrue(
                homePage.esVisibleSignOut(),
                "Error: El botón 'Sign Out' no se muestra en la página de inicio tras iniciar sesión."
        );
    }
}
