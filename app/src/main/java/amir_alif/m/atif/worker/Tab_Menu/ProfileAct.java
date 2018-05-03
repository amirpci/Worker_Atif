package amir_alif.m.atif.worker.Tab_Menu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Map;

import amir_alif.m.atif.worker.Login_Register.Login;
import amir_alif.m.atif.worker.R;

/**
 * Created by USER on 02/05/2018.
 */

public class ProfileAct extends Fragment {
    private DatabaseReference dataRef;
    private TextView nama, bidang, status, terjawab, rating, penilai;
    private String idUser;
    private ImageView signout;
    private ProgressDialog loading;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        dataRef = FirebaseDatabase.getInstance().getReference("User");
        loading = new ProgressDialog(getActivity());
        nama = (TextView)v.findViewById(R.id.nama_profile);
        bidang = (TextView)v.findViewById(R.id.bidang_profile);
        status = (TextView)v.findViewById(R.id.status_profile);
        terjawab = (TextView)v.findViewById(R.id.answered_profile);
        rating = (TextView)v.findViewById(R.id.rating_profile);
        penilai = (TextView)v.findViewById(R.id.rater_profile);
        signout = (ImageView)v.findViewById(R.id.signOut);
        idUser = FirebaseAuth.getInstance().getUid();
        loading.setMessage("loading...");
        loading.show();
        loadingData();
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        return v;
    }

    void loadingData(){
        dataRef.child(idUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                String nama_ = (String) map.get("Nama");
                String status_ = (String) map.get("Type");
                nama.setText(nama_);
                status.setText(status_);
                String bidang_ = (String) map.get("Specialization");
                if (bidang_.equals("-")) {
                    bidang.setVisibility(View.GONE);
                }else{
                    bidang.setText(bidang_);
                }
                loading.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}

        });

        dataRef.child(idUser).child("Score").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                String rating_ = String.valueOf(map.get("Rating"));
                String rater_ = String.valueOf(map.get("Rater"));
                String terjawab_ = String.valueOf(map.get("Answered"));

                penilai.setText(rater_);
                rating.setText(rating_);
                terjawab.setText(terjawab_);
                loading.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
