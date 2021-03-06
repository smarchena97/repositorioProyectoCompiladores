package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem


class ExpresionCadena():Expresion() {
    var token1: Token? = null
    var token2: Token? = null
    var expresionCadena: ExpresionCadena? = null

    constructor(token1: Token, expresionCadena: ExpresionCadena, token2: Token) : this() {
        this.token1 = token1
        this.expresionCadena = expresionCadena
        this.token2 = token2
    }

    constructor(token: Token, expresionCadena: ExpresionCadena) : this() {
        this.token1 = token
        this.expresionCadena = expresionCadena

    }

    constructor(token1: Token, token2: Token) : this() {
        this.token1 = token1
        this.token2 = token2
    }

    constructor(token1: Token) : this() {
        this.token1 = token1
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String>("ExpresionCadena")

        if (token1 != null && token2 != null && expresionCadena == null) {
            raiz.children.add(TreeItem("${token1!!.lexema}"))
            raiz.children.add(TreeItem("${token2!!.lexema}"))
        }
        if (token1 != null && token2 != null && expresionCadena != null) {
            raiz.children.add(TreeItem("${token1!!.lexema}"))
            raiz.children.add(expresionCadena!!.getArbolVisual())
            raiz.children.add(TreeItem("${token2!!.lexema}"))
        }
        if (token1 != null && token2 == null && expresionCadena == null) {
            raiz.children.add(TreeItem("${token1!!.lexema}"))
        }
        if (token1 != null && token2 == null && expresionCadena != null) {
            raiz.children.add(TreeItem("${token1!!.lexema}"))
            raiz.children.add(expresionCadena!!.getArbolVisual())
        }


        return raiz
    }

    override fun obtenerTipo(tabalSimbolos: TablaSimbolos, ambito: String): String {
        return "chordi"
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String) {
        if (expresionCadena != null) {
            expresionCadena!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        }
    }

    override fun getJavaCode(): String {
        var codigo = token1!!.getJavaCode()+""

        if (token1 != null && token2 != null && expresionCadena == null) {
            codigo += "+"+token2!!.getJavaCode()
        }
        if (token1 != null && token2 != null && expresionCadena != null) {
            codigo += "+"+token2!!.getJavaCode()+"+"+expresionCadena!!.getJavaCode()
        }
        if (token1 != null && token2 == null && expresionCadena != null) {
            codigo += "+"+expresionCadena!!.getJavaCode()
        }
        return codigo
    }
}