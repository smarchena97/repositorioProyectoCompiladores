package co.edu.uniquindio.compiladores.lexico

class AnalizadorLexico (var codigoFuente:String) {

    var posicionActual = 0
    var caracterActual = codigoFuente[0]
    var listaTokens = ArrayList<Token>()
    var finCodigo = 0.toChar()
    var filaActual = 0
    var columnaActual = 0
    val listaPalabrasReservadas = ArrayList<String>()

    init{listaPalabrasReservadas.add("numZ")
        listaPalabrasReservadas.add("numR")
        listaPalabrasReservadas.add("chordi")
        listaPalabrasReservadas.add("khar")
        listaPalabrasReservadas.add("per")
        listaPalabrasReservadas.add("mentre")
        listaPalabrasReservadas.add("se")
        listaPalabrasReservadas.add("altro")
        listaPalabrasReservadas.add("scambio")
        listaPalabrasReservadas.add("caso")
        listaPalabrasReservadas.add("principale")
        listaPalabrasReservadas.add("fun")
        listaPalabrasReservadas.add("bool")
        listaPalabrasReservadas.add("void")
    }

    fun almacenarToken(lexema:String, categoria: Categoria, fila:Int, columna:Int) = listaTokens.add( Token(lexema, categoria, fila, columna)  )



    fun hacerBT(posicionInicial:Int, filaInicial:Int, columnaInicial:Int){

        posicionActual = posicionInicial
        filaActual = filaInicial
        columnaActual = columnaInicial
        caracterActual = codigoFuente[posicionActual]
    }


    fun analizar(){

        while (caracterActual != finCodigo){

            if(caracterActual == ' ' || caracterActual == '\t' || caracterActual == '\n'){
                obtenerSiguienteCaracter()
                continue
            }

            if(esEntero2()) continue
            if(esDecimal()) continue
            if(esIdentificador()) continue
            if(esOperadorAritmetico()) continue
            if(esOperadorAsignacion()) continue
            if(esOperadorIncremento()) continue
            if(esOperadorDecremento()) continue
            if(esOperadorRelacional()) continue
            if(esOperadorLogico()) continue
            if(esPalabraReservadaEntero()) continue
            if(esPalabraReservadaDecimal()) continue
            if(esPalabraReservadaCadenaCaracteres()) continue
            if(esPalabraReservadaCaracteres()) continue
            if(esComentarioLinea()) continue
            if(esComentarioBloque()) continue
            if(esCadena()) continue
            if(esCaracter()) continue
            if(esLlaveIzq()) continue
            if(esLlaveDer()) continue
            if(esParentisisIzq()) continue
            if(esParentisisDer()) continue
            if(esCorcheteIzq()) continue
            if(esCorcheteDer()) continue
            if(esFindeSentencia()) continue
            if(esPunto()) continue
            if(esDosPuntos()) continue
            if(esSeparador()) continue
            if(esClase()) continue
            if(esMetodo()) continue

            almacenarToken(""+caracterActual, Categoria.DESCONOCIDO, filaActual, columnaActual)
            obtenerSiguienteCaracter()
        }
    }

