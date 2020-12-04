package co.edu.uniquindio.compiladores.lexico

import co.edu.uniquindio.compiladores.lexico.Categoria

class Token (var lexema:String, var categoria: Categoria, var fila:Int, var columna:Int) {

    override fun toString(): String {
        return "Token(lexema='$lexema', categoria=$categoria, fila=$fila, columna=$columna)"
    }

    fun getJavaCode():String{

        if(categoria == Categoria.PALABRA_RESERVADA){

            if(lexema == "numZ"){
                return "int"
            }
            if(lexema == "numR"){
                return "double"
            }
            if(lexema == "chordi"){
                return "String"
            }
            if(lexema == "khar"){
                return "char"
            }
            if(lexema == "bool"){
                return "boolean"
            }
            if(lexema == "in"){
                return ":"
            }
            if(lexema == "True"){
                return "true"
            }
            if(lexema == "False"){
                return "false"
            }
            if(lexema == "inmut"){
                return "final"
            }
            if(lexema == "mut"){
                return ""
            }

        }else if(categoria == Categoria.CADENA){
            lexema = lexema.substring(2,lexema.length-2)
            return "\""+lexema+"\""
        }else if(categoria == Categoria.OPERADORES_RELACIONALES){

            if(lexema == "?"){
                return "<"
            }
            if(lexema == "¿"){
                return ">"
            }
            if(lexema == "?:"){
                return "<="
            }
            if(lexema == "¿:"){
                return ">="
            }
            if(lexema == "::"){
                return "=="
            }
            if(lexema == "¬:"){
                return "!="
            }
            if(lexema == "¬"){
                return "!"
            }

        }else if(categoria == Categoria.OPERADOR_ASIGNACION){
            return "="
        }else if(categoria == Categoria.ENTERO) {
            return lexema.substring(1,lexema.length-2)
        }else if(categoria == Categoria.DECIMAL){
            return lexema.substring(1,lexema.length-2)
        }else if(categoria == Categoria.COMENTARIODELINEA){
            return lexema.replace("#","//")
        }else if(categoria == Categoria.COMENTARIODEBLOQUE){
            lexema.replace("#/","/*")
            lexema.replace("/#","*/")
            return lexema
        }else if(categoria == Categoria.CARACTER){
            lexema.replace("¡","\'")
            lexema.replace("!","\'")
            return lexema
        }

        return lexema
    }
}