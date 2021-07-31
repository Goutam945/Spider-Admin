package com.impetrosys.spideradmin.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.impetrosys.spideradmin.Modelclass.AccountDetails;
import com.impetrosys.spideradmin.Modelclass.Acountdeatil1;
import com.impetrosys.spideradmin.Modelclass.Financialdetails;
import com.impetrosys.spideradmin.Modelclass.Withdrawalsrequest;
import com.impetrosys.spideradmin.R;
import com.impetrosys.spideradmin.UtilClasses.SessionParam;
import com.impetrosys.spideradmin.retrofit.BaseRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class Ad_financialdetail extends RecyclerView.Adapter<Ad_financialdetail.MyViewHolder> {
    ArrayList<Financialdetails> list;
    ArrayList<Financialdetails>Alllist;
    Context context;
    Activity activity;
    SessionParam sessionParam;
    private BaseRequest baseRequest;
    Ad_financialdetail.add add;

   Ad_Detailsaccount ad_detailsaccount;
    ArrayList<AccountDetails> accountdetails = new ArrayList<>();


    public Ad_financialdetail(Context context) {
        this.context = context;
    }

    public Ad_financialdetail(ArrayList<Financialdetails> list, ArrayList<AccountDetails> accountdetails,Context context, SessionParam sessionParam, Activity activity, Ad_financialdetail.add add) {
        this.list = list;
        this.context = context;
        this.activity = activity;
        this.sessionParam =sessionParam;
        this.Alllist=new ArrayList<>(list);
        this.add=add;
        this.accountdetails=accountdetails;


    }

    public Ad_financialdetail(Context context, int count) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_financialdetail, parent, false);
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newp, parent, false);
        return new MyViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    if(accountdetails!=null&&accountdetails.size()>0){
     if(list.get(position).getId().equals("1")){
    if (accountdetails.get(0).getAccountdetail().getPaytmUPI()!=null && !accountdetails.get(0).getAccountdetail().getPaytmUPI().isEmpty()){
        holder.add.setVisibility(View.GONE);
        holder.detail.setVisibility(View.VISIBLE);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        ad_detailsaccount = new Ad_Detailsaccount(accountdetails.get(0).getAccountdetail().getPaytmUPI(), context, new Ad_Detailsaccount.editdetails() {
            @Override
            public void edit(JSONObject jsonObject) {
                try {
                    Toast.makeText(context, jsonObject.getString("Upiname"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                add.edit(jsonObject);

            }

            @Override
            public void delete(String id) {
                add.delete(id);

            }
        });
        holder.recyclerView.setAdapter(ad_detailsaccount);
    }

}
        if(list.get(position).getId().equals("2")){
            if (accountdetails.get(0).getAccountdetail().getGooglePay()!=null && !accountdetails.get(0).getAccountdetail().getGooglePay().isEmpty()){
                holder.add.setVisibility(View.GONE);
                holder.detail.setVisibility(View.VISIBLE);
                holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
                ad_detailsaccount = new Ad_Detailsaccount(accountdetails.get(0).getAccountdetail().getGooglePay(), context, new Ad_Detailsaccount.editdetails() {
                    @Override
                    public void edit(JSONObject jsonObject) {
//                        Toast.makeText(context, edit.getPaymentmethodid(), Toast.LENGTH_SHORT).show();
                        add.edit(jsonObject);
                    }

                    @Override
                    public void delete(String id) {
                        add.delete(id);
                    }
                });
                holder.recyclerView.setAdapter(ad_detailsaccount);
            }

        }
        if(list.get(position).getId().equals("3")){
            if (accountdetails.get(0).getAccountdetail().getPhonePay()!=null && !accountdetails.get(0).getAccountdetail().getPhonePay().isEmpty()){
                holder.add.setVisibility(View.GONE);
                holder.detail.setVisibility(View.VISIBLE);
                holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
                ad_detailsaccount = new Ad_Detailsaccount(accountdetails.get(0).getAccountdetail().getPhonePay(), context, new Ad_Detailsaccount.editdetails() {
                    @Override
                    public void edit(JSONObject jsonObject) {
//                        Toast.makeText(context, edit.getPaymentmethodid(), Toast.LENGTH_SHORT).show();
//                        add.edit(edit);
                    }

                    @Override
                    public void delete(String id) {
                        add.delete(id);

                    }
                });
                holder.recyclerView.setAdapter(ad_detailsaccount);
            }

        }

        if(list.get(position).getId().equals("4")){
            if (accountdetails.get(0).getAccountdetail().getBankTransfer()!=null && !accountdetails.get(0).getAccountdetail().getBankTransfer().isEmpty()){
                holder.add.setVisibility(View.GONE);
                holder.detail.setVisibility(View.VISIBLE);
                holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
                ad_detailsaccount = new Ad_Detailsaccount(accountdetails.get(0).getAccountdetail().getBankTransfer(), context, new Ad_Detailsaccount.editdetails() {
                    @Override
                    public void edit(JSONObject jsonObject) {
//                        Toast.makeText(context, edit.getPaymentmethodid(), Toast.LENGTH_SHORT).show();
                      add.edit(jsonObject);
                    }

                    @Override
                    public void delete(String id) {
                        add.delete(id);

                    }
                });
                holder.recyclerView.setAdapter(ad_detailsaccount);
            }

        }
        if(list.get(position).getId().equals("5")){
            if (accountdetails.get(0).getAccountdetail().getPaytmWallet()!=null && !accountdetails.get(0).getAccountdetail().getPaytmWallet().isEmpty()){
                holder.add.setVisibility(View.GONE);
                holder.detail.setVisibility(View.VISIBLE);
                holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
                ad_detailsaccount = new Ad_Detailsaccount(accountdetails.get(0).getAccountdetail().getPaytmWallet(), context, new Ad_Detailsaccount.editdetails() {
                    @Override
                    public void edit(JSONObject jsonObject) {
//                        Toast.makeText(context, edit.getPaymentmethodid(), Toast.LENGTH_SHORT).show();
                       add.edit(jsonObject);
                    }

                    @Override
                    public void delete(String id) {
                        add.delete(id);

                    }
                });
                holder.recyclerView.setAdapter(ad_detailsaccount);
            }

        }}



        holder.name.setText(list.get(position).getName());
        if(list.get(position).getPhoto() != null && !list.get(position).getPhoto().isEmpty() ) {
            Picasso.get()
                    .load(list.get(position).getPhoto())
                    .placeholder(context.getResources().getDrawable(R.drawable.spiderlogo5))
                    .into(holder.image);
        }

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add.getid(list.get(position).getId());
            }
        });
        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.layout.getVisibility() == View.VISIBLE){
                    holder.layout.setVisibility(View.GONE);
                } else {
                    holder.layout.setVisibility(View.VISIBLE);
                }
                holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
//                ad_detailsaccount = new Ad_Detailsaccount(accountdetails.get(0).getAccountdetail().getPaytmUPI(),context);
//                holder.recyclerView.setAdapter(ad_detailsaccount);
                holder.recyclerView.setHasFixedSize(true);

            }
        });





    }

    


    @Override
    public int getItemCount() {

        return list.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name,name1,email;
        CircleImageView image;
        Button add;
        ImageView detail;
        ConstraintLayout layout;
        RecyclerView recyclerView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.upiname);
            image = itemView.findViewById(R.id.icon);
            add=itemView.findViewById(R.id.add);
            detail=itemView.findViewById(R.id.drop);
            layout=itemView.findViewById(R.id.contlay);
            recyclerView=itemView.findViewById(R.id.recy_detail);




        }
    }

public interface add{
    public void getid(String id);
    //public  void edit(Acountdeatil1 edit);
    public  void edit(JSONObject edit);
    public  void delete(String id);
}
}
