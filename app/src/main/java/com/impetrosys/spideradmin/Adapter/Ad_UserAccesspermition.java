package com.impetrosys.spideradmin.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.impetrosys.spideradmin.Modelclass.UserAccesspemission;
import com.impetrosys.spideradmin.R;
import com.impetrosys.spideradmin.UtilClasses.SessionParam;

import java.util.ArrayList;

public class Ad_UserAccesspermition extends RecyclerView.Adapter<Ad_UserAccesspermition.MyViewHolder> {
    ArrayList<UserAccesspemission> list;
    Context context;
    Activity activity;
    SessionParam sessionParam;
    Ad_UserAccesspermition.menuid menuid;


    public Ad_UserAccesspermition(ArrayList<UserAccesspemission> list, Context context, SessionParam sessionParam, Activity activity, Ad_UserAccesspermition.menuid menuid) {
        this.list = list;
        this.context = context;
        this.activity = activity;
        this.sessionParam =sessionParam;
        this.menuid=menuid;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_sp,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {
        holder.checkBox1.setText(list.get(i).getName());
        holder.checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (holder.checkBox1.isChecked()) {
//                    menuid.getid(list.get(i).getId());
//                }
                menuid.getid(list.get(i).getId());
            }
        });



    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{


        TextView tv_station;
        CheckBox checkBox1;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

           // tv_station =  itemView.findViewById(R.id.tv_station);
            checkBox1 =  itemView.findViewById(R.id.checkbox);



        }
    }
    public void filterList(ArrayList<UserAccesspemission> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public interface menuid{
        public void getid(String id);
    }
}