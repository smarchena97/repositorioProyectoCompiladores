package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Variable(var nombreVariable: Token){
    override fun toString(): String {
        return "Variable(nombre=$nombreVariable)"
    }

    fun getArbolVisual(): TreeItem<String> {
        return TreeItem(nombreVariable.lexema)
    }

}