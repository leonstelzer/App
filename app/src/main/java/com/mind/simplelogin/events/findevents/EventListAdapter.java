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
        viewHolder.nametext.setText(eventList.get(i).getEventname());
        viewHolder.orttext.setText(eventList.get(i).getOrt());
        viewHolder.datum.setText(eventList.get(i).getDatum());

        final String eventid = eventList.get(i).eventid;

        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Toast.makeText(context, "User ID:"+user_id, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, otherEvent.class);
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
        public TextView orttext;
        public TextView datum;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView= itemView;
            datum = mView.findViewById(R.id.Datum);
            nametext = mView.findViewById(R.id.name_text);
            orttext = mView.findViewById(R.id.ort_text);

        }
    }
}
