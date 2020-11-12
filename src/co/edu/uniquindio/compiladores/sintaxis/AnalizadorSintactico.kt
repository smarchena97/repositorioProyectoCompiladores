package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token

class AnalizadorSintactico(var listaTokens:ArrayList<Token>) {

    var posicionActual = 0
    var tokenActual = listaTokens[0]
    var listaErrores = ArrayList<Error>()

    fun obtenerSiguienteToken(){

        posicionActual ++
        if(posicionActual < listaTokens.size){
            tokenActual = listaTokens[posicionActual]
        }
    }

}