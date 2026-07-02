package org.example.Playlist;

import org.example.Estatisticas;
import org.example.Musica.Musica;
import org.example.User;

import java.util.List;
import java.io.Serializable;

public class PlaylistPremium extends Playlist implements Serializable {
    private static final long serialVersionUID = 1L;
    private User dono;
    private int musicaAtual;

    public PlaylistPremium() {
        super();
        dono = null;
        musicaAtual = 0;
    }

    public PlaylistPremium(String name, List<Musica> musicas, boolean publica, int musicaAtual, User dono) {
        super(name, musicas, publica);
        this.musicaAtual = musicaAtual;
        this.dono = dono;
    }

    public PlaylistPremium(PlaylistPremium playlist) {
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

    public PlaylistPremium clone(){
        return new PlaylistPremium(this);
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

    public void tornar_publica() {
        setpublica(true);
    }

    public void tornar_privada(){
        setpublica(false);
    }

    public void escolher_musica(int index){
        if (index > 0 && index < musicas.size()) {
            musicaAtual = index;
        }
    }
}
