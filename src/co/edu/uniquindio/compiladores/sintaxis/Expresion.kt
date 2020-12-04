package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem
import co.edu.uniquindio.compiladores.lexico.Error

open class Expresion {
    open fun getArbolVisual(): TreeItem<String> {
        return TreeItem("Expresion")
    }
    open fun analizarSemantica(tablaSimbolos:TablaSimbolos, listaErrores:ArrayList<Error>, ambito: String) { }

    open fun obtenerTipo(tabalSimbolos: TablaSimbolos, ambito: String):String{
        return ""
    }
}