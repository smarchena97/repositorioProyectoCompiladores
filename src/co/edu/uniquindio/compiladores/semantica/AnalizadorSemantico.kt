package co.edu.uniquindio.compiladores.semantica
import co.edu.uniquindio.compiladores.sintaxis.UnidadDeCompilacion
import co.edu.uniquindio.compiladores.lexico.Error

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