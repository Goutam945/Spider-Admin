package com.impetrosys.spideradmin.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.impetrosys.spideradmin.Modelclass.AccountDetails;
import com.impetrosys.spideradmin.Modelclass.Acountdeatil1;
import com.impetrosys.spideradmin.Modelclass.Subadminlist;
import com.impetrosys.spideradmin.Modelclass.Userlist;
import com.impetrosys.spideradmin.R;
import com.impetrosys.spideradmin.UtilClasses.SessionParam;
import com.impetrosys.spideradmin.retrofit.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Ad_Detailsaccount extends RecyclerView.Adapter<Ad_Detailsaccount.MyViewHolder> {
    List<Acountdeatil1> list;
    ArrayList<Acountdeatil1>Alllist;
    Context context;
    Activity activity;
    SessionParam sessionParam;
    private BaseRequest baseRequest;
    Ad_Detailsaccount. editdetails editdetails;


    public Ad_Detailsaccount(Context context) {
        this.context = context;
    }

    public Ad_Detailsaccount(List<Acountdeatil1> list, Context context,Ad_Detailsaccount. editdetails editdetails) {
        this.list = list;
        this.context = context;
        this.editdetails=editdetails;
    }

    public Ad_Detailsaccount(ArrayList<Acountdeatil1> list,
                             Context context, SessionParam sessionParam, Activity activity) {
        this.list = list;
        this.context = context;
        this.activity = activity;
        this.sessionParam =sessionParam;
//        this.Alllist=new ArrayList<>(list);

    }

    /*public Ad_Detailsaccount(Context context, int count) {
        this.context = context;
    }*/

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.accountdetails, parent, false);
        return new MyViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String type=list.get(position).getPaymentmethodid();
        if (type.equalsIgnoreCase("4")){
            holder.Pbankname.setText(list.get(position).getBankname());
            holder.Paccount.setText(list.get(position).getAccountno());
            holder.Pifc.setText(list.get(position).getIfsc());
            holder.Pbranch.setText(list.get(position).getBranch());

            holder.Pbankname.setVisibility(View.VISIBLE);
            holder.Paccount.setVisibility(View.VISIBLE);
            holder.Pifc.setVisibility(View.VISIBLE);
            holder.Pbranch.setVisibility(View.VISIBLE);
            holder.t1.setVisibility(View.VISIBLE);
            holder.t2.setVisibility(View.VISIBLE);
            holder.t3.setVisibility(View.VISIBLE);
            holder.t4.setVisibility(View.VISIBLE);


            holder.Pmobile.setVisibility(View.GONE);
            holder.Pname.setVisibility(View.GONE);
            holder.t5.setVisibility(View.GONE);
            holder.t6.setVisibility(View.GONE);

        }else {
            holder.Pmobile.setText(list.get(position).getNumber());
            holder.Pname.setText(list.get(position).getDisplayname());
        }

        holder.imgedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                editdetails.edit(list.get(position));
                holder.Pmobile.setEnabled(true);
                holder.Pname.setEnabled(true);
                holder.Pbankname.setEnabled(true);
                holder.Paccount.setEnabled(true);
                holder.Pifc.setEnabled(true);
                holder.Pbranch.setEnabled(true);
                holder.save.setVisibility(View.VISIBLE);

            }
        });
        holder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("Upiname",holder.Pname.getText());
                    jsonObject.put("Upinumber",holder.Pmobile.getText());
                    jsonObject.put("id",list.get(position).getId());
                    jsonObject.put("Paymentid",list.get(position).getPaymentmethodid());

                    jsonObject.put("bankname",holder.Pbankname.getText());
                    jsonObject.put("bankaccount",holder.Paccount.getText());
                    jsonObject.put("bankifc",holder.Pifc.getText());
                    jsonObject.put("bankbranch",holder.Pbranch.getText());



                } catch (JSONException e) {
                    e.printStackTrace();
                }
                editdetails.edit(jsonObject);

            }
        });

        holder.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editdetails.delete(list.get(position).getId());

            }
        });

    }

    @Override
    public int getItemCount() {

        return list.size();
        //return 10;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        EditText Pname,Pmobile,Pbankname,Paccount,Pifc,Pbranch;
        ImageView imgedit,imgdelete;
        Button save;
        TextView t1,t2,t3,t4,t5,t6;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            Pmobile = itemView.findViewById(R.id.detailp);
            Pname = itemView.findViewById(R.id.dname);
            t5 = itemView.findViewById(R.id.t5);
            t6 = itemView.findViewById(R.id.t6);

           Pbankname = itemView.findViewById(R.id.dbankname);
           Paccount = itemView.findViewById(R.id.accountno);
            Pifc = itemView.findViewById(R.id.ifc);
            Pbranch = itemView.findViewById(R.id.branch);
            imgedit = itemView.findViewById(R.id.img_edit);
            imgdelete = itemView.findViewById(R.id.img_delete);
            t1 = itemView.findViewById(R.id.t1);
            t2 = itemView.findViewById(R.id.t2);
            t3 = itemView.findViewById(R.id.t3);
            t4 = itemView.findViewById(R.id.t4);

            save = itemView.findViewById(R.id.btn_send);


            Pmobile.setEnabled(false);
            Pname.setEnabled(false);
            Pbankname.setEnabled(false);
            Paccount.setEnabled(false);
            Pifc.setEnabled(false);
            Pbranch.setEnabled(false);


        }
    }
    public interface editdetails{
        public  void edit(JSONObject jsonObject);
        public  void delete(String id);



    }


}
