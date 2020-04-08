package com.mind.simplelogin.events.Freundeeinladen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.mind.simplelogin.Profil.otherProfile;
import com.mind.simplelogin.R;
import com.mind.simplelogin.Userliste.Users;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EinladenListAdapter extends RecyclerView.Adapter<EinladenListAdapter.ViewHolder> {

    public List<Users> usersList;
    public List<Event> eventList;

    public Context context;
    public EinladenListAdapter(Context context, List<Users> usersList, List<Event> eventList){
        this.usersList = usersList;
        this.eventList = eventList;
        this.context= context;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listview_eventeinladen, viewGroup, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {


        viewHolder.nametext.setText(usersList.get(i).getBenutername());
        viewHolder.orttext.setText(usersList.get(i).getOrt());

           if (usersList.get(i).getImage()==null) {
               viewHolder.image.setImageResource(R.drawable.ic_person);
           } else{ Picasso.get().load(usersList.get(i).getImage()).into(viewHolder.image);
           }

        final String user_id = usersList.get(i).userId;
        final String nametext = usersList.get(i).getBenutername();
        final String eventid = eventList.get(i).eventid;

        FirebaseAuth fAuth;
        final FirebaseFirestore fStore;
        FirebaseFirestore mFirestore;

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        final String usid = fAuth.getCurrentUser().getUid();

        final int userPos = i;


        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Map<String, String> event = new HashMap<>();
                event.put(usid, usid);
                event.put("eventid",eventid);

                fStore.collection("users").document(user_id).collection("eventeinladung").document(eventid).set(event, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Einladung gesendet an"+nametext, Toast.LENGTH_SHORT).show();
                        usersList.remove(userPos);
                        notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, "Nö", Toast.LENGTH_SHORT).show();
                }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public TextView nametext;
        public ImageView image;
        public TextView orttext;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView= itemView;

            nametext = (TextView) mView.findViewById(R.id.name_text);
            image = (ImageView) mView.findViewById(R.id.image);
            orttext = (TextView) mView.findViewById(R.id.ort_text);

        }
    }
}
