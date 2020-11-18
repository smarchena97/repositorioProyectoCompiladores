package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token

class DeclaracionVariable(var tipoVariable:Token, var listaVariables:ArrayList<Token>, var tipoDato:Token) {
    override fun toString(): String {
        return "DeclaracionVariable(tipoVariable=$tipoVariable, listaVariables=$listaVariables, tipoDato=$tipoDato)"
    }
}