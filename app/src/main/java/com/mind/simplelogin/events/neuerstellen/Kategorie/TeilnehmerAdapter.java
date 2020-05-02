package com.mind.simplelogin.events.neuerstellen.Kategorie;

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
import com.mind.simplelogin.R;
import com.mind.simplelogin.Userliste.Users;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TeilnehmerAdapter  extends RecyclerView.Adapter<TeilnehmerAdapter.ViewHolder>{

    List<Users> usersList;
    public Context context;

    public TeilnehmerAdapter(Context context, List<Users> usersList){
        this.usersList = usersList;
        this.context= context;


    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.teilnehmer_list, viewGroup, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.nametext.setText(usersList.get(i).getBenutername());

        final String user_id = usersList.get(i).userId;

        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Toast.makeText(context, "User ID:"+user_id, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, otherProfile.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                intent.putExtra("user_id", user_id);
                context.startActivity(intent);

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


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView= itemView;

            nametext = (TextView) mView.findViewById(R.id.name_text);


        }
    }
}


