package co.in.nextgencoder.database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import co.in.nextgencoder.Model.User;
import co.in.nextgencoder.Model.Validation;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailET;
    private EditText passET;
    private EditText nameET;
    private FirebaseAuth firebaseAuth;

    private FirebaseUser firebaseUser;

    private Validation validation = new Validation();

    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailET = findViewById(R.id.registerEmail);
        passET = findViewById(R.id.registerPassword);
        nameET = findViewById(R.id.registerName);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference();

    }

    public void submitRegisteredData(View view) {

        String email = emailET.getText().toString().trim();
        String password = passET.getText().toString().trim();
        String name = nameET.getText().toString().trim();

        String validationMsg = validation.validateUser(email, password);

        if (validationMsg.equals("successful")){

            firebaseAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if ((task.isSuccessful())){

                                DatabaseReference userReference = databaseReference.child("User");
                                String id = firebaseAuth.getUid();

                                User user = new User(name,email,id);
                                userReference.child(id).setValue(user);

                                Toast.makeText(RegisterActivity.this, "Registration "+validationMsg, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                            }

                            else{
                                Toast.makeText(RegisterActivity.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    });

        } else {
            Toast.makeText(RegisterActivity.this, validationMsg, Toast.LENGTH_SHORT).show();
        }

    }

    public void goToLogInActivity(View view) {
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}