package com.example.newtoolsmon.unitconvert.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.newtoolsmon.R;
import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<String> unitItemList;


    public SpinnerAdapter(@NonNull Context context, @NonNull List<String> unitItemList) {
        super(context, 0, unitItemList);
        this.context = context;
        this.unitItemList = unitItemList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String item = unitItemList.get(position);

        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_item_upper, parent, false);
        }

        TextView spinnerText = view.findViewById(R.id.textView_spinnerItem_upper);

        spinnerText.setText(item);
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView dropDownText = (TextView) super.getDropDownView(position, convertView, parent);
        return dropDownText;
    }
}
