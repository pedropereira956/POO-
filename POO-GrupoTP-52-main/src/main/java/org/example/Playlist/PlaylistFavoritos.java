package org.example.Playlist;

import org.example.Estatisticas;
import org.example.Musica.Musica;
import org.example.User;

import java.util.List;

import java.io.Serializable;

public class PlaylistFavoritos extends Playlist implements Serializable {
    private static final long serialVersionUID = 1L;
    private User dono;
    private int musicaAtual;

    public PlaylistFavoritos() {
        super();
        dono = null;
        musicaAtual = 0;
    }

    public PlaylistFavoritos(String name, List<Musica> musicas, boolean publica, int musicaAtual, User dono) {
        super(name, musicas, false);
        this.musicaAtual = musicaAtual;
        this.dono = dono;
    }

    public PlaylistFavoritos(PlaylistFavoritos playlist) {
        super(playlist);
        this.musicaAtual = playlist.getMusicaAtual();
        this.dono = playlist.getDono();
    }

    public User getDono() {
        return dono;
    }

    public int getMusicaAtual() {
        return musicaAtual;
    }

    public void setMusicaAtual(Integer musicaAtual) {
        this.musicaAtual = musicaAtual;
    }

    public void setDono(User dono) {
        this.dono = dono;
    }

    public PlaylistFavoritos clone(){
        return new PlaylistFavoritos(this);
    }

    @Override
    public void reproduzir(User p, Estatisticas e) {
        if (musicas.isEmpty()) {
            System.out.println("A playlist está vazia.");

    
            return;
        }

        Musica musica = musicas.get(musicaAtual);
        musica.reproduzir();
        e.registarReproducoes(p, musica);
         
    }

    public void avancar() {
        if (musicas.isEmpty()) {
            System.out.println("A playlist está vazia.");
            return;
        }
        musicaAtual = (musicaAtual + 1) % musicas.size();
    }

    public void retroceder() {
        if (musicas.isEmpty()) {
            System.out.println("A playlist está vazia.");
            return;
        }
        musicaAtual = (musicaAtual - 1 + musicas.size()) % musicas.size();
    }

    public void reset() {
        musicaAtual = 0;
    }

    public void escolher_musica(int index){
        if (index > 0 && index < musicas.size()) {
            musicaAtual = index;
        }
    }
}
