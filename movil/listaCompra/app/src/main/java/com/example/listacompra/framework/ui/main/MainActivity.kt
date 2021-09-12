package com.example.listacompra.framework.ui.main


import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.lifecycle.Observer
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listacompra.R
import com.example.listacompra.databinding.ActivityMainBinding
import com.example.listacompra.domain.Producto
import com.example.listacompra.framework.ui.tienda.TiendasActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.io.Serializable

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: ProductoAdapter

    private val viewModel: MainViewModel by viewModels()

    // añade el menu
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
                val dialog = MaterialAlertDialogBuilder(this)
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
                        viewModel.changeTiendaActual(
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
        Timber.d("Creando actividad!!!")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // si el target es menor que 1.8 build gradle options
        // ViewModelProvider(this)[MainViewModel::class.java]
        // viewModel = ViewModelProvider(this).get()

        adapter = ProductoAdapter(
            object : ProductoAdapter.ProductosActions {
                override fun onEditarProducto(producto: Producto, nuevoNombre: String) =
                    this@MainActivity.editarProducto(producto, nuevoNombre)

                override fun comprarProducto(producto: Producto) =
                    this@MainActivity.viewModel.comprarProducto(producto)

                override fun borrarProducto(producto: Producto) =
                    this@MainActivity.viewModel.borrarProducto(producto)

                override fun cambiarProductoDeTienda(producto: Producto) =
                    this@MainActivity.cambiarProductoDeTienda(producto)
            })

        with(binding) {
            rvCompras.adapter = adapter
            rvCompras.layoutManager = LinearLayoutManager(this@MainActivity)
        }

        viewModel.visibility.observe(this, Observer { visible ->
            binding.progressBar.visibility = if (visible) View.VISIBLE else View.GONE
        })



        viewModel.errorMessage.observe(this, Observer { error ->
            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
        })

        viewModel.productos.observe(this, Observer { productos ->
            adapter.submitList(productos)
            adapter.notifyDataSetChanged()
        })

        viewModel.tiendaActual.observe(this, Observer { tienda ->
            binding.myToolbar.title = tienda

        })

        with(binding) {
            btAdd.setOnClickListener { addProducto() }
            addMultiple.setOnClickListener { addProductosCompra() }
            fbRecargar.setOnClickListener { viewModel.cargarListaProductos() }
        }

//        binding.myToolbar.title =
//            PreferenceManager.getDefaultSharedPreferences(this).getString("TIENDA", "")
        viewModel.cargarTiendas()

        setSupportActionBar(binding.myToolbar)


    }

    private fun addProductosCompra() {
        val dialog = AlertDialog.Builder(this)
            .setTitle("CONFIRMACION")
            .setMessage("¿Seguro que quieres añadir todos los productos de la ultima compra?")
            .setNegativeButton("NO") { view, _ ->
                view.dismiss()
            }
            .setPositiveButton("YES") { view, _ ->
                viewModel.addProductosUltimaCompra()
                view.dismiss()
            }
            .setCancelable(false)
            .create()

        dialog.show()
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

    private fun editarProducto(producto: Producto, nuevoNombre: String) {
        viewModel.editarProducto(producto, nuevoNombre)
        hideKeyboard()
    }

    private fun addProducto() {
        val newProducto = Producto(binding.etProducto.text.toString())
        viewModel.addProducto(newProducto)
        binding.etProducto.setText("")
        hideKeyboard()
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
