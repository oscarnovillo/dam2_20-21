package com.example.listacompra.framework.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.example.listacompra.data.Resultado
import com.example.listacompra.data.repository.ProductoRepository
import com.example.listacompra.domain.Producto
import com.example.listacompra.framework.data.datasource.FirebaseProductoDataSource
import com.example.listacompra.usecases.GetProductosTienda
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.time.LocalDate

class MainViewModel(val savedStateHandle: SavedStateHandle) : ViewModel() {

    private var listaProductos = mutableListOf<Producto>()
    private var listaTiendas = mutableListOf<String>()

    private val _productos = MutableLiveData<List<Producto>>()
    val productos: LiveData<List<Producto>> get() = _productos

    private val _tiendas = MutableLiveData<List<String>>()
    val tiendas: LiveData<List<String>> get() = _tiendas

    private val _visibility = MutableLiveData<Boolean>()
    val visibility: LiveData<Boolean> get() = _visibility

    private val _tiendaActual: MutableLiveData<String> =
        savedStateHandle.getLiveData("tiendaActual")
    val tiendaActual: LiveData<String> get() = _tiendaActual

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private var database: DatabaseReference


    init {
        database = Firebase.database.reference
    }


    fun cambiarDeTienda(tiendaNueva: Int, producto: Producto) {
        if (listaTiendas[tiendaNueva] != tiendaActual.value) {
            database.child("tiendas/${listaTiendas[tiendaNueva]}/listas/actual")
                .child(producto.nombre).setValue(producto)
            database.child("tiendas/${tiendaActual.value}/listas/actual")
                .child(producto.nombre).removeValue()
            listaProductos.remove(producto)
            _productos.value = listaProductos
        }
    }

    fun cargarTiendas() {
        _visibility.value = true

        database.child("tiendas").get().addOnSuccessListener {
            listaTiendas.clear()
            it.children.forEach { dataSnapshot ->
                val tienda = dataSnapshot.key
                tienda?.let {
                    listaTiendas.add(it)
                }
                listaTiendas[0]?.let {
                    _tiendaActual.value = listaTiendas[0]
                    cargarListaProductos()
                }
            }
            _tiendas.value = listaTiendas
            _visibility.value = false
        }


    }


    fun comprarProducto(producto: Producto) {
        producto.comprado = !producto.comprado
        database.child("tiendas").child(tiendaActual.value ?: "").child("listas")
            .child("actual").child(producto.nombre).setValue(producto)
        _productos.value = listaProductos
    }


    fun cargarListaProductos() {
        val tienda = _tiendaActual.value ?: ""
        cargarListaProductos(tienda)
    }

    fun comprar() {
        val date: LocalDate = LocalDate.now()
        val tamañoAnterior = productos.value?.size
        date.toString()
        productos.value?.filter { producto -> producto.comprado }
            ?.forEach { producto ->
                database.child("tiendas/${tiendaActual.value}/listas/${date.toString()}/${producto.nombre}")
                    .setValue(producto)
                database.child("tiendas/${tiendaActual.value}/listas/actual/${producto.nombre}")
                    .removeValue()
                listaProductos.remove(producto)
            }

        //_productos.value?.filter { producto -> !producto.comprado }?.toCollection(listaProductos)
        _productos.value = listaProductos
    }

    fun cargarListaProductos(tienda: String) {


        viewModelScope.launch {

            _visibility.value = true
            _tiendaActual.value = tienda
            val getProductosTienda = GetProductosTienda(
                ProductoRepository(
                    FirebaseProductoDataSource(),
                    database
                )
            )
            val productos = getProductosTienda(tienda)
            when (productos) {
                is Resultado.Error -> {
                    listaProductos.clear()
                    _productos.value = listaProductos
                    //adapter.notifyDataSetChanged()
                    _errorMessage.value = productos.message
                    _visibility.value = false
                    Log.e("firebase", "Error getting data ${ productos.message}")
                }
                Resultado.Loading -> {
                    // cuando hay flows
                }

                is Resultado.Success -> {
                    listaProductos.clear()
                    listaProductos.addAll(productos.result)
                    _productos.value = listaProductos
                    Log.d("::::TAG", "Got value ${productos}")
                    _visibility.value = false
                }
            }
        }


    }


    fun addProducto(productoNuevo: Producto) {
        database.child("tiendas/${tiendaActual.value ?: ""}/listas/actual")
            .child(productoNuevo.nombre).setValue(productoNuevo)
        listaProductos.add(productoNuevo)
        _productos.value = listaProductos
    }

    fun borrarProducto(producto: Producto) {
        database.child("tiendas").child(tiendaActual.value.orEmpty()).child("listas")
            .child("actual").child(producto.nombre).removeValue()
        //val indice = productos.indexOf(producto)
        listaProductos.remove(producto)
        _productos.value = listaProductos
        //adapter.notifyItemRemoved(indice)
    }

    fun editarProducto(producto: Producto, nombreAnterior: String) {
        database.child("tiendas").child(tiendaActual.value.orEmpty()).child("listas")
            .child("actual").child(nombreAnterior).removeValue()
        database.child("tiendas").child(tiendaActual.value.orEmpty()).child("listas")
            .child("actual").child(producto.nombre).setValue(producto)
        //adapter.notifyDataSetChanged()
    }

    fun addProductosUltimaCompra() {
        database.child("tiendas/${tiendaActual.value}/listas/").get()
            .addOnSuccessListener { listas ->
                // el ultimo es el actual
                listas.children.elementAt(listas.childrenCount.toInt() - 2).children.forEach {
                    val producto = it.getValue(Producto::class.java)

                    producto?.let {
                        producto.comprado = false
                        addProducto(producto)
                    }
                }
            }
    }


}


