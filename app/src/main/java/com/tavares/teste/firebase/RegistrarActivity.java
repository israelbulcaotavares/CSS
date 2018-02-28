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
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tavares.teste.firebase.ImagePicker.ImagePicker;
import com.tavares.teste.firebase.adapter.PostarImagemAdapter;
import com.tavares.teste.firebase.entitites.PostagemImagem;

import java.util.ArrayList;
import java.util.List;

public class RegistrarActivity extends AppCompatActivity {

        RecyclerView recyclerViewPostarImagens;
         private List<PostagemImagem> postagens = new ArrayList<>();


    public static final int REQUEST_DIALOG_PHOTO = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        recyclerViewPostarImagens  =(RecyclerView) findViewById(R.id.recyclerViewPostarImagens);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayout.HORIZONTAL);
        // RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2 );
        recyclerViewPostarImagens.setLayoutManager( layoutManager );

        //define adapter
        prepararPostagens();

        PostarImagemAdapter adapter = new PostarImagemAdapter( postagens );
        recyclerViewPostarImagens.setAdapter( adapter );
        FloatingActionButton botao_perfil = (FloatingActionButton) findViewById(R.id.botao_perfil);
                botao_perfil.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        voltarProPerfil();
                    }
                });

        FloatingActionButton botao_lista = (FloatingActionButton) findViewById(R.id.botao_lista);
        botao_lista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirLista();
            }
        });

        FloatingActionButton botao_adicionar_contatos = (FloatingActionButton) findViewById(R.id.botao_adicionar_contatos);
        botao_adicionar_contatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCadastroContato();
            }
        });

        ImageView selecionar_imagem = (ImageView)findViewById(R.id.selecionar_imagem);
        selecionar_imagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent camera = ImagePicker.getPickImageIntent(getBaseContext());
                startActivityForResult(camera, REQUEST_DIALOG_PHOTO);
            }
        });
    }

    private void voltarProPerfil(){
        Intent intent = new Intent(RegistrarActivity.this, TelaInicial.class);
        startActivity(intent);

    }

    private void abrirLista(){
        Intent intent = new Intent(RegistrarActivity.this, ListaActivity.class);
        startActivity(intent);

    }

    public void prepararPostagens(){

        PostagemImagem p;

        p = new PostagemImagem(R.drawable.imagem1);
        postagens.add(p);

        p = new PostagemImagem(R.drawable.imagem2);
        postagens.add(p);

        p = new PostagemImagem(R.drawable.imagem3);
        postagens.add(p);

        p = new PostagemImagem(R.drawable.imagem4);
        postagens.add(p);





    }


    private void abrirCadastroContato(){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(RegistrarActivity.this);

        //Configurações do Dialog
        alertDialog.setTitle("Novo contato");
        alertDialog.setMessage("E-mail do usuário");
        alertDialog.setCancelable(false);

        final EditText editText = new EditText(RegistrarActivity.this);
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
