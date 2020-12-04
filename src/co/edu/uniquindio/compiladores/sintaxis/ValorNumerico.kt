package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class ValorNumerico(var signo:Token?, var valor:Token) {

    fun getArbolVisual(): TreeItem<String>{
        var raiz = TreeItem("ValorNumerico")

        if(signo != null && valor != null){
            raiz.children.add(TreeItem("Signo:${signo!!.lexema}"))
            raiz.children.add(TreeItem("Valor:${valor.lexema}"))
        }
        if(signo == null && valor != null){
            raiz.children.add(TreeItem("Valor:${valor.lexema}"))
        }

        return raiz
    }

    override fun toString(): String {
        return "ValorNumerico(signo=$signo, valor=$valor)"
    }

    fun getJavaCode():String{
        var codigo = ""

        if(signo != null && valor != null) {
            codigo = signo!!.getJavaCode() + "" + valor.getJavaCode()
        }
        if(signo == null && valor != null){
            codigo = ""+valor.getJavaCode()
        }
        return codigo
    }


}