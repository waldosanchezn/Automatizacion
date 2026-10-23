package ObjectPage;

import Control.BaseController;
import Control.DriverContext;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ListaEmpleadoPage extends BaseController {

    // LOGIN

    @FindBy(xpath = "//*[@id='app']/div[1]/div/div[1]/div/div[2]/div[2]/form/div[1]/div/div[2]/input")
    private WebElement username;

    @FindBy(xpath = "//*[@id='app']/div[1]/div/div[1]/div/div[2]/div[2]/form/div[2]/div/div[2]/input")
    private WebElement password;

    @FindBy(xpath = "//*[@id='app']/div[1]/div/div[1]/div/div[2]/div[2]/form/div[3]/button")
    private WebElement loginbtn;

    // PIM

    @FindBy(xpath = "//*[@id='app']/div[1]/div[1]/aside/nav/div[2]/ul/li[2]/a")
    private WebElement menuPim;

    @FindBy(xpath = "//*[@id='app']/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[1]/div/div[2]/div/div/input")
    private WebElement nombreempleado;

    @FindBy(xpath = "//*[@id='app']/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[2]/button[2]")
    private WebElement buscarempladobtn;

    // TC-002 EMPLOYEE ID

    @FindBy(xpath = "//*[@id='app']/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[2]/div/div[2]/input")
    private WebElement employeeId;

    @FindBy(xpath = "//*[@id='app']/div[1]/div[2]/div[2]/div/div[2]/div[2]/div/span")
    private WebElement lblCantidadRegistros;

    // TC-003 Employment Status

    @FindBy(xpath = "//*[@id='app']/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[3]/div/div[2]/div/div/div[2]/i")
    private WebElement employeestatus;

    @FindBy(xpath = "//*[@id='app']/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[3]/div/div[2]/div/div/div[1]")
    private WebElement employeestatustype;

    @FindBy(xpath = "//*[@id='app']/div[1]/div[2]/div[2]/div/div[2]/div[3]/div/div[2]/div/div/div[6]/div")
    private List<WebElement> listaEmploymentStatus;

    // TC-004 include

    // flecha del combo Include
    @FindBy(xpath = "//*[@id='app']/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[4]/div/div[2]/div/div/div[2]/i")
    private WebElement includeFilter;

    // valor seleccionado
    @FindBy(xpath = "//*[@id='app']/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[4]/div/div[2]/div/div/div[1]")
    private WebElement includeFilterType;

     // TC-005 include
    // TC-005 JOB TITLE

    @FindBy(xpath = "//*[@id='app']/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[6]/div/div[2]/div/div/div[2]/i")
    private WebElement jobTitleSelector;

    @FindBy(xpath = "//*[@id='app']/div[1]/div[2]/div[2]/div/div[2]/div[3]/div/div[2]/div[1]/div/div[5]")
    private List<WebElement> listaJobTitle;

    //SUB UNIT
    @FindBy(xpath = "//*[@id='app']/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[7]/div/div[2]/div/div/div[2]/i")
    private WebElement subUnitSelector;

    @FindBy(xpath = "//*[@id='app']/div[1]/div[2]/div[2]/div/div[2]/div[3]/div/div[2]/div[1]/div/div[7]")
    private List<WebElement> listaSubUnit;

    //SUPERVISOR NAME
    @FindBy(xpath = "//*[@id='app']/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[5]/div/div[2]/div/div/input")
    private WebElement supervisorName;


    // LOGIN

    public void escribirUsersname(String usuario) {
        username.sendKeys(usuario);
    }

    public void escribirPassword(String clave) {
        password.sendKeys(clave);
    }

    public void clickLogin() {
        loginbtn.click();
    }

    // PIM

    public void clickPIM() {
        try {
            visualizarElemento(menuPim, 10);
            menuPim.click();
        } catch (Exception e) {
            Assert.fail("Error al ingresar al módulo PIM: " + e.getMessage());
        }
    }

    public void ingresarNombreEmpleado(String nombreEmpleado) {
        nombreempleado.clear();
        nombreempleado.sendKeys(nombreEmpleado);
    }

    public void clickBuscar() {
        buscarempladobtn.click();
    }

    // TC-002

    public void ingresarEmployeeId(String id) {
        employeeId.clear();
        employeeId.sendKeys(id);
    }

    public String obtenerCantidadRegistros() {

        return lblCantidadRegistros.getText();

    }

    // TC-003
    public void seleccionarEmploymentStatus(String status) throws InterruptedException {

        employeestatus.click();

        Thread.sleep(2000);

        DriverContext.getDriver().findElement(
                By.xpath("//*[normalize-space()='" + status + "']")
        ).click();
    }

    public boolean validarEmploymentStatus(String estadoEsperado) {

        for (WebElement estado : listaEmploymentStatus) {

            System.out.println("Estado encontrado: " + estado.getText());

            if (!estado.getText().equalsIgnoreCase(estadoEsperado)) {
                return false;
            }
        }

        return true;
    }

    // TC-004
    public void seleccionarInclude(String includeOption) throws InterruptedException {

        includeFilter.click();

        Thread.sleep(2000);

        DriverContext.getDriver().findElement(
                By.xpath("//*[normalize-space()='" + includeOption + "']")
        ).click();

    }
    // TC-005
    public void seleccionarJobTitle(String jobTitle) throws InterruptedException {

        jobTitleSelector.click();

        Thread.sleep(2000);

        DriverContext.getDriver().findElement(
                By.xpath("//*[normalize-space()='" + jobTitle + "']")
        ).click();
    }

    public void seleccionarSubUnit(String subUnit) throws InterruptedException {

        subUnitSelector.click();

        Thread.sleep(2000);

        DriverContext.getDriver().findElement(
                By.xpath("//*[normalize-space()='" + subUnit + "']")
        ).click();
    }

    public void ingresarSupervisor(String supervisor) {

        supervisorName.clear();
        supervisorName.sendKeys(supervisor);

    }
}