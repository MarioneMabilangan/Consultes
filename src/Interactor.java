import java.util.Scanner;

public class Interactor {
    Scanner escaner = new Scanner(System.in);
    int nsel;
    String titulo;
    String dataEstrena;
    String pais;
    public int inicio(){
        System.out.println("Que buscas?");
        System.out.println("1. Peliculas estrenadas por año");
        System.out.println("2. Pelicula del director");
        System.out.println("3. Insertar peliculas");

        nsel = escaner.nextInt();

        return nsel;
    }

    public String director(){
        System.out.println("Nom director:");
        escaner.nextLine();
        return escaner.nextLine();
    }

    public void film(){
        System.out.println("Nom pelicula: ");
        escaner.nextLine();
        titulo = escaner.nextLine();
        System.out.println("Fecha estreno: ");
        dataEstrena = escaner.nextLine();
        System.out.println("Pais: ");
        pais = escaner.nextLine();
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDataEstrena() {
        return dataEstrena;
    }

    public String getPais() {
        return pais;
    }
}
