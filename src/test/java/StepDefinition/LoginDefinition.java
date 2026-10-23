package StepDefinition;

import ObjectPage.HomePage;
import ObjectPage.LoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class LoginDefinition {
    private LoginPage loginPage;

    private LoginPage getLoginPage() {
        if (loginPage == null) {
            loginPage = new LoginPage();
        }
        return loginPage;
    }

    @And("ingresar el usuario {string}")
    public void ingresarElUsuario(String nombreUsuario) {
        getLoginPage().escribirUsersname(nombreUsuario);
    }

    @And("ingresar la pass {string}")
    public void ingresarLaPass(String pass) {
        getLoginPage().escribirPassword(pass);
    }

    @When("presiono el boton Submit")
    public void presionoElBotonSubmit() {
        getLoginPage().clickBtnSubmit();
    }



    @Then("se valida el mensaje Logged In Successfully")
    public void seValidaElMensajeLoggedInSuccessfully() {

        loginPage.validarMsgLoginExitoso();

    }
}
