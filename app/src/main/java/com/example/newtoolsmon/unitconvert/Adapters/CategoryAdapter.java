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


public class CategoryAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<String> categoryItemList;


    public CategoryAdapter(@NonNull Context context, @NonNull List<String> categoryItemList) {
        super(context, 0, categoryItemList);
        this.context = context;
        this.categoryItemList = categoryItemList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String item = categoryItemList.get(position);

        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_item_cat, parent, false);
        }

        TextView spinnerText = view.findViewById(R.id.textView_spinnerItem_cat);

        spinnerText.setText(item);

        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        	 try {

                 TextView dropDownText = (TextView) super.getDropDownView(position, convertView, parent);
                 return dropDownText;
        	         }
        	 		catch (Exception e) {
        	             e.printStackTrace();
        	         }

        return convertView;
    }
}
