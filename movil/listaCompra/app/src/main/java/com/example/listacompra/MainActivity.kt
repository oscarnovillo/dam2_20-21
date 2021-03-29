package com.example.listacompra

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listacompra.modelo.Producto
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    lateinit var button: Button
    lateinit var btCargar: Button
    lateinit var etProducto: EditText
    lateinit var rvCompra: RecyclerView
    lateinit var adapter: ProductoAdapter
    private lateinit var database: DatabaseReference
// ...

    var productos = mutableListOf<Producto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = Firebase.database.reference
        initUI()


        rvCompra.layoutManager = LinearLayoutManager(this)
        adapter = ProductoAdapter(productos) { deleteTask(it) }
        rvCompra.adapter = adapter


        button.setOnClickListener { addTask() }

        btCargar.setOnClickListener{ cargarProductos()}
    }

    private fun cargarProductos() {
        database.child("productos").get().addOnSuccessListener {
            productos.clear()
            it.children.forEach { dataSnapshot ->
                run {
                    val user = dataSnapshot.getValue(Producto::class.java)
                    user?.let { productos.add(it)

                    }
                }
            }
            productos.sortBy { producto -> producto.comprado }
            adapter.productos = productos
            adapter.notifyDataSetChanged()
            Log.d("::::TAG", "Got value ${it.value}")
        }.addOnFailureListener {
            Log.e("firebase", "Error getting data", it)
        }

    }

    private fun initUI() {
        button = findViewById(R.id.button)
        btCargar = findViewById(R.id.btCargar)
        etProducto = findViewById(R.id.etProducto)
        rvCompra = findViewById(R.id.rvCompra)
    }

    private fun addTask() {
        val newProducto = Producto(etProducto.text.toString(), false)
        database.child("productos").child(newProducto.nombre).setValue(newProducto)
        productos.add(newProducto)
//            prefs.saveTasks(tasks)
        adapter.notifyDataSetChanged()
        etProducto.setText("")
    }

    private fun deleteTask(producto: Producto) {
        producto.comprado = !producto.comprado
        database.child("productos").child(producto.nombre).setValue(producto)
        productos.sortBy { producto -> producto.comprado }
        adapter.productos = productos
//
//        adapter = ProductoAdapter(productos) { deleteTask(it) }
//        rvCompra.swapAdapter(adapter, false)

        adapter.notifyDataSetChanged()
//        prefs.saveTasks(tasks)
    }


//    override fun onSaveInstanceState(outState: Bundle) {
//        outState?.run {
//
//        }
//        super.onSaveInstanceState(outState)
//    }
//
//    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
//
//    }
}