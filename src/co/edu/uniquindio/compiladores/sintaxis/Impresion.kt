package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Impresion(var expresion:Expresion):Sentencia(){


    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String>("Impresion")
        raiz.children.add(expresion.getArbolVisual())
        return raiz
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String) {
        expresion.analizarSemantica(tablaSimbolos, listaErrores, ambito)

    }

    override fun getJavaCode(): String {
        var codigo = ""
        if(expresion != null){
            codigo = "JOptionPane.showMessageDialog(null,"+expresion.getJavaCode()+");"
        }
        return codigo
    }

}