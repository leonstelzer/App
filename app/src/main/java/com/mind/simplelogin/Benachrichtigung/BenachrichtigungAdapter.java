package com.mind.simplelogin.Benachrichtigung;

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
        FirebaseAuth fAuth;
        final FirebaseFirestore fStore;
        FirebaseFirestore mFirestore;

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        final String usid = fAuth.getCurrentUser().getUid();

        final int userPos = i;



        viewHolder.ablehnen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fStore.collection("users").document(user_id).collection("request").document(user_id+usid).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Freundschaftsanfrage abgelehnt", Toast.LENGTH_SHORT).show();


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Nö", Toast.LENGTH_SHORT).show();


                    }
                });
                fStore.collection("users").document(usid).collection("request").document(usid+user_id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Nö", Toast.LENGTH_SHORT).show();


                    }
                });

                usersList.remove(userPos);
                notifyDataSetChanged();
            }

        });

        viewHolder.annehmen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Map<String, String> friends = new HashMap<>();
                friends.put(user_id, user_id);


                final Map<String, String> friends1 = new HashMap<>();
                friends1.put(usid, usid);

                fStore.collection("users").document(usid).collection("friends").document(user_id).set(friends, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context,"Ihr seid jetzt Freunde", Toast.LENGTH_SHORT).show();
                        fStore.collection("users").document(user_id).collection("request").document(user_id +usid).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "Nö", Toast.LENGTH_SHORT).show();

                            }
                        });
                        fStore.collection("users").document(usid).collection("request").document(usid+ user_id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "Nö", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                });
                fStore.collection("users").document(user_id).collection("friends").document(usid).set(friends1,SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                });
                usersList.remove(userPos);
                notifyDataSetChanged();
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
        Button annehmen, ablehnen;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView= itemView;

            nametext = (TextView)mView.findViewById(R.id.name);
            image = (ImageView)mView.findViewById(R.id.image);
            annehmen = (Button)mView.findViewById(R.id.annehmen);
            ablehnen = (Button)mView.findViewById(R.id.ablehnen);

        }

    }

}
