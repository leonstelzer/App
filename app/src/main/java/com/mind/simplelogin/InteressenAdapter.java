package com.mind.simplelogin;

import android.content.Context;
import android.content.Intent;
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

import com.mind.simplelogin.Profil.otherProfile;
import com.mind.simplelogin.events.Eventerstellen;
import com.mind.simplelogin.events.Freundeeinladen.EinladenListAdapter;

import java.util.ArrayList;

public class InteressenAdapter extends RecyclerView.Adapter<InteressenAdapter.InteressenViewHolder>  {
    private ArrayList<Interessenitem> minteressenlist;
    public Context context;




    public static class InteressenViewHolder extends RecyclerView.ViewHolder  {
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
    public InteressenAdapter(Context context,ArrayList<Interessenitem>interessenlist) {
        minteressenlist=interessenlist;
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
    public void onBindViewHolder(@NonNull InteressenViewHolder interessenViewHolder, int i) {
         Interessenitem currentitem = minteressenlist.get(i);
         interessenViewHolder.image.setImageResource(currentitem.getImageresource());
         interessenViewHolder.name.setText(currentitem.getName());
        final String name = currentitem.getName();




        interessenViewHolder.mView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(context, Eventerstellen.class);
                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                     intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 }
                 intent.putExtra("name", name);

                 context.startActivity(intent);
             }
         });



    }

    @Override
    public int getItemCount() {
        return minteressenlist.size();
    }


}
