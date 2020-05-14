/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lift;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author Adam
 *
 * Przykładowy algorytm... Wiem że trzeba go poprawić ale to potem. Na razie wysyłam wam to żebyście nie musieli czekać na mnie.
 * W planach mam tylko zmianę tego pliku więc najprawdopodobniej nie będziemy sobie wchodzić w drogę.
 * w razie czego się zgadamy(jakby trzeba było dodać jakiś argument do funkcji czy co).
 * wiem że powinienem oddzielić jakoś zapisywanie do pliku od ścisłego algorytmu ale nie dałem rady bo mój algorytm
 * co chwile wykrzyczał się na jakimś dziwnym wyjątku i dla tego jest taki mało spójny.
 * Na razie róbcie swoje. Ja mam nadzieje że jeszcze to poprawie. Zresztą nie wiem jak Wy ale ja byłbym za tym żeby zapytać się go
 * o dodatkowy tydzień a na razie pokazać że coś jest
 * 
 *
 */
public class MyAlgorytm1 implements Algorytm{

    public void sortuj(ArrayList<Pasazer> listaPasazerow, ArrayList<Pasazer> gora, ArrayList<Pasazer> dol, Winda winda1) {


        Queue kolejka = new LinkedList();
        Stack<Pasazer> kierunek_u = new Stack<Pasazer>();

        Collections.sort( listaPasazerow, new MyComparator());
        for (Iterator<Pasazer> iterator = listaPasazerow.iterator(); iterator.hasNext();) {
            Pasazer obiekt = (Pasazer) iterator.next();
            if(obiekt.kierunek > 0){
                kolejka.add(new Pasazer(obiekt.ilosc, obiekt.start, obiekt.cel));
                gora.add(new Pasazer(obiekt.ilosc, obiekt.start, obiekt.cel));
            }else{
                kierunek_u.push(new Pasazer(obiekt.ilosc, obiekt.start, obiekt.cel));
                dol.add(new Pasazer(obiekt.ilosc, obiekt.start, obiekt.cel));
            }
        }
        
        Collections.sort( gora, new MyComparator2());

        Collections.sort( dol, new MyComparator3());

        while (!kolejka.isEmpty()) {
            winda1.dodajPasazera((Pasazer)kolejka.remove());
        }
 
        while(!kierunek_u.empty()){
            winda1.dodajPasazera(kierunek_u.pop());
        }
    }

    public void sortuj(ArrayList<Pasazer> listaPasazerow, ArrayList<Pasazer> gora, ArrayList<Pasazer> dol, Winda winda1, Winda winda2) {
        Queue kolejka = new LinkedList();
        Stack<Pasazer> kierunek_u = new Stack<Pasazer>();

        Collections.sort( listaPasazerow, new MyComparator());
        for (Iterator<Pasazer> iterator = listaPasazerow.iterator(); iterator.hasNext();) {
            Pasazer obiekt = (Pasazer) iterator.next();
            if(obiekt.kierunek > 0){
                kolejka.add(new Pasazer(obiekt.ilosc, obiekt.start, obiekt.cel));
                gora.add(new Pasazer(obiekt.ilosc, obiekt.start, obiekt.cel));
            }else{
                kierunek_u.push(new Pasazer(obiekt.ilosc, obiekt.start, obiekt.cel));
                dol.add(new Pasazer(obiekt.ilosc, obiekt.start, obiekt.cel));
            }
        }

        Collections.sort( gora, new MyComparator2());

        Collections.sort( dol, new MyComparator3());

        while (!kolejka.isEmpty()) {
            winda1.dodajPasazera((Pasazer)kolejka.remove());
        }

        while(!kierunek_u.empty()){
            winda2.dodajPasazera(kierunek_u.pop());
        }
    }





