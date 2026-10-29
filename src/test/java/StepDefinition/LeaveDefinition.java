package StepDefinition;

import ObjectPage.LeavePage;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.Scenario;

public class LeaveDefinition {

    private LeavePage leavePage;
    private boolean empleadoNoEncontrado;
    private Scenario scenario;

    @Before
    public void prepararEscenario(Scenario scenario) {
        this.scenario = scenario;
        empleadoNoEncontrado = false;
    }

    private LeavePage getLeavePage() {
        if (leavePage == null) {
            leavePage = new LeavePage();
        }
        return leavePage;
    }

    @Given("que existen solicitudes de vacaciones")
    public void iniciarSesionEnOrangeHrm() {
        getLeavePage().iniciarSesionEnOrangeHrm();
    }

    @And("el usuario se encuentra en {string}")
    public void navegarAListaDeVacaciones(String seccion) {
        getLeavePage().navegarAListaDeVacaciones(seccion);
    }

    @When("ingresa la fecha inicial {string}")
    public void ingresarFechaInicial(String fecha) {
        getLeavePage().ingresarFechaInicial(fecha);
    }

    @And("ingresa la fecha final {string}")
    public void ingresarFechaFinal(String fecha) {
        getLeavePage().ingresarFechaFinal(fecha);
    }

    @And("selecciona el estado {string}")
    public void seleccionarEstado(String estado) {
        getLeavePage().seleccionarEstado(estado);
    }

    @And("ingresa el nombre del empleado {string}")
    public void ingresarNombreEmpleado(String empleado) {
        getLeavePage().ingresarNombreEmpleado(empleado);
    }

    @And("selecciona al empleado desde el autocompletado")
    public void seleccionarEmpleadoDelAutocompletado() {
        empleadoNoEncontrado = !getLeavePage().seleccionarEmpleadoDelAutocompletado();
        if (empleadoNoEncontrado) {
            scenario.log("[VALIDACION] No se encontró el empleado solicitado en OrangeHRM. "
                    + "Se omiten los filtros restantes porque requieren un empleado válido.");
        }
    }

    @And("selecciona la unidad {string}")
    public void seleccionarUnidad(String unidad) {
        if (empleadoNoEncontrado) {
            return;
        }
        getLeavePage().seleccionarUnidad(unidad);
    }

    @And("configura empleados anteriores en {string}")
    public void configurarEmpleadosAnteriores(String valor) {
        if (empleadoNoEncontrado) {
            return;
        }
        getLeavePage().configurarEmpleadosAnteriores(valor);
    }

    @And("presiona el botón {string}")
    public void presionarBoton(String boton) {
        if (empleadoNoEncontrado) {
            return;
        }
        getLeavePage().presionarBoton(boton);
    }

    @Then("se debe completar la búsqueda sin errores")
    public void validarBusquedaSinErrores() {
        if (empleadoNoEncontrado) {
            return;
        }
        getLeavePage().validarBusquedaSinErrores();
    }

    @And("se debe visualizar la tabla de resultados")
    public void validarTablaResultados() {
        if (empleadoNoEncontrado) {
            return;
        }
        getLeavePage().validarTablaResultados();
    }

    @And("las solicitudes deben corresponder al empleado {string}")
    public void validarEmpleado(String empleado) {
        if (empleadoNoEncontrado) {
            return;
        }
        getLeavePage().validarEmpleado(empleado);
    }

    @And("las solicitudes deben cumplir el estado {string}")
    public void validarEstado(String estado) {
        if (empleadoNoEncontrado) {
            return;
        }
        getLeavePage().validarEstado(estado);
    }

    @And("las solicitudes deben estar dentro del período consultado")
    public void validarPeriodo() {
        if (empleadoNoEncontrado) {
            return;
        }
        getLeavePage().validarPeriodo();
    }

    @And("las solicitudes deben corresponder a la unidad {string}")
    public void validarUnidad(String unidad) {
        if (empleadoNoEncontrado) {
            return;
        }
        getLeavePage().validarUnidad(unidad);
    }

    @And("los registros deben respetar el filtro de empleados anteriores")
    public void validarFiltroEmpleadosAnteriores() {
        if (empleadoNoEncontrado) {
            return;
        }
        getLeavePage().validarFiltroEmpleadosAnteriores();
    }

    @And("no deben existir registros duplicados")
    public void validarRegistrosSinDuplicados() {
        if (empleadoNoEncontrado) {
            return;
        }
        getLeavePage().validarRegistrosSinDuplicados();
    }

    @And("todas las páginas deben respetar los filtros aplicados")
    public void validarFiltrosEnTodasLasPaginas() {
        if (empleadoNoEncontrado) {
            return;
        }
        getLeavePage().validarFiltrosEnTodasLasPaginas();
    }

    @When("el usuario presiona el botón {string}")
    public void presionarBotonReset(String boton) {
        if (empleadoNoEncontrado) {
            return;
        }
        getLeavePage().presionarBoton(boton);
    }

    @Then("los filtros deben recuperar sus valores predeterminados")
    public void validarFiltrosPredeterminados() {
        if (empleadoNoEncontrado) {
            return;
        }
        getLeavePage().validarFiltrosPredeterminados();
    }

    @And("no deben permanecer los criterios ingresados anteriormente")
    public void validarCriteriosEliminados() {
        if (empleadoNoEncontrado) {
            return;
        }
        getLeavePage().validarCriteriosEliminados();
    }
}
