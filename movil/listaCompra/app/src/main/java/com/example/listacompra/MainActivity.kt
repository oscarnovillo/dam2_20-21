package com.example.listacompra

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listacompra.databinding.ActivityMainBinding
import com.example.listacompra.modelo.Producto
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //lateinit var button: Button
    lateinit var fbRecargar: FloatingActionButton

    private lateinit var binding: ActivityMainBinding

    lateinit var etProducto: EditText
    lateinit var rvCompra: RecyclerView
    lateinit var adapter: ProductoAdapter
    private lateinit var database: DatabaseReference
// ...

    var productos = mutableListOf<Producto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        database = Firebase.database.reference
        initUI()

        binding.rvCompra.layoutManager = LinearLayoutManager(this)
        adapter = ProductoAdapter(productos,{ producto: Producto, s: String -> editarProducto(producto,s)},{comprarProducto(it)},{borrarProducto(it)})
        rvCompra.adapter = adapter


        button.setOnClickListener { addTask() }
        fbRecargar.setOnClickListener{cargarProductos()}
        
        cargarProductos()
    }

    private fun cargarProductos() {
        binding.progressBar.visibility = View.VISIBLE
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
            binding.progressBar.visibility = View.GONE
        }.addOnFailureListener {
            Toast.makeText(this,"No se pudo cargar los datos",Toast.LENGTH_LONG)
            binding.progressBar.visibility = View.GONE
            Log.e("firebase", "Error getting data", it)
        }

    }

    private fun initUI() {
        //button = findViewById(R.id.button)
        fbRecargar = findViewById(R.id.fbRecargar)

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

    private fun borrarProducto(producto: Producto) {
        database.child("productos").child(producto.nombre).removeValue()
        productos.remove(producto)
        adapter.notifyDataSetChanged()
    }
    private fun editarProducto(producto: Producto,nombreAnterior : String) {
        database.child("productos").child(nombreAnterior).removeValue()
        database.child("productos").child(producto.nombre).setValue(producto)
        //adapter.notifyDataSetChanged()
    }


    private fun comprarProducto(producto: Producto) {
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