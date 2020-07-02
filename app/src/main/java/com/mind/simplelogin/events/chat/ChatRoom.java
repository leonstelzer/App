package com.mind.simplelogin.events.chat;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.mind.simplelogin.R;
import com.mind.simplelogin.Userliste.Users;
import com.mind.simplelogin.events.Freundeeinladen.Event;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import static android.view.View.VISIBLE;

public class ChatRoom extends AppCompatActivity {

    private BaseAdapter adapter;
    private FloatingActionButton fab;
    private EditText input;
    private List<ChatMessage> chatMessageList;
    private ListView listOfMessages;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        input = (EditText)findViewById(R.id.input);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Chat");
        System.out.println("IM ON CREATE");
        chatMessageList = new ArrayList<>();

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        final String eventid = getIntent().getStringExtra("eventid");
        final String username = getIntent().getStringExtra("username");


        final DocumentReference documentReference1 = fStore.collection("event").document(eventid);
        reload(documentReference1);
        adapter = new ChatListAdapter(this.getApplicationContext(), chatMessageList);
        listOfMessages = (ListView)findViewById(R.id.list_of_messages);
        listOfMessages.setAdapter(adapter);


        fab.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       String text = input.getText().toString();
                                       ChatMessage cm = new ChatMessage(text, fAuth.getCurrentUser().getUid());
                                       chatMessageList.add(cm);

                                       List<Object> list=new ArrayList<>();
                                       for (ChatMessage c: chatMessageList) {
                                           //List<Object> message=new ArrayList<>();
                                           list.add(c.getMessageUser());
                                           list.add(c.getMessageTime());
                                           list.add(c.getMessageText());
                                           //list.add(message);
                                       }

                                       documentReference1.update("Chat", list);
                                       input.setText("");
                                       reload(documentReference1);
                                   }});
    }

    public void reload(DocumentReference doc) {
        chatMessageList = new ArrayList<>();
        doc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                List<Object> out = (List) documentSnapshot.get("Chat");
                for(int i=0; i<out.size(); i=i+3) {
                    String text = (String) out.get(i+2);
                    String name = (String) out.get(i+0);
                    long time = (long) out.get(i+1);
                    chatMessageList.add(new ChatMessage(text, name, time));
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void displayChatMessages() {
        listOfMessages = (ListView)findViewById(R.id.list_of_messages);
        listOfMessages.setAdapter(adapter);
    }


}
