package org.example;
import org.example.Musica.Musica;
import org.example.Planos.PlanoFree;
import org.example.Planos.PlanoPremiumTop;
import org.example.Planos.PlanoSubscricao;
import org.example.Playlist.Playlist;
import org.example.Playlist.PlaylistPremium;

import java.util.Objects;
import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nome;
    private String email;
    private String morada;
    private PlanoSubscricao plano;
    private int pontos;
    private String password;
    private Biblioteca biblio;

    public User() {
        this.nome = "";
        this.email = "";
        this.morada = "";
        this.plano = new PlanoFree();
        this.pontos = 0;
        this.password = "";
        this.biblio = new Biblioteca();
    }

    public User(String nome, String email, String morada, PlanoSubscricao plano, String password) {
        this.nome = nome;
        this.email = email;
        this.morada = morada;
        this.plano = plano;
        this.pontos = (plano instanceof PlanoPremiumTop) ? 100 : 0;
        this.password = password;
        this.biblio = new Biblioteca();
    }

    public User(User u) {
        this.nome = u.getNome();
        this.email = u.getEmail();
        this.morada = u.getMorada();
        this.plano = u.getPlano();
        this.pontos = u.getPontos();
        this.password = u.getPassword();
        this.biblio = u.getBiblioteca();
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public void setPlano(PlanoSubscricao plano) {
        this.plano = plano;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return this.nome;
    }

    public String getEmail() {
        return this.email;
    }

    public String getMorada() {
        return this.morada;
    }

    public PlanoSubscricao getPlano() {
        return this.plano;
    }

    public int getPontos() {
        return this.pontos;
    }

    public String getPassword() {
        return this.password;
    }

    public Biblioteca getBiblioteca() {
        return this.biblio;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        User u = (User) obj;
        return pontos == u.pontos &&
                Objects.equals(nome, u.nome) &&
                Objects.equals(email, u.email) &&
                Objects.equals(morada, u.morada) &&
                Objects.equals(plano.getClass(), u.plano.getClass()) &&
                Objects.equals(password, u.password) &&
                Objects.equals(biblio, u.biblio);
    }

    @Override
    public User clone() {
        return new User(this);
    }

    public void ouvirMusica(Musica m){
    m.reproduzir();
    setPontos(this.getPontos() + this.plano.calculaPontos(this));
    }

    public void reproduzirPlaylist(Playlist p, Estatisticas e) {
        if (plano.podeOuvirPlaylist(p)) {
            p.reproduzir(this, e);
            setPontos(this.pontos + this.plano.calculaPontos(this));
        } else {
            System.out.println("O seu plano não permite ouvir esta playlist.");
        }
    }

    public void reproduzirAlbum(Album p, Estatisticas e) {
        for (Musica m : p.getMusicas()){
            ouvirMusica(m);
            e.registarReproducoes(this,m);
        }
    }

    public Playlist criarPlaylist() {
        if (!plano.podeCriarPlaylist()) {
            throw new UnsupportedOperationException("O seu plano não permite criar playlists.");
        }
        return new PlaylistPremium(); // ou podes devolver um menu de criação
    }

}
