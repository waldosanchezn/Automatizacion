package ObjectPage;

import Control.BaseController;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.junit.jupiter.api.Assertions.fail;

public class LoginPage extends BaseController {
    private WebDriver driver;

    @FindBy(xpath = "username")
    private WebElement nombreUsuario;

    @FindBy(css = "input[name='password']")
    private WebElement passwordUsuario;

    @FindBy(xpath = "//button[contains(text(),'Submit')]")
    private WebElement btnSubmit;

    @FindBy(xpath = "//*[@id='loop-container']/div/article/div[1]/h1")
    private WebElement msgLoginExitoso;

    public void escribirUsersname(String nUsuario) {
        try {
            if (visualizarElemento(this.nombreUsuario, 10)) {
                this.nombreUsuario.clear();
                this.nombreUsuario.sendKeys(nUsuario);
            } else {
                fail("No se encuentra el campo Nombre de Usuario en la página");
            }
        } catch (Exception e) {
            fail("Error al escribir en el campo de usuario: " + e.getMessage());
        }
    }

    public void escribirPassword(String pass) {
        try {
            this.passwordUsuario.clear();
            this.passwordUsuario.sendKeys(pass);
        } catch (Exception e) {
            fail("Error al escribir en el campo de password: " + e.getMessage());
        }
    }

    public void clickBtnSubmit() {
        try {
            this.btnSubmit.click();
        } catch (Exception e) {
            fail("Error al hacer click en el botón Submit: " + e.getMessage());
        }
    }
    public void validarMsgLoginExitoso() {

        try {

            if (this.msgLoginExitoso.isDisplayed()) {

                System.out.println("Mensaje de login exitoso visible");

                System.out.println(this.msgLoginExitoso.getText());

            } else {

                System.out.println("No se visualiza el mensaje de login exitoso");

            }

        } catch (Exception e) {

            fail("Error al detectar el mensaje de login exitoso: "

                    + e.getMessage());

        }

    }
}
