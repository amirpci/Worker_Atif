package amir_alif.m.atif.worker.Login_Register;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import amir_alif.m.atif.worker.MainActivity;
import amir_alif.m.atif.worker.R;

public class Login extends AppCompatActivity {
    private EditText Email,Password;
    private TextView Register;
    private Button Login;
    private ProgressDialog progress;
    private FirebaseAuth authUser;
    private FirebaseAuth.AuthStateListener authListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progress = new ProgressDialog(this);
        authUser = FirebaseAuth.getInstance();
        Email=(EditText)findViewById(R.id.email_login);
        Password=(EditText)findViewById(R.id.pass_login);
        Login=(Button)findViewById(R.id.btn_login);
        Register=(TextView) findViewById(R.id.register_login);
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                    progress.dismiss();
                    startActivity(new Intent(Login.this, MainActivity.class));
                }
            }

        };

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }

        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Email.getText().toString().matches("") || Password.getText().toString().matches("")){
                    Toast.makeText(Login.this, "Mohon isi dengan benar!", Toast.LENGTH_LONG).show();
                }else {
                    String Email_=Email.getText().toString();
                    String Pass_=Password.getText().toString();
                    progress.setMessage("Sedang masuk...!");
                    progress.show();
                    authUser.signInWithEmailAndPassword(Email_,Pass_).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                progress.dismiss();
                                AlertDialog.Builder builder_ = new AlertDialog.Builder(Login.this);
                                builder_.setMessage("Anda belum berhasil login.")
                                        .setCancelable(false)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.cancel();
                                            }
                                        });
                                AlertDialog alert = builder_.create();
                                alert.setTitle("Mohon maaf !");
                                alert.show();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        authUser.addAuthStateListener(authListener);
    }
}
