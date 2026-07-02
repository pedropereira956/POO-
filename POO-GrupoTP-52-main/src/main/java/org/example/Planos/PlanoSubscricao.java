package org.example.Planos;

import org.example.Playlist.Playlist;
import org.example.User;

public interface PlanoSubscricao {
    String getPlano();
    int calculaPontos(User user);
    boolean podeCriarPlaylist();
    boolean podeOuvirPlaylist(Playlist p);
    boolean podeCriarBiblioteca();
    boolean podePlaylistFavoritos();
}
