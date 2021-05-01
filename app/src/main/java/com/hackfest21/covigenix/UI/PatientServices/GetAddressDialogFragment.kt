package com.hackfest21.covigenix.UI.PatientServices

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.hackfest21.covigenix.R

class GetAddressDialogFragment(val listener: GetAddressListener, val pos: Int): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.custom_dialog_fragment, null)
        val EVAddress = view.findViewById<EditText>(R.id.address)
        builder.setView(view)
            .setTitle("Share Address")
            .setPositiveButton("Send", {dial, which ->
                val address = EVAddress.text.toString().trim()
                if(address.equals("")){
                    EVAddress.setError("Enter valid address.")
                }else{
                    listener.onSubmit(address, pos)
                }
            })
            .setNegativeButton("Cancel", {dial, which -> })

        return builder.create()
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    interface GetAddressListener{
        fun onSubmit(address: String, posn: Int)
    }
}