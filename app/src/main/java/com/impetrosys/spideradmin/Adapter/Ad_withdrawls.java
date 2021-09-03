package com.impetrosys.spideradmin.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.impetrosys.spideradmin.Modelclass.Websitelist;
import com.impetrosys.spideradmin.Modelclass.Withdrawalsrequest;
import com.impetrosys.spideradmin.R;
import com.impetrosys.spideradmin.UtilClasses.SessionParam;
import com.impetrosys.spideradmin.retrofit.BaseRequest;

import java.util.ArrayList;
import java.util.List;


public class Ad_withdrawls extends RecyclerView.Adapter<Ad_withdrawls.MyViewHolder> {
    ArrayList<Withdrawalsrequest> list;
    ArrayList<Withdrawalsrequest>Alllist;
    ArrayList<Withdrawalsrequest.Withdrawdetail> withdrawdetail;
    Context context;
    Activity activity;
    SessionParam sessionParam;
    private BaseRequest baseRequest;
    Ad_withdrawls.aprove aprove;


    public Ad_withdrawls(Context context) {
        this.context = context;
    }

    public Ad_withdrawls(ArrayList<Withdrawalsrequest> list, ArrayList<Withdrawalsrequest.Withdrawdetail> withdrawdetail,Ad_withdrawls.aprove aprove,Context context, SessionParam sessionParam, Activity activity) {
        this.list = list;
        this.withdrawdetail=withdrawdetail;
        this.context = context;
        this.activity = activity;
        this.sessionParam =sessionParam;
        this.Alllist=new ArrayList<>(list);
        this.aprove=aprove;

    }

