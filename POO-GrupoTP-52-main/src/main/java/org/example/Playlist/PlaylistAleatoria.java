package org.example.Playlist;

import org.example.Estatisticas;
import org.example.Musica.Musica;
import org.example.User;

import java.util.Collections;
import java.util.List;

import java.io.Serializable;

public class PlaylistAleatoria extends Playlist implements Serializable {
    private static final long serialVersionUID = 1L;

    public PlaylistAleatoria() {
        super();
        this.publica = true;
    }

    public PlaylistAleatoria(String name, List<Musica> musicas) {
        super(name, musicas, true);
    }

    public PlaylistAleatoria(PlaylistAleatoria outra) {
        super(outra);
        this.publica = true;
    }

    public PlaylistAleatoria clone(){
        return new PlaylistAleatoria(this);
    }

    @Override
    public void setpublica(boolean publica) {
        throw new UnsupportedOperationException("Uma Playlist Aleatória é sempre pública e não pode ser alterada.");
    }

    @Override
    public void reproduzir(User p, Estatisticas e) {
        List<Musica> copiaMusicas = this.getmusicas();
        Collections.shuffle(copiaMusicas);
        if (copiaMusicas.isEmpty()) {
            System.out.println("A playlist está vazia.");
            return;
        }

        for (Musica musica : copiaMusicas) {
            musica.reproduzir();
            e.registarReproducoes(p, musica);
        }
    }
}
