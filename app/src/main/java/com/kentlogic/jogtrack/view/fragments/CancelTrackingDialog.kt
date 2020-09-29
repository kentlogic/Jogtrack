package com.kentlogic.jogtrack.view.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kentlogic.jogtrack.R

class CancelTrackingDialog : DialogFragment() {

    private var yesListener: (() -> Unit)?  = null

    fun setYesListener(listener: () -> Unit) {
        yesListener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme)
            .setTitle("Cancel the Jog?")
            .setMessage("Are you sure you want to cancel the jog and reset the data?")
            .setIcon(R.drawable.ic_delete_black)
            .setPositiveButton("Yes") { _, _ ->
                yesListener?.let {yes ->
                    yes()
                }
            }
            .setNegativeButton("No") { dialogInterface, _ ->
                dialogInterface.cancel()
            }
            .create()
    }



}