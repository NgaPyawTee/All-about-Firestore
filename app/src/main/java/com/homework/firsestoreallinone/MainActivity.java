package com.homework.firsestoreallinone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText edtTitle, edtDescription, edtPriority;
    private TextView tvOne;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collRef = db.collection("Notebook 2");
    private DocumentSnapshot lastResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtTitle = findViewById(R.id.edt_title);
        edtDescription = findViewById(R.id.edt_description);
        edtPriority = findViewById(R.id.edt_priority);
        tvOne = findViewById(R.id.tv_one);
    }

    public void OnSave(View view) {
        String title = edtTitle.getText().toString();
        String description = edtDescription.getText().toString();

        if (edtPriority.length() == 0) {
            edtPriority.setText("0");
        }
        int priority = Integer.parseInt(edtPriority.getText().toString());

        Note note = new Note(title, description, priority);

        collRef.add(note);

        Toast.makeText(this, "Added successfully", Toast.LENGTH_SHORT).show();
    }

    public void OnLoad(View view) {
        Query query;
        if (lastResult == null) {
            query = collRef.orderBy("priority").limit(3);
        } else {
            query = collRef.orderBy("priority").startAfter(lastResult).limit(3);
        }

        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot querySnapshot) {
                String data = "";

                for (QueryDocumentSnapshot queryDocumentSnapshot : querySnapshot) {
                    Note note = queryDocumentSnapshot.toObject(Note.class);
                    note.setID(queryDocumentSnapshot.getId());

                    String title = note.getTitle();
                    String description = note.getDescription();
                    String id = note.getID();
                    int priority = note.getPriority();

                    data += "ID: " + id + "\nTitle: " + title + "\nDescription: " + description + "Priority: " + priority + "\n\n";
                }
                if (querySnapshot.size() > 0) {
                    data += "_______________\n\n";
                    tvOne.append(data);

                    lastResult = querySnapshot.getDocuments().get(querySnapshot.size() - 1);
                }else
                    Toast.makeText(MainActivity.this, "No more data", Toast.LENGTH_SHORT).show();
            }
        });



       /* Task task1 = collRef.whereGreaterThan("priority", 3).orderBy("priority", Query.Direction.DESCENDING).get();

        Task task2 = collRef.whereLessThan("priority", 3).orderBy("priority", Query.Direction.DESCENDING).get();

        Task<List<QuerySnapshot>> allTask = Tasks.whenAllSuccess(task1, task2);
        allTask.addOnSuccessListener(new OnSuccessListener<List<QuerySnapshot>>() {
            @Override
            public void onSuccess(List<QuerySnapshot> querySnapshots) {
                String data = "";

                for (QuerySnapshot querySnapshot : querySnapshots) {
                    for (QueryDocumentSnapshot documentSnapshots : querySnapshot) {
                        Note note = documentSnapshots.toObject(Note.class);
                        note.setID(documentSnapshots.getId());

                        String title = note.getTitle();
                        String description = note.getDescription();
                        int priority = note.getPriority();
                        String ID = note.getID();

                        data += "ID: " + ID + "\nTitle: " + title + "\nDescription: " + description + "\nPriority: " + priority + "\n\n";
                        tvOne.setText(data);
                    }
                }
            }
        });*/
    }

    /*
    @Override
    protected void onStart() {
        super.onStart();
        collRef.orderBy("priority", Query.Direction.ASCENDING)
                .addSnapshotListener(this, new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            return;
                        }
                        String data = "";

                        for (QueryDocumentSnapshot documentSnapshots : value) {
                            Note note = documentSnapshots.toObject(Note.class);
                            note.setID(documentSnapshots.getId());

                            String title = note.getTitle();
                            String description = note.getDescription();
                            int priority = note.getPriority();
                            String ID = note.getID();

                            data += "ID: " + ID + "\nTitle: " + title + "\nDescription: " + description + "\nPriority: " + priority + "\n\n";
                            tvOne.setText(data);
                        }
                    }
                });
    }*/

    /*
    public void OnUpdateDesc(View view) {
        String description = edtDescription.getText().toString();

        // Map<String, Object> note = new HashMap<>();
        // note.put(KEY_DESCRIPTION, description);
        //docRef.set(note, SetOptions.merge());

        docRef.update(KEY_DESCRIPTION, description);
    }

    public void OnDeleteDesc(View view) {
        docRef.update(KEY_DESCRIPTION, FieldValue.delete());
        Toast.makeText(MainActivity.this, "deleted description", Toast.LENGTH_SHORT).show();
    }

    public void OnDeleteDoc(View view) {
        docRef.delete();
        Toast.makeText(this, "deleted document", Toast.LENGTH_SHORT).show();
    }
   */
}