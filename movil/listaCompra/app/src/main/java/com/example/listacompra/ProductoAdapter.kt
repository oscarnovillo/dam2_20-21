package com.example.listacompra

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.listacompra.modelo.Producto

class ProductoAdapter(var productos:List<Producto>, private val onItemDone: (Producto) -> Unit): RecyclerView.Adapter<ProductoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ProductoViewHolder(layoutInflater.inflate(R.layout.item_producto, parent, false))
    }

    override fun getItemCount() = productos.size

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        holder.render(productos[position], onItemDone)
    }
}