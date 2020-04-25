package com.mind.simplelogin.Userliste;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.mind.simplelogin.R;
import com.mind.simplelogin.Freundesliste.yourFriends;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

public class findFriends extends AppCompatActivity {
    private RecyclerView friendlist;
    private FirebaseFirestore mFirestore;
    private List<Users> usersList ;
    private UsersListAdapter usersListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.
        findfriends);
        friendlist = findViewById(R.id.friendlist);
        mFirestore = FirebaseFirestore.getInstance();
        usersList = new ArrayList<>();
        usersListAdapter = new UsersListAdapter(getApplicationContext(), usersList);

        friendlist.setHasFixedSize(true);
        friendlist.setLayoutManager(new LinearLayoutManager((this)));
        friendlist.setAdapter(usersListAdapter);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Freunde finden");




        mFirestore.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null) {

                }
                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED){

                        final String user_id = doc.getDocument().getId();
                        Users users = doc.getDocument().toObject(Users.class).withId(user_id);
                        usersList.add(users);
                        Collections.sort(usersList, Users.myname);

                        usersListAdapter.notifyDataSetChanged();
                    }
                }
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem searchitem = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) searchitem.getActionView();
        final List<Users> allUsers = new ArrayList<>();
        allUsers.addAll(usersList);

        searchitem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                usersList.clear();
                usersList.addAll(allUsers);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {

                usersList.clear();
                usersList.addAll(allUsers);
                return true;
            }
        });

      //  searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                usersListAdapter.getFilter().filter(newText);
                return false;
            }

        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.item1){

            Intent intent = new Intent(findFriends.this, yourFriends.class);
            startActivity(intent);
            return true;
        }
        if(id == R.id.item2){

            Intent intent = new Intent(findFriends.this, findFriends.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

}