    public void start(Winda winda1, ArrayList<Pasazer> gora, ArrayList<Pasazer> dol, int iloscPienter, listaPieter lista) {

        int i =0;
        int index = 0;
        int index2 = 0;
        gora.add(new Pasazer(0, 0, 0));
        winda1.ustawPietro(0);

        try {
                BufferedWriter plik = new BufferedWriter(new FileWriter("zapis.txt"));

            for (Iterator<Pasazer> iterator = winda1.pasazerowie.iterator(); iterator.hasNext();) {
                Pasazer obiekt = (Pasazer) iterator.next();
                if(obiekt.kierunek > 0){
                    for(i = winda1.pietro; i < obiekt.start; i++){
                        if(gora.get(index).cel == i){
                            lista.dodaj(new Pietro(i, 1, 0, gora.get(index).ilosc, true));
                            plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                            //plik.write("Winda jest na pietrze " + i + "wysiada z niej " + gora.get(index).iloscOut() + " osob i jedzie do gory");
                            plik.newLine();
                            //System.out.print("aaaaaWinda jest na pietrze " + i + "wysiada z niej " + gora.get(index).iloscOut() + " osob i jedzie do gory\n");
                            index++;
                        }else{
                        lista.dodaj(new Pietro(i, 1, 0, 0, true));
                        plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                        //plik.write("Winda jest na pietrze " + i + " i jedzie do gory");
                        plik.newLine();
//                        System.out.print("bbbbbWinda jest na pietrze " + i + " i jedzie do gory\n");
                        }
                    }
                    if(gora.get(index).cel == obiekt.start){
                            lista.dodaj(new Pietro(obiekt.start, 1, obiekt.ilosc, gora.get(index).ilosc, true));
                            plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                            //plik.write("Winda jest na pietrze " + obiekt.start + " wysiada z niej " + gora.get(index).iloscOut() + " osob oraz zabiera " + obiekt.iloscOut() + " pasazerow i jedzie do gory");
                            plik.newLine();
//                            System.out.print("ccccccccccWinda jest na pietrze " + obiekt.start + " wysiada z niej " + gora.get(index).iloscOut() + " osob oraz zabiera " + obiekt.iloscOut() + " pasazerow i jedzie do gory\n");
                            index++;
                        }else{
                        lista.dodaj(new Pietro(obiekt.start, 1, obiekt.ilosc, 0, true));
                        plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                        //plik.write("Winda jest na pietrze " + obiekt.start + " zabiera " + obiekt.iloscOut() + " pasazerow i jedzie do gory");
                        plik.newLine();
//                        System.out.print("ddddddddddWinda jest na pietrze " + obiekt.start + " zabiera " + obiekt.ilosc + " pasazerow i jedzie do gory\n");
                    }
                    winda1.ustawPietro(obiekt.start + 1);
                }
            }
            for (Iterator<Pasazer> iterator = winda1.pasazerowie.iterator(); iterator.hasNext();) {
                Pasazer obiekt = (Pasazer) iterator.next();
                for(i = winda1.pietro; i < obiekt.start; i++){
                        if(gora.get(index ).cel == i){
                            lista.dodaj(new Pietro(i, 1, 0, gora.get(index).ilosc, true));
                            plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                            //plik.write("Winda jest na pietrze " + i + " wysiada z niej " + gora.get(index).iloscOut() + " osob i jedzie do gory");
                            plik.newLine();
//                            System.out.print("eeeeeeeeweeWinda jest na pietrze " + i + " wysiada z niej " + gora.get(index).iloscOut() + " osob i jedzie do gory\n");
                            index++;
                        }else{
                        lista.dodaj(new Pietro(i, 1, 0, 0, true));
                        plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                        //plik.write("Winda jest na pietrze " + i + " i jedzie do gory");
                        plik.newLine();
//                        System.out.print("ffffffffffWinda jest na pietrze " + i + " i jedzie do gory\n");
                    }
                    }
                if(obiekt.kierunek < 0){
                    for(i = winda1.pietro; i > obiekt.start; i--){
                        if(dol.get(index2).cel == i){
                            lista.dodaj(new Pietro(i, 1, 0, dol.get(index2).ilosc, false));
                            plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                            //plik.write("Winda jest na pietrze " + i + " wysiada z niej " + dol.get(index2).iloscOut() + " osob i jedzie w dol");
                            plik.newLine();
                            //System.out.print(i + " dol wysiada\n");
                            index2++;
                        }else{
                        lista.dodaj(new Pietro(i, 1, 0, 0, false));
                        plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                        //plik.write("Winda jest na pietrze " + i + " i jedzie w dol");
                        plik.newLine();
                        //System.out.print(i + " dol\n");
                        }
                    }
                    if(dol.get(index2).cel == obiekt.start){
                            lista.dodaj(new Pietro(obiekt.start, 1, obiekt.ilosc, dol.get(index2).ilosc, false));
                            plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                            //plik.write("Winda jest na pietrze " + obiekt.start + " wysiada z niej " + dol.get(index2).iloscOut() + " osob oraz zabiera " + obiekt.iloscOut() + " pasazerow i jedzie w dol");
                            plik.newLine();
                            //System.out.print(obiekt.start + " pauza wysiada\n");
                            index2++;
                        }else{
                    lista.dodaj(new Pietro(obiekt.start, 1, obiekt.ilosc, 0, false));
                    plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                    //plik.write("Winda jest na pietrze " + obiekt.start + " zabiera " + obiekt.iloscOut() + " pasazerow i jedzie w dol");
                    plik.newLine();
                    //System.out.print(obiekt.start + " pauza\n");
                    }
                    winda1.ustawPietro(obiekt.start - 1);
                }
            }
            i--;
            for (Iterator<Pasazer> iterator = dol.iterator(); iterator.hasNext();) {
                Pasazer obiekt = (Pasazer) iterator.next();
                for(int j =i; j >= obiekt.cel; j--){
                    if(dol.get(index2).cel == i){
                            lista.dodaj(new Pietro(i, 1, 0, dol.get(index2).ilosc, false));
                            plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                            //plik.write("Winda jest na pietrze " + i + " wysiada z niej " + dol.get(index2).iloscOut() + " osob i jedzie w dol");
                            plik.newLine();
                            //System.out.print(j + " dol wysiada\n");
                            index2++;
                        }else{
                    lista.dodaj(new Pietro(i, 1, 0, 0, false));
                    plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                    //plik.write("Winda jest na pietrze " + i + " i jedzie w dol");
                    plik.newLine();
                    //System.out.print(j + " dol\n");
                    }
                    i = (j - 1);
                }
            }

        plik.close();


        }catch (IOException e) {

        }


        }


