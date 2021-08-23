package com.example.listacompra.data.datasource

import com.example.listacompra.data.Resultado
import com.example.listacompra.domain.Producto
import com.google.firebase.database.DatabaseReference


interface IProductoRemoteDatasource {
    suspend fun getProductosTienda(
        database: DatabaseReference, tienda: String
    ): Resultado<List<Producto>>

}