package com.example.ajedrez;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.ajedrez.Logica.RecyclerViewItemOnClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class GameRoomActivity extends AppCompatActivity {

    private FirebaseController fbController;
    private RecyclerView recyclerView;
    private UserGameRoomAdapter mAdapter;
    private ArrayList<String> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_room);
        fbController = FirebaseController.INSTANCE;
        userList = new ArrayList<>();
        initRecyclerView();
        fbController.getUsersOnline(this, userList);
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new UserGameRoomAdapter(userList, new RecyclerViewItemOnClickListener() {
            @Override
            public void onClick(View v, final int position) {
                fbController.invitePlayer(GameRoomActivity.this, userList.get(position));
            }
        });
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
    }

    public void notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged();
    }

    public void iniciarPartida(boolean myTurn) {
        Intent intent = new Intent(this, OnlineGameActivity.class);
        intent.putExtra("itsMyTurn", myTurn);
        startActivity(intent);
    }
}
