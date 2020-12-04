package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Retorno(var expresion:Expresion):Sentencia() {

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String>("Retorno")
        raiz.children.add(expresion.getArbolVisual())
        return raiz
    }


    override fun getJavaCode(): String {

        var codigo = "return "+expresion.getJavaCode()+";"
        return codigo
    }


}