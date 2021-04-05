package com.example.listacompra

import android.view.View.*
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.RecyclerView
import com.example.listacompra.databinding.ItemProductoBinding
import com.example.listacompra.modelo.Producto

class ProductoViewHolder(val binding: ItemProductoBinding) : RecyclerView.ViewHolder(binding.root) {

//    private val tvTask: TextInputEditText = view.findViewById(R.id.tvTask)
//    private val tvTextView: MaterialTextView = view.findViewById(R.id.textView)
//    private val filledTextField: TextInputLayout = view.findViewById(R.id.filledTextField)
//
//    private val ivTaskDone: ImageView = view.findViewById(R.id.ivTaskDone)
//    private val ivDelete: ImageView = view.findViewById(R.id.ivDelete)

    fun render(
        producto: Producto,
        productosActions : ProductoAdapter.ProductosActions

    ) {
        with(binding) {
            tvTask.setText(producto.nombre)
            mtvProducto.text = producto.nombre
            tvTask.setSingleLine()
            tvTask.setOnFocusChangeListener { v, hasFocus -> if (!hasFocus) editText(producto,productosActions::onEditarProducto)}
            filledTextField.visibility = GONE
            mtvProducto.visibility = VISIBLE
        }
        pintarIcono(producto.comprado)

        binding.mtvProducto.setOnClickListener {
            binding.filledTextField.visibility = VISIBLE;
            binding.tvTask.requestFocus()
//            val imm = binding.root.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
//
//            // here is one more tricky issue
//            // imm.showSoftInputMethod doesn't work well
//            // and imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0) doesn't work well for all cases too
//            imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
            binding.mtvProducto.visibility = INVISIBLE
        }

        binding.tvTask.setOnEditorActionListener() { v, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE, EditorInfo.IME_ACTION_NEXT -> {


                    true
                }
                else -> false
            }
        }

        binding.ivTaskDone.setOnClickListener { productosActions.comprarProducto(producto) }

        binding.ivDelete.setOnClickListener { productosActions.borrarProducto(producto) }
        binding.ivCambiarTienda.setOnClickListener { productosActions.cambiarProductoDeTienda(producto) }
    }

    private fun editText(producto: Producto, onEditarProducto: (Producto, String) -> Unit) {
        with(binding) {
            filledTextField.visibility = GONE
            mtvProducto.visibility = VISIBLE
            val nombreAnterior = producto.nombre
            producto.nombre = tvTask.text.toString()
            mtvProducto.text = producto.nombre
            onEditarProducto(producto, nombreAnterior);
        }
    }

    fun pintarIcono(comprado: Boolean) {
        when (comprado) {
            true -> cargarComprado()
            false -> cargarAComprar()
        }
    }

    fun cargarComprado() {
        binding.ivTaskDone.setColorFilter(binding.root.context.getResources().getColor(R.color.grey));
        binding.ivTaskDone.setImageResource(R.drawable.ic_comprado);
    }

    fun cargarAComprar() {
        binding.ivTaskDone.setColorFilter( binding.root.context.getResources().getColor(R.color.green));
        binding.ivTaskDone.setImageResource(R.drawable.ic_a_comprar);
    }
}