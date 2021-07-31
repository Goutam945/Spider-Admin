package com.impetrosys.spideradmin.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.impetrosys.spideradmin.Modelclass.ClientidRequestchangepasslsit;
import com.impetrosys.spideradmin.R;
import com.impetrosys.spideradmin.UtilClasses.SessionParam;
import com.impetrosys.spideradmin.retrofit.BaseRequest;

import java.util.ArrayList;


public class Ad_ClientidRequest_changepasslist extends RecyclerView.Adapter<Ad_ClientidRequest_changepasslist.MyViewHolder> {
    ArrayList<ClientidRequestchangepasslsit> list;
    ArrayList<ClientidRequestchangepasslsit>Alllist;
    Context context;
    Activity activity;
    SessionParam sessionParam;
    private BaseRequest baseRequest;
    Ad_ClientidRequest_changepasslist.Changepass changepass;


    public Ad_ClientidRequest_changepasslist(Context context) {
        this.context = context;
    }

    public Ad_ClientidRequest_changepasslist(ArrayList<ClientidRequestchangepasslsit> list, Context context, SessionParam sessionParam, Activity activity, Ad_ClientidRequest_changepasslist.Changepass changepass) {
        this.list = list;
        this.context = context;
        this.activity = activity;
        this.sessionParam =sessionParam;
        this.Alllist=new ArrayList<>(list);
        this.changepass=changepass;

    }

    public Ad_ClientidRequest_changepasslist(Context context, int count) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_clientids_requestlist, parent, false);
        return new MyViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {



        holder.name.setText(list.get(position).getUsername());
        holder.userid.setText("Client Id "+list.get(position).getUserid());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = new ContextThemeWrapper(context, R.style.popupMenuStyle);//bgcolorpopmenu
                PopupMenu popup = new PopupMenu(context, holder.edit);
                //inflating menu from xml resource
                popup.inflate(R.menu.menuitem);
                popup.getMenu().getItem(1).setVisible(false);
                popup.getMenu().getItem(0).setTitle("Change Password");
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit:
                                changepass.pass(list.get(position).getRequestid());
                                return true;
                            case R.id.delite:
                                return false;

                            default:
                                return false;
                        }
                    }
                });
                //displaying the popup
                popup.show();

            }

        });



    }

    @Override
    public int getItemCount() {

        return list.size();
        //return 10;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name,userid;
        ImageView edit;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.re_name);
            userid = itemView.findViewById(R.id.re_id);
            edit = itemView.findViewById(R.id.re_edit);

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
   public interface Changepass{
       public void pass(
               String id
       );
   }
}
