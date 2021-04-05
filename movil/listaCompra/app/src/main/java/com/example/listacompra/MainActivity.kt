package com.example.listacompra


import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listacompra.databinding.ActivityMainBinding
import com.example.listacompra.modelo.Producto
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import java.io.Serializable
import java.time.LocalDate


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
    var tiendas = mutableListOf<String>()


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_edit -> {

                val intent = Intent(this, TiendasActivity::class.java).apply {
                    putExtra("tiendas", tiendas as Serializable)
                }
                startActivity(intent)
                finish()
                true
            }
            R.id.action_comprar -> {
                val dialog = AlertDialog.Builder(this)
                    .setTitle("CONFIRMACION")
                    .setMessage("Seguro que has acabado la compra")
                    .setNegativeButton("NO") { view, _ ->
                        view.dismiss()
                    }
                    .setPositiveButton("YES") { view, _ ->
                        val date: LocalDate = LocalDate.now()
                        val tamañoAnterior = productos.size
                        date.toString()
                        productos.filter { producto -> producto.comprado }.forEach { producto ->
                            database.child("tiendas/${binding.myToolbar.title.toString()}/listas/${date.toString()}/${producto.nombre}")
                                .setValue(producto)
                            database.child("tiendas/${binding.myToolbar.title.toString()}/listas/actual/${producto.nombre}")
                                .removeValue()
                        }
                        productos =
                            productos.filter { producto -> !producto.comprado }.toMutableList()
                        adapter.submitList(productos)
                        adapter.notifyItemRangeRemoved(productos.size - 1, tamañoAnterior)

                        //adapter.notifyDataSetChanged()
                        view.dismiss()
                    }
                    .setCancelable(false)
                    .create()

                dialog.show()
                true
            }
            R.id.action_selectTienda -> {
                val dialog = AlertDialog.Builder(this)
                    .setTitle(getString(R.string.select_tienda_dialog))
//                    .setMessage(R.string.app_name)
                    .setItems(
                        tiendas.toTypedArray(),
                        DialogInterface.OnClickListener { dialog, which ->
                            cargarListaProductos(tiendas[which])
                        })
                    .setNegativeButton("Cancel") { view, _ ->
                        view.dismiss()
                    }
                    .setCancelable(false)
                    .create()

                dialog.show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.myToolbar)

        //menuInflater.inflate(R.menu.menu,binding.myToolbar.menu)

        database = Firebase.database.reference

        initUI()

        binding.rvCompras.layoutManager = LinearLayoutManager(this)
        adapter = ProductoAdapter(
            object : ProductoAdapter.ProductosActions {
                override fun onEditarProducto(producto: Producto, nuevoNombre: String) =
                    editarProducto(producto, nuevoNombre)

                override fun comprarProducto(producto: Producto) = this@MainActivity.comprarProducto(producto)

                override fun borrarProducto(producto: Producto) = this@MainActivity.borrarProducto(producto)

                override fun cambiarProductoDeTienda(producto: Producto) =
                    this@MainActivity.cambiarProductoDeTienda(producto)

            })
        rvCompra.adapter = adapter


        btAdd.setOnClickListener { addTask() }
        fbRecargar.setOnClickListener { cargarListaProductos(binding.myToolbar.title.toString()) }

        cargarTiendas()


    }

     fun cambiarProductoDeTienda(producto: Producto) {
        val dialog = AlertDialog.Builder(this)
            .setTitle(getString(R.string.select_tienda_dialog))
//                    .setMessage(R.string.app_name)
            .setItems(
                tiendas.toTypedArray(),
                DialogInterface.OnClickListener { dialog, which ->
                    if (tiendas[which] != binding.myToolbar.title.toString()) {
                        database.child("tiendas/${tiendas[which]}/listas/actual")
                            .child(producto.nombre).setValue(producto)
                        database.child("tiendas/${binding.myToolbar.title.toString()}/listas/actual")
                            .child(producto.nombre).removeValue()
                        productos.remove(producto)

                        adapter.notifyDataSetChanged()
                    }
                })
            .setNegativeButton("Cancel") { view, _ ->
                view.dismiss()
            }
            .setCancelable(false)
            .create()

        dialog.show()

    }


    private fun cargarTiendas() {
        binding.progressBar.visibility = View.VISIBLE

        database.child("tiendas").get().addOnSuccessListener {
            tiendas.clear()
            it.children.forEach { dataSnapshot ->
                val tienda = dataSnapshot.key
                tienda?.let {
                    tiendas.add(it)
                }
                tiendas[0]?.let {
                    cargarListaProductos(it)
                }
            }
            binding.progressBar.visibility = View.GONE
        }


    }

    private fun cargarListaProductos(tienda: String) {
        binding.myToolbar.title = tienda
        binding.progressBar.visibility = View.VISIBLE
        database.child("tiendas").child(tienda).child("listas").child("actual").get()
            .addOnSuccessListener {
                productos.clear()
                it.children.forEach { dataSnapshot ->

                    val user = dataSnapshot.getValue(Producto::class.java)
                    user?.let {
                        productos.add(it)
                    }
                }
                productos.sortBy { producto -> producto.comprado }
                adapter.submitList(productos)
                adapter.notifyDataSetChanged()
                //adapter.notifyItemRangeChanged(0, productos.size)
                Log.d("::::TAG", "Got value ${it.value}")
                binding.progressBar.visibility = View.GONE
            }.addOnFailureListener {
                productos.clear()
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "No se pudo cargar los datos", Toast.LENGTH_LONG).show()
                binding.progressBar.visibility = View.GONE
                Log.e("firebase", "Error getting data", it)
            }
    }

    private fun initUI() {
        //button = findViewById(R.id.button)
        fbRecargar = findViewById(R.id.fbRecargar)

        etProducto = findViewById(R.id.etTienda)
        rvCompra = findViewById(R.id.rvCompras)
    }

    private fun addTask() {
        Toast.makeText(this, "No se pudo cargar los datos", Toast.LENGTH_LONG).show()
        val newProducto = Producto(etProducto.text.toString(), false)
        database.child("tiendas/${binding.myToolbar.title.toString()}/listas/actual")
            .child(newProducto.nombre).setValue(newProducto)
        productos.add(newProducto)
//            prefs.saveTasks(tasks)
        adapter.notifyItemInserted(productos.size - 1)
        etProducto.setText("")
    }

    private fun borrarProducto(producto: Producto) {
        database.child("tiendas").child(binding.myToolbar.title.toString()).child("listas")
            .child("actual").child(producto.nombre).removeValue()
        val indice = productos.indexOf(producto)
        productos.remove(producto)
        adapter.notifyItemRemoved(indice)
    }

    private fun editarProducto(producto: Producto, nombreAnterior: String) {
        database.child("tiendas").child(binding.myToolbar.title.toString()).child("listas")
            .child("actual").child(nombreAnterior).removeValue()
        database.child("tiendas").child(binding.myToolbar.title.toString()).child("listas")
            .child("actual").child(producto.nombre).setValue(producto)
        //adapter.notifyDataSetChanged()
    }


    private fun comprarProducto(producto: Producto) {
        producto.comprado = !producto.comprado
        database.child("tiendas").child(binding.myToolbar.title.toString()).child("listas")
            .child("actual").child(producto.nombre).setValue(producto)
        productos.sortBy { producto -> producto.comprado }
        adapter.submitList(productos)
//
//        adapter = ProductoAdapter(productos) { deleteTask(it) }
//        rvCompra.swapAdapter(adapter, false)

        adapter.notifyItemRangeChanged(0, productos.size)
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