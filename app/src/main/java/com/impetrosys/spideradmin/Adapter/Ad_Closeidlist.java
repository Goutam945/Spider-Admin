package com.impetrosys.spideradmin.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.impetrosys.spideradmin.Modelclass.CloseIdlist;
import com.impetrosys.spideradmin.Modelclass.Userlist;
import com.impetrosys.spideradmin.R;
import com.impetrosys.spideradmin.UtilClasses.SessionParam;
import com.impetrosys.spideradmin.retrofit.BaseRequest;

import java.util.ArrayList;
import java.util.List;


public class Ad_Closeidlist extends RecyclerView.Adapter<Ad_Closeidlist.MyViewHolder> {
    ArrayList<CloseIdlist> list;
    ArrayList<CloseIdlist>Alllist;
    Context context;
    Activity activity;
    SessionParam sessionParam;
    private BaseRequest baseRequest;


    public Ad_Closeidlist(Context context) {
        this.context = context;
    }

    public Ad_Closeidlist(ArrayList<CloseIdlist> list, Context context, SessionParam sessionParam, Activity activity) {
        this.list = list;
        this.context = context;
        this.activity = activity;
        this.sessionParam =sessionParam;
        this.Alllist=new ArrayList<>(list);

    }

    public Ad_Closeidlist(Context context, int count) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_close_id_list, parent, false);
        return new MyViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {



        holder.username.setText(list.get(position).getUsername());
        holder.website.setText(list.get(position).getWebsite());
        holder.date.setText(list.get(position).getCloseiddate());

        holder.reid.setText(list.get(position).getRid());
        holder.loginuser.setText(list.get(position).getLoginusername());
        holder.totalbalanceless.setText(list.get(position).getTotalbalanceless());
        holder.noactivebets.setText(list.get(position).getNoactivebets());
        holder.withdraw.setText(list.get(position).getWithdraw());
        holder.reason.setText(list.get(position).getReason());
        holder.otherissue.setText(list.get(position).getOtherissue());


        holder.dropdwn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.layout.getVisibility() == View.VISIBLE){
                    holder.layout.setVisibility(View.GONE);
                } else {
                    holder.layout.setVisibility(View.VISIBLE);
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
        LinearLayout layout;
        ImageView dropdwn;
        TextView website,username,reid,date,totalbalanceless,noactivebets,
                withdraw,reason,otherissue,loginuser;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            website = itemView.findViewById(R.id.website);
            username = itemView.findViewById(R.id.username);
            reid = itemView.findViewById(R.id.rid);
            date = itemView.findViewById(R.id.date);
            totalbalanceless = itemView.findViewById(R.id.totalbalanceless);
            noactivebets = itemView.findViewById(R.id.noactivebets);
            withdraw = itemView.findViewById(R.id.withdraw);
            reason = itemView.findViewById(R.id.reason);
            otherissue = itemView.findViewById(R.id.otherissue);
            loginuser = itemView.findViewById(R.id.loginuser);

            dropdwn = itemView.findViewById(R.id.drodow);
            layout=itemView.findViewById(R.id.lay);



        }
    }

   /* public Filter getFilter() {
        return filter;
    }
    Filter filter=new Filter() {
        //run on background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<Userlist> filterList = new ArrayList<>();
            if (charSequence.toString() == null) {
                filterList.addAll(list);
            } else {
                String serachStr = charSequence.toString().toUpperCase();
                for (Userlist servicesS : Alllist) {
                    if (servicesS.getName().toUpperCase().contains(serachStr)) {
                        filterList.add(servicesS);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            list.clear();
            list.addAll((List<Userlist>)results.values);

            notifyDataSetChanged();

        }
    };*/
}
