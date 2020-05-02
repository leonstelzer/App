package com.mind.simplelogin.events.findevents;

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

import com.mind.simplelogin.Profil.otherProfile;
import com.mind.simplelogin.R;
import com.mind.simplelogin.Userliste.Users;
import com.mind.simplelogin.Userliste.UsersListAdapter;
import com.mind.simplelogin.events.Freundeeinladen.Event;
import com.mind.simplelogin.events.neuerstellen.Kategorie.allevent;

import java.util.List;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder>{

    public List<Event> eventList;
    public Context context;
    public EventListAdapter (Context context, List<Event> eventList){
        this.eventList = eventList;
        this.context= context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_event, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.nametext.setText(eventList.get(i).getOrt());
        viewHolder.zeit.setText(eventList.get(i).getZeit());
        viewHolder.datum.setText(eventList.get(i).getDatum());



        final String eventid = eventList.get(i).eventid;
        final String kategorie = eventList.get(i).getKategorie();

        if(kategorie.equals("Fussball")){
            viewHolder.image.setImageResource(R.drawable.fussballneu);

        }
        else if(kategorie.equals("Pokern")){
            viewHolder.image.setImageResource(R.drawable.ic_pokernsvg);

        }
        else if(kategorie.equals("Badminton")){
            viewHolder.image.setImageResource(R.drawable.badminton);

        }
        else if(kategorie.equals("Bar besuch")){
            viewHolder.image.setImageResource(R.drawable.ic_barsvg);

        }
        else if(kategorie.equals("Essen")){
            viewHolder.image.setImageResource(R.drawable.essenneu);

        }
        else if(kategorie.equals("Kino")){
            viewHolder.image.setImageResource(R.drawable.ic_kinosvg);

        }
        else if(kategorie.equals("Tischtennis")){
            viewHolder.image.setImageResource(R.drawable.tischtennis);

        }
        else if(kategorie.equals("Zocken")){
            viewHolder.image.setImageResource(R.drawable.ic_playstation_4);

        }
        else if(kategorie.equals("Darts")){
            viewHolder.image.setImageResource(R.drawable.darts);

        }
        else if(kategorie.equals("Schwimmbad")){
            viewHolder.image.setImageResource(R.drawable.schwimmbad);

        }
        else if(kategorie.equals("Handball")){
            viewHolder.image.setImageResource(R.drawable.handball);

        }
        else if(kategorie.equals("Spazieren")){
            viewHolder.image.setImageResource(R.drawable.laufen);

        }
        else if(kategorie.equals("Fitnessstudio")){
            viewHolder.image.setImageResource(R.drawable.fitness);

        }
        else if(kategorie.equals("Konzert")){
            viewHolder.image.setImageResource(R.drawable.konzert);

        }
        else if(kategorie.equals("Billiard")){
            viewHolder.image.setImageResource(R.drawable.pool);

        }
        else if(kategorie.equals("Shoppen")){
            viewHolder.image.setImageResource(R.drawable.shoppen);

        }
        else if(kategorie.equals("Volleyball")){
            viewHolder.image.setImageResource(R.drawable.volleyball);

        }
        else if(kategorie.equals("Zoo/Tierpark")){
            viewHolder.image.setImageResource(R.drawable.zoo);

        }
        else if(kategorie.equals("Joggen")){
            viewHolder.image.setImageResource(R.drawable.laufenneu);

        }
        else if(kategorie.equals("Casino")){
            viewHolder.image.setImageResource(R.drawable.casino);

        }  else if (kategorie.equals("Sonstiges")) {
            viewHolder.image.setImageResource(R.drawable.fragezeichen);

        }




        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Toast.makeText(context, "User ID:"+user_id, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, allevent.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                intent.putExtra("eventid", eventid);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public TextView nametext;
        public TextView zeit;
        public TextView datum;
        public ImageView image;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView= itemView;
            datum = mView.findViewById(R.id.datum);
            nametext = mView.findViewById(R.id.name);
            zeit = mView.findViewById(R.id.zeit);
            image = mView.findViewById(R.id.image);

        }
    }
}
