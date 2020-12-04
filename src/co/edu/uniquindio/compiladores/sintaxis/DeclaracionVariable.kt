package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class DeclaracionVariable(var tipoVariable:Token, var listaVariables:ArrayList<Variable>, var tipoDato:Token, var asignacion: Asignacion?):Sentencia() {
    override fun toString(): String {
        return "DeclaracionVariable(tipoVariable=$tipoVariable, listaVariables=$listaVariables, tipoDato=$tipoDato)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String>("Declaracion")
        raiz.children.add(TreeItem("tipoVariable: ${tipoVariable.lexema}"))
        for (i in listaVariables){
            raiz.children.add(TreeItem("Variable: ${i.nombreVariable.lexema}"))
        }
        raiz.children.add(TreeItem("tipoDato:${tipoDato.lexema}"))
        return raiz
    }

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String) {
        for (lv in listaVariables){
            tablaSimbolos.guardarSimboloValor(lv.nombreVariable.lexema, tipoDato.lexema , true, ambito, lv.nombreVariable.fila, lv.nombreVariable.columna)
        }
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String) {
        /*for (v in listaVariables) {
            analizarSemantica(tablaSimbolos, listaErrores, ambito)
        }*/
    }

    override fun getJavaCode(): String {
        var codigo = ""
        if(listaVariables != null) {
            codigo = tipoDato!!.getJavaCode() + " "
            for (ls in listaVariables!!){
                codigo += ls.nombreVariable.getJavaCode()+","
            }
            codigo = codigo.substring(0,codigo.length-1)+";"
        }else{
            codigo =  tipoDato!!.getJavaCode() + " " + asignacion!!.getJavaCode()
        }
        return codigo
    }
}