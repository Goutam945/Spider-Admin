package com.impetrosys.spideradmin.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.impetrosys.spideradmin.Act_Deposit_Id_details;
import com.impetrosys.spideradmin.Modelclass.PaymentDepositid;
import com.impetrosys.spideradmin.R;
import com.impetrosys.spideradmin.UtilClasses.SessionParam;
import com.impetrosys.spideradmin.retrofit.BaseRequest;

import java.util.ArrayList;


public class Ad_PaymentdepositID extends RecyclerView.Adapter<Ad_PaymentdepositID.MyViewHolder> {
    ArrayList<PaymentDepositid> list;
    ArrayList<PaymentDepositid>Alllist;
    Context context;
    Activity activity;
    SessionParam sessionParam;
    private BaseRequest baseRequest;


    public Ad_PaymentdepositID(Context context) {
        this.context = context;
    }

    public Ad_PaymentdepositID(ArrayList<PaymentDepositid> list, Context context, SessionParam sessionParam, Activity activity) {
        this.list = list;
        this.context = context;
        this.activity = activity;
        this.sessionParam =sessionParam;
        this.Alllist=new ArrayList<>(list);

    }

    public Ad_PaymentdepositID(Context context, int count) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_depositlist_id, parent, false);
        return new MyViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String status=list.get(position).getStatus();
        if (status.equalsIgnoreCase("0")) {
            holder.username.setText(list.get(position).getUsername());
            holder.coin.setText("Coins: " + list.get(position).getCoins());
        }
        holder.lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Act_Deposit_Id_details.class);
                intent.putExtra("username",list.get(position).getUsername());
                intent.putExtra("userid",list.get(position).getUserid());
                intent.putExtra("id",list.get(position).getId());
                intent.putExtra("coins",list.get(position).getCoins());
                intent.putExtra("pay_wallet",list.get(position).getPayWallet());
                intent.putExtra("websitename",list.get(position).getWebsitename());
                intent.putExtra("websiteurl",list.get(position).getWebsiteurl());
                intent.putExtra("requestusername",list.get(position).getRequestusername());
                intent.putExtra("paymentscreenshot",list.get(position).getPaymentscreenshot());
                intent.putExtra("paymentmethod",list.get(position).getPaymentmethod());
                intent.putExtra("created_date",list.get(position).getCreatedDate());
                intent.putExtra("depositstatus",list.get(position).getDepositstatus());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {

        return list.size();
        //return 10;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView username,coin;
        ConstraintLayout lay;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.tv_username);
            lay = itemView.findViewById(R.id.c1);
            coin = itemView.findViewById(R.id.tv_coin);


        }
    }


}
