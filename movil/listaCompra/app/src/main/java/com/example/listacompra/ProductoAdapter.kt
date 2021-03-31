package com.example.listacompra

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.listacompra.databinding.ItemProductoBinding
import com.example.listacompra.modelo.Producto

class ProductoAdapter(var productos:List<Producto>,
                      private val onEditarProducto: (Producto,String) -> Unit,
                      private val onComprarProducto: (Producto) -> Unit,
                      private val onBorrarProducto: (Producto) -> Unit): RecyclerView.Adapter<ProductoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ProductoViewHolder(ItemProductoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false))
    }

    override fun getItemCount() = productos.size

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        holder.render(productos[position], onEditarProducto,onComprarProducto,onBorrarProducto)
    }
}