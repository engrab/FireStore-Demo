package com.example.myfirestoreapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.Source;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String USER_COLLECTION = "test";
    private static final String PERSION1 = "person1";
    private static final String KEY_NAME = "name";
    private static final String KEY_AGE = "age";
    private static final String TAG = MainActivity.class.getSimpleName();
    EditText mName, mAge;
    Button mSave, mRead, mUpdate, mDeleteName, mDeletePerson;
    FirebaseFirestore mFireStore;
    private DocumentReference mDocumentReference;
    TextView mOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        mFireStore = FirebaseFirestore.getInstance();
        mDocumentReference = mFireStore.collection(USER_COLLECTION).document(PERSION1);

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
        mRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                readData();
            }
        });
        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });

        mDeleteName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteName();
            }
        });

        mDeletePerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePerson();
            }
        });
    }

    private void deletePerson() {
        // delete the whole collection
        mDocumentReference.delete();
    }

    private void deleteName() {
        // delete only field of that docment
        mDocumentReference.update(KEY_NAME, FieldValue.delete());
    }

    private void updateData() {
        String name =  mName.getText().toString();
        int age = Integer.parseInt(mAge.getText().toString());

        User user = new User(name, age);
        Map<String, Object> data = new HashMap<>();
        data.put(KEY_NAME, user.getName());
//        data.put(KEY_AGE, user.getAge());

        // total replace the document...if does not exsist data write it
//        mDocumentReference.set(data);

        // only merge that data
//        mDocumentReference.set(data, SetOptions.merge());

        // only update if does not exist data ... can't show any thing
        mDocumentReference.update(data);
    }

//    private void readData() {
//
//        mDocumentReference.get(Source.DEFAULT)
//
//                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        if (documentSnapshot.exists()){
//                            String name = documentSnapshot.getString(KEY_NAME);
//                            String age = documentSnapshot.getString(KEY_AGE);
//
//
//                            mOutput.setText(name+"\n"+age);
//                        }
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                       mOutput.setText(e.getLocalizedMessage());
//                    }
//                });
//    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        mDocumentReference.addSnapshotListener(this, MetadataChanges.INCLUDE, new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
//               if (e != null){
//                   Toast.makeText(MainActivity.this, "Some error occue", Toast.LENGTH_LONG).show();
//                   Log.d(TAG, "onEvent: Error");
//                   return;
//               }
//               else {
//                   if (documentSnapshot != null &&documentSnapshot.exists()){
//                       String name =documentSnapshot.getString(KEY_NAME);
//                       String age = documentSnapshot.getString(KEY_AGE);
//
//                       mOutput.setText(name+ "\n"+age);
//                       if (documentSnapshot.getMetadata().hasPendingWrites()){
//                           mOutput.append("Server");
//                       }else {
//                           mOutput.append("Local");
//                       }
//                   }
//               }
//            }
//        });
//    }

    private void saveData() {

        String name = mName.getText().toString().trim();
        int age = Integer.parseInt(mAge.getText().toString());

        User user = new User(name, age);
       Map<String, Object > data = new HashMap<>();
        data.put(KEY_NAME, user.getName());
        data.put(KEY_AGE, user.getAge());

        mDocumentReference.set(data)
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
        mUpdate = findViewById(R.id.btn_update_data);
        mDeleteName = findViewById(R.id.btn_delete_name);
        mDeletePerson = findViewById(R.id.btn_delete_person);
    }
}