package co.edu.uniquindio.compiladores.prueba

import co.edu.uniquindio.compiladores.lexico.AnalizadorLexico

fun main(){
    val lexico = AnalizadorLexico("a7732637482a principale scambio ")
    lexico.analizar()
    print(lexico.listaTokens)
}
