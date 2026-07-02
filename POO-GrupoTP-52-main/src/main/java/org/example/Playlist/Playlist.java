package org.example.Playlist;

import org.example.Estatisticas;
import org.example.Musica.Musica;
import org.example.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import java.io.Serializable;

public abstract class Playlist implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String name;
    protected List<Musica> musicas;
    protected boolean publica;


    public Playlist() {
        musicas = new ArrayList<>();
        publica = false;
        name = "";
    }

    public Playlist(String name, List<Musica> musicas, boolean publica) {
        this.name = name;
        this.musicas = musicas.stream().map(Musica::clone).collect(Collectors.toList());
        this.publica = publica;
    }

    public Playlist(Playlist playlist) {
        this.name = playlist.getname();
        this.musicas = playlist.getmusicas();
        this.publica = playlist.getpublica();
    }

    public String getname() {
        return this.name;
    }

    public List<Musica> getmusicas() {
        return this.musicas.stream().map(Musica::clone).collect(Collectors.toCollection(ArrayList::new));
    }

    public boolean getpublica() {
        return this.publica;
    }

    public void setname(String name) {
        this.name = name;
    }

    public void setmusicas(List<Musica> musicas) {
        this.musicas = musicas.stream().map(Musica::clone).collect(Collectors.toList());
    }

    public void setpublica(boolean publica) {
        this.publica = publica;
    }

    public abstract Playlist clone();

    public int size() {
        return musicas.size();
    }

    public int duracao (){
        return this.musicas.stream().mapToInt(Musica::getDuracao).sum();

    }

    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Playlist playlist = (Playlist) o;
        return Objects.equals(musicas, playlist.musicas) &&
                Objects.equals(name, playlist.name);
    }

    public void adicionarMusica(Musica musica) {
        if (musica != null) this.musicas.add(musica.clone());
    }

    public abstract void reproduzir(User p, Estatisticas e);

}
