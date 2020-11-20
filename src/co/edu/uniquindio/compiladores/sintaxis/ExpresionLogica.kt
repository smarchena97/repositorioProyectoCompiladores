package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class ExpresionLogica():Expresion() {
    var expR:ExpresionRelacional? = null
    var expL1:ExpresionLogica? = null
    var expL2:ExpresionLogica? = null
    var valorLogico:Token? = null
    var operadorL:Token? = null

    constructor(expR:ExpresionRelacional, operadorL:Token, expL2:ExpresionLogica):this(){
        this.expR = expR
        this.operadorL = operadorL
        this.expL2 = expL2

    }
    constructor(expR:ExpresionRelacional,):this(){
        this.expR = expR
    }
    constructor(valorLogico:Token, operadorL:Token, expL2:ExpresionLogica):this(){
        this.valorLogico = valorLogico
        this.operadorL = operadorL
        this.expL2 = expL2

    }
    constructor(valorLogico:Token):this(){
        this.valorLogico = valorLogico
    }
    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String>("Expresion Logica")
        if(expR == null && expL1 == null && expL2 == null && operadorL == null && valorLogico != null){
            raiz.children.add(TreeItem("${valorLogico!!.lexema}"))
        }
        if(expR != null && expL1 == null && expL2 == null && operadorL == null && valorLogico == null){
            raiz.children.add(expR!!.getArbolVisual())
        }
        if(expR == null && expL1 == null && expL2 != null && operadorL != null && valorLogico != null){
            raiz.children.add(TreeItem("${valorLogico!!.lexema}"))
            raiz.children.add(TreeItem("${operadorL!!.lexema}"))
            raiz.children.add(expL2!!.getArbolVisual())
        }
        if(expR != null && expL1 == null && expL2 != null && operadorL != null && valorLogico == null){
            raiz.children.add(expR!!.getArbolVisual())
            raiz.children.add(TreeItem("${operadorL!!.lexema}"))
            raiz.children.add(expL2!!.getArbolVisual())
        }

        return raiz
    }

}