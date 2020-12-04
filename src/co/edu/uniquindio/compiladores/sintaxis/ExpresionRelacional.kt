package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class ExpresionRelacional():Expresion() {

    var operadorRelacional:Token? = null
    var expresionA1:ExpresionAritmetica? = null
    var expresionA2:ExpresionAritmetica? = null
    var expresionC1:ExpresionCadena? = null
    var expresionC2:ExpresionCadena? = null

    constructor(expresionA1:ExpresionAritmetica,operadorRelacional:Token,expresionA2:ExpresionAritmetica):this(){
        this.expresionA1 = expresionA1
        this.operadorRelacional = operadorRelacional
        this.expresionA2 =expresionA2
    }
    constructor(expresionC1:ExpresionCadena,operadorRelacional:Token, expresionC2:ExpresionCadena):this(){
        this.expresionC1 = expresionC1
        this.operadorRelacional = operadorRelacional
        this.expresionC2 =expresionC2
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String>("Expresion relacional")

        if(expresionC1 != null && operadorRelacional != null && expresionC2 !=null){
            raiz.children.add(expresionC1!!.getArbolVisual())
            raiz.children.add(TreeItem("OperadorRelacional:${operadorRelacional!!.lexema}"))
            raiz.children.add(expresionC2!!.getArbolVisual())
        }
        if(expresionA1 != null && operadorRelacional != null && expresionA2 !=null){
            raiz.children.add(expresionA1!!.getArbolVisual())
            raiz.children.add(TreeItem("OperadorRelacional:${operadorRelacional!!.lexema}"))
            raiz.children.add(expresionA2!!.getArbolVisual())
        }
        return raiz
    }

    override fun obtenerTipo(tabalSimbolos: TablaSimbolos, ambito: String): String {
        return "bool"
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String) {
        if (expresionC1 != null && expresionC2 != null) {
            expresionC1!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
            expresionC2!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        }else if(expresionA1!=null && expresionA2!= null){
            expresionA1!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
            expresionA2!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        }
    }

    override fun getJavaCode(): String {
        var codigo = ""
        if(expresionC1 != null && operadorRelacional != null && expresionC2 !=null){
            if(operadorRelacional!!.lexema == "¬:"){
                codigo = "!"+"("+expresionC1!!.getJavaCode()+").equals("+expresionC2!!.getJavaCode()+")"
            }else if(operadorRelacional!!.lexema == "::"){
                codigo = "("+expresionC1!!.getJavaCode()+").equals("+expresionC2!!.getJavaCode()+")"
            }
        }
        if(expresionA1 != null && operadorRelacional != null && expresionA2 !=null){
            codigo = expresionA1!!.getJavaCode()+operadorRelacional!!.getJavaCode()+expresionA2!!.getJavaCode()
        }
        return codigo
    }

}