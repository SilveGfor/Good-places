package com.example.utemp.goodplaces;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuestsViewActivity extends AppCompatActivity {
    public ListView lv;
    public ArrayAdapter<String> adapter;
    public ArrayList<String> myList;
    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quests_view);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Quests");
        final SharedPreferences prefs =
                getSharedPreferences("com.example.myapp.PREFERENCE",
                        Context.MODE_PRIVATE);

        lv=findViewById(R.id.LV);
        myList=new ArrayList<String> ();
        MapActivity2.quest_name = "";

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //

                // User user = dataSnapshot.getValue(User.class);

                for(DataSnapshot
                        snapshot:dataSnapshot.getChildren())//получить все записи
                {
                    String
                            name=snapshot.getKey().toString();//получить все ключи
                    String
                            mess=snapshot.getValue().toString();//получить все значения
                    myList.add(name);
                }
                adapter.notifyDataSetChanged();//ОБновление
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapter=new ArrayAdapter(QuestsViewActivity.this,android.R.layout.simple_list_item_1,myList);
        lv.setAdapter(adapter);



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View itemClicked, int position, long id)
            {
                Toast.makeText(QuestsViewActivity.this, "Я работаю" , Toast.LENGTH_SHORT).show();
                MapActivity2.quest_name = adapter.getItem((int) id);
                
                Intent i = new Intent(QuestsViewActivity.this, MapActivity2.class);
                startActivity(i);
            }
        });
    }
}
