package org.example;
import org.example.Musica.Musica;
import org.example.Musica.MusicaExplicita;
import org.example.Musica.MusicaMultimedia;
import org.example.Musica.TipoMusica;
import org.example.Planos.PlanoFree;
import org.example.Planos.PlanoPremiumBase;
import org.example.Planos.PlanoPremiumTop;
import org.example.Planos.PlanoSubscricao;
import org.example.Playlist.Playlist;
import org.example.Playlist.PlaylistAleatoria;
import org.example.Playlist.PlaylistFavoritos;
import org.example.Playlist.PlaylistPremium;

import java.util.*;


import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Scanner;


public class Menu {
    private SpotifyUM model;
    private Scanner sc;

    public Menu() {
        this.model = new SpotifyUM(); // nome genérico do modelo principal
        sc = new Scanner(System.in);
    }

    public void run() {
    while (true) {
        System.out.println("\n--- Bem-vindo ao SpotifUM ---");
        System.out.println("1 - Entrar como Admin");
        System.out.println("2 - Entrar como Utilizador");
        System.out.println("0 - Sair");

        System.out.print("Opção: ");
        String op = sc.nextLine();

        switch (op) {
            case "1" -> runAdmin();
            case "2" -> loginUser();
            case "0" -> {
                System.out.println("A sair...");
                return;
            }
            default -> System.out.println("Opção inválida.");
        }
    }
}


private void runAdmin() {
    NewMenu menu = new NewMenu(new String[] {
            "Gestão de Utilizadores",
            "Gestão de Álbuns",
            "Gestão de Playlists",
            "Estatísticas",
            "Guardar Estado",
            "Carregar Estado"
    });

    menu.setHandler(1, this::menuUtilizadores);
    menu.setHandler(2, this::menuAlbuns);
    menu.setHandler(3, this::menuPlaylists);
    menu.setHandler(4, this::menuEstatisticas);
    menu.setHandler(5, this::guardarEstado);
    menu.setHandler(6, this::carregarEstado);

    menu.run();
}

  private void menuUtilizadores() {
    NewMenu menu = new NewMenu(new String[] {
        "Adicionar Utilizador",
        "Listar Utilizadores",
        "Alterar Plano de Subscrição"
    });

    menu.setHandler(1, this::adicionarUtilizador);
    menu.setHandler(2, this::listarUtilizadores);
    menu.setHandler(3, this::alterarPlano);

    menu.run();
}

private void loginUser() {
    System.out.print("Email do utilizador: ");
    String email = sc.nextLine();
    User u = model.getUtilizador(email);

    if (u == null) {
        System.out.println("Utilizador não encontrado.");
        return;
    }

    System.out.print("Password: ");
    String password = sc.nextLine();

    if (!u.getPassword().equals(password)) {
        System.out.println("Password incorreta.");
        return;
    }

    // Redirecionar conforme o plano
    PlanoSubscricao plano = u.getPlano();

    if (plano instanceof PlanoFree) {
        menuUserFree(u);
    } else if (plano instanceof PlanoPremiumBase) {
        menuUserPremiumBase(u);
    } else if (plano instanceof PlanoPremiumTop) {
        menuUserPremiumTop(u);
    } else {
        System.out.println("Plano desconhecido.");
    }
}


private void adicionarUtilizador() {
    System.out.println("\n--- Adicionar Utilizador ---");
    System.out.print("Nome: ");
    String nome = sc.nextLine();

    System.out.print("Email: ");
    String email = sc.nextLine();

    if (model.existeUtilizador(email)) {
        System.out.println("Já existe um utilizador com esse email.");
        return;
    }

    System.out.print("Morada: ");
    String morada = sc.nextLine();

    System.out.print("Password: ");
    String password = sc.nextLine();

    System.out.print("Plano (free, base, top): ");
    String planoStr = sc.nextLine().trim().toLowerCase();

    PlanoSubscricao plano;
    switch (planoStr) {
        case "base" -> plano = new PlanoPremiumBase();
        case "top" -> plano = new PlanoPremiumTop();
        default -> plano = new PlanoFree();
    }

    User novo = new User(nome, email, morada, plano, password);
    model.adicionarUtilizador(novo);

    System.out.println("Utilizador adicionado com sucesso!");
}

private void listarUtilizadores() {
    Collection<User> utilizadores = model.getTodosUtilizadores();

    if (utilizadores.isEmpty()) {
        System.out.println("Nenhum utilizador registado.");
        return;
    }

    System.out.println("\n--- Utilizadores ---");
    for (User u : utilizadores) {
        System.out.println("• " + u.getNome() + " (" + u.getEmail() + ") - Plano: " + u.getPlano().getClass().getSimpleName());
    }
}

//-------------------------------------------------

private void alterarPlano() {
    System.out.println("\n--- Alterar Plano de Subscrição ---");
    System.out.print("Email do utilizador: ");
    String email = sc.nextLine();

    User u = model.getUtilizador(email);
    if (u == null) {
        System.out.println("Utilizador não encontrado.");
        return;
    }

    System.out.print("Novo plano (free, base, top): ");
    String planoStr = sc.nextLine().trim().toLowerCase();

    PlanoSubscricao novoPlano;
    switch (planoStr) {
        case "base" -> novoPlano = new PlanoPremiumBase();
        case "top" -> novoPlano = new PlanoPremiumTop();
        case "free" -> novoPlano = new PlanoFree();
        default -> {
            System.out.println("Plano inválido. Plano não alterado.");
            return;
        }
    }

    u.setPlano(novoPlano);
    System.out.println("Plano alterado com sucesso para " + planoStr.toUpperCase());
}

//-------------------------------------------------

