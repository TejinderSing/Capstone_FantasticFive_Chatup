package com.example.chatupgmail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatupgmail.Adapter.ChatAdapter;
import com.example.chatupgmail.Models.Messages;
import com.example.chatupgmail.databinding.ActivityChattingBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Chatting extends AppCompatActivity {

    EditText typingspace;
    ImageButton send_msg_btn;

    androidx.appcompat.widget.Toolbar toolbar;
    ImageView imageviewofspecificuser;
    TextView nameofselecteduser;

    private String enteredmessage;
    Intent intent;
    String recievername,sendername,recieveruid,senderuid;
    private FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    String senderroom,recieverroom;

    ImageButton backbuttontochating;

    RecyclerView messagerecyclerview;

    String currenttime;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;

    ChatAdapter chatAdapter;
    ArrayList<Messages> messagesArrayList;

    ActivityChattingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChattingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        typingspace=findViewById(R.id.getmessage);
        send_msg_btn=findViewById(R.id.imageviewsendmessage);
        toolbar=findViewById(R.id.toolbarofspecificchat);
        nameofselecteduser=findViewById(R.id.Nameofspecificuser);
        imageviewofspecificuser=findViewById(R.id.specificuserimageinimageview);
        backbuttontochating=findViewById(R.id.backbuttonofspecificchat);

        messagesArrayList=new ArrayList<>();

        //messagerecyclerview = findViewById(R.id.recyclerviewofspecific);


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        //messagerecyclerview.setLayoutManager(linearLayoutManager);
        chatAdapter=new ChatAdapter(Chatting.this,messagesArrayList);
        binding.recyclerviewofchatting.setLayoutManager(linearLayoutManager);
        binding.recyclerviewofchatting.setAdapter(chatAdapter);
        //messagerecyclerview.setAdapter(chatAdapter);




        intent=getIntent();

        //setSupportActionBar(toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Toolbar is Clicked",Toast.LENGTH_SHORT).show();


            }
        });

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        calendar=Calendar.getInstance();
        simpleDateFormat=new SimpleDateFormat("hh:mm a");


        senderuid=firebaseAuth.getUid();
        recieveruid=getIntent().getStringExtra("receiveruid");
        recievername=getIntent().getStringExtra("name");



        senderroom=senderuid+recieveruid;
        recieverroom=recieveruid+senderuid;



//        DatabaseReference databaseReference=firebaseDatabase.getReference().child("chats").child(senderroom).child("messages");
//        chatAdapter=new ChatAdapter(Chatting.this,messagesArrayList);
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                messagesArrayList.clear();
//                for(DataSnapshot snapshot1:snapshot.getChildren())
//                {
//                    Messages messages=snapshot1.getValue(Messages.class);
//                    messagesArrayList.add(messages);
//                }
//                chatAdapter.notifyDataSetChanged();
//            }
//        @Override
//        public void onCancelled(@NonNull DatabaseError error) {
//
//        }
//    });
        chatAdapter=new ChatAdapter(Chatting.this,messagesArrayList);
        firebaseDatabase.getReference().child("chats").child(senderroom).child("messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                messagesArrayList.clear();
                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    Messages messages=snapshot1.getValue(Messages.class);
                    messagesArrayList.add(messages);
                }
                chatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });






        backbuttontochating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        nameofselecteduser.setText(recievername);
        String uri=intent.getStringExtra("imageuri");
        if(uri.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"null is recieved",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Picasso.get().load(uri).into(imageviewofspecificuser);
        }


        send_msg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                enteredmessage=typingspace.getText().toString();
                if(enteredmessage.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter message first",Toast.LENGTH_SHORT).show();
                }

                else

                {
                    Date date=new Date();
                    currenttime=simpleDateFormat.format(calendar.getTime());
                    Messages messages=new Messages(enteredmessage,firebaseAuth.getUid(),date.getTime(),currenttime);
                    firebaseDatabase=FirebaseDatabase.getInstance();
                    firebaseDatabase.getReference().child("chats")
                            .child(senderroom)
                            .child("messages")
                            .push().setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            chatAdapter.notifyDataSetChanged();
                            firebaseDatabase.getReference()
                                    .child("chats")
                                    .child(recieverroom)
                                    .child("messages")
                                    .push()
                                    .setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                }
                            });
                        }
                    });

                    typingspace.setText("");




                }




            }
        });




    }


    @Override
    public void onStart() {
        super.onStart();
        System.out.println("on strt");
        chatAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStop() {
        super.onStop();
        System.out.println("on stop");
        if(chatAdapter!=null)
        {
            chatAdapter.notifyDataSetChanged();
        }
    }



}