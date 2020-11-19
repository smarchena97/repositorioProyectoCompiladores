package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

class Dato(var nombre: ArrayList<Entero>) {
    override fun toString(): String {
        return "Dato(valor=$nombre)"
    }
/**
    fun getArbolVisual(): TreeItem<String> {
        return TreeItem("${nombre.lexema}")
    }*/
}