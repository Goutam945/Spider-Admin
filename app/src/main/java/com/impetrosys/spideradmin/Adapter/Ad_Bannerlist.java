package com.impetrosys.spideradmin.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.impetrosys.spideradmin.Act_CloseId_list;
import com.impetrosys.spideradmin.Act_Raiseconcern;
import com.impetrosys.spideradmin.Act_User_requestlist;
import com.impetrosys.spideradmin.Act_Withdrawals_request;
import com.impetrosys.spideradmin.Act_paymentdeposit;
import com.impetrosys.spideradmin.Modelclass.Bannerlist;
import com.impetrosys.spideradmin.Modelclass.Notification;
import com.impetrosys.spideradmin.Modelclass.Subadminlist;
import com.impetrosys.spideradmin.R;
import com.impetrosys.spideradmin.UtilClasses.SessionParam;
import com.impetrosys.spideradmin.retrofit.BaseRequest;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Ad_Bannerlist extends RecyclerView.Adapter<Ad_Bannerlist.MyViewHolder> {
    ArrayList<Bannerlist> list;
    ArrayList<Bannerlist>Alllist;
    Context context;
    Activity activity;
    SessionParam sessionParam;
    private BaseRequest baseRequest;
    Ad_Bannerlist.delete delete;



    public Ad_Bannerlist(Context context) {
        this.context = context;
    }

    public Ad_Bannerlist(ArrayList<Bannerlist> list, Context context, SessionParam sessionParam, Activity activity,Ad_Bannerlist.delete delete) {
        this.list = list;
        this.context = context;
        this.activity = activity;
        this.sessionParam =sessionParam;
        this.Alllist=new ArrayList<>(list);
        this.delete=delete;

    }

    public Ad_Bannerlist(Context context, int count) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_act_bannerlist, parent, false);
        return new MyViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {



        holder.name.setText(list.get(position).getName());
        holder.url.setText(list.get(position).getUrl());

        Picasso.get()
                .load(list.get(position).getPhoto())
                .into(holder.imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progress.setVisibility(View.GONE);
                    }
                    @Override
                    public void onError(Exception e) {

                    }
                });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete.getid(list.get(position).getId());
            }
        });

    }

    @Override
    public int getItemCount() {

        return list.size();
        //return 10;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView,delete;
        ProgressBar progress;
        TextView name,url;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img_web);
            progress = itemView.findViewById(R.id.progressbar);
            name = itemView.findViewById(R.id.name);
            url = itemView.findViewById(R.id.url);
            delete = itemView.findViewById(R.id.delete);


        }
    }
    public interface delete{
        public void getid(
                String id
        );
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
