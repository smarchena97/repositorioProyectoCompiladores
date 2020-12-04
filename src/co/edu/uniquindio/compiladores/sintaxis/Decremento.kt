package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class Decremento(var nombre: Token):Sentencia() {

    override fun toString(): String {
        return "Decremento(nombre=$nombre)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz= TreeItem("Decremento")
        raiz.children.add(TreeItem("${nombre.lexema} --"))
        return raiz
    }

    override fun getJavaCode(): String {
        return nombre.lexema+"--;"
    }
}