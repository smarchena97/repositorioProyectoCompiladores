package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token

class Funcion(var nombreFuncion:Token, var tipoRetorno:Token, var listaParametros: ArrayList<Parametro>?, var listaSentencias:ArrayList<Sentencia>) {

    override fun toString(): String {
        return "Funcion(nombreFuncion=$nombreFuncion, tipoRetorno=$tipoRetorno, listaParametros=$listaParametros, listaSentencias=$listaSentencias)"
    }
}