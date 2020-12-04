package co.edu.uniquindio.compiladores.controladores

import co.edu.uniquindio.compiladores.lexico.AnalizadorLexico
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.semantica.AnalizadorSemantico
import co.edu.uniquindio.compiladores.sintaxis.AnalizadorSintactico
import co.edu.uniquindio.compiladores.sintaxis.UnidadDeCompilacion
import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import java.io.File
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

    @FXML lateinit var tablaErroresSemanticos:TableView<Error>

    @FXML lateinit var mensajeErrorSemantico:TableColumn<Error,String>
    @FXML lateinit var filaErrorSemantico:TableColumn<Error,Int>
    @FXML lateinit var columnaErrorSemantico:TableColumn<Error,Int>


    @FXML lateinit var arbolVisual:TreeView<String>

    private var lexico:AnalizadorLexico? = null
    private var sintactico:AnalizadorSintactico? = null
    private var semantica:AnalizadorSemantico? = null
    private var uc: UnidadDeCompilacion? = null

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

        mensajeErrorSemantico.cellValueFactory = PropertyValueFactory("error")
        filaErrorSemantico.cellValueFactory = PropertyValueFactory("fila")
        columnaErrorSemantico.cellValueFactory = PropertyValueFactory("columna")
    }

    @FXML
    fun analizarCodigo(e: ActionEvent) {

        if (codigoFuente.text.length > 0) {
            lexico = AnalizadorLexico(codigoFuente.text)
            lexico!!.analizar()

            sintactico = AnalizadorSintactico(lexico!!.listaTokens)
            sintactico!!.esUnidadDeCompilacion()

            tablaTokens.items = FXCollections.observableArrayList(lexico!!.listaTokens)
            tablaErrores.items = FXCollections.observableArrayList(sintactico!!.listaErrores)



            if (lexico!!.listaErrores.isEmpty()) {

                val sintaxis = AnalizadorSintactico(lexico!!.listaTokens)
                uc = sintaxis.esUnidadDeCompilacion()
                if (uc != null) {
                    arbolVisual.root = uc!!.getArbolVisual()
                    val semantica = AnalizadorSemantico(uc!!)
                    semantica.llenarTablaSimbolos()
                    semantica.analizarSemantica()
                    tablaErroresSemanticos.items = FXCollections.observableArrayList(semantica.erroresSemanticos)
                    print(semantica.tablaSimbolos)
                }
            }else{
                var alerta = Alert(Alert.AlertType.WARNING)
                alerta.headerText = "Mensaje"
                alerta.contentText = "Hay errores en el codigo fuente"
            }
        }
    }

    @FXML
    fun traducirCodigo(e:ActionEvent){

        if(lexico!!.listaErrores.isEmpty() && sintactico!!.listaErrores.isEmpty() ){
            val codigo =uc!!.getJavaCode()
            File("src/Principal.java").writeText(codigo)

            val runtime = Runtime.getRuntime().exec("javac src/Principal.java")
            runtime.waitFor()
            Runtime.getRuntime().exec("java Principal",null, File("src"))


        }else{
            var alerta = Alert(Alert.AlertType.WARNING)
            alerta.headerText = "Mensaje"
            alerta.contentText = "El codigo no se puede traducir por que hay errores"
            alerta.show()
        }

    }

}