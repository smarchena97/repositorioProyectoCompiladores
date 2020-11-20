package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token

class Lectura(var cadena:Token):Sentencia() {

    override fun toString(): String {
        return "Lectura(cadena='$cadena')"
    }
}