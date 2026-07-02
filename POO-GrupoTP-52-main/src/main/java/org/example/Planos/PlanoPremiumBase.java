package org.example.Planos;

import org.example.Playlist.Playlist;
import org.example.User;

import java.io.Serializable;

public class PlanoPremiumBase implements PlanoSubscricao,Serializable {
    private static final long serialVersionUID = 1L;
    @Override
    public String getPlano(){
        return "PremiumBase";
    }

    @Override
    public int calculaPontos(User user){
        return 10;
    }

    @Override
    public boolean podeCriarPlaylist(){
        return true;
    }

    @Override
    public boolean podeOuvirPlaylist(Playlist p) { return true; } // pode ouvir qualquer playlist

    @Override
    public boolean podeCriarBiblioteca(){
        return true;
    }

    @Override
    public boolean podePlaylistFavoritos(){
        return false;
    }

}

