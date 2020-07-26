package com.encoderbytes.qrcodeScannerAndGenerator.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.encoderbytes.qrcodeScannerAndGenerator.DBManager;
import com.encoderbytes.qrcodeScannerAndGenerator.MyResult;
import com.encoderbytes.qrcodeScannerAndGenerator.R;
 import com.encoderbytes.qrcodeScannerAndGenerator.models.History_model;


import java.util.ArrayList;

public class History_adapter extends RecyclerView.Adapter<History_adapter.HistoryHolder> {

    Context context;
    ArrayList<History_model>listHistory;



    public History_adapter(Context context, ArrayList<History_model> listHistory) {
        this.context = context;
        this.listHistory = listHistory;



    }

    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.items_history,null);

        return new HistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HistoryHolder holder, final int position) {

         History_model model=listHistory.get(position);

        final String titles=model.getTitle();
        final  int myId=model.getId();

        holder.txtTitle.setText(titles);


        String type=model.getType();
        holder.txtType.setText(type);

        if (titles.contains("http://") || titles.contains("https://")){
            holder.imgType.setImageResource(R.drawable.ic_link);
        }else {
            holder.imgType.setImageResource(R.drawable.ic_letter_a);
        }

        holder.relativeHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, MyResult.class);
                intent.putExtra("result",titles);
                context.startActivity(intent);
            }
        });

        holder.btnPopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                PopupMenu popup=new PopupMenu(context,holder.btnPopUp);
                popup.inflate(R.menu.popup_menu);

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()){


                            case R.id.delete:

                                DBManager dbManager=new DBManager(context);
                                dbManager.open();
                                dbManager.delete(myId);
                                listHistory.remove(position);
                                notifyDataSetChanged();


                                break;
                        }

                        return false;
                    }
                });
                popup.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listHistory.size();
    }

    public class HistoryHolder extends RecyclerView.ViewHolder {

        TextView txtTitle,txtType;
        ImageView imgType;
        RelativeLayout relativeHistory;
        ImageButton btnPopUp;

        public HistoryHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle=itemView.findViewById(R.id.item_title);
            txtType=itemView.findViewById(R.id.item_txtType);
            imgType=itemView.findViewById(R.id.item_imgType);
            relativeHistory=itemView.findViewById(R.id.relative_history);
            btnPopUp=itemView.findViewById(R.id.btn_popup);
        }
    }
}
