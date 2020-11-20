package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.Token

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

}