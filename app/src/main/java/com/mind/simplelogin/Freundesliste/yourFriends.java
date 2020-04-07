package com.mind.simplelogin.Freundesliste;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.mind.simplelogin.Userliste.Users;
import com.mind.simplelogin.R;
import com.mind.simplelogin.Userliste.UsersListAdapter;
import com.mind.simplelogin.Userliste.findFriends;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;


public class yourFriends extends AppCompatActivity {

    private RecyclerView friendlist;
    private FirebaseFirestore mFirestore;
    private List<Users> usersList ;
    private UsersListAdapter usersListAdapter;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findfriends);
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();


        friendlist = findViewById(R.id.friendlist);
        mFirestore = FirebaseFirestore.getInstance();
        usersList = new ArrayList<>();
        usersListAdapter = new UsersListAdapter(getApplicationContext(), usersList);
        String usid = fAuth.getCurrentUser().getUid();


        friendlist.setHasFixedSize(true);
        friendlist.setLayoutManager(new LinearLayoutManager((this)));
        friendlist.setAdapter(usersListAdapter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Meine Freunde");

        mFirestore.collection("users").document(usid).collection("friends").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null) {

                }
                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED){

                        final String user_id = doc.getDocument().getId();
                        //Toast.makeText(yourFriends.this, fAuth.getUid(), Toast.LENGTH_SHORT).show();


                        mFirestore.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                                if (e != null) {

                                }
                                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                                    if (doc.getType() == DocumentChange.Type.ADDED){

                                        String id = doc.getDocument().getId();
                                        if(user_id.equals(id)) {
                                            Users users = doc.getDocument().toObject(Users.class).withId(user_id);
                                            usersList.add(users);
                                            usersListAdapter.notifyDataSetChanged();
                                        }
                                    }
                                }
                            }
                        });
                        //if (fAuth.getUid().equals(user_id)){

                            //Map map = doc.getDocument().getData();
                            //System.out.println(map.size());
                            //Toast.makeText(yourFriends.this, map.size(), Toast.LENGTH_SHORT).show();


                            //for(Object friendid : map.values()){

                                //friendid.toString();


                                //Users users = doc.getDocument().toObject(Users.class).withId(user_id);
                                //Toast.makeText(yourFriends.this, (String)friendid, Toast.LENGTH_SHORT).show();
                                //usersList.add(users);
                           // }
                        //}
                        //usersListAdapter.notifyDataSetChanged();

                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.item1){

            Intent intent = new Intent(yourFriends.this, yourFriends.class);
            startActivity(intent);

            return true;
        }
        if(id == R.id.item2){

            Intent intent = new Intent(yourFriends.this, findFriends.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);

    }
}