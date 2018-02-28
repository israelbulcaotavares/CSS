package com.tavares.teste.firebase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RedefinirSenha extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redefinir_senha);

        final EditText edtEmail = (EditText) findViewById(R.id.editText9);

        Button btnVoltar = (Button) findViewById(R.id.button8);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RedefinirSenha.super.onBackPressed();
            }
        });

        Button btnRedSenha = (Button) findViewById(R.id.button7);
        btnRedSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

                firebaseAuth.sendPasswordResetEmail(edtEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RedefinirSenha.this, "E-mail enviado!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });


    }
}