   private void menuAlbuns() {
    NewMenu menu = new NewMenu(new String[] {
        "Adicionar Álbum",
        "Listar Álbuns"
    });

    menu.setHandler(1, this::adicionarAlbum);
    menu.setHandler(2, this::listarAlbuns);

    menu.run();
}

private void adicionarAlbum() {
    System.out.println("\n--- Adicionar Álbum ---");

    System.out.print("Nome do álbum: ");
    String nomeAlbum = sc.nextLine();

    System.out.print("Intérprete: ");
    String interprete = sc.nextLine();

    List<Musica> musicas = new ArrayList<>();
    boolean continuar = true;

    while (continuar) {
        System.out.println("\n--- Nova Música para o Álbum ---");

        System.out.print("Nome da música: ");
        String nomeMusica = sc.nextLine();

        System.out.print("Género: ");
        String genero = sc.nextLine();

        System.out.print("Editora: ");
        String editora = sc.nextLine();

        System.out.print("Letra: ");
        String letra = sc.nextLine();

        System.out.print("Pauta: ");
        String pauta = sc.nextLine();

        System.out.print("Duração (em segundos): ");
        int duracao = Integer.parseInt(sc.nextLine());

        System.out.print("Tipo (explícita, multimédia ou nada): ");
        String tipo = sc.nextLine().trim().toLowerCase();

        TipoMusica tipoMusica = null;
        if (tipo.equals("explícita") || tipo.equals("explicita")) {
            tipoMusica = new MusicaExplicita();
        } else if (tipo.equals("multimédia") || tipo.equals("multimedia")) {
            tipoMusica = new MusicaMultimedia();
        }

        Musica m = new Musica(nomeMusica, interprete, genero, editora, letra, pauta, duracao, tipoMusica);
        musicas.add(m);
        model.adicionarMusica(m);

        System.out.print("Deseja adicionar outra música? (s/n): ");
        String resposta = sc.nextLine().trim().toLowerCase();
        if (!resposta.equals("s")) continuar = false;
    }

    Album album = new Album(nomeAlbum, interprete, musicas);
    model.adicionarAlbum(album);

    System.out.println("Álbum adicionado com sucesso!");
}


private void listarAlbuns() {
    List<Album> albuns = model.getAlbuns();

    if (albuns.isEmpty()) {
        System.out.println("Não existem álbuns registados.");
        return;
    }

    System.out.println("\n--- Álbuns ---");
    for (Album a : albuns) {
        System.out.println("• " + a.getNome() + " (por " + a.getInterprete() + ")");
    }
}

//-------------------------------------------------

    private void menuPlaylists() {
        System.out.println("[sub-menu] Gestão de Playlists ainda não implementado.");
    }

