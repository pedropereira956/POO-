package org.example;

import org.example.Playlist.Playlist;
import org.example.Playlist.PlaylistPremium;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import java.io.Serializable;

public class Biblioteca implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Playlist> playlists;
    private List<Album> albuns;

    public Biblioteca(){
        this.playlists = new ArrayList<>();
        this.albuns = new ArrayList<>();
    }

    public Biblioteca(List<Playlist> playlists, List<Album> albuns) {
        this.playlists = playlists.stream().map(Playlist::clone).collect(Collectors.toList());
        this.albuns = albuns.stream().map(Album::clone).collect(Collectors.toList());
    }

    public Biblioteca(Biblioteca biblio){
        this.playlists = biblio.getPlaylists();
        this.albuns = biblio.getAlbums();
    }


    public List<Playlist> getPlaylists() {
        return this.playlists.stream().map(Playlist::clone).collect(Collectors.toCollection(ArrayList::new));
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists.stream().map(Playlist::clone).collect(Collectors.toList());
    }

    public List<Album> getAlbums() {
        return this.albuns.stream().map(Album::clone).collect(Collectors.toCollection(ArrayList::new));
    }

    public void setAlbums(List<Album> albuns) {
        this.albuns = albuns.stream().map(Album::clone).collect(Collectors.toList());
    }

    public void addAlbum(Album album) {
        if (album != null){
            this.albuns.add(album);
        }
    }

    public void addPlaylist(Playlist playlist) {
        if (playlist != null){
            this.playlists.add(playlist);
        }
    }

    public Playlist getPlaylist(String nome) {
        Playlist p = new PlaylistPremium();
        p = playlists.stream().filter(album -> album.getname().equals(nome)).findFirst().orElse(null);
        return p;
    }

    public Album getAlbum(String nome) {
        Album p = new Album();
        p = albuns.stream().filter(album -> album.getNome().equals(nome)).findFirst().orElse(null);
        return p;
    }
}
