package com.example.listacompra.data

sealed class Resultado<out T: Any> {

    object Loading : Resultado<Nothing>()
    data class Success<out T:Any>(val result :T) : Resultado<T>()
    data class Error(val message :String) : Resultado<Nothing>()



}