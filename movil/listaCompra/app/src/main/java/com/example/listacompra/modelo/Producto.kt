package com.example.listacompra.modelo

import java.io.Serializable

data class Producto(var nombre:String = "", var comprado:Boolean=false, val cantidad:Int = 0) : Serializable