    private void menuEstatisticas() {
    NewMenu menu = new NewMenu(new String[] {
        "Música mais reproduzida",
        "Utilizador com mais pontos",
        "Artista mais reproduzido",
        "Utilizador com mais reproduções",
        "Género mais reproduzido",
        "Utilizador com mais playlists",
        "Número total de playlists públicas"
    });

    menu.setHandler(1, this::musicaMaisReproduzida);
    menu.setHandler(2, this::utilizadorMaisPontos);
    menu.setHandler(3, this::artistaMaisReproduzido);
    menu.setHandler(4, this::userMaisReproducoes);
    menu.setHandler(5, this::generoMaisReproduzido);
    menu.setHandler(6, this::userMaisPlaylists);
    menu.setHandler(7, this::numPlaylistsPublicas);

    menu.run();
}

private void musicaMaisReproduzida() {
    Musica m = model.getEstatisticas().getMusicaMaisReproduzida(model.getTodasMusicas());
    if (m != null) System.out.println("Música mais reproduzida: " + m.getNome());
    else System.out.println("Nenhuma música foi reproduzida.");
}

private void utilizadorMaisPontos() {
    User u = model.getEstatisticas().getUserMaisPontos(model.getTodosUtilizadores().stream().toList());
    if (u != null) System.out.println("Utilizador com mais pontos: " + u.getNome() + " (" + u.getPontos() + " pontos)");
    else System.out.println("Nenhum utilizador com pontos.");
}

private void artistaMaisReproduzido() {
    String artista = model.getEstatisticas().getArtistaMaisReproduzido();
    if (artista != null) System.out.println("Artista mais reproduzido: " + artista);
    else System.out.println("Nenhuma reprodução ainda.");
}

private void userMaisReproducoes() {
    User u = model.getEstatisticas().getUserComMaisReproducoes();
    if (u != null) System.out.println("Utilizador com mais reproduções: " + u.getNome());
    else System.out.println("Nenhuma reprodução ainda.");
}

private void generoMaisReproduzido() {
    String genero = model.getEstatisticas().getGeneroMaisReproduzido();
    if (genero != null) System.out.println("Género mais reproduzido: " + genero);
    else System.out.println("Nenhuma reprodução ainda.");
}

private void userMaisPlaylists() {
    User u = model.getEstatisticas().getUserMaisPlaylists(model.getTodosUtilizadores().stream().toList());
    if (u != null) System.out.println("Utilizador com mais playlists: " + u.getNome());
    else System.out.println("Nenhuma playlist encontrada.");
}

private void numPlaylistsPublicas() {
    System.out.println("Total de playlists públicas: " + model.numPlaylistsPublicas());
}

//-------------------------------------------------

private void guardarEstado() {
    String ficheiro = "spotifyum_estado.dat";  // nome fixo

    try {
        model.guardarEstado(ficheiro);
        System.out.println("Estado guardado automaticamente em " + ficheiro);
    } catch (IOException e) {
        System.out.println("Erro ao guardar o estado: " + e.getMessage());
    }
}

private void carregarEstado() {
    System.out.print("Nome do ficheiro a carregar (ex: estado.dat): ");
    String ficheiro = sc.nextLine();

    try {
        this.model = SpotifyUM.carregarEstado(ficheiro);
        System.out.println("Estado carregado com sucesso.");
    } catch (IOException | ClassNotFoundException e) {
        System.out.println("Erro ao carregar: " + e.getMessage());
    }
}

//-------------------------------------------------

private void menuUserFree(User u) {
    NewMenu menu = new NewMenu(new String[] {
        "Ouvir playlists públicas aleatórias"
    });

    menu.setHandler(1, () -> ouvirMusicasAleatorias(u));
    menu.run();
}

private void menuUserPremiumBase(User u) {
    NewMenu menu = new NewMenu(new String[] {
        "Ouvir playlists públicas",
        "Biblioteca",
        "Criar playlist",
        "Tornar playlist pública"
    });
    menu.setHandler(1, () -> ouvirPlaylistPublica(u));
    menu.setHandler(2, () -> menuBiblioteca(u));
    menu.setHandler(3, () -> criarPlaylistUser(u));
    menu.setHandler(4, () -> tornarPlaylistPublica(u));
    menu.run();
}

private void menuUserPremiumTop(User u) {
    NewMenu menu = new NewMenu(new String[] {
        "Criar Playlist com base nos Favoritos",
        "Ouvir playlists públicas",
        "Biblioteca",
        "Criar playlist",
        "Tornar playlist pública"
    });

    menu.setHandler(1, () -> verPlaylistsRecomendadas(u));
    menu.setHandler(2, () -> ouvirPlaylistPublica(u));
    menu.setHandler(3, () -> menuBiblioteca(u)) ;
    menu.setHandler(4, () -> criarPlaylistUser(u));
    menu.setHandler(5, () -> tornarPlaylistPublica(u));
    menu.run();
}

private void ouvirPlaylistPublica(User u) {
        if(model.numPlaylistsPublicas() > 0) {
            List<Playlist> publicas = model.getPlaylistsPublicas();

            if (publicas.isEmpty()) {
                System.out.println("Não existem playlists públicas disponíveis.");
                return;
            }

            System.out.println("\nPlaylists públicas disponíveis:");
            for (int i = 0; i < publicas.size(); i++) {
                System.out.println((i + 1) + " - " + publicas.get(i).getname());
            }

            System.out.print("Escolhe o número da playlist: ");
            int escolha = Integer.parseInt(sc.nextLine());

            if (escolha < 1 || escolha > publicas.size()) {
                System.out.println("Opção inválida.");
                return;
            }

            Playlist selecionada = publicas.get(escolha - 1);

            // Criar uma versão aleatória da playlist
            Playlist aleatoria = new PlaylistAleatoria(selecionada.getname(), selecionada.getmusicas());

            menuReproduzirPlaylist(u, aleatoria);
        }
        else {
            System.out.println("Não existem Playlists públicas.");
        }
}



private void ouvirMusicasAleatorias(User u) {
    List<Musica> todas = model.getTodasMusicas(); // ou dos álbuns, se for o caso

    if (todas.isEmpty()) {
        System.out.println("Não há músicas disponíveis no sistema.");
        return;
    }

    Collections.shuffle(todas); // embaralha

    System.out.println("\nA reproduzir músicas aleatórias:");

    for (Musica m : todas.subList(0, Math.min(5, todas.size()))) { // reproduz 5 músicas aleatórias
        m.reproduzir();
        model.getEstatisticas().registarReproducoes(u, m);
        u.setPontos(u.getPontos() + u.getPlano().calculaPontos(u));
    }
}

private void menuReproduzirPlaylist(User u, Playlist p) {
    if (!u.getPlano().podeOuvirPlaylist(p)) {
        System.out.println("O seu plano não permite ouvir esta playlist.");
        return;
    }

    boolean continuar = true;

    while (continuar) {
        System.out.println("\n--- Reprodução: " + p.getname() + " ---");

        if (!p.getmusicas().isEmpty()) {
            int atual = getIndiceAtual(p);
            System.out.println("Música atual: " + p.getmusicas().get(atual).getNome());
        } else {
            System.out.println("Playlist vazia.");
            return;
        }

        System.out.println("1 - Tocar música");
        System.out.println("2 - Avançar");
        System.out.println("3 - Retroceder");
        System.out.println("4 - Escolher música");
        System.out.println("0 - Sair da reprodução");
        System.out.print("Opção: ");
        String op = sc.nextLine();

        switch (op) {
            case "1" -> {
                u.reproduzirPlaylist(p, model.getEstatisticas());
            }
            case "2" -> avancarMusica(p);
            case "3" -> retrocederMusica(p);
            case "4" -> {
                System.out.print("Número da música (0 a " + (p.size() - 1) + "): ");
                int i = Integer.parseInt(sc.nextLine());
                escolherMusica(p, i);
            }
            case "0" -> continuar = false;
            default -> System.out.println("Opção inválida.");
        }
    }
}

private int getIndiceAtual(Playlist p) {
    if (p instanceof PlaylistPremium prem) return prem.getMusicaAtual();
    if (p instanceof PlaylistFavoritos fav) return fav.getMusicaAtual();
    return 0;
}

private void avancarMusica(Playlist p) {
    if (p instanceof PlaylistPremium prem) prem.avancar();
    if (p instanceof PlaylistFavoritos fav) fav.avancar();
}

private void retrocederMusica(Playlist p) {
    if (p instanceof PlaylistPremium prem) prem.retroceder();
    if (p instanceof PlaylistFavoritos fav) fav.retroceder();
}

private void escolherMusica(Playlist p, int i) {
    if (p instanceof PlaylistPremium prem) prem.escolher_musica(i);
    if (p instanceof PlaylistFavoritos fav) fav.escolher_musica(i);
}

private void criarPlaylistUser(User u) {
    System.out.print("Nome da Playlist: ");
    String nome = sc.nextLine();
    List<Musica> musicas = new ArrayList<>();
    boolean continuar = true;
    while (continuar) {
        Musica m = new Musica();
        System.out.print("Nome da Musica: ");
        String nomeMusica = sc.nextLine();
        m = model.getMusica(nomeMusica);
        if (m == null) {
            System.out.println("Musica Inexistente.");
        }
        else {
             musicas.add(m);
        }
        System.out.print("Deseja continuar?(s/n):  ");
        String continuacao = sc.nextLine();
        continuar = continuacao.equals("s");
    }
    if (!musicas.isEmpty()) {
        System.out.print("Playlist Pública?(s/n):  ");
        String publica = sc.nextLine();
        boolean pub = publica.equals("s");
        Playlist playlist = new PlaylistPremium(nome, musicas, pub, 0, u);
        model.adicionarPlaylist(playlist);
        if (pub) {
            model.adicionarPlaylistPublica(playlist);
        }
        u.getBiblioteca().addPlaylist(playlist);
        System.out.println("Playlist criada com sucesso.");

    }
    else{
        System.out.println("Não foi criada Playlist.");
    }
}

