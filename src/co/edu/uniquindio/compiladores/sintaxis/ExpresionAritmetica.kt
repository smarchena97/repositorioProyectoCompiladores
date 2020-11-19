package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class ExpresionAritmetica():Expresion() {
    var expresion1:ExpresionAritmetica? = null
    var operador:Token? = null
    var expresion2:ExpresionAritmetica? = null
    var valorNumerico:ValorNumerico? = null

    constructor(expresion1: ExpresionAritmetica,operador:Token, expresion2: ExpresionAritmetica):this(){
        this.expresion1 = expresion1
        this.operador = operador
        this.expresion2 = expresion2
    }
    constructor(expresion1:ExpresionAritmetica):this(){
        this.expresion1 = expresion1
    }
    constructor(valorNumerico: ValorNumerico,operador: Token,expresion2: ExpresionAritmetica):this(){
        this.valorNumerico = valorNumerico
        this.operador = operador
        this.expresion2 = expresion2
    }
    constructor(valorNumerico: ValorNumerico):this(){
        this.valorNumerico = valorNumerico
    }

    fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String>("Expresion Aritmetica")
        return  raiz
    }

}