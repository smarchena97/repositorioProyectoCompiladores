package co.edu.uniquindio.compiladores.prueba

import co.edu.uniquindio.compiladores.lexico.AnalizadorLexico
import co.edu.uniquindio.compiladores.sintaxis.AnalizadorSintactico

fun main(){
    val lexico = AnalizadorLexico("fun numZ calcular (numZ dato1, numR dato2)< se(a?b || True)<>altro<> >")
    lexico.analizar()
    val sintaxis = AnalizadorSintactico(lexico.listaTokens)
    print(sintaxis.esUnidadDeCompilacion())
    print(sintaxis.listaErrores)

}
