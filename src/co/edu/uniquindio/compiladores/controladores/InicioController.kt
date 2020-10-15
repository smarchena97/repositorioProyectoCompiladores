package co.edu.uniquindio.compiladores.controladores

import co.edu.uniquindio.compiladores.lexico.AnalizadorLexico
import co.edu.uniquindio.compiladores.lexico.Token
import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.TextArea
import javafx.scene.control.cell.PropertyValueFactory

class InicioController {

    @FXML lateinit var codigoFuente:TextArea

    @FXML lateinit var tablaTokens:TableView<Token>

    @FXML lateinit var colLexema:TableColumn<Token, String>
    @FXML lateinit var colCategoria:TableColumn<Token, String>
    @FXML lateinit var colFila:TableColumn<Token, Int>
    @FXML lateinit var colColumna:TableColumn<Token,Int>

    @FXML
    fun analizarCodigo( e : ActionEvent ){

        if(codigoFuente.text.length > 0){
            val lexico = AnalizadorLexico(codigoFuente.text)
            lexico.analizar()

            colLexema.cellValueFactory = PropertyValueFactory("lexema")
            colCategoria.cellValueFactory = PropertyValueFactory("categoria")
            colFila.cellValueFactory = PropertyValueFactory("fila")
            colColumna.cellValueFactory = PropertyValueFactory("columna")

            tablaTokens.items = FXCollections.observableArrayList( lexico.listaTokens )
            print(lexico.listaTokens)
        }
    }
}