    private void menuBiblioteca(User u) {
        NewMenu menu = new NewMenu(new String[] {
                "Listar Playlists",
                "Adicionar Playlist",
                "Listar Álbuns",
                "Adicionar Album",
                "Ouvir Playlist",
                "Ouvir Álbum"
        });

        menu.setHandler(1, () -> listarPlaylistsUser(u));
        menu.setHandler(2, () -> adicionarPlaylistPublicaBiblioteca(u));
        menu.setHandler(3, () -> listarAlbunsUser(u));
        menu.setHandler(4, () -> adicionarAlbumBiblioteca(u));
        menu.setHandler(5, () -> ouvirPlaylistUser(u));
        menu.setHandler(6, () -> ouvirAlbumUser(u));

        menu.run();
    }

    private void listarPlaylistsUser(User u) {
        List<Playlist> playlists = u.getBiblioteca().getPlaylists();

        if (playlists.isEmpty()) {
            System.out.println("Nenhuma playlist na biblioteca.");
            return;
        }

        System.out.println("\n--- Playlists ---");
        for (Playlist p : playlists) {
            System.out.println("- " + p.getname());
        }
    }

    private void listarAlbunsUser(User u) {
        List<Album> playlists = u.getBiblioteca().getAlbums();

        if (playlists.isEmpty()) {
            System.out.println("Nenhum album na biblioteca.");
            return;
        }

        System.out.println("\n--- Albuns ---");
        for (Album p : playlists) {
            System.out.println("- " + p.getNome());
        }
    }

