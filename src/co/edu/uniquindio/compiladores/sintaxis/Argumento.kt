package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class Argumento(var nombre: Token) {
    override fun toString(): String {
        return "Argumento(nombre=$nombre)"
    }

    fun getArbolVisual(): TreeItem<String> {
        return TreeItem("${nombre.lexema}")
    }
}