package org.example.Musica;

import java.io.Serializable;

public class Musica implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private String artista;
    private String genero;
    private String editora;
    private String letra;
    private String pauta;
    private int duracao;
    private int reproducoes;
    private TipoMusica tipo;

    public Musica() {
        this.nome = "";
        this.artista = "";
        this.genero = "";
        this.editora = "";
        this.letra = "";
        this.pauta = "";
        this.duracao = 0;
        this.reproducoes = 0;
        this.tipo = null;
    }

    public Musica(String nome, String artista, String genero, String editora,
                  String letra, String pauta, int duracao, TipoMusica tipo) {
        this.nome = nome;
        this.artista = artista;
        this.genero = genero;
        this.editora = editora;
        this.letra = letra;
        this.pauta = pauta;
        this.duracao = duracao;
        this.reproducoes = 0;
        this.tipo = tipo;
    }

    public Musica(Musica musica) {
        this.nome = musica.getNome();
        this.artista = musica.getArtista();
        this.genero = musica.getGenero();
        this.editora = musica.getEditora();
        this.letra = musica.getLetra();
        this.pauta = musica.getPauta();
        this.duracao = musica.getDuracao();
        this.reproducoes = musica.getReproducoes();
        this.tipo = musica.getTipo();
    }

    // Getters e Setters

    public String getNome() { return nome; }
    public String getArtista() { return artista; }
    public String getGenero() { return genero; }
    public String getEditora() { return editora; }
    public String getLetra() { return letra; }
    public String getPauta() { return pauta; }
    public int getDuracao() { return duracao; }
    public int getReproducoes() { return reproducoes; }
    public TipoMusica getTipo() { return tipo; }

    public void setTipo(TipoMusica tipo) { this.tipo = tipo; }
    public void setNome(String nome) { this.nome = nome; }
    public void setArtista(String artista) { this.artista = artista; }
    public void setGenero(String genero) { this.genero = genero; }
    public void setEditora(String editora) { this.editora = editora; }
    public void setLetra(String letra) { this.letra = letra; }
    public void setPauta(String pauta) { this.pauta = pauta; }
    public void setDuracao(int duracao) { this.duracao = duracao; }

    public void incrementarReproducoes() { this.reproducoes++; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Musica m = (Musica) obj;
        return duracao == m.duracao &&
               nome.equals(m.nome) &&
               artista.equals(m.artista) &&
               genero.equals(m.genero) &&
               editora.equals(m.editora) &&
               letra.equals(m.letra) &&
               pauta.equals(m.pauta) &&
               ((tipo == null && m.tipo == null) || (tipo != null && tipo.equals(m.tipo)));
    }

    @Override
    public Musica clone() {
        return new Musica(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nome: ").append(nome).append("\n");
        sb.append("Artista: ").append(artista).append("\n");
        sb.append("Género: ").append(genero).append("\n");
        sb.append("Editora: ").append(editora).append("\n");
        sb.append("Letra: ").append(letra).append("\n");
        sb.append("Pauta: ").append(pauta).append("\n");
        sb.append("Duração: ").append(duracao).append("s\n");
        sb.append("Reproduções: ").append(reproducoes).append("\n");

        if (tipo != null) {
            sb.append("Tipo: ").append(tipo.getTipo()).append("\n");
        }

        return sb.toString();
    }

    public void reproduzir() {
        System.out.println("A reproduzir: " + nome);
        System.out.println(letra);
        incrementarReproducoes();

    }
}
