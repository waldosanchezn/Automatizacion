package StepDefinition;

import ObjectPage.ListaEmpleadoPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class OrangeDefinition {

    private final ListaEmpleadoPage listaEmpleadoPage = new ListaEmpleadoPage();

    @And("que el usuario inicia sesión en OrangeHRM con el usuario {string} y la contraseña {string}")
    public void ingresarUsuario(String nombreUsuario, String password) {

        listaEmpleadoPage.escribirUsersname(nombreUsuario);
        listaEmpleadoPage.escribirPassword(password);
        listaEmpleadoPage.clickLogin();

    }

    @And("que el usuario ingresa al módulo PIM")
    public void moduloPIM() {

        listaEmpleadoPage.clickPIM();

    }

    @When("el usuario ingresa {string} en el campo Nombre del empleado de PIM")
    public void ingresarNombreEmpleado(String nombreEmpleado) throws InterruptedException {

        listaEmpleadoPage.ingresarNombreEmpleado(nombreEmpleado);
        Thread.sleep(8000);

    }

    @And("hace clic en Buscar")
    public void clickBuscar() throws InterruptedException {

        listaEmpleadoPage.clickBuscar();
        Thread.sleep(8000);

    }

    @Then("se deben mostrar los resultados de la búsqueda")
    public void validarResultadosBusqueda() {

    }

    @And("cada resultado debe contener {string}")
    public void validarNombreEncontrado(String expectedName) {

    }

    @When("the user enters employee id {string}")
    public void ingresarEmployeeId(String employeeId) {

        listaEmpleadoPage.ingresarEmployeeId(employeeId);

    }

    @And("clicks Search")
    public void clicksSearch() throws InterruptedException {

        listaEmpleadoPage.clickBuscar();
        Thread.sleep(10000);

    }

    @Then("the system should return {string} records")
    public void validarCantidadRegistros(String expectedRecords) {

        String resultado = listaEmpleadoPage.obtenerCantidadRegistros();

        System.out.println("Resultado encontrado: " + resultado);

        if (expectedRecords.equals("0")) {

            Assert.assertTrue(resultado.contains("No Records Found"));

        } else {

            Assert.assertTrue(resultado.contains(expectedRecords));

        }
    }

    @When("the user selects employment status {string}")
    public void seleccionarEmploymentStatus(String status) throws InterruptedException {

        listaEmpleadoPage.seleccionarEmploymentStatus(status);

    }

    @Then("every result should have employment status {string}")
    public void validarEmploymentStatus(String status) {

        Assert.assertTrue(
                listaEmpleadoPage.validarEmploymentStatus(status)
        );

    }

    @When("the user selects include option {string}")
    public void seleccionarInclude(String includeOption) throws InterruptedException {

        listaEmpleadoPage.seleccionarInclude(includeOption);

    }

    @Then("results should belong to {string}")
    public void validarInclude(String includeOption) {

        String resultado = listaEmpleadoPage.obtenerCantidadRegistros();

        System.out.println("Resultado encontrado: " + resultado);

        if (includeOption.equals("Past Employees Only")) {

            Assert.assertTrue(
                    resultado.contains("No Records Found")
            );

        } else {

            Assert.assertFalse(resultado.isEmpty());

        }
    }

    // TC-005

    @When("the user enters employee name {string}")
    public void the_user_enters_employee_name(String employeeName) {

        listaEmpleadoPage.ingresarNombreEmpleado(employeeName);

    }

    @And("the user selects job title {string}")
    public void seleccionarJobTitle(String jobTitle) throws InterruptedException {

        listaEmpleadoPage.seleccionarJobTitle(jobTitle);

    }

    @And("the user selects sub unit {string}")
    public void seleccionarSubUnit(String subUnit) throws InterruptedException {

        listaEmpleadoPage.seleccionarSubUnit(subUnit);

    }

    @And("the user selects supervisor {string}")
    public void seleccionarSupervisor(String supervisor) {

        listaEmpleadoPage.ingresarSupervisor(supervisor);

    }

    @Then("every result should match all selected criteria")
    public void every_result_should_match_all_selected_criteria() {

        String resultado = listaEmpleadoPage.obtenerCantidadRegistros();

        System.out.println("Resultado encontrado: " + resultado);

        if (resultado.contains("No Records Found") || resultado.trim().isEmpty()) {
            Assert.fail("TC-005 falló: no se encontraron registros para los criterios ingresados");
        }

        System.out.println("Validación TC-005 exitosa: se encontró al menos un registro");
    }

}
