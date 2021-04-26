package com.example.listacompra

import android.content.Context.INPUT_METHOD_SERVICE
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.listacompra.modelo.Producto
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
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

    private val _tiendaActual : MutableLiveData<String> = savedStateHandle.getLiveData("tiendaActual")
    val tiendaActual: LiveData<String> get() = _tiendaActual

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private var database: DatabaseReference


    init {
        database = Firebase.database.reference
    }


    fun cambiarDeTienda(tiendaNueva: Int,producto:Producto) {
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
        database.child("tiendas").child(tiendaActual.value?: "").child("listas")
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
            }
        _productos.value?.filter {producto -> !producto.comprado  }?.toCollection(listaProductos)
        _productos.value = listaProductos
    }

    fun cargarListaProductos(tienda: String) {
        _visibility.value = true
        _tiendaActual.value = tienda
        database.child("tiendas").child(tienda).child("listas").child("actual").get()
            .addOnSuccessListener {
                listaProductos.clear()
                it.children.forEach { dataSnapshot ->

                    val user = dataSnapshot.getValue(Producto::class.java)
                    user?.let {
                        listaProductos.add(it)
                    }
                }
                listaProductos.sortBy { producto -> producto.comprado }
                _productos.value = listaProductos
                Log.d("::::TAG", "Got value ${it.value}")
                _visibility.value = false
            }.addOnFailureListener {
                listaProductos.clear()
                _productos.value = listaProductos
                //adapter.notifyDataSetChanged()
                _errorMessage.value = "No se pudo cargar los datos"
                _visibility.value = false
                Log.e("firebase", "Error getting data", it)
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


}

