package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class Impresion():Sentencia() {
    var expresionAritmetica:ExpresionAritmetica? = null
    var expresionRelacional:ExpresionRelacional? = null
    var identificador:Token? = null
    var cadena:Token? = null

    constructor(expresionAritmetica: ExpresionAritmetica?):this(){
        this.expresionAritmetica = expresionAritmetica
    }

    constructor(expresionRelacional: ExpresionRelacional?):this(){
        this.expresionRelacional = expresionRelacional
    }

    constructor(token:Token):this(){
        if(token.categoria == Categoria.IDENTIFICADOR){
            this.identificador = token
        }
        if(token.categoria == Categoria.CADENA){
            this.cadena = token
        }
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String>("impresion")
        if(cadena != null){
            raiz.children.add(TreeItem("Cadena:${cadena!!.lexema}"))
        }
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