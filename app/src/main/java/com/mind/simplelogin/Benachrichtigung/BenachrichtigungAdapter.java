package com.mind.simplelogin.Benachrichtigung;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.mind.simplelogin.Profil.otherProfile;
import com.mind.simplelogin.R;
import com.mind.simplelogin.Userliste.Users;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BenachrichtigungAdapter extends RecyclerView.Adapter<BenachrichtigungAdapter.ViewHolder> {

    public List<Users> usersList;
    //public List<Users> acceptedList;
    public Context context;
    //private String myUsId;
    private final int PEND_REQ_FLAG = 0;
    private final int ACC_REQ_FLAG = 1;


    public BenachrichtigungAdapter(Context context, List<Users> usersList){
        this.usersList = usersList;
        //this.acceptedList = new ArrayList<>();
        this.context= context;
        //this.myUsId = fAuth.getUid();
    }

    @Override
    public int getItemViewType(int position) {
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();

        String myId = FirebaseAuth.getInstance().getUid();
        String otherId = usersList.get(position).getUsId();

        DocumentReference docIdRef = rootRef.collection("users").document(myId).collection("friends").document(otherId);


        final boolean[] isFriends = new boolean[1];
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        isFriends[0] = true;
                    } else {
                        isFriends[0] = false;
                    }
                } else {
                    isFriends[0] = false;
                }
            }
        });
        System.out.println("WIR SIND BEFREUNDET : " + Boolean.toString(isFriends[0]));
        if (isFriends[0]) {
            return ACC_REQ_FLAG;
        } else {
            return PEND_REQ_FLAG;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = null;

        switch (viewType) {
            case ACC_REQ_FLAG:
                v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_req_accepted, viewGroup, false);
                return new AcceptedReqViewHolder(v);
            case PEND_REQ_FLAG:
                v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_benachrichtigung, viewGroup, false);
                return new RequestViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final BenachrichtigungAdapter.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof BenachrichtigungAdapter.RequestViewHolder) {
            RequestViewHolder reqViewHolder = (RequestViewHolder) viewHolder;
            reqViewHolder.nametext.setText(usersList.get(i).getBenutername());

            if (usersList.get(i).getImage() == null) {
                ((RequestViewHolder) viewHolder).image.setImageResource(R.drawable.ic_person);
            } else {
                Picasso.get().load(usersList.get(i).getImage()).into(((RequestViewHolder) viewHolder).image);
            }


            final String user_id = usersList.get(i).userId;

            ((RequestViewHolder) viewHolder).mView.setOnClickListener(new View.OnClickListener() {
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


            ((RequestViewHolder) viewHolder).ablehnen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fStore.collection("users").document(user_id).collection("request").document(user_id + usid).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
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
                    fStore.collection("users").document(usid).collection("request").document(usid + user_id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "Nö", Toast.LENGTH_SHORT).show();


                        }
                    });
                    Users added = usersList.get(userPos);
                    usersList.remove(added);
                    usersList.add(added);
                    notifyDataSetChanged();
                }

            });

            ((RequestViewHolder) viewHolder).annehmen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Map<String, String> friends = new HashMap<>();
                    friends.put(user_id, user_id);


                    final Map<String, String> friends1 = new HashMap<>();
                    friends1.put(usid, usid);

                    fStore.collection("users").document(usid).collection("friends").document(user_id).set(friends, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(context, "Ihr seid jetzt Freunde", Toast.LENGTH_SHORT).show();
                            fStore.collection("users").document(user_id).collection("request").document(user_id + usid).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "Nö", Toast.LENGTH_SHORT).show();

                                }
                            });
                            fStore.collection("users").document(usid).collection("request").document(usid + user_id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
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
                    fStore.collection("users").document(user_id).collection("friends").document(usid).set(friends1, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                        }
                    });
                    usersList.remove(userPos);
                    notifyDataSetChanged();
                }

            });
        } else if (viewHolder instanceof BenachrichtigungAdapter.AcceptedReqViewHolder) {
            AcceptedReqViewHolder acceptedReqViewHolder = (AcceptedReqViewHolder) viewHolder;
            acceptedReqViewHolder.nametext.setText(usersList.get(i).getBenutername());

            if (usersList.get(i).getImage() == null) {
                acceptedReqViewHolder.image.setImageResource(R.drawable.ic_person);
            } else {
                Picasso.get().load(usersList.get(i).getImage()).into(acceptedReqViewHolder.image);
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



    }



    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public abstract class ViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView= itemView;
        }
    }

    public class AcceptedReqViewHolder extends ViewHolder {

        public TextView nametext;
        public ImageView image;

        public AcceptedReqViewHolder(@NonNull View itemView) {
            super(itemView);

            nametext = (TextView)mView.findViewById(R.id.name);
            image = (ImageView)mView.findViewById(R.id.image);
        }
    }

    public class RequestViewHolder extends ViewHolder {

        public TextView nametext;
        public ImageView image;
        Button annehmen, ablehnen;



        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);

            nametext = (TextView)mView.findViewById(R.id.name);
            image = (ImageView)mView.findViewById(R.id.image);
            annehmen = (Button)mView.findViewById(R.id.annehmen);
            ablehnen = (Button)mView.findViewById(R.id.ablehnen);

        }

    }

}