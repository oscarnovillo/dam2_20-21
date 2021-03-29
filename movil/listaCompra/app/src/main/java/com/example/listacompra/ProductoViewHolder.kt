package com.example.listacompra

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.TypedArrayUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.listacompra.modelo.Producto

class ProductoViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private val tvTask: TextView = view.findViewById(R.id.tvTask)
    private val ivTaskDone: ImageView = view.findViewById(R.id.ivTaskDone)

    fun render(producto: Producto, onItemDone: (Producto) -> Unit) {
        tvTask.text = producto.nombre

        pintarIcono(producto.comprado)

        ivTaskDone.setOnClickListener {

            onItemDone(producto)
        }
    }

    fun pintarIcono(comprado: Boolean) {
        when (comprado) {
            true -> cargarComprado()
            false -> cargarAComprar()
        }
    }

    fun cargarComprado() {
        ivTaskDone.setColorFilter(view.context.getResources().getColor(R.color.grey));
        ivTaskDone.setImageResource(R.drawable.ic_comprado);
    }

    fun cargarAComprar() {
        ivTaskDone.setColorFilter(view.context.getResources().getColor(R.color.green));
        ivTaskDone.setImageResource(R.drawable.ic_a_comprar);
    }
}