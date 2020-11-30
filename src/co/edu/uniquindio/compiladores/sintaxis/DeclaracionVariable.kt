package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class DeclaracionVariable(var tipoVariable:Token, var listaVariables:ArrayList<Token>, var tipoDato:Token):Sentencia() {
    override fun toString(): String {
        return "DeclaracionVariable(tipoVariable=$tipoVariable, listaVariables=$listaVariables, tipoDato=$tipoDato)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String>("Declaracion")
        raiz.children.add(TreeItem("tipoVariable: ${tipoVariable.lexema}"))
        for (i in listaVariables){
            raiz.children.add(TreeItem("Variable: ${i.lexema}"))
        }
        raiz.children.add(TreeItem("tipoDato:${tipoDato.lexema}"))
        return raiz
    }

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String) {
        for (lv in listaVariables){
            tablaSimbolos.guardarSimboloValor(lv.lexema, tipoDato.lexema , true, ambito, lv.fila, lv.columna)
        }
    }
}