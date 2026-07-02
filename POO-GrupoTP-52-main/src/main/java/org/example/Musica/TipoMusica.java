package org.example.Musica;
import java.io.Serializable;



public abstract class TipoMusica implements Serializable {
    public abstract String getTipo();

    @Override
    public String toString() {
        return getTipo();
    }
}