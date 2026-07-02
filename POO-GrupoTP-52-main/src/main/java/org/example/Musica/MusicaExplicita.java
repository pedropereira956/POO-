package org.example.Musica;

public class MusicaExplicita extends TipoMusica {

    @Override
    public String getTipo() {
        return "Explícita";
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof MusicaExplicita;
    }

    @Override
    public int hashCode() {
        return getTipo().hashCode();
    }
}