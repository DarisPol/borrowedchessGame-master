package com.example.ajedrez;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MenuActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    private FirebaseUser user;
    private DatabaseReference mDatabase;
    private String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("uid-username");
        initializeUserName();
    }

    public void initializeUserName() {
        if (user == null) {
            userName = null;
            mostrarPantalla();
        }else {

            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    userName = Objects.requireNonNull(dataSnapshot.child(user.getUid()).getValue()).toString();
                    mostrarPantalla();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    public void nuevaPartidaOffline(View v) {
        Intent intent = new Intent(this, OfflineGameActivity.class);
        startActivity(intent);
    }

    public void cerrarSesion(View v) {
        mDatabase.child(user.getUid()).removeValue();
        FirebaseAuth.getInstance().signOut();
        user = null;
        userName = null;
        mostrarPantalla();
    }


    public void nuevaPartidaOnline(View v) {
        if (user == null) {
            AlertDialog.Builder alert = new AlertDialog.Builder(MenuActivity.this);
            alert.setTitle("Not Logged in");
            alert.setMessage("You must log in to play online");
            alert.setPositiveButton("OK", (dialog, which) -> createSignInIntent());
            alert.setNegativeButton("Cancel", null);
            alert.show();
        }else{
            Intent intent = new Intent(this, GameRoomActivity.class);
            startActivity(intent);
        }
    }

    public void createSignInIntent() {
        // [START auth_fui_create_intent]
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build());

        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
        // [END auth_fui_create_intent]
    }

    // [START auth_fui_result]
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                user = FirebaseAuth.getInstance().getCurrentUser();
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!snapshot.hasChild(user.getUid())) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
                            final EditText input = new EditText(MenuActivity.this);
                            input.setInputType(InputType.TYPE_CLASS_TEXT);
                            builder.setView(input);
                            builder.setTitle("Ingresa un nombre de usuario");
                            builder.setPositiveButton("OK", (dialog, which) -> {
                                userName = input.getText().toString();
                                mDatabase.child(user.getUid()).setValue(user.getUid());
                                mostrarPantalla();
                            });
                            builder.show();
                        }else {
                            userName = Objects.requireNonNull(snapshot.child(user.getUid()).getValue()).toString();
                            mostrarPantalla();
                        }


                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



                // ...
            }  // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...

        }
    }

    @SuppressLint("SetTextI18n")
    public void setUserNameDisplay() {
        TextView userDisplay = findViewById(R.id.user_name);
        if (user == null) {
            userDisplay.setText("");
            userDisplay.setVisibility(View.GONE);
        }else{
            userDisplay.setText("Hello, \n" + userName + "!");
            userDisplay.setVisibility(View.VISIBLE);
        }
    }

    public void mostrarPantalla() {
        setUserNameDisplay();
        Button logOut = findViewById(R.id.logOut);
        if (user == null)
            logOut.setVisibility(View.GONE);
        else
            logOut.setVisibility(View.VISIBLE);
    }

    public void Exit(View view) {
    }
}
