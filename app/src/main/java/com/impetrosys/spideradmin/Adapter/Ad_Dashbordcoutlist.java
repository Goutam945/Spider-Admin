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

import com.impetrosys.spideradmin.Modelclass.Dashbordcountlist;
import com.impetrosys.spideradmin.R;
import com.impetrosys.spideradmin.Act_User_requestlist;
import com.impetrosys.spideradmin.UtilClasses.SessionParam;
import com.impetrosys.spideradmin.retrofit.BaseRequest;

import java.util.ArrayList;


public class Ad_Dashbordcoutlist extends RecyclerView.Adapter<Ad_Dashbordcoutlist.MyViewHolder> {
    ArrayList<Dashbordcountlist> list;
    ArrayList<Dashbordcountlist>Alllist;
    Context context;
    Activity activity;
    SessionParam sessionParam;
    private BaseRequest baseRequest;


    public Ad_Dashbordcoutlist(Context context) {
        this.context = context;
    }

    public Ad_Dashbordcoutlist(ArrayList<Dashbordcountlist> list, Context context, SessionParam sessionParam, Activity activity) {
        this.list = list;
        this.context = context;
        this.activity = activity;
        this.sessionParam =sessionParam;
        this.Alllist=new ArrayList<>(list);

    }

    public Ad_Dashbordcoutlist(Context context, int count) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashbordcountlist, parent, false);
        return new MyViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {



        holder.newuser.setText(list.get(position).getNewuser());
       holder.withdrawl.setText(list.get(position).getApprovewithdraw()+" / "+list.get(position).getNewwithdrawal());
       holder.deposits.setText(list.get(position).getApprovedeposit()+" / "+list.get(position).getNewdeposit());
       holder.clientid.setText(list.get(position).getUserrequest().toString());


       holder.idclik.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i=new Intent(context, Act_User_requestlist.class);
               i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               context.startActivity(i);
           }
       });
    }

    @Override
    public int getItemCount() {

        return list.size();
        //return 10;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView newuser,withdrawl,deposits,clientid;
        LinearLayout idclik;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            newuser = itemView.findViewById(R.id.usercount);
            withdrawl = itemView.findViewById(R.id.withdralcount);
            deposits = itemView.findViewById(R.id.countdeposit);
            clientid = itemView.findViewById(R.id.userrequest);
            idclik=itemView.findViewById(R.id.layclick);


        }
    }


}
