package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token

class ExpresionLogica():Expresion() {
    var expR:ExpresionRelacional? = null
    var expL1:ExpresionLogica? = null
    var expL2:ExpresionLogica? = null
    var valorLogico:Token? = null
    var operadorL:Token? = null

    constructor(expL:ExpresionLogica, operadorL:Token, expL2:ExpresionLogica):this(){
        this.expL1 = expL
        this.operadorL = operadorL
        this.expL2 = expL2

    }
    constructor(expL:ExpresionLogica):this(){
        this.expL1 = expL
    }

    constructor(expR:ExpresionRelacional, operadorL:Token, expL2:ExpresionLogica):this(){
        this.expR = expR
        this.operadorL = operadorL
        this.expL2 = expL2

    }
    constructor(expR:ExpresionRelacional,):this(){
        this.expR = expR
    }
    constructor(valorLogico:Token, operadorL:Token, expL2:ExpresionLogica):this(){
        this.valorLogico = valorLogico
        this.operadorL = operadorL
        this.expL2 = expL2

    }
    constructor(valorLogico:Token):this(){
        this.valorLogico = valorLogico
    }


}