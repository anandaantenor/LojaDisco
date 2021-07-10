//package br.com.letscode.service;
//
//import br.com.letscode.dominio.AlbumParaTeste;
//import jakarta.inject.Inject;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.Arrays;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//
//class Scratch {
//    @Inject
//    private static AlbumParaTeste album;
//
//    public static void main(String[] args) {
//
//
//        String path = "C:/Users/DELL/Documents/LojaDeDiscos/src/main/java/arquivo/discos.csv";
//
//        String line;
//        printAllAlbuns(path);
//
//        findAlbumCatalog(path, "La vem a Morte");
//        findArtistCatalog(path, "Nirvana");
//        findTrackCatalog(path, "Onda Negra");
//        findMatchingStringInCsv(path, "Nirvana");
//
//
//        try {
//            BufferedReader br = new BufferedReader(new FileReader(path));
//            while ((line = br.readLine()) != null) {
//                String[] values = line.split(",");
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public static String getArtistaFromCsv(String[] values) {
//        return values[0];
//    }
//
//
//    public static String getAlbumNameFromCsv(String[] values) {
//        return values[1];
//    }
//
//
//    public static String getGeneroFromCsv(String[] values) {
//        return values[2];
//    }
//
//    public static double getPrecoFromCsv(String[] values) {
//        String preco = values[3];
//        return Double.parseDouble(preco);
//    }
//
//
//    public static int getEstoqueFromCsv(String[] values) {
//        String estoque = values[4];
//        return Integer.parseInt(estoque);
//    }
//
//
//    public static int getNumeroDeFaixasFromCsv(String[] values) {
//        String numeroDeFaixas = values[5];
//        return Integer.parseInt(numeroDeFaixas);
//    }
//
//    public static String[] getFaixasArrayFromCsv(String[] values) {
//        int numeroDeFaixas = getNumeroDeFaixasFromCsv(values);
//        String[] faixas = new String[numeroDeFaixas];
//        int j = 6;
//        for (int i = 0; i < numeroDeFaixas; i++, j = j + 2) {
//            faixas[i] = values[j];
//        }
//        return faixas;
//    }
//
//    public static String[] getDuracaoDeFaixasArrayFromCsv(String[] values) {
//        int numeroDeFaixas = getNumeroDeFaixasFromCsv(values);
//        String[] duracaoDeFaixas = new String[numeroDeFaixas];
//        int j = 7;
//        for (int i = 0; i < numeroDeFaixas; i++, j = j + 2) {
//            duracaoDeFaixas[i] = values[j];
//        }
//        return duracaoDeFaixas;
//    }
//
//    public static boolean findByArtistName(String searchedArtistName, String[] values) {
//        return (getArtistaFromCsv(values).equals(searchedArtistName));
//    }
//
//    public static boolean findByAlbumName(String searchedAlbumName, String[] values) {
//        return (getAlbumNameFromCsv(values).equals(searchedAlbumName));
//    }
//
//    public static boolean findByGenreName(String searchedGenre, String[] values) {
//        return (getGeneroFromCsv(values).equals(searchedGenre));
//    }
//
//    public static boolean findByTrackName(String searchedTrack, String[] values) {
//        return Arrays.stream(getFaixasArrayFromCsv(values)).anyMatch(searchedTrack::equals);
//    }
//
//    public static void findMatchingStringInCsv(String path, String searchedString) {
//        findArtistCatalog(path, searchedString);
//        findAlbumCatalog(path, searchedString);
//        findTrackCatalog(path, searchedString);
//        findGenreCatalog(path, searchedString);
//    }
//
//    public static void findAlbumCatalog(String path, String attemptAlbumName) {
//        String line;
//        boolean notFound = true;
//        try {
//            BufferedReader br = new BufferedReader(new FileReader(path));
//            while ((line = br.readLine()) != null) {
//                String[] values = line.split(",");
//                if (findByAlbumName(attemptAlbumName, values)) {
//                    System.out.printf("\nFoi encontrado um disco com esse nome!\n" +
//                            "Disco: %s | Artista:%s\nPreço:R$%.2f\n", getAlbumNameFromCsv(values),
//                            getArtistaFromCsv(values), getPrecoFromCsv(values));
//                    notFound = false;
//                }
//            }
//            if (notFound) {
//                System.out.println();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public static void findArtistCatalog(String path, String attemptArtistName) {
//        String line;
//        boolean notFound = true;
//        try {
//            BufferedReader br = new BufferedReader(new FileReader(path));
//            while ((line = br.readLine()) != null) {
//                String[] values = line.split(",");
//                if (findByArtistName(attemptArtistName, values)) {
//                    System.out.printf("\nFoi encontrado um disco do artista %s!\nDisco: %s | Preço:R$%.2f\n", attemptArtistName, getAlbumNameFromCsv(values), getPrecoFromCsv(values));
//                    notFound = false;
//                }
//            }
//            if (notFound) {
//                System.out.println();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void findTrackCatalog(String path, String attemptTrackName) {
//        String line;
//        boolean notFound = true;
//        try {
//            BufferedReader br = new BufferedReader(new FileReader(path));
//            while ((line = br.readLine()) != null) {
//                String[] values = line.split(",");
//                if (findByTrackName(attemptTrackName, values)) {
//                    System.out.printf("\nFoi encontrado um disco do artista %s " +
//                            "que contém a música %s!\nDisco: %s | Preço:R$%.2f\n",
//                            getArtistaFromCsv(values), attemptTrackName,
//                            getAlbumNameFromCsv(values), getPrecoFromCsv(values));
//                    notFound = false;
//                }
//            }
//            if (notFound) {
//                System.out.println();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void findGenreCatalog(String path, String attemptGenreName) {
//        String line;
//        boolean notFound = true;
//        try {
//            BufferedReader br = new BufferedReader(new FileReader(path));
//            while ((line = br.readLine()) != null) {
//                String[] values = line.split(",");
//                if (findByGenreName(attemptGenreName, values)) {
//                    System.out.printf("\nFoi encontrado um disco de %s \n" +
//                            "Artista: %s | Disco: %s | Preço:R$%.2f\n",
//                            attemptGenreName, getArtistaFromCsv(values),
//                            getAlbumNameFromCsv(values), getPrecoFromCsv(values));
//                    notFound = false;
//                }
//            }
//            if (notFound) {
//                System.out.println();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public static void printAllAlbuns(String path) {
//        String line;
//        try {
//            BufferedReader br = new BufferedReader(new FileReader(path));
//            System.out.println("----CATÁLOGO DE DISCOS----");
//            while ((line = br.readLine()) != null) {
//                String[] values = line.split(",");
//                System.out.printf("Album: %s do arista: %s\n",
//                        getArtistaFromCsv(values), getAlbumNameFromCsv(values));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static int countAlbumCatalog(String path) throws IOException {
//        int numberOfAlbums = 0;
//        BufferedReader br = new BufferedReader(new FileReader(path));
//        while ((br.readLine()) != null) {
//            numberOfAlbums++;
//        }
//        return numberOfAlbums;
//    }
//
//
//    public static AlbumParaTeste createAlbumFromCsV(String[] values) {
//        album.setArtista(getArtistaFromCsv(values));
//        album.setNome(getAlbumNameFromCsv(values));
//        album.setGenero(getGeneroFromCsv(values));
//        album.setPreco(getPrecoFromCsv(values));
//        album.setQuantidadeEmEstoque(getEstoqueFromCsv(values));
//        album.setFaixas(getFaixasArrayFromCsv(values));
//        album.setDuracaoDeFaixas(getDuracaoDeFaixasArrayFromCsv(values));
//        return album;
//    }
//
//    public static AlbumParaTeste[] transformCsvIntoArrayOfAlbum(String path) throws IOException {
//        AlbumParaTeste[] albumColection = new AlbumParaTeste[countAlbumCatalog(path)];
//        String line = "";
//        BufferedReader br = new BufferedReader(new FileReader(path));
//        for (int i = 0; i < albumColection.length; i++) {
//            String[] values = line.split(",");
//            albumColection[i] = createAlbumFromCsV(values);
//        }
//        return albumColection;
//    }
//
//    public static void addAlbumToCsv(String path, AlbumParaTeste disco) throws IOException {
//        String[] values = new String[disco.getNumeroDeFaixas() * 2 + 6];
//        StringBuilder line = new StringBuilder();
//        ;
//
//        FileWriter writer = new FileWriter(path);
//        /**
//         * O array values é o que vai ser passado, no final, como uma linha para o csv.
//         * Como os discos tem números de faixas variáveis, ele deve ter um tamanho de 6 (os parâmetros fixos dos discos)
//         * mais o tamanho do vetor de Strings das faixas e o outro vetor com o comprimento das faixas
//         */
//
//        values[0] = disco.getArtista();
//        values[1] = disco.getNome();
//        values[2] = disco.getGenero();
//        values[3] = String.valueOf(disco.getPreco());
//        values[4] = String.valueOf(disco.getQuantidadeEmEstoque());
//        values[5] = String.valueOf(disco.getNumeroDeFaixas());
//        String[] faixas = disco.getFaixas();
//        String[] duracao = disco.getDuracaoDeFaixas();
//
//        int j = 0;
//        int k = 0;
//        for (int i = 0; i < disco.getNumeroDeFaixas() * 2 + 6; i++) {
//            if (i % 2 == 0) {
//                values[i] = faixas[j];
//                j++;
//            } else {
//                values[i] = duracao[k];
//                k++;
//            }
//            line.append(values[i]);
//            line.append(",");
//        }
//        writer.append(line.toString());
//        writer.close();
//    }
//
//    public static void removerEstoque(AlbumParaTeste disco) {
//        disco.setQuantidadeEmEstoque(disco.getQuantidadeEmEstoque() - 1);
//    }
//}
//
//
//
//
//
//
//
//
