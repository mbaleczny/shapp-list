package com.mbaleczny.shapp_list.ui.add

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.mbaleczny.shapp_list.R
import org.jetbrains.anko.support.v4.toast

/**
 * @author Mariusz Baleczny
 * @date 26/04/19
 */
class AddItemDialogFragment : DialogFragment() {

    companion object {

        const val TAG = "AddItemDialogFragment"
        private const val TITLE = "title_arg"
        private const val HINT = "hint_arg"

        fun newInstance(title: String?, hint: String?): AddItemDialogFragment {
            return AddItemDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(TITLE, title)
                    putString(HINT, hint)
                }
            }
        }
    }

    private lateinit var alertDialog: AlertDialog
    private lateinit var inputText: TextInputEditText
    private lateinit var inputLayout: TextInputLayout

    var onAddItemListener: OnAddItemListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_add_item, null)
        inputText = view.findViewById(R.id.dialog_add_item_input_text)
        inputLayout = view.findViewById(R.id.dialog_add_item_input_layout)

        val builder = AlertDialog.Builder(requireContext())
            .setView(view)
            .setPositiveButton(R.string.create) { _, _ -> }
            .setNegativeButton(android.R.string.cancel) { d, _ -> d.dismiss() }

        arguments?.apply {
            builder.setTitle(getString(TITLE))
            inputLayout.hint = getString(HINT)
        }

        alertDialog = builder.create()

        return alertDialog
    }

    override fun onResume() {
        super.onResume()
        setupPositiveButton(alertDialog)
    }

    private fun setupPositiveButton(dialog: AlertDialog) {
        dialog.getButton(Dialog.BUTTON_POSITIVE).setOnClickListener {
            val inputString = inputText.text.toString()

            with(inputString) {
                if (this.isEmpty()) {
                    toast(R.string.missing_text_message).show()
                } else {
                    onAddItemListener?.addItem(this)
                    dialog.dismiss()
                }
            }
        }
    }
}