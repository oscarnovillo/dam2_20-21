package com.example.listacompra

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment


/**
 * A simple [Fragment] subclass.
 * Use the [DialogSelectTiendaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DialogSelectTiendaFragment : DialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(R.string.app_name)
                .setItems(
                    arrayOf("ll","asda"),
                    DialogInterface.OnClickListener { dialog, which ->

                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }



}