package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class Lectura(var cadena:Token):Sentencia() {

    override fun toString(): String {
        return "Lectura(cadena='$cadena')"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String>("Lectura")
        raiz.children.add(TreeItem("Cadena:${cadena.lexema}"))
        return raiz
    }
}