    /*
    Funcion que permite saber si una sucesion de caracteres corresponden a un numero entero en el lenguaje empleado
     */
    fun esEntero2():Boolean{

        if(caracterActual == '#'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()


            if(caracterActual.isDigit()){
                lexema+= caracterActual
                obtenerSiguienteCaracter()

                while (caracterActual.isDigit()){
                    lexema+= caracterActual
                    obtenerSiguienteCaracter()
                }
            }
            if(caracterActual == '.'){
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }

            if(caracterActual == 'N'){
                lexema+=caracterActual
                obtenerSiguienteCaracter()
                if(caracterActual == 'Z'){
                    lexema+=caracterActual
                    obtenerSiguienteCaracter()

                    almacenarToken(lexema, Categoria.ENTERO, filaInicial, columnaInicial)
                    return true
                }else{
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            }

            hacerBT(posicionInicial, filaInicial, columnaInicial)

        }

        return false
    }

    /*
    Funcion que permite saber si una sucesion de caracteres corresponden a un numero decimal en el lenguaje empleado
     */
    fun esDecimal():Boolean{

        if(caracterActual == '#'){

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual.isDigit()){
                lexema += caracterActual
                obtenerSiguienteCaracter()

                while (caracterActual.isDigit()){
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }
            }

            if(caracterActual == '.'){
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if(caracterActual.isDigit()){
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    while (caracterActual.isDigit()){
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                    }
                }
            }else{
                hacerBT(posicionInicial, filaInicial,columnaInicial )
                return false
            }
            if(caracterActual == 'N'){
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if(caracterActual == 'R'){
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.DECIMAL, filaInicial, columnaInicial)
                    return true
                }
            }
            hacerBT(posicionInicial, filaInicial, columnaInicial)

        }

