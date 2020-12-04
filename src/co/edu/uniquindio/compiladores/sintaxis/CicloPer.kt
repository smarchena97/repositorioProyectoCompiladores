package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class CicloPer(var tipoDato:Token, var variableControl:Token,var indice:Token,var listaSentencia: ArrayList<Sentencia>): Sentencia() {

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("CicloPer")

        raiz.children.add(TreeItem("VariableDeControl:${variableControl.lexema}"))
        raiz.children.add(TreeItem("Indice${indice.lexema}"))

        var raizSentencia = TreeItem("SentenciasDeCicloPer")
        for(i in listaSentencia){
            raizSentencia.children.add(i.getArbolVisual())
        }

        raiz.children.add(raizSentencia)
        return raiz
    }

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String) {
        for(s in listaSentencia){
            s.llenarTablaSimbolos(tablaSimbolos, listaErrores, ambito)
        }
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String) {
        for (s in listaSentencia) {
            s.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        }
    }

    override fun getJavaCode(): String {
        var  codigo = "for("+tipoDato.getJavaCode()+" "+variableControl.lexema+":"+indice.lexema+"){"
        for(s in listaSentencia){
            codigo += s.getJavaCode()
        }
        codigo +="}"
        return codigo
    }

}