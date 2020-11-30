package co.edu.uniquindio.compiladores.semantica

class Simbolo() {

    var nombre:String = ""
    var tipo:String = ""
    var moidificable:Boolean = false
    var ambito:String = ""
    var fila:Int = 0
    var columna:Int = 0
    var tiposParametros:ArrayList<String>? = null

    /*
    /contructor para crear un simbolo de tipo valor
     */
    constructor( nombre:String, tipoDato:String, modificable:Boolean, ambito:String, fila:Int, columna:Int):this(){
        this.nombre  = nombre
        this.tipo = tipoDato
        this.moidificable = modificable
        this.ambito = ambito
        this.fila = fila
        this.columna = columna
    }

    /*
  /contructor para crear un simbolo de tipo función
   */
    constructor(nombre: String,tipoRetorno:String,tipoParametros:ArrayList<String>,ambito:String):this(){
        this.nombre = nombre
        this.tipo = tipoRetorno
        this.tiposParametros = tipoParametros
        this.ambito = ambito
    }

    override fun toString(): String {
        return if(tiposParametros == null){
            "Simbolo(nombre='$nombre', tipo='$tipo', modificable=$moidificable, ambito=$ambito, fila=$fila, columna =$columna)"
        }else{
            "Simbolo(nombre='$nombre', tipo='$tipo', ambito=$ambito, tiposParametros=$tiposParametros)"
        }
     }

}