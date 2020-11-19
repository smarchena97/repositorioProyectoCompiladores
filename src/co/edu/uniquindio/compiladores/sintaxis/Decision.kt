package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

class Decision(var expresionLogica: Expresion, var listaSentencia: ArrayList<Sentencia>, var listaSentenciaAltro:ArrayList<Sentencia>?):Sentencia() {

    override fun toString(): String {
        return "Desicion(expresionLogica=$expresionLogica, listaSentencia=$listaSentencia)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String>("Desicion")
        return  raiz
    }

}