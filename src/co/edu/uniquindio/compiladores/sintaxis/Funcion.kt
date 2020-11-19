package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class Funcion(var nombreFuncion:Token, var tipoRetorno:Token, var listaParametros: ArrayList<Parametro>?, var listaSentencias:ArrayList<Sentencia>) {

    override fun toString(): String {
        return "Funcion(nombreFuncion=$nombreFuncion, tipoRetorno=$tipoRetorno, listaParametros=$listaParametros, listaSentencias=$listaSentencias)"
    }
    fun getArbolVisual():TreeItem<String>{
        var raiz = TreeItem<String>("Funcion")
        raiz.children.add(TreeItem("Nombre: "+nombreFuncion.lexema))
        raiz.children.add(TreeItem("Tipo Retorno: "+tipoRetorno.lexema))
        var raizParametros = TreeItem("Parametros")
        for(i in listaParametros!!){
            raizParametros.children.add(i.getArbolVisual())
        }
            raiz.children.add(raizParametros)

      var raizSentencias= TreeItem("Sentencias")

        for(i in listaSentencias){
            raizSentencias.children.add(i.getArbolVisual())
        }
        raiz.children.add(raizSentencias)

        return raiz
    }
}