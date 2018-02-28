package com.tavares.teste.firebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

public class TelaLogin extends AppCompatActivity {

    private EditText edtEmail, edtSenha;
    private FirebaseAuth firebaseAuth;
    CallbackManager callbackManager;
    private FirebaseAnalytics mFirebaseAnalytics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login);
        firebaseAuth = FirebaseAuth.getInstance();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        edtEmail = (EditText) findViewById(R.id.editText7);
        edtSenha = (EditText) findViewById(R.id.editText8);


        TextView txtRedSenha = (TextView) findViewById(R.id.textView4);
        txtRedSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TelaLogin.this, RedefinirSenha.class));
            }
        });


        Button btnVoltar = (Button) findViewById(R.id.button5);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TelaLogin.super.onBackPressed();
            }
        });


        LoginButton loginbutton = (LoginButton) findViewById(R.id.login_button);

        loginbutton.setReadPermissions("public_profile, email, user_friends, user_birthday");
        callbackManager = CallbackManager.Factory.create();

        loginbutton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
                TelaLogin.super.onBackPressed();

                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "login_facebook_id");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "login_facebook");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "menu_click");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

                bundle = new Bundle();
                bundle.putString("menu_content", "login_facebook");
                mFirebaseAnalytics.logEvent("menu_content_custom", bundle);

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });


    }


    public void login(View view){
        firebaseAuth.signInWithEmailAndPassword(edtEmail.getText().toString(), edtSenha.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(TelaLogin.this, "Não foi possível!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(TelaLogin.this, "Login efetudado com sucesso!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(TelaLogin.this, TelaInicial.class));
                    finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    private void handleFacebookAccessToken(AccessToken token){
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()){
                    Toast.makeText(TelaLogin.this, "Autenticação falhou!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}
