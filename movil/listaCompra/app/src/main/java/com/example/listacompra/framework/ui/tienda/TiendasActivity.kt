package com.example.listacompra.framework.ui.tienda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.addCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listacompra.R
import com.example.listacompra.databinding.ActivityTiendasBinding
import com.example.listacompra.framework.ui.main.MainActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class TiendasActivity : AppCompatActivity() {


    private lateinit var binding: ActivityTiendasBinding
    private lateinit var database: DatabaseReference
    private lateinit var adapter: TiendaAdapter
    private var tiendas = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tiendas)

        // calling the action bar
        // This callback will only be called when MyFragment is at least Started.
        onBackPressedDispatcher.addCallback(this, onBackPressed = {
            val intent = Intent(this@TiendasActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        })

        binding = ActivityTiendasBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.myToolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        database = Firebase.database.reference
        binding.rvTiendas.layoutManager = LinearLayoutManager(this)
        adapter = TiendaAdapter({ producto: String, s: String -> editarProducto(producto, s) },
            { borrarProducto(it) })
        binding.rvTiendas.adapter = adapter
        adapter.submitList(tiendas)
        cargarTiendas()


        btAdd.setOnClickListener { addTask() }
    }


    private fun cargarTiendas() {
        database.child("tiendas").get().addOnSuccessListener {
            tiendas.clear()
            it.children.forEach { dataSnapshot ->
                val tienda = dataSnapshot.key
                tienda?.let {
                    tiendas.add(it)
                }
            }
            adapter.notifyDataSetChanged()
        }


    }


    private fun borrarProducto(producto: String) {
//        database.child("tiendas").child(binding.myToolbar.title.toString()).child("listas")
//            .child("actual").child(producto).removeValue()
        val indice = tiendas.indexOf(producto)
        tiendas.remove(producto)
        adapter.notifyItemRemoved(indice)
    }

    private fun editarProducto(producto: String, nombreAnterior: String) {
//        database.child("tiendas").child(binding.myToolbar.title.toString()).child("listas")
//            .child("actual").child(nombreAnterior).removeValue()
//        database.child("tiendas").child(binding.myToolbar.title.toString()).child("listas")
//            .child("actual").child(producto).setValue(producto)
        adapter.notifyDataSetChanged()
    }


    private fun addTask() {
        val newProducto = binding.etTienda.text.toString()
        database.child("tiendas/${newProducto}/nombre").setValue(newProducto)
        tiendas.add(newProducto)
        adapter.notifyItemInserted(tiendas.size-1)
        binding.etTienda.setText("")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(this@TiendasActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
                return true
            }
            else ->  super.onOptionsItemSelected(item)
        }

    }

    // this event will enable the back
    // function to the button on press
    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(this@TiendasActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }
}
