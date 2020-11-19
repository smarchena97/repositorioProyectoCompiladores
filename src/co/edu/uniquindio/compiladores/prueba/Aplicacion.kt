package co.edu.uniquindio.compiladores.prueba

import co.edu.uniquindio.compiladores.lexico.AnalizadorLexico
import co.edu.uniquindio.compiladores.sintaxis.AnalizadorSintactico

fun main(){
    val lexico = AnalizadorLexico("fun numZ calcular (numZ dato1, numR dato2< > \$calcular(dato1,dato2)")
    lexico.analizar()
    val sintaxis = AnalizadorSintactico(lexico.listaTokens)
    print(sintaxis.esUnidadDeCompilacion())
    print(sintaxis.listaErrores)

}