    private void adicionarAlbumBiblioteca(User u) {
        System.out.print("Nome do Album: ");
        String nomeAlbum = sc.nextLine();
        Album album = model.getAlbum(nomeAlbum);
        if (album == null) {
            System.out.println("Album Inexistente.");
        }
        else {
            u.getBiblioteca().addAlbum(album);
            System.out.println("Album Adicionado com sucesso.");
        }
    }

    private void ouvirPlaylistUser(User u) {
        System.out.print("Nome do Playlist: ");
        String nomePlaylist = sc.nextLine();
        Playlist p = u.getBiblioteca().getPlaylist(nomePlaylist);
        menuReproduzirPlaylist(u,p);
    }

    private void ouvirAlbumUser(User u) {
        System.out.print("Nome do Album: ");
        String nomePlaylist = sc.nextLine();
        Album p = u.getBiblioteca().getAlbum(nomePlaylist);
        menuReproduzirAlbum(u,p);
    }

    private void menuReproduzirAlbum(User u, Album p) {
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n--- Reprodução: " + p.getNome() + " ---");



            System.out.println("1 - Play");
            System.out.println("0 - Sair da reprodução");
            System.out.print("Opção: ");
            String op = sc.nextLine();

            switch (op) {
                case "1" -> {
                    u.reproduzirAlbum(p, model.getEstatisticas());
                }

                case "0" -> continuar = false;
                default -> System.out.println("Opção inválida.");
            }
        }
    }
