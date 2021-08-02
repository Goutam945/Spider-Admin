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

import com.impetrosys.spideradmin.Act_paymentdeposit;
import com.impetrosys.spideradmin.Modelclass.Notification;
import com.impetrosys.spideradmin.R;
import com.impetrosys.spideradmin.Act_User_requestlist;
import com.impetrosys.spideradmin.UtilClasses.SessionParam;
import com.impetrosys.spideradmin.Act_Withdrawals_request;
import com.impetrosys.spideradmin.retrofit.BaseRequest;

import java.util.ArrayList;


public class Ad_pushNotification extends RecyclerView.Adapter<Ad_pushNotification.MyViewHolder> {
    ArrayList<Notification> list;
    ArrayList<Notification>Alllist;
    Context context;
    Activity activity;
    SessionParam sessionParam;
    private BaseRequest baseRequest;


    public Ad_pushNotification(Context context) {
        this.context = context;
    }

    public Ad_pushNotification(ArrayList<Notification> list, Context context, SessionParam sessionParam, Activity activity) {
        this.list = list;
        this.context = context;
        this.activity = activity;
        this.sessionParam =sessionParam;
        this.Alllist=new ArrayList<>(list);

    }

    public Ad_pushNotification(Context context, int count) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_puch_notification, parent, false);
        return new MyViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {



        holder.titile.setText(list.get(position).getTitle());
        holder.message.setText(list.get(position).getMessage());
        holder.date.setText(list.get(position).getNotificationDate());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type=list.get(position).getType();
                if (type.equalsIgnoreCase("requestid")){
                    Intent intent = new Intent(context, Act_User_requestlist.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
                if (type.equalsIgnoreCase("withdraw")){
                    Intent intent = new Intent(context, Act_Withdrawals_request.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
                if (type.equalsIgnoreCase("deposit")){
                    Intent intent = new Intent(context, Act_paymentdeposit.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }

            }
        });

    }

    @Override
    public int getItemCount() {

        return list.size();
        //return 10;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titile,message,date;
        LinearLayout layout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            titile = itemView.findViewById(R.id.titile);
            message = itemView.findViewById(R.id.massage);
            date = itemView.findViewById(R.id.date);
            layout = itemView.findViewById(R.id.layclick);

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
//            List<Userlist> filterList = new ArrayList<>();
//            if (charSequence.toString() == null) {
//                filterList.addAll(list);
//            } else {
//                String serachStr = charSequence.toString().toUpperCase();
//                for (Userlist servicesS : Alllist) {
//                    if (servicesS.getName().toUpperCase().contains(serachStr)) {
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
//            list.addAll((List<Userlist>)results.values);
//
//            notifyDataSetChanged();
//
//        }
//    };
}
