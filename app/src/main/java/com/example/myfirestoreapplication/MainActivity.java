package com.example.myfirestoreapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String USER_COLLECTION = "users";
    private static final String PERSION1 = "person1";
    private static final String KEY_NAME = "name";
    private static final String KEY_AGE = "age";
    EditText mName, mAge;
    Button mSave, mRead;
    FirebaseFirestore mFireStore;
    private DocumentReference documentReference;
    TextView mOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        mFireStore = FirebaseFirestore.getInstance();
        documentReference = mFireStore.collection(USER_COLLECTION).document("person1");

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
        mRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readData();
            }
        });
    }

    private void readData() {

        documentReference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String name = documentSnapshot.getString(KEY_NAME);
                            String age = documentSnapshot.getString(KEY_AGE);

                            mOutput.setText(name+"\n"+age);
                        }
                    }
                });
    }

    private void saveData() {

        String name = mName.getText().toString().trim();
        String age = mAge.getText().toString();

       Map<String, String > data = new HashMap<>();
        data.put(KEY_NAME, name);
        data.put(KEY_AGE, age);

        documentReference.set(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Data is Saved", Toast.LENGTH_SHORT).show();

                            mOutput.setText("Data is Saved");
                        } else
                            Toast.makeText(MainActivity.this, "Some Error occure", Toast.LENGTH_LONG).show();
                    }
                });

    }

    private void initView() {
        mName = findViewById(R.id.et_name);
        mAge = findViewById(R.id.et_age);
        mRead = findViewById(R.id.btn_read);
        mSave = findViewById(R.id.btn_save);
        mOutput = findViewById(R.id.tv_output);
    }
}