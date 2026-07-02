package org.example.Musica;

public class MusicaMultimedia extends TipoMusica {

    @Override
    public String getTipo() {
        return "Multimédia";
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof MusicaMultimedia;
    }

    @Override
    public int hashCode() {
        return getTipo().hashCode();
    }
}