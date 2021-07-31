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
import androidx.recyclerview.widget.RecyclerView;

import com.impetrosys.spideradmin.Deposits_details;
import com.impetrosys.spideradmin.Full_image;
import com.impetrosys.spideradmin.Modelclass.Paymentdepositslist;
import com.impetrosys.spideradmin.Modelclass.Websitelist;
import com.impetrosys.spideradmin.R;
import com.impetrosys.spideradmin.UtilClasses.SessionParam;
import com.impetrosys.spideradmin.retrofit.BaseRequest;

import java.util.ArrayList;


public class Ad_Paymentdepositslist extends RecyclerView.Adapter<Ad_Paymentdepositslist.MyViewHolder> {
    ArrayList<Paymentdepositslist> list;
    ArrayList<Paymentdepositslist>Alllist;
    Context context;
    Activity activity;
    SessionParam sessionParam;
    private BaseRequest baseRequest;


    public Ad_Paymentdepositslist(Context context) {
        this.context = context;
    }

    public Ad_Paymentdepositslist(ArrayList<Paymentdepositslist> list, Context context, SessionParam sessionParam, Activity activity) {
        this.list = list;
        this.context = context;
        this.activity = activity;
        this.sessionParam =sessionParam;
        this.Alllist=new ArrayList<>(list);

    }

    public Ad_Paymentdepositslist(Context context, int count) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_paymentdeposits_list, parent, false);
        return new MyViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String status=list.get(position).getStatus();
        if (status.equalsIgnoreCase("0")) {
            holder.paymentname.setText(list.get(position).getUsername());
            holder.coin.setText("Coins: " + list.get(position).getCoins());
        }
        holder.lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Deposits_details.class);
                intent.putExtra("username",list.get(position).getUsername());
                intent.putExtra("id",list.get(position).getId());
                intent.putExtra("userid",list.get(position).getUserid());
                intent.putExtra("paymentmethod",list.get(position).getPaymentmethod());
                intent.putExtra("coins",list.get(position).getCoins());
                intent.putExtra("paymentpic",list.get(position).getPaymentscreenshot());
                intent.putExtra("created_date",list.get(position).getCreatedDate());
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

        TextView paymentname,coin;
        LinearLayout lay;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            paymentname = itemView.findViewById(R.id.payment);
            lay = itemView.findViewById(R.id.btn_layout);
            coin = itemView.findViewById(R.id.paymetcoin);


        }
    }

//    public Filter getFilter() {
//        return filter;
//    }
//    Filter filter=new Filter() {
//        //run on background thread
//        @Override
//        protected FilterResults performFiltering(CharSequence charSequence) {
//
//            List<Helpline> filterList = new ArrayList<>();
//            if (charSequence.toString() == null) {
//                filterList.addAll(list);
//            } else {
//                String serachStr = charSequence.toString().toUpperCase();
//                for (Helpline servicesS : Alllist) {
//                    if (servicesS.getHelplineName().toUpperCase().contains(serachStr)) {
//                        filterList.add(servicesS);
//                    }
//                }
//            }
//            FilterResults filterResults = new FilterResults();
//            filterResults.values = filterList;
//            return filterResults;
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//
//            list.clear();
//            list.addAll((List<Helpline>)results.values);
//
//            notifyDataSetChanged();
//
//        }
//    };
}