        return false
    }

    /*
    Funcion que permite saber si una sucesion de caracteres corresponden a un identificador de maximo 10 caracteres en el lenguaje empleado
     */
    fun esIdentificador():Boolean{
        var i = 0
        if(caracterActual.isLetter() || caracterActual.isDigit()){
            i += 1
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            while( caracterActual.isLetter() || caracterActual.isDigit() || caracterActual == '_'){
                lexema += caracterActual
                obtenerSiguienteCaracter()
                i += 1
            }

            if(esPalabraReservada2(lexema)){
                //hacerBT(posicionInicial, filaInicial, columnaInicial)
                almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                return true
            }

            if(i <= 10){
                almacenarToken(lexema, Categoria.IDENTIFICADOR, filaInicial, columnaInicial)
                return true
            }else{
                hacerBT(posicionInicial,filaInicial,columnaInicial)
                return false
            }

        }
        return false
    }

     fun esPalabraReservada2(lexema: String):Boolean{

        var i = 0
         var filaInicial = filaActual
         var columnaInicial = columnaActual
         var posicionInicial = posicionActual

        while (i < listaPalabrasReservadas.size){

            if(listaPalabrasReservadas[i] == lexema){
                return true
            }
            i ++

        }

        return false
    }

    fun esOperadorAritmetico():Boolean{

        if(caracterActual == '+' || caracterActual == '-' || caracterActual == '*' || caracterActual == '/' || caracterActual == '%'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual == ':'){
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }

            almacenarToken(lexema, Categoria.OPERADOR_ARITMETICO, filaInicial, columnaInicial)
            return true
        }
        return false
    }

    fun esOperadorAsignacion():Boolean{
        if(caracterActual == ':'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if(caracterActual == ':'){
                hacerBT(posicionInicial,filaInicial,columnaInicial)
                return false
            }
            almacenarToken(lexema, Categoria.OPERADOR_ASIGNACION, filaInicial, columnaInicial)
            return true
        }else{
            if(caracterActual == '+' || caracterActual == '-' || caracterActual == '*' || caracterActual == '%' || caracterActual == '/' || caracterActual == '^'){
                var lexema = ""
                var filaInicial = filaActual
                var columnaInicial = columnaActual
                var posicionInicial = posicionActual
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if(caracterActual == ':'){
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.OPERADOR_ASIGNACION, filaInicial, columnaInicial)
                    return true
                }
            }
        }
        return false
    }

    fun esOperadorIncremento():Boolean{

        if (caracterActual == '+'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if(caracterActual == '+'){
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema, Categoria.OPERADOR_INCREMENTO, filaInicial, columnaInicial)
                return true
            }
        }

        return false
    }

    fun esOperadorDecremento():Boolean{

        if (caracterActual == '-'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if(caracterActual == '-'){
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema, Categoria.OPERADOR_DECREMENTO, filaInicial, columnaInicial)
                return true
            }
        }

        return false
    }

    fun esOperadorRelacional():Boolean{

        if(caracterActual == '¿' || caracterActual == '?' || caracterActual == '¬'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if(caracterActual == ':'){
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }
            almacenarToken(lexema, Categoria.OPERADORES_RELACIONALES, filaInicial, columnaInicial)
            return true
        }else{
            if (caracterActual == ':'){
                var lexema = ""
                var filaInicial = filaActual
                var columnaInicial = columnaActual
                var posicionInicial = posicionActual
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if(caracterActual == ':'){
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.OPERADORES_RELACIONALES, filaInicial, columnaInicial)
                    return true
                }
            }
        }

        return false
    }

    fun esOperadorLogico():Boolean{

        if(caracterActual == '¬'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema, Categoria.OPERADOR_LOGICO, filaInicial, columnaInicial)
            return true
        }

        if(caracterActual == '^'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if(caracterActual == '^'){
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema, Categoria.OPERADOR_LOGICO, filaInicial, columnaInicial)
                return true
            }
        }else{
            if(caracterActual == '|'){
                var lexema = ""
                var filaInicial = filaActual
                var columnaInicial = columnaActual
                var posicionInicial = posicionActual
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if(caracterActual == '|'){
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.OPERADOR_LOGICO, filaInicial, columnaInicial)
                    return true
                }
            }
        }
        return false
    }

    fun esPalabraReservada(lexema:String):Boolean{
        var i = 0
        while ( i <= listaPalabrasReservadas.size-1){
            if(lexema.contains(listaPalabrasReservadas[i],ignoreCase = true)){
                return true
            }
            i += 1
        }
        return false
    }

    fun esPalabraReservadaEntero():Boolean{

        if(caracterActual == 'n'){

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual == 'u'){

                lexema+=caracterActual
                obtenerSiguienteCaracter()

                if (caracterActual == 'm'){

                    lexema+=caracterActual
                    obtenerSiguienteCaracter()

                    if(caracterActual == 'Z'){

                        lexema+=caracterActual
                        obtenerSiguienteCaracter()

                        almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                        return true
                    }
                }
            }
        }
        return false
    }

    fun esPalabraReservadaDecimal():Boolean{

        if(caracterActual == 'n'){

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual == 'u'){

                lexema+=caracterActual
                obtenerSiguienteCaracter()

                if (caracterActual == 'm'){

                    lexema+=caracterActual
                    obtenerSiguienteCaracter()

                    if(caracterActual == 'R'){

                        lexema+=caracterActual
                        obtenerSiguienteCaracter()

                        almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                        return true
                    }
                }
            }
        }
        return false
    }

    /*
    Metodo que verifica si una sucesion de caracteres corresponden a una palabra reservada para las cadenas de caracteres
     */
    fun esPalabraReservadaCadenaCaracteres():Boolean{

        if(caracterActual == 'c'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if(caracterActual == 'h'){
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if(caracterActual == 'o'){
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if(caracterActual == 'r'){
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if(caracterActual == 'd'){
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            if(caracterActual == 'i'){
                                lexema += caracterActual
                                obtenerSiguienteCaracter()

                                almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                                return true
                            }
                        }
                    }
                }
            }

        }
        return false
    }

    /*
    Metodo que verifica si una sucesion de caracteres corresponden a una palabra reservada para los caracteres
     */
    fun esPalabraReservadaCaracteres():Boolean{

        if(caracterActual == 'k'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if(caracterActual == 'h'){
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if(caracterActual == 'a'){
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if(caracterActual == 'r'){
                        lexema += caracterActual
                        obtenerSiguienteCaracter()

                        almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                        return true
                    }
                }
            }
        }
        return false
    }

    fun obtenerSiguienteCaracter(){

        if(posicionActual == codigoFuente.length - 1){
            caracterActual = finCodigo
        }else{

            if(caracterActual == '\n'){
                filaActual++
                columnaActual = 0
            }else{
                columnaActual++
            }

            posicionActual ++
            caracterActual = codigoFuente[posicionActual]
        }
    }

    //------------------------------------------------------------------------------------

    fun esComentarioLinea():Boolean{

        if(caracterActual == '#'){

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual
            var posicionInicial = posicionActual
            obtenerSiguienteCaracter()

            if(caracterActual != '/'){

                lexema += caracterActual
                obtenerSiguienteCaracter()

                while (caracterActual != '\n' && posicionActual != codigoFuente.length-1){

                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }
                lexema += caracterActual
                almacenarToken(lexema, Categoria.COMENTARIODELINEA, filaInicial, columnaInicial)
                obtenerSiguienteCaracter()
                return true
            }else{
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }

        return false
    }

    fun esComentarioBloque():Boolean{

        if(caracterActual == '#') {

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == '/') {

                lexema += caracterActual
                obtenerSiguienteCaracter()

                while(caracterActual != '#' && posicionActual != codigoFuente.length-1) {

                    while (caracterActual != '/' && posicionActual != codigoFuente.length - 1) {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                    }
                    if(caracterActual == '/' && posicionActual != codigoFuente.length - 1) {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                    }
                }


                if(caracterActual == '#' && lexema[lexema.length-1] == '/'){
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.COMENTARIODEBLOQUE, filaInicial, columnaInicial)
                    return true
                }
            }

            hacerBT(posicionInicial, filaInicial, columnaInicial)
        }

        return false
    }

    fun esCadena():Boolean{

        if(caracterActual == '¡'){

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual == '¡'){

                lexema += caracterActual
                obtenerSiguienteCaracter()

                while(caracterActual != '!' && posicionActual != codigoFuente.length-1){

                    lexema += caracterActual
                    obtenerSiguienteCaracter()

                }

                lexema += caracterActual
                obtenerSiguienteCaracter()

                if(caracterActual == '!'){

                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.CADENA, filaInicial, columnaInicial)
                    return true

                }
            }
            hacerBT(posicionInicial, filaInicial, columnaInicial)

        }

        return false
    }

    fun esCaracter():Boolean{

        if(caracterActual == '¡'){

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual !=  '!'){

                lexema += caracterActual
                obtenerSiguienteCaracter()

            }

            if(caracterActual == '!'){

                lexema += caracterActual
                almacenarToken(lexema, Categoria.CARACTER, filaInicial, columnaInicial)
                obtenerSiguienteCaracter()
                return true
            }

            hacerBT(posicionInicial, filaInicial, columnaInicial)

        }
        return false
    }



    fun desicionAltro():Boolean{

        if(caracterActual == 'a'){

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual == 'l'){

                lexema += caracterActual
                obtenerSiguienteCaracter()

                if(caracterActual == 't'){

                    lexema += caracterActual
                    obtenerSiguienteCaracter()

                    if (caracterActual == 'r'){

                        lexema += caracterActual
                        obtenerSiguienteCaracter()

                        if(caracterActual == 'o'){

                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            almacenarToken(lexema, Categoria.CONDICIONAL, filaInicial, columnaInicial)
                            return true

                        }

                    }

                }

            }

            hacerBT(posicionInicial, filaInicial, columnaInicial)

        }
        return false
    }

    fun desicionScambio():Boolean{

        if(caracterActual == 's'){

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual == 'c'){

                lexema += caracterActual
                obtenerSiguienteCaracter()

                if(caracterActual == 'a'){

                    lexema += caracterActual
                    obtenerSiguienteCaracter()

                    if (caracterActual == 'm'){

                        lexema += caracterActual
                        obtenerSiguienteCaracter()

                        if(caracterActual == 'b'){

                            lexema += caracterActual
                            obtenerSiguienteCaracter()

                            if (caracterActual == 'i'){

                                lexema += caracterActual
                                obtenerSiguienteCaracter()

                                if(caracterActual == 'o'){

                                    lexema += caracterActual
                                    obtenerSiguienteCaracter()
                                    almacenarToken(lexema, Categoria.CONDICIONAL, filaInicial, columnaInicial)
                                    return true
                                }

                            }

                        }

                    }

                }

            }

            hacerBT(posicionInicial, filaInicial, columnaInicial)

        }
        return false
    }

    



    //---------------------------------------------------------------------------------------------------

    //funciones
    fun esLlaveIzq():Boolean{

        if(caracterActual== '<' ){

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema, Categoria.LLAVE_IZQ, filaInicial, columnaInicial)
            return true
        }
        return false
    }

    fun esLlaveDer():Boolean{

        if(caracterActual== '>' ){

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema, Categoria.LLAVE_DER, filaInicial, columnaInicial)
            return true
        }
        return false
    }

    fun esParentisisIzq():Boolean{

        if(caracterActual== '('){

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema, Categoria.PARENTESIS_IZQ, filaInicial, columnaInicial)
            return true
        }
        return false
    }

    fun esParentisisDer():Boolean{

        if(caracterActual== ')'){

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema, Categoria.PARENTESIS_DER, filaInicial, columnaInicial)
            return true
        }
        return false
    }

    fun esCorcheteIzq():Boolean{

        if(caracterActual== '[' ){

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema, Categoria.CORCHETE_IZQ, filaInicial, columnaInicial)
            return true
        }
        return false
    }

    fun esCorcheteDer():Boolean{

        if(caracterActual== ']' ){

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema, Categoria.CORCHETE_DER, filaInicial, columnaInicial)
            return true
        }
        return false
    }

    fun esFindeSentencia():Boolean{

        if(caracterActual== '.' ){

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema, Categoria.FINDESENTENCIA, filaInicial, columnaInicial)
            return true
        }
        return false
    }

    fun esPunto():Boolean{

        if(caracterActual== '°' ){

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema, Categoria.PUNTO, filaInicial, columnaInicial)
            return true
        }
        return false
    }

    fun esDosPuntos():Boolean{

        if(caracterActual== '=' ){

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema, Categoria.DOSPUNTOS, filaInicial, columnaInicial)
            return true
        }
        return false
    }

    fun esSeparador():Boolean{
        if(caracterActual== ',' ) {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema, Categoria.COMA, filaInicial, columnaInicial)
            return true
        }

        return false
    }

    fun esMetodo():Boolean{

        if(caracterActual=='$') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            var control=10
            lexema += caracterActual

            obtenerSiguienteCaracter()
            if (caracterActual.isLetter()) {
                control -= 1
                lexema += caracterActual
                obtenerSiguienteCaracter()

                if (caracterActual.isLetter() || caracterActual.isDigit() || caracterActual == '_') {
                    control -= 1
                    while (caracterActual.isLetter() || caracterActual.isDigit() || caracterActual == '_') {
                        control -= 1
                        if (control>=0) {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                        }
                        else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }
                    }
                    almacenarToken(lexema, Categoria.METODO, filaInicial, columnaInicial)
                    return true
                }
                almacenarToken(lexema, Categoria.METODO, filaInicial, columnaInicial)
            }
            else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
            almacenarToken(lexema, Categoria.METODO, filaInicial, columnaInicial)
            return true
        }
        return false
    }


    fun esClase():Boolean{

        if(caracterActual.isUpperCase()) {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            var control = 10
            lexema += caracterActual

            obtenerSiguienteCaracter()
            if (caracterActual.isLetter() || caracterActual.isDigit() ) {
                control -= 1
                lexema += caracterActual
                obtenerSiguienteCaracter()
                control -= 1
                while (caracterActual.isLetter() || caracterActual.isDigit()) {
                    control -= 1
                    if (control >= 0) {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                }
                almacenarToken(lexema, Categoria.CLASE, filaInicial, columnaInicial)
                return true
            }
            almacenarToken(lexema, Categoria.CLASE, filaInicial, columnaInicial)
            return true
        }
        return false
    }
}