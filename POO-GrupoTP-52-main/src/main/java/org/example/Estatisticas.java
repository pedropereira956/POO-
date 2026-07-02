package org.example;

import org.example.Musica.Musica;
import org.example.Playlist.Playlist;

import java.util.*;
import java.io.Serializable;

public class Estatisticas implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, Integer> reproducoesPorArtista = new HashMap<>();
    private Map<User, Integer> reproducoesPorUser = new HashMap<>();
    private Map<String, Integer> reproducoesPorGenero = new HashMap<>();

    public Estatisticas(){
        this.reproducoesPorArtista = new HashMap<>();
        this.reproducoesPorUser = new HashMap<>();
        this.reproducoesPorGenero = new HashMap<>();
    }

    public void registarReproducoes(User user, Musica musica){
        reproducoesPorArtista.put(
                musica.getArtista(),
                reproducoesPorArtista.getOrDefault(musica.getArtista(), 0) + 1
        );


        reproducoesPorUser.put(
                user, reproducoesPorUser.getOrDefault(user, 0) + 1
        );

        reproducoesPorGenero.put(
                musica.getGenero(), reproducoesPorGenero.getOrDefault(musica.getGenero(), 0) + 1
        );
    }

    public Musica getMusicaMaisReproduzida(List<Musica> musicas) {
        return musicas.stream()
                .max(Comparator.comparingInt(Musica::getReproducoes))
                .orElse(null);
    }

    public User getUserMaisPontos (List<User> users) {
        return users.stream().max(Comparator.comparingInt(User::getPontos)).orElse(null);
    }

    public String getArtistaMaisReproduzido() {
        return reproducoesPorArtista.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public User getUserComMaisReproducoes() {
        return reproducoesPorUser.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public String getGeneroMaisReproduzido() {
        return reproducoesPorGenero.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public User getUserMaisPlaylists(List<User> users) {
        return users.stream()
                .max(Comparator.comparingInt(u -> u.getBiblioteca().getPlaylists().size()))
                .orElse(null);
    }

    public int getNumPlaylistsPublicas(List<User> users) {
        return users.stream()
                .flatMap(u -> u.getBiblioteca().getPlaylists().stream())
                .filter(Playlist::getpublica)
                .mapToInt(p -> 1)
                .sum();
    }

}
