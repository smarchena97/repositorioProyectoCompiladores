package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

open class Sentencia {

    open fun getArbolVisual(): TreeItem<String> {
        return TreeItem("Sentencia")
    }
}