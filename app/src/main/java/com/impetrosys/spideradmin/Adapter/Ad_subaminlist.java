package com.impetrosys.spideradmin.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.impetrosys.spideradmin.Modelclass.Subadminlist;
import com.impetrosys.spideradmin.Modelclass.Userlist;
import com.impetrosys.spideradmin.Modelclass.Websitelist;
import com.impetrosys.spideradmin.R;
import com.impetrosys.spideradmin.UtilClasses.SessionParam;
import com.impetrosys.spideradmin.retrofit.BaseRequest;

import java.util.ArrayList;
import java.util.List;


public class Ad_subaminlist extends RecyclerView.Adapter<Ad_subaminlist.MyViewHolder> {
    ArrayList<Subadminlist> list;
    ArrayList<Subadminlist>Alllist;
    Context context;
    Activity activity;
    SessionParam sessionParam;
    private BaseRequest baseRequest;
    Ad_subaminlist.delete delete;


    public Ad_subaminlist(Context context) {
        this.context = context;
    }

    public Ad_subaminlist(ArrayList<Subadminlist> list, Context context, SessionParam sessionParam, Activity activity, Ad_subaminlist.delete delete) {
        this.list = list;
        this.context = context;
        this.activity = activity;
        this.sessionParam =sessionParam;
        this.Alllist=new ArrayList<>(list);
        this.delete=delete;

    }

    public Ad_subaminlist(Context context, int count) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_subadmin_list, parent, false);
        return new MyViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {



        holder.name.setText(list.get(position).getName());
        holder.mobile.setText("+91 "+list.get(position).getContact());
        holder.role.setText(list.get(position).getRole());
        holder.subdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete.getid(list.get(position).getId());
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete.edit(list.get(position));

            }
        });

    }

    @Override
    public int getItemCount() {

        return list.size();
        //return 10;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name,mobile,role;
        ImageView subdelete,edit;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.subadminname);
            mobile=itemView.findViewById(R.id.sub_mobile);
            role=itemView.findViewById(R.id.sub_role);
            subdelete=itemView.findViewById(R.id.sub_delete);
            edit=itemView.findViewById(R.id.sub_edit);


        }
    }

    public Filter getFilter() {
        return filter;
    }
    Filter filter=new Filter() {
        //run on background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<Subadminlist> filterList = new ArrayList<>();
            if (charSequence.toString() == null) {
                filterList.addAll(list);
            } else {
                String serachStr = charSequence.toString().toUpperCase();
                for (Subadminlist servicesS : Alllist) {
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
            list.addAll((List<Subadminlist>)results.values);
            notifyDataSetChanged();

        }
    };
    public interface delete{
        public void getid(
                String id
        );
        public  void edit(Subadminlist edit);
    }
}
