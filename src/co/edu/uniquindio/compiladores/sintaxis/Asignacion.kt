package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class Asignacion(var identificador:Token , var expresion: Expresion):Sentencia() {
    override fun toString(): String {
        return "Asignacion(identificador=$identificador, expresion=$expresion)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String>("asignacion")
        raiz.children.add(TreeItem("Identificador:${identificador.lexema}"))
        raiz.children.add(expresion.getArbolVisual())
        return raiz
    }
}