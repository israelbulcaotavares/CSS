package com.tavares.teste.firebase;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.tavares.teste.firebase.config.ConfiguracaoFirebase;
import com.tavares.teste.firebase.helper.Base64Custom;
import com.tavares.teste.firebase.helper.Preferencias;
import com.tavares.teste.firebase.model.Contato;
import com.tavares.teste.firebase.model.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

public class TelaInicial extends AppCompatActivity {

    private String nome;
    private String email;
    private String sexo;
    private String id;
    private String aniversario;
    private String caminhoimagem;
    private ImageView imageView;
    AccessToken accessToken;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaReference = database.getReference();
    private static final int GALLERY_INTENT = 2;
    private ProgressDialog progressDialog;

    private String identificadorContato;
    private DatabaseReference firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);



        FloatingActionButton botao_adicionar_contatos = (FloatingActionButton) findViewById(R.id.botao_adicionar_contatos);
                        botao_adicionar_contatos.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                abrirCadastroContato();
                            }
                        });




        FloatingActionButton botao_deslogar = (FloatingActionButton) findViewById(R.id.botao_deslogar) ;
                        botao_deslogar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FirebaseAuth.getInstance().signOut();
                                LoginManager.getInstance().logOut();
                                startActivity(new Intent(TelaInicial.this, PainelActivity.class));
                                finish();
                            }
                        });

        FloatingActionButton botao_lista = (FloatingActionButton) findViewById(R.id.botao_lista);
                            botao_lista.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    abrirPublicacoes();
                                }
                            });


        FloatingActionButton botao_registrar = (FloatingActionButton) findViewById(R.id.botao_registrar);
        botao_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirRegistros();
            }
        });



        imageView = (ImageView) findViewById(R.id.imageView14);
        accessToken = AccessToken.getCurrentAccessToken();

        if (accessToken != null){
            GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    try{
                        nome = object.getString("name");
                        email = object.getString("email");
                        id = object.getString("id");
                        aniversario = object.getString("age_range");
                        sexo = object.getString("gender");
                        caminhoimagem = "https://graph.facebook.com/" + object.getString("id") + "/picture?type=large";

                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        DatabaseReference reference = firebaseDatabase.getReference("usuarios").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        reference.child("Nome").setValue(nome);
                        reference.child("Email").setValue(email);
                        reference.child("Idade").setValue(aniversario);
                        reference.child("Id").setValue(id);
                        reference.child("Sexo").setValue(sexo);
                        reference.child("Imagem").setValue(caminhoimagem);
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                }
            });

            Bundle paramenters = new Bundle();
            paramenters.putString("fields", "id, name, email, gender, age_range");
            request.setParameters(paramenters);
            request.executeAsync();
        }

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReferenceFromUrl("gs://cursoandroidfirebase-21b49.appspot.com/").child("lambo.jpg");
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                ImageView imageView = (ImageView) findViewById(R.id.imageView15);
                Picasso.with(TelaInicial.this).load(uri).into(imageView);
            }
        });


        databaReference.child("usuarios").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Nome").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String nome;
                nome = (String) dataSnapshot.getValue();
                TextView txtNome = (TextView) findViewById(R.id.txtNome);
                txtNome.setText(nome);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        databaReference.child("usuarios").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Imagem").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String imagem;
                imagem = (String) dataSnapshot.getValue();
                Picasso.with(TelaInicial.this).load(imagem).into(imageView);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        progressDialog = new ProgressDialog(this);
        storageReference = FirebaseStorage.getInstance().getReference();

        ImageButton btnMdImg = (ImageButton) findViewById(R.id.botao_trocar_imagem);
        btnMdImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);

                intent.setType("image/*");

                startActivityForResult(intent, GALLERY_INTENT);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK){
            progressDialog.setMessage("Mudando a foto de perfil ...");
            progressDialog.show();

            Uri uri = data.getData();

            StorageReference filepath = storageReference.child("Fotos").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    String imagem;
                    imagem = downloadUrl.toString();

                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference reference = firebaseDatabase.getReference("usuarios").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    reference.child("Imagem").setValue(imagem);
                    databaReference.child("usuarios").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Imagem").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String imagem;
                            imagem = (String) dataSnapshot.getValue();
                            Picasso.with(TelaInicial.this).load(imagem).into(imageView);
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    progressDialog.dismiss();
                    Toast.makeText(TelaInicial.this, "Foto de perfil alterada com sucesso!", Toast.LENGTH_LONG).show();
                }
            });
        }

    }


    private void abrirPublicacoes(){

            Intent intent = new Intent(TelaInicial.this,ListaActivity.class);
                startActivity(intent);

    }

    private void abrirRegistros(){

        Intent intent = new Intent(TelaInicial.this,RegistrarActivity.class);
        startActivity(intent);

    }

    private void abrirCadastroContato(){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(TelaInicial.this);

        //Configurações do Dialog
        alertDialog.setTitle("Novo contato");
        alertDialog.setMessage("E-mail do usuário");
        alertDialog.setCancelable(false);

        final EditText editText = new EditText(TelaInicial.this);
        alertDialog.setView( editText );

        //Configura botões
        alertDialog.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String emailContato = editText.getText().toString();

                //Valida se o e-mail foi digitado
                if( emailContato.isEmpty() ){
                    Toast.makeText(TelaInicial.this, "Preencha o e-mail", Toast.LENGTH_LONG).show();
                }else{

                    //Verificar se o usuário já está cadastrado no nosso App
                    identificadorContato = Base64Custom.codificarBase64(emailContato);

                    //Recuperar instância Firebase
                    firebase = ConfiguracaoFirebase.getFirebase().child("usuarios").child(identificadorContato);

                    firebase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if( dataSnapshot.getValue() != null ){

                                //Recuperar dados do contato a ser adicionado
                                Usuario usuarioContato = dataSnapshot.getValue( Usuario.class );

                                //Recuperar identificador usuario logado (base64)
                                Preferencias preferencias = new Preferencias(TelaInicial.this);
                                String identificadorUsuarioLogado = preferencias.getIdentificador();

                                firebase = ConfiguracaoFirebase.getFirebase();
                                firebase = firebase.child("contatos")
                                        .child( identificadorUsuarioLogado )
                                        .child( identificadorContato );

                                Contato contato = new Contato();
                                contato.setIdentificadorUsuario( identificadorContato );
                                contato.setEmail( usuarioContato.getEmail() );
                                contato.setNome( usuarioContato.getNome() );

                                firebase.setValue( contato );

                            }else {
                                Toast.makeText(TelaInicial.this, "Usuário não possui cadastro.", Toast.LENGTH_LONG)
                                        .show();
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }

            }
        });

        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.create();
        alertDialog.show();

    }

}



