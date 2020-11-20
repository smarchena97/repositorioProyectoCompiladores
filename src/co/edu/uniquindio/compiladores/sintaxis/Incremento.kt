package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class Incremento(var nombre: Token): Sentencia() {

    override fun toString(): String {
        return "Incremento(nombre=$nombre)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz= TreeItem("Incremento")
        raiz.children.add(TreeItem("${nombre.lexema} ++"))
        return raiz
    }
}