package com.example.listacompra.framework.data.datasource

import com.example.listacompra.data.Resultado
import com.example.listacompra.data.datasource.IProductoRemoteDatasource
import com.example.listacompra.domain.Producto
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseProductoDataSource @Inject constructor() : IProductoRemoteDatasource  {
    override suspend fun getProductosTienda(
        database: DatabaseReference,
        tienda: String
    ): Resultado<List<Producto>> {
        val listaProductos = mutableListOf<Producto>()
        val task: Task<DataSnapshot>
        val productos: DataSnapshot

        withContext(Dispatchers.IO) {

            task = database.child("tiendas").child(tienda).child("listas").child("actual").get()
            productos = task.await()
        }
        if (task.isSuccessful) {
            productos.children.forEach { dataSnapshot ->
                val producto = try {
                    dataSnapshot.getValue(Producto::class.java)
                } catch (e: Exception) {
                    null
                }
                producto?.let {
                    listaProductos.add(it)
                }
            }
            listaProductos.sortBy { producto -> producto.comprado }
            return Resultado.Success(listaProductos.toList())
        } else {
            return Resultado.Error("No se pudo cargar los datos ${task.exception?.message}")
        }

    }


//            }.addOnFailureListener {
//                onFail("No se pudo cargar los datos ${it.message}")
////                listaProductos.clear()
////                _productos.value = listaProductos
////                //adapter.notifyDataSetChanged()
////                _errorMessage.value = "No se pudo cargar los datos"
////                _visibility.value = false
////                Log.e("firebase", "Error getting data", it)
}







