package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token

class Asignacion(var identificador:Token , var expresion: Expresion?):Sentencia() {
    override fun toString(): String {
        return "Asignacion(identificador=$identificador, expresion=$expresion)"
    }
}