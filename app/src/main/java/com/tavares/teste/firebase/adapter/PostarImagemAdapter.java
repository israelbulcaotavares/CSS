package com.tavares.teste.firebase.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tavares.teste.firebase.R;
import com.tavares.teste.firebase.entitites.PostagemImagem;

import java.util.List;

/**
 * Created by jamiltondamasceno
 */

public class PostarImagemAdapter extends RecyclerView.Adapter<PostarImagemAdapter.MyViewHolder> {

    private List<PostagemImagem> postagensImagens;

    public PostarImagemAdapter(List<PostagemImagem> listaPostagens) {
        this.postagensImagens = listaPostagens;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.postagem_imagem, parent, false);

        return new MyViewHolder(itemLista);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        PostagemImagem postagem = postagensImagens.get( position );


        holder.imagePostagem.setImageResource( postagem.getImagem() );


    }

    @Override
    public int getItemCount() {
        return postagensImagens.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        private ImageView imagePostagem;


        public MyViewHolder(View itemView) {
            super(itemView);

            imagePostagem = itemView.findViewById(R.id.imagePostagem2);


        }
    }

}
