package amir_alif.m.atif.worker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;

import amir_alif.m.atif.worker.Login_Register.Login;

public class MainActivity extends AppCompatActivity {
    private Button logout;
    private TextView helloworld;
    private String accountType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logout = (Button)findViewById(R.id.logout_btn);
        helloworld = (TextView)findViewById(R.id.helloworld);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });
        String id_user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference refDatabase = FirebaseDatabase.getInstance().getReference();

        refDatabase.child("User").child(id_user).child("Type").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Toast.makeText(getApplicationContext(), (String)snapshot.getValue(), Toast.LENGTH_LONG).show();
                accountType=(String)snapshot.getValue();
                /*
                helloworld.setText();
                if(!type.equals("Expert")){
                    FirebaseAuth.getInstance().signOut();
                    AlertDialog.Builder builder_ = new AlertDialog.Builder(MainActivity.this);
                    builder_.setMessage("You're not registered as Expert")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog alert = builder_.create();
                    alert.setTitle("We're Sorry !");
                    alert.show();
                } */
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Something wrong happens", Toast.LENGTH_LONG).show();
            }
        });
    }
    public void addProblem(View v){
        startActivity(new Intent(MainActivity.this, Share_knowledge.class));
    }
}
