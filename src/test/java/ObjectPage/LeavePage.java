package ObjectPage;

import Control.BaseController;
import Control.DriverContext;
import Control.NavSelector;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LeavePage extends BaseController {

    private static final String ORANGE_HRM_URL =
            "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
    private final List<String> defaultFilters = new ArrayList<>();
    private boolean incluirEmpleadosAnteriores;
    private String unidadSeleccionada;
    private String empleadoIngresado;

    public void iniciarSesionEnOrangeHrm() {
        boolean iniciarDriver = DriverContext.getDriver() == null;
        if (!iniciarDriver) {
            try {
                DriverContext.getDriver().getCurrentUrl();
            } catch (WebDriverException exception) {
                iniciarDriver = true;
            }
        }
        if (iniciarDriver) {
            DriverContext.setUp(
                    NavSelector.seleccionNavegador(System.getProperty("nav", "default")),
                    ORANGE_HRM_URL
            );
        }

        WebDriverWait wait = waitFor();
        By campoUsuario = By.name("username");
        By menuLeave = By.xpath("//span[normalize-space()='Leave']");
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(campoUsuario),
                ExpectedConditions.visibilityOfElementLocated(menuLeave)
        ));

        List<WebElement> camposUsuario = driver().findElements(campoUsuario);
        if (!camposUsuario.isEmpty() && camposUsuario.get(0).isDisplayed()) {
            camposUsuario.get(0).sendKeys("Admin");
            driver().findElement(By.name("password")).sendKeys("admin123");
            driver().findElement(By.cssSelector("button[type='submit']")).click();
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(menuLeave));
    }

    public void navegarAListaDeVacaciones(String seccion) {
        Assert.assertEquals("La sección esperada debe ser Leave List", "Leave List", seccion);
        WebDriverWait wait = waitFor();
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[normalize-space()='Leave']")
        )).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//label[normalize-space()='From Date']")
        ));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".oxd-table")));
        guardarFiltrosPredeterminados();
    }

    public void ingresarFechaInicial(String fecha) {
        ingresarFecha(0, fecha);
    }

    public void ingresarFechaFinal(String fecha) {
        ingresarFecha(1, fecha);
    }

    public void seleccionarEstado(String estado) {
        WebElement grupo = grupoEstado();
        List<WebElement> estadosActuales = grupo.findElements(By.cssSelector(".oxd-chip"));
        for (WebElement estadoActual : estadosActuales) {
            estadoActual.findElement(By.cssSelector("i")).click();
        }
        grupo.findElement(By.cssSelector(".oxd-select-text")).click();
        seleccionarOpcionAbierta(estado);
    }

    public void ingresarNombreEmpleado(String empleado) {
        WebElement campo = campoPorEtiqueta("Employee Name");
        campo.clear();
        campo.sendKeys(empleado);
        empleadoIngresado = empleado;
    }

    public boolean seleccionarEmpleadoDelAutocompletado() {
        String empleado = campoPorEtiqueta("Employee Name").getAttribute("value");
        Assert.assertFalse("Debe ingresarse un nombre de empleado", empleado == null || empleado.isBlank());
        By opcionEmpleado = By.xpath(
                "//div[contains(@class,'oxd-autocomplete-option')][contains(normalize-space(.),"
                        + xpathLiteral(empleado) + ")]"
        );
        By sinResultados = By.xpath(
                "//div[contains(@class,'oxd-autocomplete-option') and normalize-space(.)='No Records Found']"
        );
        By mensajeError = By.cssSelector(".oxd-input-field-error-message");
        new WebDriverWait(driver(), Duration.ofSeconds(5)).until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(opcionEmpleado),
                ExpectedConditions.visibilityOfElementLocated(sinResultados),
                ExpectedConditions.visibilityOfElementLocated(mensajeError)
        ));
        List<WebElement> opciones = driver().findElements(opcionEmpleado);
        if (opciones.isEmpty()) {
            return false;
        }
        opciones.get(0).click();
        return true;
    }

    public void seleccionarUnidad(String unidad) {
        seleccionarOpcion("Sub Unit", unidad);
        unidadSeleccionada = unidad;
    }

    public void configurarEmpleadosAnteriores(String valor) {
        Assert.assertTrue("El filtro solo admite Yes o No", valor.equalsIgnoreCase("Yes")
                || valor.equalsIgnoreCase("No"));
        WebElement checkbox = checkboxEmpleadosAnteriores();
        boolean debeEstarMarcado = valor.equalsIgnoreCase("Yes");
        incluirEmpleadosAnteriores = debeEstarMarcado;
        if (checkbox.isSelected() != debeEstarMarcado) {
            checkbox.findElement(By.xpath("following-sibling::*")).click();
        }
        Assert.assertEquals("No se pudo configurar Include Past Employees",
                debeEstarMarcado, checkbox.isSelected());
    }

    public void presionarBoton(String boton) {
        Assert.assertTrue("Solo se admite Search o Reset",
                boton.equalsIgnoreCase("Search") || boton.equalsIgnoreCase("Reset"));
        waitFor().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[normalize-space()=" + xpathLiteral(boton) + "]")
        )).click();
        waitFor().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".oxd-table")));
    }

    public void validarBusquedaSinErrores() {
        Assert.assertTrue("No se muestra la tabla de resultados",
                driver().findElement(By.cssSelector(".oxd-table")).isDisplayed());
        Assert.assertFalse("La página muestra un mensaje de error",
                driver().findElements(By.cssSelector(".oxd-input-field-error-message")).size() > 0);
    }

    public void validarTablaResultados() {
        Assert.assertTrue("No se muestra el encabezado de la tabla",
                driver().findElement(By.cssSelector(".oxd-table-header")).isDisplayed());
    }

    public void validarEmpleado(String empleado) {
        validarTodasLasPaginas(empleado, null, null, null);
    }

    public void validarEstado(String estado) {
        validarTodasLasPaginas(null, estado, null, null);
    }

    public void validarPeriodo() {
        List<String> fechas = obtenerFechasConsultadas();
        validarTodasLasPaginas(null, null, fechas.get(0), fechas.get(1));
    }

    public void validarUnidad(String unidad) {
        Assert.assertEquals("El filtro de unidad no coincide con el criterio aplicado",
                unidad, valorSeleccionado("Sub Unit"));
    }

    public void validarFiltroEmpleadosAnteriores() {
        WebElement checkbox = checkboxEmpleadosAnteriores();
        Assert.assertEquals("Los resultados no respetan el filtro de empleados anteriores",
                incluirEmpleadosAnteriores, checkbox.isSelected());
    }

    public void validarRegistrosSinDuplicados() {
        Set<String> registros = new HashSet<>();
        recorrerTodasLasPaginas(registro -> Assert.assertTrue(
                "Se encontró un registro duplicado: " + registro,
                registros.add(registro)
        ));
    }

    public void validarFiltrosEnTodasLasPaginas() {
        String empleado = campoPorEtiqueta("Employee Name").getAttribute("value");
        String estado = valorSeleccionado("Show Leave with Status");
        List<String> fechas = obtenerFechasConsultadas();
        Assert.assertEquals("El filtro de unidad cambió durante la paginación",
                unidadSeleccionada, valorSeleccionado("Sub Unit"));
        validarFiltroEmpleadosAnteriores();
        validarTodasLasPaginas(empleado, estado, fechas.get(0), fechas.get(1));
    }

    public void validarFiltrosPredeterminados() {
        List<String> filtrosActuales = leerFiltros();
        Assert.assertEquals("Reset no restituyó los valores predeterminados",
                defaultFilters, filtrosActuales);
    }

    public void validarCriteriosEliminados() {
        List<String> filtros = leerFiltros();
        Assert.assertEquals("Reset dejó criterios de búsqueda en el formulario",
                defaultFilters, filtros);
        Assert.assertFalse("El nombre ingresado continúa en el formulario",
                empleadoIngresado != null && empleadoIngresado.equals(filtros.get(2)));
    }

    private void ingresarFecha(int indice, String fecha) {
        String etiqueta = indice == 0 ? "From Date" : "To Date";
        WebElement campo = campoPorEtiqueta(etiqueta);
        campo.click();
        campo.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        campo.sendKeys(Keys.BACK_SPACE);
        campo.sendKeys(fecha);
    }

    private void seleccionarOpcion(String etiqueta, String opcion) {
        WebElement grupo = grupoPorEtiqueta(etiqueta);
        grupo.findElement(By.cssSelector(".oxd-select-text")).click();
        seleccionarOpcionAbierta(opcion);
    }

    private void seleccionarOpcionAbierta(String opcion) {
        waitFor().until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//div[contains(@class,'oxd-select-option') and normalize-space(.)="
                        + xpathLiteral(opcion) + "]"
        ))).click();
    }

    private WebElement grupoPorEtiqueta(String etiqueta) {
        return driver().findElement(By.xpath(
                "//label[normalize-space()=" + xpathLiteral(etiqueta)
                        + "]/ancestor::div[contains(@class,'oxd-input-group')]"
        ));
    }

    private WebElement grupoEstado() {
        return driver().findElement(By.xpath(
                "//label[contains(normalize-space(.), 'Show Leave with Status')]/ancestor::div[contains(@class,'oxd-input-group')]"
        ));
    }

    private WebElement campoPorEtiqueta(String etiqueta) {
        return driver().findElement(By.xpath(
                "//label[normalize-space()=" + xpathLiteral(etiqueta)
                        + "]/ancestor::div[contains(@class,'oxd-input-group')]//input"
        ));
    }

    private String valorSeleccionado(String etiqueta) {
        WebElement grupo = etiqueta.equals("Show Leave with Status") ? grupoEstado() : grupoPorEtiqueta(etiqueta);
        if (etiqueta.equals("Show Leave with Status")) {
            List<WebElement> etiquetasSeleccionadas = grupo.findElements(By.cssSelector(".oxd-chip"));
            if (!etiquetasSeleccionadas.isEmpty()) {
                List<String> valores = new ArrayList<>();
                for (WebElement seleccion : etiquetasSeleccionadas) {
                    valores.add(seleccion.getText().trim());
                }
                return String.join(", ", valores);
            }
        }
        return grupo.findElement(By.cssSelector(".oxd-select-text")).getText().trim();
    }

    private WebElement checkboxEmpleadosAnteriores() {
        List<WebElement> checkboxes = driver().findElements(By.cssSelector("input[type='checkbox']"));
        Assert.assertFalse("No se encontró el switch Include Past Employees", checkboxes.isEmpty());
        return checkboxes.get(0);
    }

    private void guardarFiltrosPredeterminados() {
        defaultFilters.clear();
        defaultFilters.addAll(leerFiltros());
    }

    private List<String> leerFiltros() {
        WebElement checkbox = checkboxEmpleadosAnteriores();
        List<String> filtros = new ArrayList<>();
        filtros.add(campoPorEtiqueta("From Date").getAttribute("value"));
        filtros.add(campoPorEtiqueta("To Date").getAttribute("value"));
        filtros.add(campoPorEtiqueta("Employee Name").getAttribute("value"));
        filtros.add(valorSeleccionado("Show Leave with Status"));
        filtros.add(valorSeleccionado("Sub Unit"));
        filtros.add(Boolean.toString(checkbox.isSelected()));
        return filtros;
    }

    private List<String> obtenerFechasConsultadas() {
        List<String> fechas = new ArrayList<>();
        fechas.add(campoPorEtiqueta("From Date").getAttribute("value"));
        fechas.add(campoPorEtiqueta("To Date").getAttribute("value"));
        return fechas;
    }

    private void validarTodasLasPaginas(String empleado, String estado, String desde, String hasta) {
        volverALaPrimeraPagina();
        recorrerTodasLasPaginas(registro -> {
            if (empleado != null && !empleado.isBlank()) {
                Assert.assertTrue("El resultado no corresponde al empleado " + empleado + ": " + registro,
                        registro.contains(empleado));
            }
            if (estado != null && !estado.isBlank() && !estado.equalsIgnoreCase("All")) {
                Assert.assertTrue("El resultado no cumple el estado " + estado + ": " + registro,
                        registro.contains(estado));
            }
            if (desde != null && hasta != null) {
                validarFechasDeRegistro(registro, LocalDate.parse(desde), LocalDate.parse(hasta));
            }
        });
    }

    private void validarFechasDeRegistro(String registro, LocalDate desde, LocalDate hasta) {
        List<LocalDate> fechas = new ArrayList<>();
        for (String texto : registro.split("\\s+")) {
            try {
                fechas.add(LocalDate.parse(texto, DateTimeFormatter.ISO_LOCAL_DATE));
            } catch (DateTimeParseException ignored) {
                // Other table values are not dates.
            }
        }
        Assert.assertTrue("No se encontraron fechas ISO en el resultado: " + registro, fechas.size() >= 2);
        Assert.assertFalse("La solicitud está fuera del período consultado: " + registro,
                fechas.get(0).isBefore(desde) || fechas.get(1).isAfter(hasta));
    }

    private void recorrerTodasLasPaginas(RegistroValidator validator) {
        while (true) {
            List<WebElement> filas = driver().findElements(By.cssSelector(".oxd-table-body .oxd-table-card"));
            for (WebElement fila : filas) {
                validator.validar(fila.getText().replaceAll("\\s+", " ").trim());
            }
            List<WebElement> siguiente = driver().findElements(By.cssSelector("button[aria-label='Next']"));
            if (siguiente.isEmpty() || !siguiente.get(0).isEnabled()) {
                break;
            }
            String paginaActual = obtenerPaginaActual();
            siguiente.get(0).click();
            waitFor().until(driver -> !obtenerPaginaActual().equals(paginaActual));
        }
    }

    private void volverALaPrimeraPagina() {
        List<WebElement> anterior = driver().findElements(By.cssSelector("button[aria-label='Previous']"));
        while (!anterior.isEmpty() && anterior.get(0).isEnabled()) {
            String paginaActual = obtenerPaginaActual();
            anterior.get(0).click();
            waitFor().until(driver -> !obtenerPaginaActual().equals(paginaActual));
            anterior = driver().findElements(By.cssSelector("button[aria-label='Previous']"));
        }
    }

    private String obtenerPaginaActual() {
        return driver().findElement(By.cssSelector(".oxd-pagination-page-item--page")).getText().trim();
    }

    private WebDriver driver() {
        WebDriver currentDriver = DriverContext.getDriver();
        Assert.assertNotNull("WebDriver no está inicializado", currentDriver);
        return currentDriver;
    }

    private WebDriverWait waitFor() {
        return new WebDriverWait(driver(), Duration.ofSeconds(20));
    }

    private String xpathLiteral(String value) {
        if (!value.contains("'")) {
            return "'" + value + "'";
        }
        if (!value.contains("\"")) {
            return "\"" + value + "\"";
        }
        String[] parts = value.split("'", -1);
        List<String> quotedParts = new ArrayList<>();
        for (String part : parts) {
            quotedParts.add("'" + part + "'");
        }
        return "concat(" + String.join(", \"'\", ", quotedParts) + ")";
    }

    @FunctionalInterface
    private interface RegistroValidator {
        void validar(String registro);
    }
}
