package amir_alif.m.atif.worker.Login_Register;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import amir_alif.m.atif.worker.R;

public class Register extends AppCompatActivity {
    private EditText Nama, Email, Wa, Password, Re_password;
    private Button Register;
    private FirebaseAuth authUser;
    private DatabaseReference db;
    private ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        progress = new ProgressDialog(this);
        db = FirebaseDatabase.getInstance().getReference().child("User");
        authUser = FirebaseAuth.getInstance();
        Nama = (EditText)findViewById(R.id.nama_reg);
        Email = (EditText)findViewById(R.id.email_reg);
        Wa = (EditText)findViewById(R.id.wa_reg);
        Password = (EditText)findViewById(R.id.pass_reg);
        Re_password = (EditText)findViewById(R.id.retypepass_reg);
        Register = (Button)findViewById(R.id.btn_register);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });
    }

    public void Register(){
        final String Nama_ = Nama.getText().toString().trim();
        final String Email_ = Email.getText().toString().trim();
        final String Wa_ = Wa.getText().toString().trim();
        final String Pass = Password.getText().toString().trim();
        final String Re_Pass = Re_password.getText().toString().trim();
        char[] cekEmail = Email_.toCharArray();

        if(Nama_.matches("") || Email_.matches("") || Wa_.matches("") || Pass.matches("") || Re_Pass.matches("")){
            Toast.makeText(Register.this, "Fill the blank!", Toast.LENGTH_LONG).show();
        }else if(!cekEmail(cekEmail)){
            Toast.makeText(Register.this, "Wrong email format!", Toast.LENGTH_LONG).show();
        }else if(Pass.length()<6){
            Toast.makeText(Register.this, "Password length less then 6.", Toast.LENGTH_LONG).show();
        }else if(!Pass.equals(Re_Pass)){
            Toast.makeText(Register.this, "Password doesn't match!", Toast.LENGTH_LONG).show();
        } else{
            progress.setMessage("Registring...!");
            progress.show();
            authUser.createUserWithEmailAndPassword(Email_, Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        AlertDialog.Builder builder_ = new AlertDialog.Builder(Register.this);
                        String id_user = authUser.getCurrentUser().getUid();
                        DatabaseReference idRef =  db.child(id_user);
                        idRef.child("Nama").setValue(Nama_);
                        idRef.child("Email").setValue(Email_);
                        idRef.child("Wa").setValue(Wa_);
                        progress.dismiss();
                    }else {
                        progress.dismiss();
                        Toast.makeText(Register.this, "Register failed!", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

    public Boolean cekEmail(char[] mail_char){
        int isEmail=0;
        Boolean isEmailBisa=false;
        int position=0;
        for(int i=0;i<mail_char.length;i++){
            if(mail_char[i]=='@'){
                isEmail++;
                position=i;
            }
            if(mail_char[i]=='!' || mail_char[i]=='#' || mail_char[i]=='$' || mail_char[i]=='%' || mail_char[i]=='^' ||
                    mail_char[i]=='&' || mail_char[i]=='*' || mail_char[i]=='(' || mail_char[i]==')' || mail_char[i]=='-' || mail_char[i]=='=' ||
                    mail_char[i]=='+' || mail_char[i]=='[' || mail_char[i]==']' || mail_char[i]=='{' || mail_char[i]=='}' || mail_char[i]==':' || mail_char[i]==';' || mail_char[i]=='/' ||
                    mail_char[i]=='?' || mail_char[i]=='>' || mail_char[i]=='<' || mail_char[i]==',' || mail_char[i]=='`' || mail_char[i]=='~'){
                isEmail=0;
                break;
            }
        }

        if(isEmail==1 && (mail_char.length-position-1)>4 && mail_char[position+1]!='.'){
            int pointChar=0;
            int[] titikPosisi = new int[mail_char.length-position-1];
            for(int i=position+1; i<mail_char.length;i++){
                if(mail_char[i]=='.'){
                    titikPosisi[pointChar]=i;
                    pointChar++;
                }
            }
            if(pointChar>0 && pointChar<3 && (titikPosisi[1]-titikPosisi[0]!=1) && titikPosisi[1]!=(mail_char.length-1)){
                isEmailBisa=true;
            }
        }
        return isEmailBisa;
    }
}
