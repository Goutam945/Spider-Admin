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
import com.impetrosys.spideradmin.Modelclass.Referalcode;
import com.impetrosys.spideradmin.Modelclass.Subadminlist;
import com.impetrosys.spideradmin.Modelclass.UserRequestlist;
import com.impetrosys.spideradmin.R;
import com.impetrosys.spideradmin.UtilClasses.SessionParam;
import com.impetrosys.spideradmin.retrofit.BaseRequest;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Ad_Referalcodelist extends RecyclerView.Adapter<Ad_Referalcodelist.MyViewHolder> {
    ArrayList<Referalcode> list;
    ArrayList<Referalcode>Alllist;
    Context context;
    Activity activity;
    SessionParam sessionParam;
    Ad_Referalcodelist.update update;

    public Ad_Referalcodelist(Context context) {
        this.context = context;
    }

    public Ad_Referalcodelist(ArrayList<Referalcode> list, Context context, SessionParam sessionParam, Activity activity,Ad_Referalcodelist.update update) {
        this.list = list;
        this.context = context;
        this.activity = activity;
        this.sessionParam =sessionParam;
        this.Alllist=new ArrayList<>(list);
        this.update=update;

    }

    public Ad_Referalcodelist(Context context, int count) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.allreferalcode_list, parent, false);
        return new MyViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(list.get(position).getUsername());
        holder.code.setText("Code  "+list.get(position).getCode());
        holder.reward.setText("Reward  "+list.get(position).getReward());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update.edit(list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {

        return list.size();
        //return 10;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name,code,reward;
        ImageView edit;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.username);
            code = itemView.findViewById(R.id.code);
            reward = itemView.findViewById(R.id.reward);
            edit=itemView.findViewById(R.id.r_edit);


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

    public interface update{
        public  void edit(Referalcode edit);
    }

}
