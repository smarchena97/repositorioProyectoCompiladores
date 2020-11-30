package co.edu.uniquindio.compiladores.semantica

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.sintaxis.UnidadDeCompilacion

class AnalizadorSemantico(var uc: UnidadDeCompilacion) {

    var erroresSemanticos: ArrayList<Error> = ArrayList()
    var tablaSimbolos:TablaSimbolos = TablaSimbolos(erroresSemanticos)

    fun llenarTablaSimbolos(){
        uc.llenarTablaSimbolos(tablaSimbolos,erroresSemanticos)
    }
    fun analizarSemantica() {
        uc.analizarSemantica(tablaSimbolos,erroresSemanticos)
    }
}