package com.tavares.teste.firebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TelaCadastro extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private EditText edtNome, edtSobrenome, edtEmail, edtAniversario, edtSenha, edtConfSenha;
    private RadioButton radioButtonFem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);

        firebaseAuth = FirebaseAuth.getInstance();
        edtNome = (EditText) findViewById(R.id.editText);
        edtSobrenome = (EditText) findViewById(R.id.editText2);
        edtEmail = (EditText) findViewById(R.id.editText3);
        edtAniversario = (EditText) findViewById(R.id.editText4);
        edtSenha = (EditText) findViewById(R.id.editText5);
        edtConfSenha = (EditText) findViewById(R.id.editText6);
        radioButtonFem = (RadioButton) findViewById(R.id.radioButton);


        Button btnVoltar = (Button) findViewById(R.id.button4);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TelaCadastro.super.onBackPressed();
            }
        });
    }

    public void cadastro (View view){
        if (!edtEmail.getText().toString().equals("")){
            if (edtSenha.getText().toString().equals(edtConfSenha.getText().toString())){
                firebaseAuth.createUserWithEmailAndPassword(edtEmail.getText().toString(), edtSenha.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(TelaCadastro.this, "Erro no cadastro!", Toast.LENGTH_LONG).show();
                        }else{
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference databaseReference = database.getReference("usuarios").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            databaseReference.child("Nome").setValue(edtNome.getText().toString());
                            databaseReference.child("Sobrenome").setValue(edtSobrenome.getText().toString());
                            databaseReference.child("Email").setValue(edtEmail.getText().toString());
                            databaseReference.child("Aniversario").setValue(edtAniversario.getText().toString());
                            databaseReference.child("Senha").setValue(edtSenha.getText().toString());
                            if (radioButtonFem.isChecked()){
                                databaseReference.child("Sexo").setValue("Feminino");
                            }else{
                                databaseReference.child("Sexo").setValue("Masculino");
                            }
                            Toast.makeText(TelaCadastro.this, "Usuario criado com sucesso!", Toast.LENGTH_LONG).show();

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(TelaCadastro.this, "Email de verificação enviado!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                            startActivity(new Intent(TelaCadastro.this, TelaInicial.class));
                            finish();
                        }
                    }
                });
            }else{
                Toast.makeText(TelaCadastro.this, "Senhas diferentes!", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(TelaCadastro.this, "Preencha o campo do e-mail!", Toast.LENGTH_LONG).show();
        }
    }



}
