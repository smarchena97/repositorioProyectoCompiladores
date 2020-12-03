package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

open class Expresion {
    open fun getArbolVisual(): TreeItem<String> {
        return TreeItem("Expresion")
    }
    open fun obtenerTipo(tabalSimbolos: TablaSimbolos, ambito:String, listaErrores: ArrayList<Error>):String{
        return ""
    }
}