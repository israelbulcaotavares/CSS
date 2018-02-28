package com.tavares.teste.firebase;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.tavares.teste.firebase.adapter.PostagemAdapter;
import com.tavares.teste.firebase.entitites.Postagem;

import java.util.ArrayList;
import java.util.List;

public class ListaActivity extends AppCompatActivity {
    RecyclerView recyclerPostagem;
    private List<Postagem> postagens = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        FloatingActionButton botao_chat = (FloatingActionButton) findViewById(R.id.botao_chat);
            botao_chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    abrirChat();
                }
            });

        recyclerPostagem = findViewById(R.id.recyclerView);


        FloatingActionButton botao_adicionar_contatos = (FloatingActionButton) findViewById(R.id.botao_adicionar_contatos);
        botao_adicionar_contatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCadastroContato();
            }
        });


        FloatingActionButton botao_perfil = (FloatingActionButton)findViewById(R.id.botao_voltar_perfil);
            botao_perfil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    voltarAoPerfil();
                }
            });

        FloatingActionButton botao_registro = (FloatingActionButton)findViewById(R.id.botao_registrar);
        botao_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirRegistros();
            }
        });




        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        // RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2 );
        recyclerPostagem.setLayoutManager( layoutManager );

        //define adapter
        prepararPostagens();

        PostagemAdapter adapter = new PostagemAdapter( postagens );
        recyclerPostagem.setAdapter( adapter );

    }

    private void voltarAoPerfil(){
        Intent intent= new Intent(ListaActivity.this, TelaInicial.class);
            startActivity(intent);

    }

    private void abrirChat(){
        Intent intent = new Intent(ListaActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void abrirRegistros(){
        Intent intent= new Intent(ListaActivity.this, RegistrarActivity.class);
        startActivity(intent);
    }
    public void prepararPostagens(){

        Postagem p;

        p = new Postagem("Israel Tavares", "Audi R8", R.drawable.imagem1, "Há 7 min" );
        postagens.add(p);

        p = new Postagem("Carlos Augusto", "Audi A7 2.0T", R.drawable.imagem2, "Há 1 hora" );
        postagens.add(p);

        p = new Postagem("Maria Luiza", "Golf GTI 1.4T", R.drawable.imagem3, "Há 39 min");
        postagens.add(p);

        p = new Postagem("Marcos Paulo", "Jaguar XE 35T Premium", R.drawable.imagem4, "Há 2 horas");
        postagens.add(p);

    }

    private void abrirCadastroContato(){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ListaActivity.this);

        //Configurações do Dialog
        alertDialog.setTitle("Novo contato");
        alertDialog.setMessage("E-mail do usuário");
        alertDialog.setCancelable(false);

        final EditText editText = new EditText(ListaActivity.this);
        alertDialog.setView( editText );

        //Configura botões
        alertDialog.setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {






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
