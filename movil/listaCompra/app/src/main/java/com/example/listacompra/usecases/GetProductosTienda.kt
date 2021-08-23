package com.example.listacompra.usecases

import com.example.listacompra.data.Resultado
import com.example.listacompra.data.datasource.IProductoRemoteDatasource
import com.example.listacompra.data.repository.ProductoRepository
import com.example.listacompra.domain.Producto
import com.google.firebase.database.DatabaseReference

class GetProductosTienda (
    private val repository: ProductoRepository
){

    suspend operator fun invoke(tienda:String) : Resultado<List<Producto>> = repository.getProductosTienda(tienda)

}