    public Ad_withdrawls(Context context, int count) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_withdrawals_request, parent, false);
        return new MyViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        String status=list.get(position).getStatus();
        if(status.equalsIgnoreCase("0")) {
            holder.clientname.setText("User Name : " + list.get(position).getClientname());
            holder.websitename.setText("Website Name : " + list.get(position).getWebsitename());
            holder.username.setText("Client Name : " + list.get(position).getUsername());
            holder.userid.setText("UserID : " + list.get(position).getUserid());
            holder.amount.setText("Coins : " + list.get(position).getCoins());
            holder.withdrawstatus.setText("Withdraws : " + list.get(position).getWithdrawstatus());

            if(list.get(position).getPaymentmethod()!=null){
                holder.Pmethode.setText("Payment : " + list.get(position).getPaymentmethod());
            }else {
                holder.Pmethode.setVisibility(View.GONE);
            }

            for (int k = 0; k < list.get(position).getWithdrawdetail().size(); k++) {
                String name = list.get(position).getPaymentmethod();
                if(name!=null) {

                    if (name == null || name.equalsIgnoreCase("BANK TRANSFER")) {
                        holder.Bankname.setText("Bankname : " + list.get(position).getWithdrawdetail().get(k).getBankname());
                        holder.Branch.setText("Branch : " + list.get(position).getWithdrawdetail().get(k).getBranch());
                        holder.Ifc.setText("IFC code : " + list.get(position).getWithdrawdetail().get(k).getIfsc());
                        holder.Accountnumber.setText("Accountnumber : " + list.get(position).getWithdrawdetail().get(k).getAccountno());
                        holder.Bankname.setVisibility(View.VISIBLE);
                        holder.Branch.setVisibility(View.VISIBLE);
                        holder.Ifc.setVisibility(View.VISIBLE);
                        holder.Accountnumber.setVisibility(View.VISIBLE);
                    }
                    if (name == null || name.equalsIgnoreCase("PAYTM UPI")) {
                        holder.Upiname.setText("Name : " + list.get(position).getWithdrawdetail().get(k).getDisplayname());
                        holder.Upinumber.setText("UPI Number : " + list.get(position).getWithdrawdetail().get(k).getNumber());
                        holder.Upiname.setVisibility(View.VISIBLE);
                        holder.Upinumber.setVisibility(View.VISIBLE);

                    }
                    if (name == null || name.equalsIgnoreCase("GOOGLE PAY")) {
                        holder.Upiname.setText("Name : " + list.get(position).getWithdrawdetail().get(k).getDisplayname());
                        holder.Upinumber.setText("UPI Number : " + list.get(position).getWithdrawdetail().get(k).getNumber());
                        holder.Upiname.setVisibility(View.VISIBLE);
                        holder.Upinumber.setVisibility(View.VISIBLE);

                    }
                    if (name == null || name.equalsIgnoreCase("PHONE PAY")) {
                        holder.Upiname.setText("Name : " + list.get(position).getWithdrawdetail().get(k).getDisplayname());
                        holder.Upinumber.setText("UPI Number : " + list.get(position).getWithdrawdetail().get(k).getNumber());
                        holder.Upiname.setVisibility(View.VISIBLE);
                        holder.Upinumber.setVisibility(View.VISIBLE);

                    }
                    if (name == null || name.equalsIgnoreCase("PAYTM WALLET")) {
                        holder.Upiname.setText("Name : " + list.get(position).getWithdrawdetail().get(k).getDisplayname());
                        holder.Upinumber.setText("UPI Number : " + list.get(position).getWithdrawdetail().get(k).getNumber());
                        holder.Upiname.setVisibility(View.VISIBLE);
                        holder.Upinumber.setVisibility(View.VISIBLE);

                    }
                }else {
                    holder.Bankname.setVisibility(View.GONE);
                    holder.Branch.setVisibility(View.GONE);
                    holder.Ifc.setVisibility(View.GONE);
                    holder.Accountnumber.setVisibility(View.GONE);

                    holder.Upiname.setVisibility(View.GONE);
                    holder.Upinumber.setVisibility(View.GONE);
                }
            }
        }
        holder.Approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aprove.aproveid(list.get(position).getId());
            }
        });
        holder.Reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aprove.rejetid(list.get(position).getId());
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView clientname,websitename,username,userid,amount,Pmethode,Upiname,Upinumber,Bankname,
        Ifc,Branch,Accountnumber,withdrawstatus;
        Button Approve,Reject;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.tv_username);
            userid = itemView.findViewById(R.id.tv_userid);
            clientname = itemView.findViewById(R.id.tv_clientname);
            websitename = itemView.findViewById(R.id.tv_websitename);
            amount = itemView.findViewById(R.id.tv_coins);
            Pmethode = itemView.findViewById(R.id.tv_pmethod);

            Upiname = itemView.findViewById(R.id.tv_upiname);
            Upinumber = itemView.findViewById(R.id.tv_upinuber);
            Ifc = itemView.findViewById(R.id.tv_ifc);
            Branch = itemView.findViewById(R.id.tv_branch);
            Accountnumber = itemView.findViewById(R.id.tv_accountnum);
            Bankname = itemView.findViewById(R.id.tv_bankname);

            withdrawstatus = itemView.findViewById(R.id.tv_withdrawstatus);

            Approve = itemView.findViewById(R.id.btn_approve);
            Reject = itemView.findViewById(R.id.btn_reject);


        }
    }

    public interface aprove{
        public void aproveid(String id);
        public void rejetid(String id);

    }



    public Filter getFilter() {
        return filter;
    }
    Filter filter=new Filter() {
        //run on background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<Withdrawalsrequest> filterList = new ArrayList<>();
            if (charSequence.toString() == null) {
                filterList.addAll(list);
            } else {
                String serachStr = charSequence.toString().toUpperCase();
                for (Withdrawalsrequest servicesS : Alllist) {
                    if (servicesS.getUsername().toUpperCase().contains(serachStr)) {
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
            list.addAll((List<Withdrawalsrequest>)results.values);

            notifyDataSetChanged();

        }
    };
}
