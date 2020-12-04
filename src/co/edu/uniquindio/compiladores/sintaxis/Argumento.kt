package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Argumento(var nombre: Token): Expresion() {
    override fun toString(): String {
        return "Argumento(nombre=$nombre)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        return TreeItem(nombre.lexema)
    }

    override fun getJavaCode():String{
        return nombre.lexema
    }

}