package co.edu.uniquindio.compiladores.prueba

import co.edu.uniquindio.compiladores.lexico.AnalizadorLexico

fun main(){
    val lexico = AnalizadorLexico("a7732637482a \$aaaaaaaaaaaaa casa #887799NZ !!cs¡¡ ")
    lexico.analizar()
    print(lexico.listaTokens)
}

fun esPrimo(numero:Int):Boolean{

    for(i in 2..numero/2){
        if(numero%i==0){
            return false
        }
    }
    return true
}

fun esPrimo(numero: Int, i:Int):Boolean{

    return if(numero/2 < i){
         true
    }else if (numero % i == 0){
         false
    }else{
         esPrimo(numero, i+1)
    }
}

fun operar(a:Int,b:Int, fn:(Int,Int)->Int):Int{
    return fn(a, b)
}

fun sumar(a:Int, b:Int) = a+b

fun multiplicar(a:Int, b:Int) = a*b

fun dividir(a:Int, b:Int) = a/b


/* var numero:Any = 12
    numero = "hello"
    var arreglo = arrayOf(1, 2, 3, 4, 5)

    var matriz = arrayOf( intArrayOf(3,6,9) , intArrayOf(2,4,6) )

    var matrizVacia = Array(3){IntArray(3)}

    for ((i,v) in matriz.withIndex()){
        for ((j,v) in matriz[0].withIndex()){
            print(matriz[i][j])
        }
        println()

    }

    var lista = mutableListOf<Int>(33, 55, 99, 87)
    lista.add(33)

    var lista2 = ArrayList<Int>()
    lista2.add(76)
    lista2.add(447)
    lista2.add(97)

    for((i,v) in lista.withIndex()){
        println(" La posicion ${i} contiene el valor : ${v} ")
    }

    println(esPrimo(5, i=2))

    println(operar(33,3, ::multiplicar))*/