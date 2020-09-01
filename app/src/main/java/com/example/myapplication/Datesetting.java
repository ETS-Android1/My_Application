package com.example.myapplication;
import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

public class Datesetting implements DatePickerDialog.OnDateSetListener{

    Context context;
    public Datesetting(Context context) {

        this.context = context;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        String date=i2+"/"+i1+"/"+i;
        if(com.example.myapplication.DatePicker.flag==1)
            Modedislpay.datefield.setText(date);
        else
        Modedislpay.datefield.setText(date);
    }
}
