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

import com.encoderbytes.qrcodeScannerAndGenerator.DBManager2;
import com.encoderbytes.qrcodeScannerAndGenerator.MyResult;
import com.encoderbytes.qrcodeScannerAndGenerator.R;
import com.encoderbytes.qrcodeScannerAndGenerator.models.History_model;

import java.util.ArrayList;

public class MyCodes_adapter extends RecyclerView.Adapter<MyCodes_adapter.MyCodesHolder> {

    Context context;
    ArrayList<History_model>listCodes;

    public MyCodes_adapter(Context context, ArrayList<History_model> listCodes) {
        this.context = context;
        this.listCodes = listCodes;
    }

    @NonNull
    @Override
    public MyCodesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.items_history,null);

        return new MyCodesHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final MyCodesHolder holder, final int position) {
        History_model model=listCodes.get(position);

        final String titles=model.getTitle();
        final  int myId=model.getId();

        holder.txtTitle.setText(titles);


        final String type=model.getType();
        holder.txtType.setText(type);

        final String desc=model.getDesc();

        if (type.equals("url")){
            holder.imgType.setImageResource(R.drawable.ic_url);
        }else if(type.equals("message")) {
            holder.imgType.setImageResource(R.drawable.ic_message);
        }else if (type.equals("email")){
            holder.imgType.setImageResource(R.drawable.ic_email);
        }else if (type.equals("text")){
            holder.imgType.setImageResource(R.drawable.ic_text);
        }

        holder.relativeHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 Intent intent=new Intent(context, MyResult.class);
                 if (type.equals("text")){
                     intent.putExtra("result",titles);
                 }else if (type.equals("url")){
                     intent.putExtra("result",titles);
                 }else if (type.equals("message")){
                     String txtResult="Phone :"+titles+ "\nMessage : "+desc;
                     intent.putExtra("result",txtResult);

                 }else if (type.equals("email")){
                     String txtResult2="Email :"+titles+ "\nbody : "+desc;
                     intent.putExtra("result",txtResult2);

                 }
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

                                DBManager2 dbManager=new DBManager2(context);
                                dbManager.open();
                                dbManager.delete(myId);
                                listCodes.remove(position);
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
        return listCodes.size();
    }

    public class MyCodesHolder extends RecyclerView.ViewHolder {

        TextView txtTitle,txtType;
        ImageView imgType;
        RelativeLayout relativeHistory;
        ImageButton btnPopUp;

        public MyCodesHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle=itemView.findViewById(R.id.item_title);
            txtType=itemView.findViewById(R.id.item_txtType);
            imgType=itemView.findViewById(R.id.item_imgType);
            relativeHistory=itemView.findViewById(R.id.relative_history);
            btnPopUp=itemView.findViewById(R.id.btn_popup);
        }
    }
}
