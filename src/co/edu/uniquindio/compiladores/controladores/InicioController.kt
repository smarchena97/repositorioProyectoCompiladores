package co.edu.uniquindio.compiladores.controladores

import co.edu.uniquindio.compiladores.lexico.AnalizadorLexico
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.sintaxis.AnalizadorSintactico
import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import java.net.URL
import java.util.*

class InicioController: Initializable {

    @FXML
    lateinit var codigoFuente: TextArea

    @FXML
    lateinit var tablaTokens: TableView<Token>

    @FXML
    lateinit var colLexema: TableColumn<Token, String>
    @FXML
    lateinit var colCategoria: TableColumn<Token, String>
    @FXML
    lateinit var colFila: TableColumn<Token, Int>
    @FXML
    lateinit var colColumna: TableColumn<Token, Int>

    @FXML lateinit var tablaErrores:TableView<Error>

    @FXML lateinit var mensajeError:TableColumn<Error,String>
    @FXML lateinit var filaError:TableColumn<Error,Int>
    @FXML lateinit var columnaError:TableColumn<Error,Int>


    @FXML lateinit var arbolVisual:TreeView<String>

    /**
     * Construye las tablas de la interfaz grafica
     */
    override fun initialize(location: URL?, resources: ResourceBundle?) {
        colLexema.cellValueFactory = PropertyValueFactory("lexema")
        colCategoria.cellValueFactory = PropertyValueFactory("categoria")
        colFila.cellValueFactory = PropertyValueFactory("fila")
        colColumna.cellValueFactory = PropertyValueFactory("columna")

        mensajeError.cellValueFactory = PropertyValueFactory("error")
        filaError.cellValueFactory = PropertyValueFactory("fila")
        columnaError.cellValueFactory = PropertyValueFactory("columna")
    }

    @FXML
    fun analizarCodigo(e: ActionEvent) {

        if (codigoFuente.text.length > 0) {
            val lexico = AnalizadorLexico(codigoFuente.text)
            lexico.analizar()

            tablaTokens.items = FXCollections.observableArrayList(lexico.listaTokens)
            tablaErrores.items = FXCollections.observableArrayList(lexico.listaErrores)



            if (lexico.listaErrores.isEmpty()) {

                val sintaxis = AnalizadorSintactico(lexico.listaTokens)
                val uc = sintaxis.esUnidadDeCompilacion()
                if (uc != null) {
                    arbolVisual.root = uc.getArbolVisual()
                }
            }else{
                var alerta = Alert(Alert.AlertType.WARNING)
                alerta.headerText = "Mensaje"
                alerta.contentText = "Hay errores en el codigo fuente"
            }
        }
    }


}