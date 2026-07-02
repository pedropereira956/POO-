package org.example;
import org.example.Musica.Musica;
import org.example.Playlist.Playlist;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.Serializable;
import java.util.*;

public class SpotifyUM implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, User> utilizadores;
    private List<Musica> musicas;
    private List<Playlist> playlists;
    private List<Playlist> playlistsPublicas;
    private List<Album> albuns;
    private Estatisticas estatisticas;

    public SpotifyUM() {
        this.utilizadores = new HashMap<>();
        this.musicas = new ArrayList<>();
        this.playlists = new ArrayList<>();
        this.playlistsPublicas = new ArrayList<>(); // <-- esta linha estava a faltar
        this.albuns = new ArrayList<>();
        this.estatisticas = new Estatisticas();
    }

    // Utilizadores
    public void adicionarUtilizador(User u) {
        this.utilizadores.put(u.getEmail(), u);
    }


    public boolean existeUtilizador(String email) {
        return this.utilizadores.containsKey(email);
    }

    public Collection<User> getTodosUtilizadores() {
    return utilizadores.values()
                       .stream()
                       .map(User::clone)
                       .toList();
}

public User getUtilizador(String email) {
    return this.utilizadores.get(email);
}

//Musicas

public void adicionarMusica(Musica m) {
    this.musicas.add(m.clone());
}

public Musica getMusica(String nome) {
        Musica m = new Musica();
        m = musicas.stream().filter(musica -> musica.getNome().equals(nome)).findFirst().orElse(null);
        return m;
}

public List<Musica> getTodasMusicas() {
    List<Musica> todas = new ArrayList<>();
    for (Album a : this.albuns) {
        todas.addAll(a.getMusicas());
    }
    return todas;
}


    //Albuns

public void adicionarAlbum(Album a) {
    this.albuns.add(a.clone());
}

public List<Album> getAlbuns() {
    return this.albuns.stream().map(Album::clone).toList();
}

public Album getAlbum(String nome) {
    Album m = new Album();
    m = albuns.stream().filter(album -> album.getNome().equals(nome)).findFirst().orElse(null);
    return m;
}

    // Playlists públicas (opcional)

    public List<Playlist> getPlaylistsPublicas() {
        List<Playlist> todas = utilizadores.values().stream()
                .map(User::getBiblioteca)
                .filter(Objects::nonNull)
                .flatMap(b -> b.getPlaylists().stream())
                .filter(Playlist::getpublica)
                .map(Playlist::clone)
                .toList();


        return todas;
}

    public int numPlaylistsPublicas() {
        return this.playlistsPublicas.size();
    }


    public void adicionarPlaylist(Playlist p) {
        this.playlists.add(p.clone());
    }

    public void adicionarPlaylistPublica(Playlist p) {

        this.playlistsPublicas.add(p.clone());

    }

 

    //Estatisticas

    public Estatisticas getEstatisticas() {
    return this.estatisticas;
}

/*public void reproduzirMusica(User u, Musica m) {
    m.reproduzir();
    u.ouvirMusica(m);
    estatisticas.registarReproducao(u, m);
}
*/

//--------------------------
public void guardarEstado(String ficheiro) throws IOException {
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ficheiro))) {
        out.writeObject(this);
    }
}

public static SpotifyUM carregarEstado(String ficheiro) throws IOException, ClassNotFoundException {
    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(ficheiro))) {
        return (SpotifyUM) in.readObject();
    }
}

}


