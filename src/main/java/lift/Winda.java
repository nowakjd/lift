/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lift;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 *
 * @author Adam
 *
 * Klasa windy... no i tam jej metody
 *
 *
 */
public class Winda {

    int pietro;
    int kierunek;
    int ilosc_pasazerow;
    List<Pasazer> pasazerowie;
    int ilosc_pieter;
    int ilosc_obsluzonych_pas;
    double laczny_czas;
    double sredni_czas;

    public Winda() {
            pasazerowie = new ArrayList<Pasazer>();
            init();
    }

    private void init() {
        Zmienne.szybkosc = 3;
        Zmienne.otwarte = 4;
        Zmienne.opoznienie = 4;
    }

    public void dodajPasazera(int ilosc, int start, int cel){

        pasazerowie.add(new Pasazer(ilosc, start, cel));

    }

    public void ustawPietro(int nr_pietro){

        this.pietro = nr_pietro;

    }

    public int pietroOut(){

        return this.pietro;

    }

    public void dodajPasazera(Pasazer nowy){

        pasazerowie.add(new Pasazer(nowy));

    }


    @Override
    public String toString(){

        String tekst = "";

           for (Iterator<Pasazer> iterator = pasazerowie.iterator(); iterator.hasNext();) {
                Pasazer obiekt = (Pasazer) iterator.next();
                tekst += "test " + obiekt.iloscOut() + " " + obiekt.startOut() + " " + obiekt.celOut() + " " + obiekt.kierunekOut() + "\n";
           }

        return tekst;
    }

    public ArrayList<String> toFile(){

        ArrayList<String> tekst = new ArrayList();

           for (Iterator<Pasazer> iterator = pasazerowie.iterator(); iterator.hasNext();) {
                Pasazer obiekt = (Pasazer) iterator.next();
                tekst.add("   - ilosc pas.: " + obiekt.iloscOut() + " P. pocz.: " + obiekt.startOut() + " P. konc.: " + obiekt.celOut() + " P. do pokonania: " + obiekt.kierunekOut());
           }

        return tekst;
    }
    public void zapiszDane(BufferedWriter plik, listaPieter lista, int nrWindy) {
        try {
            plik.write("WINDA " + nrWindy + " - DANE WEJŚCIOWE: ");
            plik.newLine();
            plik.write("Ilosc pieter w budynku: " + Zmienne.iloscPieter);
            plik.newLine();
            plik.write("Parametry ruchu windy:");
            plik.newLine();
            plik.write("   - szybkosc przejazdu miedzy pietrami:  " + Zmienne.szybkosc + "s");
            plik.newLine();
            plik.write("   - przewidywany czas ruchu pojedynczego pasazera: " + Zmienne.otwarte + "s");
            plik.newLine();
            plik.write("   - opoznienie zamkniecia drzwi: " + Zmienne.opoznienie + "s");
            plik.newLine();
            plik.write("Pasazerowie: ");
            plik.newLine();
            ArrayList <String> pas = toFile();
            int pasazerowLacznie = 0;
            for (int i = 0; i < pasazerowie.size();i ++) {
                pasazerowLacznie += pasazerowie.get(i).ilosc;
                plik.write(pas.remove(0));
                plik.newLine();
            }
            plik.write("Ilosc obsluzonych grup pasazerow: " + pasazerowie.size());
            plik.newLine();
            int pieterLacznie = 0;
            for (int i = 0; i < lista.lista.size(); i++) {
                if (lista.lista.get(i).numerWindy == nrWindy)
                    pieterLacznie++;
            }
            plik.write("Ilosc przejechanych pieter: " + pieterLacznie);
            ilosc_pieter = pieterLacznie;
            plik.newLine();
            double czasLacznie = 0;
            czasLacznie = pasazerowLacznie * Zmienne.otwarte + pasazerowie.size() * Zmienne.opoznienie + pieterLacznie * Zmienne.szybkosc;
            plik.write("Laczna liczba obsluzonych pasazerow: " + pasazerowLacznie);
            plik.newLine();
            ilosc_obsluzonych_pas = pasazerowLacznie;
            plik.write("Laczny czas obslugi pasazerow: " + czasLacznie + "s");
            laczny_czas = czasLacznie;
            double sredni = 0;
            sredni = czasLacznie/pasazerowLacznie;
            sredni_czas = sredni;
            plik.newLine();
            plik.write("Czas sredni obslugi jednego pasazera: ok. " + sredni + "s");

        } catch (IOException e) {}
    }

}
