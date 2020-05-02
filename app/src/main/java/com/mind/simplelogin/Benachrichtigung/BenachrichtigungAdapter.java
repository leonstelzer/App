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
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.mind.simplelogin.Profil.otherProfile;
import com.mind.simplelogin.R;
import com.mind.simplelogin.Userliste.Users;
import com.mind.simplelogin.events.Freundeeinladen.Event;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class BenachrichtigungAdapter extends RecyclerView.Adapter<BenachrichtigungAdapter.ViewHolder> {

    public List<Object> usersList;
    public List<Boolean> acceptedList;
    //public List<Users> acceptedList;
    public Context context;
    //private String myUsId;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    private final int PEND_REQ_FLAG = 0;
    private final int ACC_REQ_FLAG = 1;
    private final int PEND_EVENT_FLAG = 2;
    private final int ACC_EVENT_FLAG = 3;


    public BenachrichtigungAdapter(Context context, List<Object> usersList){
        this.usersList = usersList;
        this.acceptedList = new ArrayList<>();
        //this.acceptedList = new ArrayList<>();
        this.context= context;
        //this.myUsId = fAuth.getUid();
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
    }

    @Override
    public int getItemViewType(int position) {
        checkListSizes();
        if (usersList.get(position) instanceof Users) {
            if (acceptedList.get(position)==true) {
                return ACC_REQ_FLAG;
            } else {
                return PEND_REQ_FLAG;
            }
        } else if (usersList.get(position) instanceof Event) {
            if (acceptedList.get(position)==true) {
                return ACC_EVENT_FLAG;
            } else {
                return PEND_EVENT_FLAG;
            }
        } else {
            return -1;
        }
        /*
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        String myId = FirebaseAuth.getInstance().getUid();
        String otherId = usersList.get(position).getUsId();
        DocumentReference docIdRef = rootRef.collection("users").document(myId).collection("friends").document(otherId);
        boolean isFriends = areWeFriends(myId, otherId);
        if (isFriends) {
            return ACC_REQ_FLAG;
        } else {
            return PEND_REQ_FLAG;
        }
        */
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
            case PEND_EVENT_FLAG:
                v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event_benachrichtigung, viewGroup, false);
                return new EventViewHolder(v);
            case ACC_EVENT_FLAG:
                v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_req_accepted, viewGroup, false);
                return new AcceptedEventViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final BenachrichtigungAdapter.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof BenachrichtigungAdapter.RequestViewHolder) {
            RequestViewHolder reqViewHolder = (RequestViewHolder) viewHolder;
            ReqViewHolderAction(reqViewHolder, (Users) usersList.get(i));
            acceptedList.set(i, true);
        } else if (viewHolder instanceof BenachrichtigungAdapter.AcceptedReqViewHolder) {
            AcceptedReqViewHolder acceptedReqViewHolder = (AcceptedReqViewHolder) viewHolder;
            AcceptedReqViewHolderAction(acceptedReqViewHolder, i);
        } else if (viewHolder instanceof BenachrichtigungAdapter.EventViewHolder) {
            EventViewHolder eventViewHolder = (EventViewHolder) viewHolder;
            EventViewHolderAction(eventViewHolder, (Event) usersList.get(i));
            acceptedList.set(i, true);
        } else if (viewHolder instanceof BenachrichtigungAdapter.AcceptedEventViewHolder) {
            AcceptedEventViewHolder acceptedEventViewHolder = (AcceptedEventViewHolder) viewHolder;
            AcceptedEventViewHolderAction(acceptedEventViewHolder, i);
        }



    }

    private void EventViewHolderAction(final EventViewHolder eventViewHolder, final Event event) {
        eventViewHolder.nametext.setText(event.getOrt());

        /*
        HIER WIRD DAS INTERESSEN IMAGE GELADEN
        if (event.getImage() == null) {
            reqViewHolder.image.setImageResource(R.drawable.ic_person);
        } else {
            Picasso.get().load(user.getImage()).into(reqViewHolder.image);
        }
         */

        final String event_id = event.eventid;

        eventViewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Toast.makeText(context, "User ID:"+user_id, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, com.mind.simplelogin.events.findevents.otherEvent.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                intent.putExtra("eventid", event_id);
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


        fStore.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {


                    String id = doc.getDocument().getId();


                    if(id.equals(usid)) {
                        final String username = doc.getDocument().toObject(Users.class).getBenutername();
                        System.out.println(username);

                        eventViewHolder.teilnehmen.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                fStore.collection("event").addSnapshotListener(new EventListener<QuerySnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                        List<String>teilnehmern=new ArrayList<>();

                                        for(DocumentChange doc1 : queryDocumentSnapshots.getDocumentChanges()) {
                                            if (doc1.getType() == DocumentChange.Type.ADDED) {
                                                teilnehmern = ((Event) doc1.getDocument().toObject(Event.class)).getTeilnehmer();
                                                if(!teilnehmern.contains(username)) {
                                                    teilnehmern.add(username);
                                                }
                                                DocumentReference doc = fStore.collection("event").document(event_id);
                                                doc.update("Teilnehmer", teilnehmern);
                                                fStore.collection("users").document(usid).collection("eventeinladung").document(event_id).delete();
                                            }
                                            break;
                                        }

                                    }
                                });

                            }
                        });



                    }
                }
            }
        });

        eventViewHolder.absagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fStore.collection("users").document(usid).collection("eventeinladung").document(event_id).delete();
                usersList.remove(event);
                notifyDataSetChanged();
            }
        });
    }

    private void ReqViewHolderAction(RequestViewHolder reqViewHolder, final Users user) {
        reqViewHolder.nametext.setText(user.getBenutername());

        if (user.getImage() == null) {
            reqViewHolder.image.setImageResource(R.drawable.ic_person);
        } else {
            Picasso.get().load(user.getImage()).into(reqViewHolder.image);
        }


        final String user_id = user.userId;

        reqViewHolder.mView.setOnClickListener(new View.OnClickListener() {
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


        //final int userPos = i;


        reqViewHolder.ablehnen.setOnClickListener(new View.OnClickListener() {
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
                usersList.remove(user);
                notifyDataSetChanged();
            }

        });

        reqViewHolder.annehmen.setOnClickListener(new View.OnClickListener() {
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
                notifyDataSetChanged();


            }

        });
    }

    private void AcceptedEventViewHolderAction(AcceptedEventViewHolder viewHolder, int i) {

    }


    private void AcceptedReqViewHolderAction(AcceptedReqViewHolder viewHolder, int i) {
        AcceptedReqViewHolder acceptedReqViewHolder = (AcceptedReqViewHolder) viewHolder;
        Users user = (Users) usersList.get(i);
        acceptedReqViewHolder.nametext.setText("Sie und "+user.getBenutername()+" sind jetzt befreundet!");
        if (user.getImage() == null) {
            acceptedReqViewHolder.image.setImageResource(R.drawable.ic_person);
        } else {
            Picasso.get().load(user.getImage()).into(acceptedReqViewHolder.image);
        }
        final String user_id = user.userId;
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

    private void checkListSizes() {
        for (int i=acceptedList.size(); i<usersList.size(); i++) {
            acceptedList.add(false);
        }
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

    public class EventViewHolder extends ViewHolder {

        public TextView nametext;
        public ImageView image;
        Button teilnehmen, absagen;



        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            nametext = (TextView)mView.findViewById(R.id.name);
            image = (ImageView)mView.findViewById(R.id.image);
            teilnehmen = (Button)mView.findViewById(R.id.teilnehmen);
            absagen = (Button)mView.findViewById(R.id.absagen);

        }

    }

    public class AcceptedEventViewHolder extends ViewHolder {

        public TextView nametext;
        public ImageView image;

        public AcceptedEventViewHolder(@NonNull View itemView) {
            super(itemView);

            nametext = (TextView)mView.findViewById(R.id.name);
            image = (ImageView)mView.findViewById(R.id.image);
        }
    }


}