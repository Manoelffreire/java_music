package model;

import exception.DadosInvalidosException;
import java.util.UUID;

public class Musica {
    private final UUID id;
    private String titulo;
    private String artista;
    private String album;
    private String genero;
    private int duracaoSegundos;

    public Musica(String titulo, String artista, String album, String genero, int duracaoSegundos) throws DadosInvalidosException {
        this(UUID.randomUUID(), titulo, artista, album, genero, duracaoSegundos);
    }

    public Musica(UUID id, String titulo, String artista, String album, String genero, int duracaoSegundos) throws DadosInvalidosException {
        this.id = (id == null ? UUID.randomUUID() : id);
        setTitulo(titulo);
        setArtista(artista);
        setAlbum(album);
        setGenero(genero);
        setDuracaoSegundos(duracaoSegundos);
    }

    public UUID getId() { return id; }

    public String getTitulo() { return titulo; }

    public void setTitulo(String titulo) throws DadosInvalidosException {
        if (titulo == null || titulo.isBlank()) {
            throw new DadosInvalidosException("O título da música é obrigatório.");
        }
        this.titulo = titulo.trim();
    }

    public String getArtista() { return artista; }

    public void setArtista(String artista) throws DadosInvalidosException {
        if (artista == null || artista.isBlank()) {
            throw new DadosInvalidosException("O nome do artista é obrigatório.");
        }
        this.artista = artista.trim();
    }

    public String getAlbum() { return album; }
    public void setAlbum(String album) {
        this.album = (album == null ? "" : album.trim());
    }

    public String getGenero() { return genero; }
    public void setGenero(String genero) {
        this.genero = (genero == null ? "" : genero.trim());
    }

    public int getDuracaoSegundos() { return duracaoSegundos; }
    public void setDuracaoSegundos(int duracaoSegundos) throws DadosInvalidosException {
        if (duracaoSegundos <= 0) {
            throw new DadosInvalidosException("A duração deve ser maior que zero segundos.");
        }
        this.duracaoSegundos = duracaoSegundos;
    }

    public String toLinha() {
        return String.format("%s - %s (%s) [%ds]", titulo, artista, genero, duracaoSegundos);
    }

    @Override
    public String toString() { return toLinha(); }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Musica)) return false;
        Musica outra = (Musica) obj;
        return titulo.equalsIgnoreCase(outra.titulo)
            && artista.equalsIgnoreCase(outra.artista)
            && album.equalsIgnoreCase(outra.album);
    }

    @Override
    public int hashCode() {
        return (titulo.toLowerCase() + "|" + artista.toLowerCase() + "|" + album.toLowerCase()).hashCode();
    }
}
