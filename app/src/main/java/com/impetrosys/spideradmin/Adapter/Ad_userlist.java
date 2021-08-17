package com.impetrosys.spideradmin.Adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.impetrosys.spideradmin.Modelclass.Userlist;
import com.impetrosys.spideradmin.Modelclass.Websitelist;
import com.impetrosys.spideradmin.Modelclass.Withdrawalsrequest;
import com.impetrosys.spideradmin.R;
import com.impetrosys.spideradmin.UtilClasses.SessionParam;
import com.impetrosys.spideradmin.retrofit.BaseRequest;

import java.util.ArrayList;
import java.util.List;


public class Ad_userlist extends RecyclerView.Adapter<Ad_userlist.MyViewHolder> {
    ArrayList<Userlist> list;
    ArrayList<Userlist>Alllist;
    ArrayList<Userlist.PaymentDetail> paymentdetails;
    Context context;
    Activity activity;
    SessionParam sessionParam;
    private BaseRequest baseRequest;
    Ad_userlist.CreateRefral createRefral;


    public Ad_userlist(Context context) {
        this.context = context;
    }

    public Ad_userlist(ArrayList<Userlist> list, ArrayList<Userlist.PaymentDetail> paymentdetails, Context context, SessionParam sessionParam, Activity activity, Ad_userlist.CreateRefral createRefral) {
        this.list = list;
        this.context = context;
        this.activity = activity;
        this.sessionParam =sessionParam;
        this.Alllist=new ArrayList<>(list);
        this.createRefral=createRefral;

    }

