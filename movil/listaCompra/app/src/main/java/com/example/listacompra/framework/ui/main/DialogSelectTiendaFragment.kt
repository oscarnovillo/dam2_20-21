package com.example.listacompra.framework.ui.main

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.listacompra.R


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