    public void start(Winda winda1, Winda winda2, ArrayList<Pasazer> gora, ArrayList<Pasazer> dol, int iloscPienter, listaPieter lista) {
        int i =0;
        int index = 0;
        int index2 = 0;
        gora.add(new Pasazer(0, 0, 0));
        winda1.ustawPietro(0);
        winda1.ustawPietro(iloscPienter);

        try {
                BufferedWriter plik = new BufferedWriter(new FileWriter("zapis.txt"));

            for (Iterator<Pasazer> iterator = winda1.pasazerowie.iterator(); iterator.hasNext();) {
                Pasazer obiekt = (Pasazer) iterator.next();
                if(obiekt.kierunek > 0){
                    for(i = winda1.pietro; i < obiekt.start; i++){
                        if(gora.get(index).cel == i){
                            lista.dodaj(new Pietro(i, 1, 0, gora.get(index).ilosc, true));
                            plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                            //plik.write("Winda1 jest na pietrze " + i + "wysiada z niej " + gora.get(index).iloscOut() + " osob i jedzie do gory");
                            plik.newLine();
                            //System.out.print(i + " gora wysiada\n");
                            index++;
                            //System.out.print(index + "index\n");
                        }else{
                        lista.dodaj(new Pietro(i, 1, 0, 0, true));
                        plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                        //plik.write("Winda1 jest na pietrze " + i + " i jedzie do gory");
                        plik.newLine();
                        //System.out.print(i + " gora\n");
                        }
                    }
                    if(gora.get(index).cel == obiekt.start){
                            lista.dodaj(new Pietro(obiekt.start, 1, obiekt.ilosc, gora.get(index).ilosc, true));
                            plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                            //plik.write("Winda1 jest na pietrze " + obiekt.start + " wysiada z niej " + gora.get(index).iloscOut() + " osob oraz zabiera " + obiekt.iloscOut() + " pasazerow i jedzie do gory");
                            plik.newLine();
                            //System.out.print(obiekt.start + " pauza wysiada\n");
                            index++;
                        }else{
                        lista.dodaj(new Pietro(obiekt.start, 1, obiekt.ilosc, 0, true));
                        plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                        //plik.write("Winda1 jest na pietrze " + obiekt.start + " zabiera " + obiekt.iloscOut() + " pasazerow i jedzie do gory");
                            plik.newLine();
                    //System.out.print(obiekt.start + " pauza\n");
                    }
                    winda1.ustawPietro(obiekt.start + 1);
                }
            }
            for (Iterator<Pasazer> iterator = winda1.pasazerowie.iterator(); iterator.hasNext();) {
                Pasazer obiekt = (Pasazer) iterator.next();
                for(i = winda1.pietro; i <= gora.get(gora.size()-2).cel; i++){
                        if(gora.get(index ).cel == i){
                            lista.dodaj(new Pietro(i, 1, 0, gora.get(index).ilosc, true));
                            plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                            //plik.write("Winda1 jest na pietrze " + i + " wysiada z niej " + gora.get(index).iloscOut() + " osob i jedzie do gory");
                            plik.newLine();
                            //System.out.print(i + " gora wysiada\n");
                            winda1.ustawPietro(i);
                            index++;
                        }else{
                        lista.dodaj(new Pietro(i, 1, 0, 0, true));
                        plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                        //plik.write("Winda1 jest na pietrze " + i + " i jedzie do gory");
                        plik.newLine();
                        //System.out.print(i + " gora\n");
                        winda1.ustawPietro(i);
                    }
                    }
            }
                
             for (Iterator<Pasazer> iterator = winda2.pasazerowie.iterator(); iterator.hasNext();) {
                 Pasazer obiekt = (Pasazer) iterator.next();
                if(obiekt.kierunek < 0){
                    for(i = winda2.pietro; i > obiekt.start; i--){
                        
                        if(dol.get(index2).cel == i){
                            lista.dodaj(new Pietro(i, 2, 0, dol.get(index2).ilosc, false));
                            plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                            //plik.write("Winda2 jest na pietrze " + i + " wysiada z niej " + dol.get(index2).iloscOut() + " osob i jedzie w dol");
                            plik.newLine();
                            //System.out.print(i + " dol wysiada\n");
                            index2++;
                        }else{
                        lista.dodaj(new Pietro(i, 2, 0, 0, false));
                        plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                        //plik.write("Winda2 jest na pietrze " + i + " i jedzie w dol");
                        plik.newLine();
                        //System.out.print(i + " dol\n");
                        }
                    }
                    if(dol.get(index2).cel == obiekt.start){
                            lista.dodaj(new Pietro(obiekt.start, 2, obiekt.ilosc, dol.get(index2).ilosc, false));
                            plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                            //plik.write("Winda2 jest na pietrze " + obiekt.start + " wysiada z niej " + dol.get(index2).iloscOut() + " osob oraz zabiera " + obiekt.iloscOut() + " pasazerow i jedzie w dol");
                            plik.newLine();
                            //System.out.print(obiekt.start + " pauza wysiada\n");
                            index2++;
                        }else{
                    lista.dodaj(new Pietro(obiekt.start, 2, obiekt.ilosc, 0, false));
                    plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                    //plik.write("Winda2 jest na pietrze " + obiekt.start + " zabiera " + obiekt.iloscOut() + " pasazerow i jedzie w dol");
                    plik.newLine();
                    //System.out.print(obiekt.start + " pauza\n");
                    }
                    winda2.ustawPietro(obiekt.start - 1);
                }
            }
            i--;
            for (Iterator<Pasazer> iterator = dol.iterator(); iterator.hasNext();) {
                Pasazer obiekt = (Pasazer) iterator.next();
                for(int j =i; j >= obiekt.cel; j--){
                    if(dol.get(index2).cel == i){
                            lista.dodaj(new Pietro(i, 2, 0, dol.get(index2).ilosc, false));
                            plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                            //plik.write("Winda2 jest na pietrze " + i + " wysiada z niej " + dol.get(index2).iloscOut() + " osob i jedzie w dol");
                            plik.newLine();
                            //System.out.print(j + " dol wysiada\n");
                            index2++;
                        }else{
                    lista.dodaj(new Pietro(i, 2, 0, 0, false));
                    plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                    //plik.write("Winda2 jest na pietrze " + i + " i jedzie w dol");
                    plik.newLine();
                    //System.out.print(j + " dol\n");
                    }
                    i = (j - 1);
                }
            }

        plik.close();


        }catch (IOException e) {

        }
    }

    


}