    public Ad_userlist(Context context, int count) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_user_list, parent, false);
        return new MyViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {



        holder.name.setText(list.get(position).getName());
        holder.mobile.setText("+91 "+list.get(position).getContact());
        holder.id.setText("Client id's  "+list.get(position).getId());

        for (int k = 0; k < list.get(position).getPaymentDetail().size(); k++) {
            String name = list.get(position).getPaymentDetail().get(k).getPaymentmethod();
            if (name != null) {
                if (name == null || name.equalsIgnoreCase("PAYTM UPI")) {
                    holder.paymentmethod.setText("Paymentmethod : " + list.get(position).getPaymentDetail().get(k).getPaymentmethod());
                    holder.upiname.setText("Name : " + list.get(position).getPaymentDetail().get(k).getDisplayname());
                    holder.upinumber.setText("UPI Number : " + list.get(position).getPaymentDetail().get(k).getNumber());
                    holder.paymentmethod.setVisibility(View.VISIBLE);
                    holder.upiname.setVisibility(View.VISIBLE);
                    holder.upinumber.setVisibility(View.VISIBLE);
                    holder.v1.setVisibility(View.VISIBLE);
                }
                if (name == null || name.equalsIgnoreCase("GOOGLE PAY")) {
                    holder.paymentmethod1.setText("Paymentmethod : " + list.get(position).getPaymentDetail().get(k).getPaymentmethod());
                    holder.upiname1.setText("Name : " + list.get(position).getPaymentDetail().get(k).getDisplayname());
                    holder.upinumber1.setText("UPI Number : " + list.get(position).getPaymentDetail().get(k).getNumber());
                    holder.paymentmethod1.setVisibility(View.VISIBLE);
                    holder.upiname1.setVisibility(View.VISIBLE);
                    holder.upinumber1.setVisibility(View.VISIBLE);
                    holder.v2.setVisibility(View.VISIBLE);
                }
                if (name == null || name.equalsIgnoreCase("PHONE PAY")) {
                    holder.paymentmethod2.setText("Paymentmethod : " + list.get(position).getPaymentDetail().get(k).getPaymentmethod());
                    holder.upiname2.setText("Name : " + list.get(position).getPaymentDetail().get(k).getDisplayname());
                    holder.upinumber2.setText("UPI Number : " + list.get(position).getPaymentDetail().get(k).getNumber());
                    holder.paymentmethod2.setVisibility(View.VISIBLE);
                    holder.upiname2.setVisibility(View.VISIBLE);
                    holder.upinumber2.setVisibility(View.VISIBLE);
                    holder.v3.setVisibility(View.VISIBLE);
                }
                if (name == null || name.equalsIgnoreCase("PAYTM WALLET")) {
                    holder.paymentmethod3.setText("Paymentmethod : " + list.get(position).getPaymentDetail().get(k).getPaymentmethod());
                    holder.upiname3.setText("Name : " + list.get(position).getPaymentDetail().get(k).getDisplayname());
                    holder.upinumber3.setText("UPI Number : " + list.get(position).getPaymentDetail().get(k).getNumber());
                    holder.paymentmethod3.setVisibility(View.VISIBLE);
                    holder.upiname3.setVisibility(View.VISIBLE);
                    holder.upinumber3.setVisibility(View.VISIBLE);
                    holder.v4.setVisibility(View.VISIBLE);
                }

                if (name == null || name.equalsIgnoreCase("BANK TRANSFER")) {
                    holder.paymentmethod4.setText("Paymentmethod : " + list.get(position).getPaymentDetail().get(k).getPaymentmethod());
                    holder.bname.setText("Bankname : " + list.get(position).getPaymentDetail().get(k).getBankname());
                    holder.bifsc.setText("IFSC Code : " + list.get(position).getPaymentDetail().get(k).getIfsc());
                    holder.baccountno.setText("Branch : " + list.get(position).getPaymentDetail().get(k).getBranch());
                    holder.bbranch.setText("Accountno. : " + list.get(position).getPaymentDetail().get(k).getAccountno());
                    holder.baccountholder.setText("Accountholder : " + list.get(position).getPaymentDetail().get(k).getAccountholder());
                    holder.baccounttype.setText("Accounttype : " + list.get(position).getPaymentDetail().get(k).getAccounttype());
                    holder.bname.setVisibility(View.VISIBLE);
                    holder.bifsc.setVisibility(View.VISIBLE);
                    holder.baccountno.setVisibility(View.VISIBLE);
                    holder.bbranch.setVisibility(View.VISIBLE);
                    holder.baccountholder.setVisibility(View.VISIBLE);
                    holder.baccounttype.setVisibility(View.VISIBLE);
                    holder.paymentmethod4.setVisibility(View.VISIBLE);
                }

            }
        }

       /* for (int k = 0; k < list.get(position).getReferaldetail().size(); k++) {
            String name = list.get(position).getIsrefer().toString();
            if (name.equalsIgnoreCase("1")) {


            }
        }*/


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
        holder.moreoption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* context = new ContextThemeWrapper(context, R.style.popupMenuStyle);//bgcolorpopmenu
                PopupMenu popup = new PopupMenu(context, holder.moreoption);
                popup.inflate(R.menu.menuitem);
                popup.getMenu().getItem(0).setTitle("Details Referal");
                popup.getMenu().getItem(1).setVisible(false);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit:
                                createRefral.detail(list.get(position));
                                return true;
                            case R.id.delite:
                                return false;
                            default:
                                return false;
                        }
                    }
                });
                //displaying the popup
                popup.show();*/

                createRefral.detail(list.get(position));

            }

        });




    }

    @Override
    public int getItemCount() {

        return list.size();
        //return 10;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name,mobile,id;
        ImageView dropdwn,moreoption;
        LinearLayout layout;
        View v1,v2,v3,v4;
        TextView upiname,upinumber,upiname1,upinumber1,upiname2,upinumber2,upiname3,upinumber3
                ,bname,bifsc,bbranch,baccountno,baccountholder,baccounttype,paymentmethod,
                paymentmethod1,paymentmethod2,paymentmethod3,paymentmethod4;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            mobile = itemView.findViewById(R.id.mobile);
            id = itemView.findViewById(R.id.clintid);
            dropdwn = itemView.findViewById(R.id.drodow);
            layout=itemView.findViewById(R.id.lay);

            upiname=itemView.findViewById(R.id.displayname);
            upinumber=itemView.findViewById(R.id.number);
            upiname1=itemView.findViewById(R.id.displayname1);
            upinumber1=itemView.findViewById(R.id.number1);
            upiname2=itemView.findViewById(R.id.displayname2);
            upinumber2=itemView.findViewById(R.id.number2);
            upiname3=itemView.findViewById(R.id.displayname3);
            upinumber3=itemView.findViewById(R.id.number3);

            v1=itemView.findViewById(R.id.v_view1);
            v2=itemView.findViewById(R.id.v_view2);
            v3=itemView.findViewById(R.id.v_view3);
            v4=itemView.findViewById(R.id.v_view4);


            bname=itemView.findViewById(R.id.bankname);
            bifsc=itemView.findViewById(R.id.ifsc);
            bbranch=itemView.findViewById(R.id.branch);
            baccountno=itemView.findViewById(R.id.accountno);
            baccountholder=itemView.findViewById(R.id.accountholder);
            baccounttype=itemView.findViewById(R.id.accounttype);

            paymentmethod=itemView.findViewById(R.id.paymentmethod);
            paymentmethod1=itemView.findViewById(R.id.paymentmethod1);
            paymentmethod2=itemView.findViewById(R.id.paymentmethod2);
            paymentmethod3=itemView.findViewById(R.id.paymentmethod3);
           paymentmethod4=itemView.findViewById(R.id.paymentmethod4);

            moreoption=itemView.findViewById(R.id.moredetail);





        }
    }

    public Filter getFilter() {
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
    };

    public interface CreateRefral{
//        public void getid(
//                String id
//        );
        public  void detail(Userlist detail);
    }
}
