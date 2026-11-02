package StepDefinition;

import ObjectPage.SodimacPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;


public class SodimacDefinition {

    private SodimacPage sodimacPage = new SodimacPage();

    @When("finaliza la carga visual de la pagina")
    public void finalizaLaCargaVisualDeLaPagina() {

        sodimacPage.esperarCargaPagina();
    }

    @Then("debe visualizar el encabezado principal")
    public void debeVisualizarElEncabezadoPrincipal() {
        Assert.assertTrue(
                "No se visualiza el encabezado principal",
                sodimacPage.visualizarEncabezadoPrincipal()
        );

    }

    @Then("debe visualizar el titulo de la categoria")
    public void debeVisualizarElTituloCategoria() {
        Assert.assertTrue(
                "No se visualiza el título de la categoria",
                sodimacPage.visualizarTituloCategoria()
        );
    }

    @Then("debe visualizar el contenedor de productos")
    public void debeVisualizarElContenedorProductos() {
        Assert.assertTrue(
                "No se visualiza el contenedor de productos",
                sodimacPage.visualizarContenedorProductos()
        );

    }

    @Then("cada tarjeta de producto visible debe mostrar sus elementos obligatorios")
    public void validarTarjetas() {

        Assert.assertTrue(
                "Las tarjetas no contienen todos los elementos requeridos",
                sodimacPage.validarTarjetasCompletas()
        );
    }

    @Then("los enlaces de los productos deben ser validos")
    public void validarLinksProductos() {

        Assert.assertTrue(
                "Existen productos sin enlace valido",
                sodimacPage.validarLinksProductos()
        );
    }

    @Then("las imagenes deben mostrarse correctamente")
    public void lasImagenesDebenMostrarseCorrectamente() {
        Assert.assertTrue(
                "Existen imagenes que no cargaron correctamente",
                sodimacPage.validarImagenesCargadas()
        );
    }

    @Then("los nombres no deben estar vacios")
    public void losNombresNoDebenEstarVacios() {
        Assert.assertTrue(
                "Existen productos con nombre vacio",
                sodimacPage.validarNombresNoVacios()
        );
    }

    @Then("los precios deben tener formato monetario CLP")
    public void losPreciosDebenTenerFormatoMonetarioCLP() {
        Assert.assertTrue(
                "Existen precios con formato invalido",
                sodimacPage.validarFormatoMonedaCLP()
        );
    }

    @Then("deben mostrarse productos del catalogo")
    public void debenMostrarseProductosDelCatalogo() {

        Assert.assertTrue(
                "No se encontraron suficientes productos",
                sodimacPage.validarCantidadMinimaProductos()
        );
    }

    @And("deben cargarse mas de {int} productos")
    public void validarCantidad(int minimo) {

        Assert.assertTrue(
                "La cantidad de productos es menor a " + minimo,
                sodimacPage.obtenerCantidadProductos() >= minimo
        );
    }

    @Then("ningun elemento debe superponerse")
    public void ningunElementoDebeSuperponerse() {
        Assert.assertTrue(
                "Se detectaron tarjetas superpuestas o invisibles",
                sodimacPage.validarTarjetasVisibles()
        );
    }

    @Then("no deben existir tarjetas visualmente duplicadas")
    public void noDebenExistirTarjetasDuplicadas() {
        Assert.assertTrue(
                "Se detectaron productos duplicados",
                sodimacPage.validarSinDuplicados()
        );
    }

    @Then("validar locators principales")
    public void validarLocatorsPrincipales() {

        System.out.println(
                "Header: " +
                        sodimacPage.visualizarEncabezadoPrincipal());

        System.out.println(
                "Categoria: " +
                        sodimacPage.visualizarTituloCategoria());

        System.out.println(
                "Cantidad Productos: " +
                        sodimacPage.obtenerCantidadProductos());

    }
}