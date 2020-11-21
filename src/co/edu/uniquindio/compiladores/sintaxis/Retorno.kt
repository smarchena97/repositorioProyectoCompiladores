package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class Retorno():Sentencia() {

    var expresionAritmetica:ExpresionAritmetica? = null
    var expresionRelacional:ExpresionRelacional? = null
    var identificador:Token? = null

    constructor(expresionAritmetica: ExpresionAritmetica?):this(){
        this.expresionAritmetica = expresionAritmetica
    }

    constructor(expresionRelacional: ExpresionRelacional?):this(){
        this.expresionRelacional = expresionRelacional
    }

    constructor(identificador:Token?):this(){
        this.identificador = identificador
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String>("Retorno")
        if(expresionAritmetica != null){
            raiz.children.add(expresionAritmetica!!.getArbolVisual())
        }
        if(expresionRelacional != null){
            raiz.children.add(expresionRelacional!!.getArbolVisual())
        }
        if(identificador != null){
            raiz.children.add(TreeItem("Identificador:${identificador!!.lexema}"))
        }
        return raiz
    }


}