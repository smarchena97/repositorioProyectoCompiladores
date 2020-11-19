package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

open class Expresion {

    open fun getArbolVisual(): TreeItem<String> {
        return TreeItem("Expresion")
    }
}