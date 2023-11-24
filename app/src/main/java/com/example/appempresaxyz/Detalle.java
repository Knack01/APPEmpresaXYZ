package com.example.appempresaxyz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Detalle extends AppCompatActivity {
    TextView descDetalle, nombreDetalle, precioDetalle;
    ImageView imageDetalle;
    FloatingActionButton editButton, deleteButton;
    String key ="";
    String imageURL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        descDetalle = findViewById(R.id.descDetalle);
        nombreDetalle = findViewById(R.id.nombreDetalle);
        precioDetalle = findViewById(R.id.precioDetelle);
        imageDetalle = findViewById(R.id.imageDetalle);
        editButton = findViewById(R.id.editButton);
        deleteButton = findViewById(R.id.deleteButton);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            descDetalle.setText(bundle.getString("Descipción"));
            nombreDetalle.setText(bundle.getString("Nombre"));
            precioDetalle.setText(bundle.getString("Precio"));
            key = bundle.getString("Key");
            imageURL = bundle.getString("Image");
            Glide.with(this).load(bundle.getString("Image")).into(imageDetalle);
        }
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("EmpresaXYZ_DB");
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageReference = storage.getReferenceFromUrl(imageURL);
                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        reference.child(key).removeValue();
                        Toast.makeText(Detalle.this, "Eliminar", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), inventario.class));
                        finish();
                    }
                });
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Detalle.this, Subir.class).putExtra("Nombre", nombreDetalle.getText().toString()).putExtra("Descripcion", descDetalle.getText().toString()).putExtra("Precio", precioDetalle.getText().toString()).putExtra("Image", imageURL).putExtra("Key", key);
                startActivity(intent);
            }
        });
    }
}