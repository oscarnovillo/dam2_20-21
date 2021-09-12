package com.example.listacompra.framework.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.listacompra.databinding.ItemProductoBinding
import com.example.listacompra.domain.Producto

class ProductoAdapter(
//                      private val onEditarProducto: (Producto,String) -> Unit,
//                      private val onComprarProducto: (Producto) -> Unit,
//                      private val onBorrarProducto: (Producto) -> Unit,
//                      private val onCambiarProductoDeTienda: (Producto) -> Unit,
                      private val productosActions: ProductosActions
): ListAdapter<Producto, ProductoViewHolder>(DiffUtilCallback) {


    interface ProductosActions {
        fun onEditarProducto(producto: Producto, nuevoNombre: String) : Unit
        fun comprarProducto (producto: Producto) : Unit
        fun borrarProducto (producto: Producto) : Unit
        fun cambiarProductoDeTienda (producto: Producto) : Unit
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
//        val layoutInflater = LayoutInflater.from(parent.context)
        return ProductoViewHolder(ItemProductoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false))
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        holder.render(currentList[position],productosActions)
    }


    private object DiffUtilCallback : DiffUtil.ItemCallback<Producto>(){
        override fun areItemsTheSame(oldItem: Producto, newItem: Producto): Boolean =
            oldItem.nombre == newItem.nombre

        override fun areContentsTheSame(oldItem: Producto, newItem: Producto): Boolean =
            oldItem == newItem

    }
}