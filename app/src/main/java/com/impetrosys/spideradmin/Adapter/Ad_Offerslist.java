package com.impetrosys.spideradmin.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.impetrosys.spideradmin.Modelclass.Bannerlist;
import com.impetrosys.spideradmin.Modelclass.Offerslist;
import com.impetrosys.spideradmin.R;
import com.impetrosys.spideradmin.UtilClasses.SessionParam;
import com.impetrosys.spideradmin.retrofit.BaseRequest;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Ad_Offerslist extends RecyclerView.Adapter<Ad_Offerslist.MyViewHolder> {
    ArrayList<Offerslist> list;
    ArrayList<Offerslist>Alllist;
    Context context;
    Activity activity;
    SessionParam sessionParam;
    private BaseRequest baseRequest;
    Ad_Offerslist.delete delete;



    public Ad_Offerslist(Context context) {
        this.context = context;
    }

    public Ad_Offerslist(ArrayList<Offerslist> list, Context context, SessionParam sessionParam, Activity activity, Ad_Offerslist.delete delete) {
        this.list = list;
        this.context = context;
        this.activity = activity;
        this.sessionParam =sessionParam;
        this.Alllist=new ArrayList<>(list);
        this.delete=delete;

    }

    public Ad_Offerslist(Context context, int count) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_act_offerslist, parent, false);
        return new MyViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {



       holder.title.setText(list.get(position).getTitle());
        holder.code.setText("Code:  "+list.get(position).getCode());
        holder.price.setText("Price:  "+list.get(position).getPrice());

        Picasso.get()
                .load(list.get(position).getPhoto())
                .into(holder.imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progress.setVisibility(View.GONE);
                    }
                    @Override
                    public void onError(Exception e) {

                    }
                });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete.getid(list.get(position).getId());
            }
        });

    }

    @Override
    public int getItemCount() {

        return list.size();
        //return 10;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView,delete;
        ProgressBar progress;
        TextView title,code,price;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img_web);
            progress = itemView.findViewById(R.id.progressbar);
            title = itemView.findViewById(R.id.name);
            code = itemView.findViewById(R.id.code);
            price = itemView.findViewById(R.id.price);
            delete = itemView.findViewById(R.id.delete);


        }
    }
    public interface delete{
        public void getid(
                String id
        );
    }

//    public Filter getFilter() {
//        return filter;
//    }
//    Filter filter=new Filter() {
//        //run on background thread
//        @Override
//        protected FilterResults performFiltering(CharSequence charSequence) {
//
//            List<Userlist> filterList = new ArrayList<>();
//            if (charSequence.toString() == null) {
//                filterList.addAll(list);
//            } else {
//                String serachStr = charSequence.toString().toUpperCase();
//                for (Userlist servicesS : Alllist) {
//                    if (servicesS.getName().toUpperCase().contains(serachStr)) {
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
//            list.addAll((List<Userlist>)results.values);
//
//            notifyDataSetChanged();
//
//        }
//    };
}
