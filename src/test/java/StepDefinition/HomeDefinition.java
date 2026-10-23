package StepDefinition;

import ObjectPage.HomePage;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;

public class HomeDefinition {
    private HomePage homePage;

    private HomePage getHomePage() {
        if (homePage == null) {
            homePage = new HomePage();
        }
        return homePage;
    }

//    @Then("se valida el mensaje Logged In Successfully")
//    public void seValidaElMensaje() {
 //       getHomePage().validarMsgBienvenida();
 //   }

}
