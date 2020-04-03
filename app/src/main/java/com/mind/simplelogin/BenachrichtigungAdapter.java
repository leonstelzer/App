package com.mind.simplelogin;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mind.simplelogin.Profil.otherProfile;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BenachrichtigungAdapter extends RecyclerView.Adapter<BenachrichtigungAdapter.ViewHolder> {

    public List<Users> usersList;
    public Context context;
    public BenachrichtigungAdapter(Context context, List<Users> usersList){
        this.usersList = usersList;
        this.context= context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_benachrichtigung, viewGroup, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull BenachrichtigungAdapter.ViewHolder viewHolder, int i) {
        viewHolder.nametext.setText(usersList.get(i).getBenutername());

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



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView= itemView;

            nametext = (TextView) mView.findViewById(R.id.name);
            image = (ImageView) mView.findViewById(R.id.image);

        }
    }

}
