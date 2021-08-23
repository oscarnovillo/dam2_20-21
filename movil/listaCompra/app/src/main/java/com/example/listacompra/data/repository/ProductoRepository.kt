package com.example.listacompra.data.repository

import com.example.listacompra.data.Resultado
import com.example.listacompra.data.datasource.IProductoRemoteDatasource
import com.example.listacompra.domain.Producto
import com.google.firebase.database.DatabaseReference

class ProductoRepository(
    private val remoteDatasource: IProductoRemoteDatasource,
    private val database: DatabaseReference
) {

    suspend fun getProductosTienda(tienda: String) : Resultado<List<Producto>> {

        return remoteDatasource.getProductosTienda(database,tienda)
    }


}

