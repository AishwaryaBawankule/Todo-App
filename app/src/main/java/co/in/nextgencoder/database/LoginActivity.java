package co.in.nextgencoder.database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import co.in.nextgencoder.Model.Validation;

public class LoginActivity extends AppCompatActivity {

    private EditText mailLoginET,passLoginET;

    private final Validation validation = new Validation();

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mailLoginET = findViewById((R.id.loginEmail));
        passLoginET = findViewById(R.id.loginPassword);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

//        if(firebaseUser != null){
//            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//        }
    }

    public void submitLoginData(View view) {

        String email = mailLoginET.getText().toString().trim();
        String password = passLoginET.getText().toString().trim();

        String validationMsg = validation.validateUser(email, password);

        if (validationMsg.equals("successful")){

            firebaseAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(task -> {
                        if ((task.isSuccessful())){

                            Toast.makeText(LoginActivity.this, "Login "+validationMsg, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        }
                        else{
                            Toast.makeText(this, ""+ Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show());

        } else {
            Toast.makeText(LoginActivity.this, validationMsg, Toast.LENGTH_SHORT).show();
        }


    }

    public void goToRegisterationActivity(View view) {
        Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}