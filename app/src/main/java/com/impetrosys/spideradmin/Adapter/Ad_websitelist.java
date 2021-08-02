package com.impetrosys.spideradmin.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Filter;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.impetrosys.spideradmin.Modelclass.Websitelist;
import com.impetrosys.spideradmin.R;
import com.impetrosys.spideradmin.UtilClasses.SessionParam;
import com.impetrosys.spideradmin.retrofit.BaseRequest;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class Ad_websitelist extends RecyclerView.Adapter<Ad_websitelist.MyViewHolder> {
    ArrayList<Websitelist> list;
    ArrayList<Websitelist.Game> gamelist;
    ArrayList<Websitelist>Alllist;
    Context context;
    Activity activity;
    SessionParam sessionParam;
    private BaseRequest baseRequest;
    Ad_websitelist.delete delete;


    public Ad_websitelist(Context context) {
        this.context = context;
    }

    public Ad_websitelist(ArrayList<Websitelist> list,ArrayList<Websitelist.Game> gamelist ,Context context, SessionParam sessionParam, Activity activity,Ad_websitelist.delete delete) {
        this.list = list;
        this.gamelist=gamelist;
        this.context = context;
        this.activity = activity;
        this.sessionParam =sessionParam;
        this.Alllist=new ArrayList<>(list);
        this.delete=delete; //interface

    }

    public Ad_websitelist(Context context, int count) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newwebsite, parent, false);
        return new MyViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {



        holder.name.setText(list.get(position).getName());
        holder.url.setText(list.get(position).getUrl());
        holder.descriptin.setText(list.get(position).getDescription());

    if(list.get(position).getPhoto() != null && !list.get(position).getPhoto().isEmpty() ) {
        Picasso.get()
                .load(list.get(position).getPhoto())
                .placeholder(context.getResources().getDrawable(R.drawable.spiderlogo5))
                .into(holder.img);
    }

        for(int k=0;k<list.get(position).getGames().size();k++){
            String name=list.get(position).getGames().get(k).getName();
            String price=list.get(position).getGames().get(k).getPrice().toString();
            if (name.equalsIgnoreCase("cricket")){
                holder.ncricket.setVisibility(View.VISIBLE);
                holder.ncricket.setText(name);
                holder.pcricket.setVisibility(View.VISIBLE);
                holder.pcricket.setText(price);
                holder.iconcricket.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.apptheamcolor)));
            }

            if (name.equalsIgnoreCase("football")){
                holder.nfootball.setVisibility(View.VISIBLE);
                holder.nfootball.setText(name);
                holder.pfootball.setVisibility(View.VISIBLE);
                holder.pfootball.setText(price);
                holder.iconfootball.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.apptheamcolor)));
            }
            if (name.equalsIgnoreCase("tennis")){
                holder.ntenis.setVisibility(View.VISIBLE);
                holder.ntenis.setText(name);
                holder.ptenis.setVisibility(View.VISIBLE);
                holder.ptenis.setText(price);

                holder.icontenis.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.apptheamcolor)));
            }
            if (name.equalsIgnoreCase("horse racing")){
                holder.nhourse.setVisibility(View.VISIBLE);
                holder.nhourse.setText(name);
                holder.phourse.setVisibility(View.VISIBLE);
                holder.phourse.setText(price);
                holder.iconhourse.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.apptheamcolor)));
            }
            if (name.equalsIgnoreCase("cards")){
                holder.ncard.setVisibility(View.VISIBLE);
                holder.ncard.setText(name);
                holder.pcard.setVisibility(View.VISIBLE);
                holder.pcard.setText(price);
                holder.iconcard.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.apptheamcolor)));
            }
            if (name.equalsIgnoreCase("live casino")){
                holder.nlivecasino.setVisibility(View.VISIBLE);
                holder.nlivecasino.setText(name);
                holder.plivecasino.setVisibility(View.VISIBLE);
                holder.plivecasino.setText(price);

                holder.iconlivecasino.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.apptheamcolor)));
            }
            if (name.equalsIgnoreCase("politics")){
                holder.npolitics.setVisibility(View.VISIBLE);
                holder.npolitics.setText(name);
                holder.ppolitics.setVisibility(View.VISIBLE);
                holder.ppolitics.setText(price);
                holder.iconpolitics.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.apptheamcolor)));
            }
        }



        holder.dropdwn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(holder.layout.getVisibility() == View.VISIBLE){
//                    holder.layout.setVisibility(View.GONE);
//                } else {
//                    holder.layout.setVisibility(View.VISIBLE);
//                }
                if(holder.constraintLayout.getVisibility() == View.VISIBLE){
                    holder.constraintLayout.setVisibility(View.GONE);
                } else {
                    holder.constraintLayout.setVisibility(View.VISIBLE);
                }

            }
        });
        holder.Ed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = new ContextThemeWrapper(context, R.style.popupMenuStyle);//bgcolorpopmenu
                PopupMenu popup = new PopupMenu(context, holder.Ed);
                //inflating menu from xml resource
                popup.inflate(R.menu.menuitem);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit:
                                //handle menu1 click
                                delete.edit(list.get(position));
                                return true;
                            case R.id.delite:
                                //interfacecall
                                delete.getid(list.get(position).getId());
                                //handle menu2 click
                                return true;
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

        TextView name,url,descriptin;
        ImageView dropdwn;
        LinearLayout layout;
        ImageView Ed;
        CircleImageView img;
        ImageView iconcricket,iconfootball,icontenis,iconhourse,iconlivecasino,iconcard,iconpolitics;
        ConstraintLayout constraintLayout;
        TextView pcricket,pfootball,ptenis,phourse,plivecasino,pcard,ppolitics;
        TextView ncricket,nfootball,ntenis,nhourse,nlivecasino,ncard,npolitics;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_webname);
            url = itemView.findViewById(R.id.tv_url);
            descriptin = itemView.findViewById(R.id.tv_desciption);
            img = itemView.findViewById(R.id.img_web);

            dropdwn=itemView.findViewById(R.id.drodow);
            layout=itemView.findViewById(R.id.lay1);
            Ed=itemView.findViewById(R.id.Eddi);
            constraintLayout=itemView.findViewById(R.id.cont_lay);
            pcricket=itemView.findViewById(R.id.tv_cricket);
            pfootball=itemView.findViewById(R.id.tv_football);
            ptenis=itemView.findViewById(R.id.tv_tenis);
            phourse=itemView.findViewById(R.id.tv_hourse);
            plivecasino=itemView.findViewById(R.id.tv_livecasio);
            pcard=itemView.findViewById(R.id.tv_card);
            ppolitics=itemView.findViewById(R.id.tv_ps);


            ncricket=itemView.findViewById(R.id.ct);
            nfootball=itemView.findViewById(R.id.ft);
            ntenis=itemView.findViewById(R.id.ts);
            nhourse=itemView.findViewById(R.id.hs);
            nlivecasino=itemView.findViewById(R.id.lc);
            ncard=itemView.findViewById(R.id.cd);
            npolitics=itemView.findViewById(R.id.ps);

            iconcricket=itemView.findViewById(R.id.mv_crickate);
            iconfootball=itemView.findViewById(R.id.mv_football);
            icontenis=itemView.findViewById(R.id.mv_tenis);
            iconhourse=itemView.findViewById(R.id.mv_hourse);
            iconcard=itemView.findViewById(R.id.mv_cads);
            iconlivecasino=itemView.findViewById(R.id.mv_casino);
            iconpolitics=itemView.findViewById(R.id.mv_politics);







        }
    }

    public Filter getFilter() {
        return filter;
    }
    Filter filter=new Filter() {
        //run on background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<Websitelist> filterList = new ArrayList<>();
            if (charSequence.toString() == null) {
                filterList.addAll(list);
            } else {
                String serachStr = charSequence.toString().toUpperCase();
                for (Websitelist servicesS : Alllist) {
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
            list.addAll((List<Websitelist>)results.values);

            notifyDataSetChanged();

        }
    };


    //interface
   public interface delete{
        public void getid(
                String id
        );
        public  void edit(Websitelist edit);
    }

}

