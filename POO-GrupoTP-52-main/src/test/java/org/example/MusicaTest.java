package org.example;

import org.example.Musica.Musica;
import org.example.Musica.MusicaExplicita;
import org.junit.jupiter.api.Assertions;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class MusicaTest {

    @org.junit.jupiter.api.Test
    void setNome() {
        Musica musica = new Musica();
        musica.setNome("Caos");
    }

    @org.junit.jupiter.api.Test
    void setArtista() {
    }

    @org.junit.jupiter.api.Test
    void setGenero() {
    }

    @org.junit.jupiter.api.Test
    void setEditora() {
    }

    @org.junit.jupiter.api.Test
    void setLetra() {
    }

    @org.junit.jupiter.api.Test
    void setPauta() {
    }

    @org.junit.jupiter.api.Test
    void setDuracao() {
    }

    @org.junit.jupiter.api.Test
    void getNome() {
        Musica musica = new Musica();
        musica.setNome("Caos");
        Assertions.assertEquals("Caos", musica.getNome());
    }

    @org.junit.jupiter.api.Test
    void getArtista() {
    }

    @org.junit.jupiter.api.Test
    void getGenero() {
    }

    @org.junit.jupiter.api.Test
    void getEditora() {
    }

    @org.junit.jupiter.api.Test
    void getLetra() {
    }

    @org.junit.jupiter.api.Test
    void getPauta() {
    }

    @org.junit.jupiter.api.Test
    void getDuracao() {
    }

    @org.junit.jupiter.api.Test
    void testEquals() {
    }

    @org.junit.jupiter.api.Test
    void testClone() {
    }

    @org.junit.jupiter.api.Test
    void reproduzir() {
        MusicaExplicita tipoMusica = new MusicaExplicita();
        Musica musica = new Musica("Nome da Música", "Artista", "Pop", "Editora", "Letra da música", "Pauta", 1, tipoMusica);

        // Capturar o output do System.out
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Act
        musica.reproduzir();

        // Restore System.out
        System.setOut(System.out);

        // Assert
        String output = outputStream.toString();
        Assertions.assertTrue(output.contains("A reproduzir: Nome da Música"));
        Assertions.assertTrue(output.contains("Letra da música"));
    }
}