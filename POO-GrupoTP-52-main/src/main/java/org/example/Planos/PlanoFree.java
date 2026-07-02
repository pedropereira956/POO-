package org.example.Planos;

import org.example.Playlist.Playlist;
import org.example.Playlist.PlaylistAleatoria;
import org.example.User;

import java.io.Serializable;

public class PlanoFree implements PlanoSubscricao,Serializable {
    private static final long serialVersionUID = 1L;
    @Override
    public String getPlano(){
        return "Free";
    }

    @Override
    public int calculaPontos(User user){
        return 5;
    }

    @Override
    public boolean podeCriarPlaylist(){
        return false;
    }

    @Override
    public boolean podeOuvirPlaylist(Playlist p) { return p instanceof PlaylistAleatoria; }

    @Override
    public boolean podeCriarBiblioteca(){
        return false;
    }

    @Override
    public boolean podePlaylistFavoritos(){
        return false;
    }

}

