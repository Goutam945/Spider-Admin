package com.impetrosys.spideradmin.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.impetrosys.spideradmin.Act_CloseId_list;
import com.impetrosys.spideradmin.Act_Raiseconcern;
import com.impetrosys.spideradmin.Act_User_requestlist;
import com.impetrosys.spideradmin.Act_Withdrawals_request;
import com.impetrosys.spideradmin.Act_paymentdeposit;
import com.impetrosys.spideradmin.Modelclass.Notice;
import com.impetrosys.spideradmin.Modelclass.Notification;
import com.impetrosys.spideradmin.Modelclass.Subadminlist;
import com.impetrosys.spideradmin.R;
import com.impetrosys.spideradmin.UtilClasses.SessionParam;
import com.impetrosys.spideradmin.retrofit.BaseRequest;

import java.util.ArrayList;


public class Ad_Noticelist extends RecyclerView.Adapter<Ad_Noticelist.MyViewHolder> {
    ArrayList<Notice> list;
    ArrayList<Notice>Alllist;
    Context context;
    Activity activity;
    SessionParam sessionParam;
    private BaseRequest baseRequest;
    Ad_Noticelist.edit edit;


    public Ad_Noticelist(Context context) {
        this.context = context;
    }

    public Ad_Noticelist(ArrayList<Notice> list, Context context, SessionParam sessionParam, Activity activity,Ad_Noticelist.edit edit) {
        this.list = list;
        this.context = context;
        this.activity = activity;
        this.sessionParam =sessionParam;
        this.Alllist=new ArrayList<>(list);
        this.edit=edit;

    }

    public Ad_Noticelist(Context context, int count) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_act_notice, parent, false);
        return new MyViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {



        holder.notice.setText("Notice:  "+list.get(position).getNotice());
        holder.mobile.setText("Supportno. :  "+list.get(position).getSupportno());

        holder.noticeedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.edit(list.get(position));

            }
        });
        holder.noticedelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.delete(list.get(position).getId());

            }
        });




    }

    @Override
    public int getItemCount() {

        return list.size();
        //return 10;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView notice,mobile;
        ImageView noticeedit,noticedelete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            notice = itemView.findViewById(R.id.notice);
            mobile = itemView.findViewById(R.id.mobile);
            noticeedit = itemView.findViewById(R.id.notice_edit);
            noticedelete = itemView.findViewById(R.id.notice_delete);


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
public interface edit{
    public  void edit(Notice edit);
    public  void delete(String id);
}
}
