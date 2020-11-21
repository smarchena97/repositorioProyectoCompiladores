package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class CicloPer(var variableControl:Token,var indice:Token,var listaSentencia: ArrayList<Sentencia>): Sentencia() {

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

}