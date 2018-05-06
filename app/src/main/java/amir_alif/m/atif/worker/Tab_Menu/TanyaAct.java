package amir_alif.m.atif.worker.Tab_Menu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import amir_alif.m.atif.worker.R;

/**
 * Created by USER on 02/05/2018.
 */

public class TanyaAct extends Fragment {
    private LinearLayout relatedQuestion, addQuestion;
    private EditText search_input,question_input;
    private ImageView search_btn, uploadPhoto, uploadedPhoto;
    private Button add_question;
    private ProgressBar uploadPhotoProgress, loading;
    private DatabaseReference dataRef;
    //private String cari_input;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tanya, container, false);
        dataRef = FirebaseDatabase.getInstance().getReference();
        relatedQuestion = (LinearLayout)v.findViewById(R.id.tanya_related_q);
        addQuestion = (LinearLayout)v.findViewById(R.id.tanya_add_question);
        search_input = (EditText)v.findViewById(R.id.tanya_search);
        question_input = (EditText)v.findViewById(R.id.tanya_desc);
        search_btn = (ImageView)v.findViewById(R.id.tanya_cari_icon);
        uploadPhoto = (ImageView)v.findViewById(R.id.tanya_add_photo);
        uploadedPhoto = (ImageView)v.findViewById(R.id.tanya_uploaded_photo);
        uploadPhotoProgress = (ProgressBar) v.findViewById(R.id.tanya_upPhoto_loading);
        loading = (ProgressBar)v.findViewById(R.id.tanya_progress);
        relatedQuestion.setVisibility(View.GONE);
        addQuestion.setVisibility(View.GONE);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.animate().alpha(0.0f).setDuration(500).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        loading.setVisibility(View.GONE);
                    }
                });
                searchQuestion(search_input.getText().toString());
            }
        });
        return v;
    }
    //cuma method percobaan
    void searchQuestion(final String input){
        dataRef.child("search").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(input.equals(dataSnapshot.getValue(String.class))){
                    transisiFadeIn(relatedQuestion);
                    transisiFadeOut(addQuestion);
                }else{
                    transisiFadeIn(addQuestion);
                    transisiFadeOut(relatedQuestion);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    void transisiFadeOut(final View v){
        v.animate()
                .translationY(v.getHeight())
                .alpha(0.0f)
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        v.clearAnimation();
                        v.setVisibility(View.GONE);
                    }
                });
    }

    void transisiFadeIn(final View v){
        v.animate()
                .translationY(0)
                .alpha(1.0f)
                .setDuration(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        v.setVisibility(View.VISIBLE);
                    }
                });
    }
}

