package com.example.myapplication;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TimePicker;


public class TimeChange implements TimePickerDialog.OnTimeSetListener {

    Context context;

    public TimeChange(Context context) {
        this.context = context;
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1){
        String time=i+":"+i1;
        if(com.example.myapplication.TimePicker.flag==1)
            Modedislpay.timefield.setText(time);
        else
            Modedislpay.timefield.setText(time);

    }

}
