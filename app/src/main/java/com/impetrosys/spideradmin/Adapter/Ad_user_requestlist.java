package com.impetrosys.spideradmin.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.impetrosys.spideradmin.Act_Full_image;
import com.impetrosys.spideradmin.Modelclass.UserRequestlist;
import com.impetrosys.spideradmin.R;
import com.impetrosys.spideradmin.UtilClasses.SessionParam;
import com.impetrosys.spideradmin.retrofit.BaseRequest;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Ad_user_requestlist extends RecyclerView.Adapter<Ad_user_requestlist.MyViewHolder> {
    ArrayList<UserRequestlist> list;
    ArrayList<UserRequestlist>Alllist;
    Context context;
    Activity activity;
    SessionParam sessionParam;
    private BaseRequest baseRequest;
    boolean isImageFitToScreen;
    Ad_user_requestlist.aprove aprove;

    public Ad_user_requestlist(Context context) {
        this.context = context;
    }

    public Ad_user_requestlist(ArrayList<UserRequestlist> list, Context context, SessionParam sessionParam, Activity activity,Ad_user_requestlist.aprove aprove) {
        this.list = list;
        this.context = context;
        this.activity = activity;
        this.sessionParam =sessionParam;
        this.Alllist=new ArrayList<>(list);
        this.aprove=aprove;

    }

    public Ad_user_requestlist(Context context, int count) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_user_requestlist, parent, false);
        return new MyViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        String status=list.get(position).getStatus();

        if (status.equalsIgnoreCase("0")){
            holder.constraintLayout.setVisibility(View.VISIBLE);
            holder.websitename.setText((CharSequence) list.get(position).getWebsitename());
            holder.paymentmethod.setText(list.get(position).getPaymentmethod());
            holder.loginusername.setText("Login: "+list.get(position).getLoginusername());
            holder.username.setText("User: "+list.get(position).getUsername());
            holder.coins.setText("Coins: "+list.get(position).getCoins());
            Picasso.get()
                    .load(list.get(position).getPaymentscreenshot())
                    .into(holder.paymentscreenshot, new Callback() {
                        @Override
                        public void onSuccess() {
                            holder.progress.setVisibility(View.GONE);
                        }
                        @Override
                        public void onError(Exception e) {

                        }
                    });
        }else {
            holder.constraintLayout.setVisibility(View.GONE);
        }






        holder.paymentscreenshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Act_Full_image.class);
                intent.putExtra("paymentscreenshot",list.get(position).getPaymentscreenshot());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
        holder.acceptrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aprove.getid(list.get(position).getId());
            }
        });
        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aprove.rejetid(list.get(position).getId());
            }
        });




    }

    @Override
    public int getItemCount() {

        return list.size();
        //return 10;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView websitename,paymentmethod,loginusername,username,coins;
        ImageView paymentscreenshot;
        Button acceptrequest,reject;
        ProgressBar progress;
        ConstraintLayout constraintLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            websitename = itemView.findViewById(R.id.websitename);
            paymentmethod = itemView.findViewById(R.id.paymentmethod);
            loginusername = itemView.findViewById(R.id.loginusername);
            username = itemView.findViewById(R.id.username);
            coins = itemView.findViewById(R.id.coins);
            paymentscreenshot = itemView.findViewById(R.id.paymentscreenshot);
            acceptrequest = itemView.findViewById(R.id.btn_accept);
            progress = itemView.findViewById(R.id.progressbar);
            reject = itemView.findViewById(R.id.btn_reject);
            constraintLayout = itemView.findViewById(R.id.cont_lay);

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

    public interface aprove{
        public void getid(String id);
        public void rejetid(String id);

    }


}