private void tornarPlaylistPublica(User u) {
    List<Playlist> playlists = u.getBiblioteca().getPlaylists().stream()
            .filter(p -> !p.getpublica())
            .toList();


    if (playlists.isEmpty()) {
        System.out.println("Nenhuma playlist na biblioteca.");
        return;
    }

    System.out.println("\n--- Playlists ---");
    for (Playlist p : playlists) {
        System.out.println("- " + p.getname());
    }

    System.out.print("Escolha a Playlist: ");
    String op = sc.nextLine();

    Playlist p = u.getBiblioteca().getPlaylist(op);
    p.setpublica(true);
    model.adicionarPlaylistPublica(p);
    System.out.println("Playlist tornada pública com sucesso.");
}

    private void adicionarPlaylistPublicaBiblioteca(User u) {
        if (model.numPlaylistsPublicas() > 0) {
            List<Playlist> publicas = model.getPlaylistsPublicas();

            System.out.println("\nPlaylists públicas disponíveis:");
            for (int i = 0; i < publicas.size(); i++) {
                System.out.println((i + 1) + " - " + publicas.get(i).getname());
            }

            System.out.print("Escolhe o número da playlist: ");
            int escolha = Integer.parseInt(sc.nextLine());

            if (escolha < 1 || escolha > publicas.size()) {
                System.out.println("Opção inválida.");
                return;
            }

            Playlist selecionada = publicas.get(escolha - 1).clone();
            List<Playlist> playlistsUser = u.getBiblioteca().getPlaylists();

            if (playlistsUser.contains(selecionada)) {
                System.out.println("Já tens essa playlist na tua biblioteca.");
            } else {
                u.getBiblioteca().addPlaylist(selecionada);
                System.out.println("Playlist adicionada com sucesso à tua biblioteca.");
            }
        } else {
            System.out.println("Não existem playlists públicas disponíveis.");
        }
    }

    private void verPlaylistsRecomendadas(User u) {
        System.out.print("Indica o género musical que pretendes: ");
        String genero = sc.nextLine().trim().toLowerCase();

        System.out.print("Queres definir um tempo máximo em minutos? (s/n): ");
        String usarTempo = sc.nextLine().trim().toLowerCase();

        boolean usarLimiteTempo = usarTempo.equals("s");
        int tempoMaximoSegundos = 0;
        int maxMusicas = 20;

        if (usarLimiteTempo) {
            System.out.print("Indica o tempo máximo (em minutos): ");
            int tempoMinutos = Integer.parseInt(sc.nextLine());
            tempoMaximoSegundos = tempoMinutos * 60;
        }

        List<Musica> todas = new ArrayList<>();

        // Músicas dos álbuns
        for (Album a : u.getBiblioteca().getAlbums()) {
            for (Musica m : a.getMusicas()) {
                if (m.getGenero().equalsIgnoreCase(genero) && !todas.contains(m)) {
                    todas.add(m.clone());
                }
            }
        }

        // Músicas das playlists
        for (Playlist p : u.getBiblioteca().getPlaylists()) {
            for (Musica m : p.getmusicas()) {
                if (m.getGenero().equalsIgnoreCase(genero) && !todas.contains(m)) {
                    todas.add(m.clone());
                }
            }
        }

        if (todas.isEmpty()) {
            System.out.println("Nenhuma música encontrada com o género \"" + genero + "\".");
            return;
        }

        // Ordena por duração crescente
        todas.sort(Comparator.comparingInt(Musica::getDuracao));

        List<Musica> recomendadas = new ArrayList<>();
        int tempoAtual = 0;

        for (Musica m : todas) {
            if (usarLimiteTempo) {
                if (tempoAtual + m.getDuracao() <= tempoMaximoSegundos) {
                    recomendadas.add(m);
                    tempoAtual += m.getDuracao();
                }
            } else {
                if (recomendadas.size() < maxMusicas) {
                    recomendadas.add(m);
                } else {
                    break;
                }
            }
        }

        if (recomendadas.isEmpty()) {
            System.out.println("Nenhuma música encontrada dentro dos critérios.");
            return;
        }

        String baseNome = "Favoritos_" + genero;
        String nomePlaylist = baseNome;
        int contador = 1;

        List<Playlist> playlists = u.getBiblioteca().getPlaylists();
        boolean nomeExiste = true;

        while (nomeExiste) {
            nomeExiste = false;
            for (Playlist p : playlists) {
                if (p.getname().equalsIgnoreCase(nomePlaylist)) {
                    nomePlaylist = baseNome + "_" + contador;
                    contador++;
                    nomeExiste = true;
                    break;
                }
            }
        }

        PlaylistFavoritos playlist = new PlaylistFavoritos(
                nomePlaylist,
                recomendadas,
                false, // privada
                0,
                u
        );

        u.getBiblioteca().addPlaylist(playlist);

        System.out.println("Playlist \"" + nomePlaylist + "\" criada com " + recomendadas.size() +
                " músicas. " + (usarLimiteTempo? "Duração total: " + tempoAtual/60 + " minutos." : ""));
    }


}
