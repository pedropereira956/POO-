package org.example;

import org.example.Musica.Musica;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import java.io.Serializable;

public class Album implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nome;
    private String interprete;
    private List<Musica> musicas;

    public Album() {
        this.nome = "";
        this.interprete = "";
        this.musicas = new ArrayList<>();
    }

    public Album(String nome, String interprete, List<Musica> musicas) {
        this.nome = nome;
        this.interprete = interprete;
        this.musicas = musicas.stream().map(Musica::clone).collect(Collectors.toList());
    }

    public Album(Album outro) {
        this.nome = outro.getNome();
        this.interprete = outro.getInterprete();
        this.musicas = outro.getMusicas();
    }

    public String getNome() {
        return nome;
    }

    public String getInterprete() {
        return interprete;
    }

    public List<Musica> getMusicas() {
        return musicas.stream().map(Musica::clone).collect(Collectors.toCollection(ArrayList::new));
    }

    public Album clone(){
        return new Album(this);
    }

    public int getDuracaoTotal() {
        return musicas.stream().mapToInt(Musica::getDuracao).sum();
    }

    public void adicionarMusica(Musica musica) {
        if (musica != null) {
            this.musicas.add(musica.clone());
        }
    }

    public void reproduzirAlbum() {
        if (musicas.isEmpty()) {
            System.out.println("O álbum está vazio.");
            return;
        }

        System.out.println("A reproduzir álbum: " + nome + " do artista " + interprete);

        for (Musica musica : musicas) {
            musica.reproduzir();
        }
    }
}

