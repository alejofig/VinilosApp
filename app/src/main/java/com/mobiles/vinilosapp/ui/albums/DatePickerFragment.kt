package com.mobiles.vinilosapp.ui.albums

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.mobiles.vinilosapp.R
import java.util.*

class DatePickerFragment( val listener: (day:Int, month:Int, year:Int) -> Unit): DialogFragment(),
    DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)

        val picker = DatePickerDialog(activity as Context, R.style.dataPickerTheme, this, year, month, day)
        picker.datePicker.maxDate = c.timeInMillis
        return picker

    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMont: Int) {
        listener(dayOfMont, month, year)
    }

}