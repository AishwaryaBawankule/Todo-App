package co.in.nextgencoder.database;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import co.in.nextgencoder.Model.Todo;
import co.in.nextgencoder.Model.User;
import co.in.nextgencoder.Model.Validation;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private EditText taskET;
    private FirebaseAuth firebaseAuth;
    private final Validation validation = new Validation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskET = findViewById(R.id.task);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();


    }
    public void addDataToTodo(View view) {

        String task = taskET.getText().toString().trim();
        DatabaseReference toDoReference = databaseReference.child("User").child(firebaseAuth.getUid()).child("Task");
        String id = toDoReference.push().getKey();

        Todo todo = new Todo(id,task,false);

        toDoReference.child(id).setValue(todo);

    }
}