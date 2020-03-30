package com.mind.simplelogin;

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
import com.squareup.picasso.Picasso;

import java.util.List;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.ViewHolder> {

    public List<Users> usersList;
    public Context context;
    public UsersListAdapter(Context context, List<Users> usersList){
        this.usersList = usersList;
        this.context= context;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
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
