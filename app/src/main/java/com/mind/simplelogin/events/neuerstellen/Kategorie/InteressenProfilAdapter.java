package com.mind.simplelogin.events.neuerstellen.Kategorie;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.mind.simplelogin.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class InteressenProfilAdapter extends RecyclerView.Adapter<InteressenProfilAdapter.InteressenViewHolder> implements Filterable {
    private ArrayList<InteressenProfilItem> minteressenlist;
    private ArrayList<InteressenProfilItem> minteressenlistall;
    public Context context;




    public class InteressenViewHolder extends RecyclerView.ViewHolder  {
        public ImageView image;
        public TextView name;
        View mView;

        public InteressenViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
        }


    }
    public InteressenProfilAdapter(Context context,ArrayList<InteressenProfilItem>interessenlist) {
        minteressenlist=interessenlist;
        minteressenlistall= new ArrayList<InteressenProfilItem>(interessenlist);

        this.context= context;

    }

    @NonNull
    @Override
    public InteressenViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_interessen, viewGroup, false);
        InteressenViewHolder ih= new InteressenViewHolder(view);
        return ih;
    }

    @Override
    public void onBindViewHolder(@NonNull final InteressenViewHolder interessenViewHolder, int i) {
        final InteressenProfilItem currentitem = minteressenlist.get(i);
        interessenViewHolder.image.setImageResource(currentitem.getImageresource());
        interessenViewHolder.name.setText(currentitem.getName());
        final String name = currentitem.getName();


        interessenViewHolder.mView.setBackgroundColor(currentitem.isSelected() ? Color.CYAN : Color.WHITE);
        interessenViewHolder.mView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                currentitem.setSelected(!currentitem.isSelected());
                interessenViewHolder.mView.setBackgroundColor(currentitem.isSelected() ? Color.CYAN : Color.WHITE);

            }
        });



        /*interessenViewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, date_time.class);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                intent.putExtra("kategorie", name);

                context.startActivity(intent);

            }
        });
        */

    }


    @Override
    public int getItemCount() {
        //return minteressenlist.size();
        return minteressenlist == null? 0 : minteressenlist.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<InteressenProfilItem> filterlist = new ArrayList<>();
            if (constraint.toString().isEmpty()){
                filterlist.addAll(minteressenlistall);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (InteressenProfilItem interesse : minteressenlistall){
                    if (interesse.getName().toLowerCase().contains(filterPattern)){
                        filterlist.add(interesse);


                    }

                }
            }
            FilterResults filterResults = new FilterResults();

            filterResults.values = filterlist;
            Collections.sort(filterlist, InteressenProfilItem.myname);

            return filterResults;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            minteressenlist.clear();
            minteressenlist.addAll((Collection<? extends InteressenProfilItem>) results.values);


            notifyDataSetChanged();

        }
    };
}
