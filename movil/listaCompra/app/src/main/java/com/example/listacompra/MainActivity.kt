package com.example.listacompra


import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listacompra.databinding.ActivityMainBinding
import com.example.listacompra.modelo.Producto
import kotlinx.android.synthetic.main.activity_main.*
import java.io.Serializable


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var adapter: ProductoAdapter

    private lateinit var viewModel: MainViewModel

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_edit -> {

                val intent = Intent(this, TiendasActivity::class.java).apply {
                    putExtra("tiendas", viewModel.tiendas.value as Serializable)
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
                        viewModel.comprar()
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
                    .setItems(
                        viewModel.tiendas.value?.toTypedArray()
                    ) { _, which ->
                        viewModel.cargarListaProductos(
                            viewModel.tiendas.value?.get(which).orEmpty()
                        )
                    }
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

        // si el target es menor que 1.8 build gradle options
        // ViewModelProvider(this)[MainViewModel::class.java]
        viewModel = ViewModelProvider(this).get()

        adapter = ProductoAdapter(
            object : ProductoAdapter.ProductosActions {
                override fun onEditarProducto(producto: Producto, nuevoNombre: String) =
                    this@MainActivity.editarProducto(producto,nuevoNombre)

                override fun comprarProducto(producto: Producto) =
                    this@MainActivity.viewModel.comprarProducto(producto)

                override fun borrarProducto(producto: Producto) =
                    this@MainActivity.viewModel.borrarProducto(producto)

                override fun cambiarProductoDeTienda(producto: Producto) =
                    this@MainActivity.cambiarProductoDeTienda(producto)
            })

        binding.rvCompras.adapter = adapter
        binding.rvCompras.layoutManager = LinearLayoutManager(this)


        viewModel.visibility.observe(this, Observer { visible ->
            binding.progressBar.visibility = if (visible) View.VISIBLE else View.GONE
        })

        viewModel.tiendaActual.observe(this, Observer { tienda ->
            binding.myToolbar.title = tienda
        })

        viewModel.errorMessage.observe(this, Observer { error ->
            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
        })

        viewModel.productos.observe(this, Observer { productos ->
            adapter.submitList(productos)
            adapter.notifyDataSetChanged()
        })

        btAdd.setOnClickListener { addProducto() }
        binding.fbRecargar.setOnClickListener { viewModel.cargarListaProductos() }

        if (viewModel.tiendaActual.value == null) {
            viewModel.cargarTiendas()
        } else {
            binding.myToolbar.title = viewModel.tiendaActual.value
        }
        setSupportActionBar(binding.myToolbar)
    }

    fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    fun cambiarProductoDeTienda(producto: Producto) {
        val dialog = AlertDialog.Builder(this)
            .setTitle(getString(R.string.select_tienda_dialog))
            .setItems(
                viewModel.tiendas.value?.toTypedArray(),
                DialogInterface.OnClickListener { dialog, which ->
                    viewModel.cambiarDeTienda(which, producto)
                })
            .setNegativeButton("Cancel") { view, _ ->
                view.dismiss()
            }
            .setCancelable(false)
            .create()

        dialog.show()

    }

    private fun editarProducto(producto: Producto, nuevoNombre: String)
    {
        viewModel.editarProducto(producto, nuevoNombre)
        hideKeyboard()
    }

    private fun addProducto() {
        val newProducto = Producto(binding.etProducto.text.toString(), false)
        viewModel.addProducto(newProducto)
        binding.etProducto.setText("")
    }

    override fun onSaveInstanceState(outState: Bundle) {
//        if (::viewModel.isInitialized)
//            viewModel.saveState()
        super.onSaveInstanceState(outState)
    }
//
//    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
//
//    }
}
