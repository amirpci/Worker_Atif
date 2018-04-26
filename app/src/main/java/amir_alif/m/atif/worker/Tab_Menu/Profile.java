package amir_alif.m.atif.worker.Tab_Menu;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import amir_alif.m.atif.worker.Login_Register.Login;
import amir_alif.m.atif.worker.R;


public class Profile extends Fragment {
    private Button logout;
    private TextView helloworld;
    private String accountType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        logout = (Button)v.findViewById(R.id.logout_btn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

        String id_user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference refDatabase = FirebaseDatabase.getInstance().getReference();

        refDatabase.child("User").child(id_user).child("Type").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Toast.makeText(getActivity(), (String)snapshot.getValue(), Toast.LENGTH_LONG).show();
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
              //  Toast.makeText(getActivity(), "Something wrong happens", Toast.LENGTH_LONG).show();
            }
        });
        return v;
    }

}
