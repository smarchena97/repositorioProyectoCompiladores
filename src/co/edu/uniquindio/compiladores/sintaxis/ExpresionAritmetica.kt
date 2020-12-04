package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem


class ExpresionAritmetica():Expresion() {
    var expresion1:ExpresionAritmetica? = null
    var operador:Token? = null
    var expresion2:ExpresionAritmetica? = null
    var valorNumerico:ValorNumerico? = null

    constructor(expresion1: ExpresionAritmetica,operador:Token, expresion2: ExpresionAritmetica):this(){
        this.expresion1 = expresion1
        this.operador = operador
        this.expresion2 = expresion2
    }
    constructor(expresion1:ExpresionAritmetica):this(){
        this.expresion1 = expresion1
    }
    constructor(valorNumerico: ValorNumerico,operador: Token,expresion2: ExpresionAritmetica):this(){
        this.valorNumerico = valorNumerico
        this.operador = operador
        this.expresion2 = expresion2
    }
    constructor(valorNumerico: ValorNumerico):this(){
        this.valorNumerico = valorNumerico
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String>("Expresion Aritmetica")

        if(valorNumerico != null && operador == null){
            raiz.children.add(valorNumerico!!.getArbolVisual())
        }
        if(valorNumerico != null && operador != null && expresion2 != null){
            raiz.children.add(valorNumerico!!.getArbolVisual())
            raiz.children.add(TreeItem("${operador!!.lexema}"))
            raiz.children.add(expresion2!!.getArbolVisual())
        }
        if(expresion1 != null && expresion2==null){
            raiz.children.add(expresion1!!.getArbolVisual())
        }
        if(expresion1 != null && expresion2!=null){
            raiz.children.add(expresion1!!.getArbolVisual())
            raiz.children.add(TreeItem("${operador!!.lexema}"))
            raiz.children.add(expresion2!!.getArbolVisual())
        }

        return  raiz
    }

    /**
     * Funcionamiento dudoso
     */
    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito: String): String {
        if(expresion1 != null && expresion2!=null && valorNumerico==null){
            val tipo1 =expresion1!!.obtenerTipo(tablaSimbolos, ambito)
            val tipo2 =expresion2!!.obtenerTipo(tablaSimbolos, ambito)

            if(tipo1 == "numR" || tipo2 == "numR"){
                return "numR"
            }else{
                return "numZ"
            }
        }
        else if(expresion1 != null ){
            return expresion1!!.obtenerTipo(tablaSimbolos, ambito)
        }
        else if(valorNumerico != null && expresion1 != null ) {

            var tipo1 = obtenerTipoCampo(valorNumerico, tablaSimbolos,ambito)
            val tipo2 = expresion1!!.obtenerTipo(tablaSimbolos, ambito)

            if (tipo1 == "numR" || tipo2 == "numR") {
                return "numR"
            } else {
                return "numZ"
            }

        } else if(valorNumerico != null ) {
            obtenerTipoCampo(valorNumerico, tablaSimbolos, ambito)
        }
        return ""
    }

    fun obtenerTipoCampo(valorNumerico: ValorNumerico? , tablaSimbolos: TablaSimbolos, ambito:String):String
    {
        if(valorNumerico!!.valor.categoria == Categoria.ENTERO ){
            return "numZ"
        }else if(valorNumerico!!.valor.categoria == Categoria.DECIMAL){
            return "numR"
        }else{
            val simbolo = tablaSimbolos.buscarSimboloValor(valorNumerico!!.valor.lexema,ambito)
            if(simbolo != null){
                return simbolo.tipo
            }
        }
        return ""
    }
    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String) {
        if (valorNumerico != null) {
            if (valorNumerico!!.valor.categoria == Categoria.IDENTIFICADOR)
            {
                var simbolo=tablaSimbolos.buscarSimboloValor(valorNumerico!!.valor.lexema, ambito)
                if (simbolo == null){
                    // capturar el simbolo.tipo y preguntar si es numerico
                    listaErrores.add(
                        Error( "El campo (${valorNumerico!!.valor.lexema}) no existe dentro del ambito ($ambito)",
                        valorNumerico!!.valor.fila,
                        valorNumerico!!.valor.columna
                        )
                    )
                }
            }
        }
        if (expresion1!=null) {
            expresion1!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        }
        if (expresion2!=null) {
            expresion2!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        }
    }
}