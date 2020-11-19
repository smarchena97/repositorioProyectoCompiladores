package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token

class ExpresionRelacional():Expresion() {

    var operadorRelacional:Token? = null
    var expresionA1:ExpresionAritmetica? = null
    var expresionA2:ExpresionAritmetica? = null
    var expresionC1:ExpresionCadena? = null
    var expresionC2:ExpresionCadena? = null

    constructor(expresionA1:ExpresionAritmetica,operadorRelacional:Token,expresionA2:ExpresionAritmetica):this(){
        this.expresionA1 = expresionA1
        this.operadorRelacional = operadorRelacional
        this.expresionA2 =expresionA2
    }
    constructor(expresionC1:ExpresionCadena,operadorRelacional:Token, expresionC2:ExpresionCadena):this(){
        this.expresionC1 = expresionC1
        this.operadorRelacional = operadorRelacional
        this.expresionC2 =expresionC2
    }

}