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
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.protobuf.Any;

import java.util.HashMap;
import java.util.Map;

import static com.google.common.net.HttpHeaders.AGE;

public class MainActivity extends AppCompatActivity {

    private static final String USER_COLLECTION = "users";
    private static final String PERSION1 = "person1";
    private static final String KEY_NAME = "name";
    private static final String KEY_AGE = "age";
    EditText mName, mAge;
    Button mSave, mRead;
    FirebaseFirestore mFireStore;
    TextView mOutput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        mFireStore = FirebaseFirestore.getInstance();

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

    }

    private void saveData() {

        String name = mName.getText().toString().trim();
        String age = mAge.getText().toString();

        HashMap<String , String> data = new HashMap<>();
        data.put(KEY_NAME, name);
        data.put(KEY_AGE, age);

        mFireStore.collection(USER_COLLECTION).document().set(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Data is Saved", Toast.LENGTH_SHORT).show();

                            mOutput.setText("Data is Saved");
                        }
                        else
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