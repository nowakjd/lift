/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lift;

import java.util.ArrayList;

/**
 *
 * @author Adam
 *
 *interfejs do naszych algorytmów. Wszystkie nasze algorytmy muszą go implementować.
 *ma dwa zestawy funkcji. Dla jednej i dla dwóch wind.
 *powinno się trochę te funkcje porozbijać ale nie mam już na to siły
 *teoretycznie od początku miało być robione porządnie ale w miedzy czasie z powodu braku czasu zacząłem robić kod przyrostowo wiec wiecie jak to potem wygląda
 *
 *
 */
public interface Algorytm {
    //sortowanie pasazerow po kolejnosci ich obsłużenia
    public void sortuj(ArrayList<Pasazer> listaPasazerow, ArrayList<Pasazer> gora, ArrayList<Pasazer> dol, Winda winda1);
    //sortowanie pasazerow po kolejnosci ich obsłużenia i podział na dwie windy
    public void sortuj(ArrayList<Pasazer> listaPasazerow, ArrayList<Pasazer> gora, ArrayList<Pasazer> dol, Winda winda1, Winda winda2);
    //startowanie algorytmu obsługi pasazerów
    public void start(Winda winda1, ArrayList<Pasazer> gora, ArrayList<Pasazer> dol, int iloscPienter, listaPieter lista);
    //startowanie algorytmu obsługi pasazerów dla dwoch wind
    public void start(Winda winda1, Winda winda2, ArrayList<Pasazer> gora, ArrayList<Pasazer> dol, int iloscPienter, listaPieter lista);

}
