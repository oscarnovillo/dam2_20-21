package com.example.listacompra

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.listacompra.databinding.ItemTiendaBinding

class TiendaAdapter(
    val onEditarProducto: (String, String) -> Unit,
    private val onBorrarProducto: (String) -> Unit
) : ListAdapter<String, TiendaAdapter.TiendaViewHolder>(DiffUtilTiendaCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TiendaViewHolder {
//        LayoutInflater.from(parent.context)
        return TiendaViewHolder(
            ItemTiendaBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: TiendaViewHolder, position: Int) {
        holder.render(getItem(position), onEditarProducto, onBorrarProducto)
    }


    private object DiffUtilTiendaCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem

    }


    class TiendaViewHolder(val binding: ItemTiendaBinding) : RecyclerView.ViewHolder(binding.root) {

//    private val tvTask: TextInputEditText = view.findViewById(R.id.tvTask)
//    private val tvTextView: MaterialTextView = view.findViewById(R.id.textView)
//    private val filledTextField: TextInputLayout = view.findViewById(R.id.filledTextField)
//
//    private val ivTaskDone: ImageView = view.findViewById(R.id.ivTaskDone)
//    private val ivDelete: ImageView = view.findViewById(R.id.ivDelete)

        fun render(
            producto: String,
            onEditarProducto: (String, String) -> Unit,
            onBorrarProducto: (String) -> Unit
        ) {
            with(binding) {
                tvTask.setText(producto)
                mtvProducto.text = producto
                tvTask.setSingleLine()
                tvTask.setOnFocusChangeListener { v, hasFocus ->
                    if (!hasFocus) editText(
                        producto,
                        onEditarProducto
                    )
                }
                filledTextField.visibility = View.GONE
                mtvProducto.visibility = View.VISIBLE
            }
            binding.mtvProducto.setOnClickListener {
                binding.filledTextField.visibility = View.VISIBLE
                binding.tvTask.requestFocus()
//            val imm = binding.root.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
//
//            // here is one more tricky issue
//            // imm.showSoftInputMethod doesn't work well
//            // and imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0) doesn't work well for all cases too
//            imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
                binding.mtvProducto.visibility = View.INVISIBLE
            }

            binding.tvTask.setOnEditorActionListener { v, actionId, event ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE, EditorInfo.IME_ACTION_NEXT -> {


                        true
                    }
                    else -> false
                }
            }

            binding.ivDelete.setOnClickListener { onBorrarProducto(producto) }
        }

        private fun editText(producto: String, onEditarProducto: (String, String) -> Unit) {
            with(binding) {
                filledTextField.visibility = View.GONE
                mtvProducto.visibility = View.VISIBLE
                val nombreAnterior = producto
                val productoNuevo = tvTask.text.toString()
                mtvProducto.text = productoNuevo
                onEditarProducto(productoNuevo, nombreAnterior)
            }
        }


    }
}