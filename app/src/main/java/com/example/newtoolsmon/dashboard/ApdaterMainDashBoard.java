package com.example.newtoolsmon.dashboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newtoolsmon.R;

import java.util.ArrayList;
import java.util.List;

public class ApdaterMainDashBoard extends RecyclerView.Adapter<ApdaterMainDashBoard.RecyclerViewViewHolder> {

    private final LayoutInflater mInflater;
    private List<DashBoardMainArrayList>mItemList;

    public static class RecyclerViewViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView item_name;
      //  TextView subText;
        ImageView item_pic;

        public RecyclerViewViewHolder(View itemView){
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            item_name = (TextView)itemView.findViewById(R.id.item_name);
         //   subText = (TextView)itemView.findViewById(R.id.item_description);
            item_pic = (ImageView)itemView.findViewById(R.id.item_pic);
        }

        public void bind(DashBoardMainArrayList dashBoardMainArrayList){
            item_name.setText(dashBoardMainArrayList.getTitle());
          //  subText.setText(dashBoardMainArrayList.getLocation());
            item_pic.setImageResource(dashBoardMainArrayList.getJobType());
        }
    }

    public ApdaterMainDashBoard(Context context, List<DashBoardMainArrayList> itemList) {
        mInflater = LayoutInflater.from(context);
        mItemList = new ArrayList<>(itemList);

    }

    @NonNull
    @Override
    public RecyclerViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = mInflater.inflate(R.layout.item_dashboard_main, parent, false);
        return new RecyclerViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final DashBoardMainArrayList dashBoardMainArrayList = mItemList.get(position);
        holder.bind(dashBoardMainArrayList);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = holder.itemView.getContext();
                Intent intent = new Intent(v.getContext(), DashBoardMainClicks.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("DATA", mItemList.get(position));
                intent.putExtras(bundle);
                context.startActivity(intent);

                Log.d("mytesta", "data " + mItemList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.mItemList.size();

    }

    public void setItems(List<DashBoardMainArrayList> dashBoardMainArrayLists){
        mItemList = new ArrayList<>(dashBoardMainArrayLists);
    }




    List<DashBoardMainArrayList> filter(List<DashBoardMainArrayList> dashBoardMainArrayLists, String newText) {
        newText = newText.toLowerCase().trim();

        final List<DashBoardMainArrayList> filteredModelList = new ArrayList<>();
        for (DashBoardMainArrayList dashBoardMainArrayList : dashBoardMainArrayLists) {
            final String text = dashBoardMainArrayList.getTitle().toLowerCase().trim();
            if (text.contains(newText)) {
                filteredModelList.add(dashBoardMainArrayList);
            }
        }
        return filteredModelList;
    }




}
