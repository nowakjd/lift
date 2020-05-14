package lift;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Maciek
 */

public class MyAlgorytm3 implements Algorytm {
    /* Wyszukuje pietro, na ktorym jest najwieksza ilosc pasazerow */
    private int getMost(ArrayList<Pasazer> listaPasazerow, ArrayList<Pasazer> odwiedzone) {
        int most = 0;
        int i;
        for (i = 0; i < listaPasazerow.size(); i++) {
            if((listaPasazerow.get(i).ilosc > listaPasazerow.get(most).ilosc || odwiedzone.contains(listaPasazerow.get(most))) && !odwiedzone.contains(listaPasazerow.get(i))) {
               most = i;
            }
            /*jezeli na dwóch piętrach jest największa ilość pasażerów wybieramy te bliższe aktualnej lokalizacji windy*/
            if((listaPasazerow.get(i).ilosc == listaPasazerow.get(most).ilosc || odwiedzone.contains(listaPasazerow.get(most))) && !odwiedzone.isEmpty() && !odwiedzone.contains(listaPasazerow.get(i))) {
                int a = odwiedzone.get(odwiedzone.size()-1).cel - listaPasazerow.get(i).start;
                int b = odwiedzone.get(odwiedzone.size()-1).cel - listaPasazerow.get(most).start;
                if (a > 0 && b > 0 && a < b)
                        most = i;
                else if(a < 0 && b < 0 && a > b)
                        most = i;
                else if(a < 0 && b > 0 && a > -b)
                        most = i;
                else if(a > 0 && b < 0 && a < -b)
                        most = i;
            }
        }

        return most;
    }
    /* Sortuje kolejne pietra wg kolejnosci odwiedzania (kryterium wyboru pietra jest ilosc pasazerow) */
    public void sortuj(ArrayList<Pasazer> listaPasazerow, ArrayList<Pasazer> gora, ArrayList<Pasazer> dol, Winda winda1) {

        Queue kolejka = new LinkedList();
        ArrayList<Pasazer> odwiedzone = new ArrayList<Pasazer>();
        /*sortowanie w kolejnosci wg pieter */
        Collections.sort(listaPasazerow, new MyComparator23());
        /*sprawdzanie kolejnych pasazerow */
        for (int i = 0; i < listaPasazerow.size(); i++) {
            int k = getMost(listaPasazerow, odwiedzone);
            Pasazer p = listaPasazerow.get(k);
            kolejka.add(new Pasazer(p.ilosc, p.start, p.cel));
            /*przy kolejnym poszukiwaniu najwiekszej ilosci pasazerow pietro nie bedzie uwzgledniane*/
            odwiedzone.add(p);
        }

        /*zapisanie danych sortowania do windy*/
        while (!kolejka.isEmpty()) {
            winda1.dodajPasazera((Pasazer)kolejka.remove());
        }
    }


    public void sortuj(ArrayList<Pasazer> listaPasazerow, ArrayList<Pasazer> gora, ArrayList<Pasazer> dol, Winda winda1, Winda winda2) {
        Queue kolejka1 = new LinkedList();
        Queue kolejka2 = new LinkedList();
        ArrayList<Pasazer> odwiedzone = new ArrayList<Pasazer>();
        /*sortowanie w kolejnosci wg pieter */
        Collections.sort(listaPasazerow, new MyComparator23());
        /*sprawdzanie kolejnych pasazerow */
        for (int i = 0; i < listaPasazerow.size(); i++) {
            /*PIERWSZA WINDA*/
            int k1 = getMost(listaPasazerow, odwiedzone);
            Pasazer p = listaPasazerow.get(k1);
            if (!odwiedzone.contains(p)) {
                kolejka1.add(new Pasazer(p.ilosc, p.start, p.cel));
                /*przy kolejnym poszukiwaniu najwiekszej ilosci pasazerow pietro nie bedzie uwzgledniane*/
                odwiedzone.add(p);
            }
            /*DRUGA WINDA*/
            int k2 = getMost(listaPasazerow, odwiedzone);
            Pasazer p2 = listaPasazerow.get(k2);
            if (!odwiedzone.contains(p2)) {
                kolejka2.add(new Pasazer(p2.ilosc, p2.start, p2.cel));
                odwiedzone.add(p2);
            }
        }

        /*zapisanie danych sortowania do windy*/
        while (!kolejka1.isEmpty()) {
            winda1.dodajPasazera((Pasazer)kolejka1.remove());
        }
        while (!kolejka2.isEmpty()) {
            winda2.dodajPasazera((Pasazer)kolejka2.remove());
        }
        /*for (int i = 0; i < winda1.pasazerowie.size(); i++)
            System.out.println("1 Pieterko: " +winda1.pasazerowie.get(i).iloscOut() + " -- " + winda1.pasazerowie.get(i).startOut() + " -> " + winda1.pasazerowie.get(i).celOut());
        for (int i = 0; i < winda2.pasazerowie.size(); i++)
            System.out.println("2 Pieterko: " +winda2.pasazerowie.get(i).iloscOut() + " -- " + winda2.pasazerowie.get(i).startOut() + " -> " + winda2.pasazerowie.get(i).celOut());
*/
    }
    /*Obrazuje przejazd windy przez kolejne pietra przy wykorzystaniu Algorytmu3*/
    public void start(Winda winda1, ArrayList<Pasazer> gora, ArrayList<Pasazer> dol, int iloscPieter, listaPieter lista) {

        int i = 0;
        /*startujemy na parterze*/
        winda1.ustawPietro(0);

        try {
            BufferedWriter plik = new BufferedWriter(new FileWriter("zapis.txt"));
            int last = -1;
            Pasazer next = new Pasazer(0,0,0);
            Pasazer prev = new Pasazer(0,0,0);
            int j = 0;
            /*obsluga kolejnych pasazerow*/
            for (i = 0; i < winda1.pasazerowie.size(); i++) {
                /*obslugiwany pasazer*/
                Pasazer p = winda1.pasazerowie.get(i);
                /*nastepny pasazer do obslugi*/
                if(i < winda1.pasazerowie.size()-1)
                    next = winda1.pasazerowie.get(i+1);
                /*ostatni obsluzony pasazer*/
                if(i > 0)
                    prev = winda1.pasazerowie.get(i-1);

                int m = i;
                int tmp = 0;
                /*laczna ilosc pasazerow wsiadajacych, jezeli winda musiala jechac do nastepnego pasazera*/
                for (m = i; m < winda1.pasazerowie.size(); m++) {
                    if (winda1.pasazerowie.get(m).start != p.start)
                                break;
                    if ((winda1.pasazerowie.get(m).kierunek > 0 && p.kierunek > 0) || winda1.pasazerowie.get(m).kierunek < 0 && p.kierunek < 0) {
                        tmp += winda1.pasazerowie.get(m).ilosc;
                    }
                }
                int tmp2 = 0;
                /*laczna ilosc pasazerow wsiadajacych, jezeli na pietrze docelowym poprzedniego pasazera czekaja inni*/
                if(i < winda1.pasazerowie.size()-1) {
                    for (m = i+1; m < winda1.pasazerowie.size(); m++) {
                        if (winda1.pasazerowie.get(m).start != next.start)
                                break;
                        if ((winda1.pasazerowie.get(m).kierunek > 0 && next.kierunek > 0) || winda1.pasazerowie.get(m).kierunek < 0 && next.kierunek < 0) {
                            tmp2 += winda1.pasazerowie.get(m).ilosc;
                        }
                    }
                }
                /*pasazer wsiadl do windy*/
                int started = 0;
                /*jezeli startowy wyzej niz winda jest aktualnie i jedziemy w gore (caly czas w gore)*/
                if (p.start >= winda1.pietro && p.kierunek > 0) {
                    if (p.kierunek > 0) {
                        for (j = winda1.pietro; j <= p.cel; j++) {
                            /*info: zabieraja sie*/
                            if (j == p.start) {
                                if ((p.start != last && p.start != prev.start) || (prev.kierunek < 0 && p.kierunek > 0 && p.start != last) ) {
                                    lista.dodaj(new Pietro(j, 1, tmp, 0, true));
                                    plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                                    plik.newLine();
                                }
                                started = 1;
                                last = j;
                            }
                            /*info: wysiadaja*/
                            if (j == p.cel && started == 1) {
                                /*jezeli na tym pietrze czeka pasazer*/
                                if (p.cel == next.start) {
                                    if (next.kierunek > 0)  {
                                        lista.dodaj(new Pietro(j, 1, tmp2, p.ilosc, true));
                                    }
                                    else {
                                        lista.dodaj(new Pietro(j, 1, tmp2, p.ilosc, false));
                                    }
                                }
                                /*jezeli kolejny pasazer jedzie z innego pietra niz aktualny*/
                                else if(p.cel < next.start && next.ilosc != 0 && p.start != next.start) {
                                    lista.dodaj(new Pietro(j, 1, 0, p.ilosc, true));
                                }
                                else if (p.cel > next.start && next.ilosc != 0 && p.start != next.start) {
                                    lista.dodaj(new Pietro(j, 1, 0, p.ilosc, false));
                                }
                                /*jezeli kolejny pasazer jedzie z tego samego pietra co aktualny*/
                                else if(p.cel < next.cel && next.ilosc != 0) {
                                    lista.dodaj(new Pietro(j, 1, 0, p.ilosc, true));
                                }
                                else if (p.cel > next.cel && next.ilosc != 0) {
                                    lista.dodaj(new Pietro(j, 1, 0, p.ilosc, false));
                                }
                                else lista.dodaj(new Pietro(j, 1, 0, p.ilosc, false));

                                plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                                plik.newLine();
                                last = j;
                            }
                            /*pasazer obsluzony*/
                            if (last != j) {
                                if (prev.start != p.start || j > prev.cel) {
                                    lista.dodaj(new Pietro(j, 1, 0, 0, true));
                                    plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                                    plik.newLine();
                                }
                                last = j;
                            }
                        }
                    }
                }
                /*jezeli startowy nizej niz winda jest aktualnie i jedziemy potem w gore*/
                else if (p.start < winda1.pietro && p.kierunek > 0) {
                    /*ruch windy w kierunku pietra startowego (zjazd w dol)*/
                    for (j = winda1.pietro; j >= p.start; j--) {
                        /*info: zabieraja sie*/
                        if (p.start == j) {
                            if ((p.start != last && p.start != prev.start) || (prev.kierunek < 0 && p.kierunek > 0 && p.start != last) )  {
                                lista.dodaj(new Pietro(j, 1, tmp, 0, true));
                                plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                                plik.newLine();
                            }
                            started = 1;
                            last = j;
                            break;
                        }
                        if(last != j) {
                            if (prev.start != p.start || j > p.cel) {
                                lista.dodaj(new Pietro(j, 1, 0, 0, false));
                                plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                                plik.newLine();
                            }
                            last = j;
                        }
                    }
                    /*ruch windy w kierunku pietra docelowego (w gore)*/
                    for (j = p.start; j <= p.cel; j++) {
                        /*info: wysiadaja*/
                        if (j == p.cel && started == 1) {
                            /*jezeli na tym pietrze czeka pasazer*/
                            if (p.cel == next.start) {
                                if (next.kierunek > 0) {
                                    lista.dodaj(new Pietro(j, 1, tmp2, p.ilosc, true));
                                }
                                else {
                                    lista.dodaj(new Pietro(j, 1, tmp2, p.ilosc, false));
                                }

                            }
                            /*jezeli kolejny pasazer jedzie z innego pietra niz aktualny*/
                            else if(p.cel < next.start && next.ilosc != 0 && p.start != next.start) {
                                lista.dodaj(new Pietro(j, 1, 0, p.ilosc, true));
                            }
                            else if (p.cel > next.start && next.ilosc != 0 && p.start != next.start) {
                                lista.dodaj(new Pietro(j, 1, 0, p.ilosc, false));
                            }
                            /*jezeli kolejny pasazer jedzie z tego samego pietra co aktualny*/
                            else if(p.cel < next.cel && next.ilosc != 0) {
                                lista.dodaj(new Pietro(j, 1, 0, p.ilosc, true));
                            }
                            else if (p.cel > next.cel && next.ilosc != 0) {
                                lista.dodaj(new Pietro(j, 1, 0, p.ilosc, false));
                            }
                            else lista.dodaj(new Pietro(j, 1, 0, p.ilosc, false));

                            last = j;
                            plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                            plik.newLine();
                            break;
                        }
                        if(last != j) {
                            if (prev.start != p.start || (prev.cel < j && j < p.cel) ) {
                                lista.dodaj(new Pietro(j, 1, 0, 0, true));
                                plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                                plik.newLine();
                            }
                            last = j;
                        }
                    }
                }
                /*jezeli startowy jest nizej niz aktualnie winda, a jedziemy w dol (caly czas w dol)*/
                else if (p.start <= winda1.pietro && p.kierunek < 0) {
                    if (p.kierunek < 0) {
                        for (j = winda1.pietro; j >= p.cel; j--) {
                            /*info: zabieraja sie*/
                            if (j == p.start) {
                                if ((p.start != last && p.start != prev.start) || (prev.kierunek > 0 && p.kierunek < 0 && p.start != last) ) {
                                    lista.dodaj(new Pietro(j, 1, tmp, 0, false));
                                    plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                                    plik.newLine();
                                }
                                started = 1;
                                last = j;
                            }
                            /*info: wysiadaja*/
                            if (j == p.cel && started == 1) {
                                /*jezeli na tym pietrze czeka pasazer*/
                                if (p.cel == next.start) {
                                    if (next.kierunek > 0) {
                                        lista.dodaj(new Pietro(j, 1, tmp2, p.ilosc, true));
                                    }
                                    else {
                                        lista.dodaj(new Pietro(j, 1, tmp2, p.ilosc, false));
                                    }
                                }
                                /*jezeli kolejny pasazer jedzie z innego pietra niz aktualny*/
                                else if(p.cel < next.start && next.ilosc != 0 && p.start != next.start) {
                                    lista.dodaj(new Pietro(j, 1, 0, p.ilosc, true));
                                }
                                else if (p.cel > next.start && next.ilosc != 0 && p.start != next.start) {
                                    lista.dodaj(new Pietro(j, 1, 0, p.ilosc, false));
                                }
                                /*jezeli kolejny pasazer jedzie z tego samego pietra co aktualny*/
                                else if(p.cel < next.cel && next.ilosc != 0) {
                                    lista.dodaj(new Pietro(j, 1, 0, p.ilosc, true));
                                }
                                else if (p.cel > next.cel && next.ilosc != 0) {
                                    lista.dodaj(new Pietro(j, 1, 0, p.ilosc, false));
                                }
                                else lista.dodaj(new Pietro(j, 1, 0, p.ilosc, false));

                                last = j;
                                plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                                plik.newLine();
                            }
                            /*pasazer obsluzony*/
                            if(last != j) {
                                if (prev.start != p.start || j < prev.cel) {
                                    lista.dodaj(new Pietro(j, 1, 0, 0, false));
                                    plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                                    plik.newLine();
                                }
                                last = j;
                            }
                        }
                    }
                }
                /*jezeli startowy jest wyzej niz aktualnie winda, a jedziemy potem w dol*/
                else if (p.start > winda1.pietro && p.kierunek < 0) {
                    /*ruch windy w kierunku pietra poczatkowego (w gore)*/
                    for (j = winda1.pietro; j <= p.start; j++) {
                        /*info: zabieraja sie*/
                        if (p.start == j) {
                            if ((p.start != last && p.start != prev.start) || (prev.kierunek > 0 && p.kierunek < 0 && p.start != last) ) {
                                lista.dodaj(new Pietro(j, 1, tmp, 0, false));
                                plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                                plik.newLine();
                            }
                            started = 1;
                            last = j;
                            break;
                        }
                        if(last != j) {
                            /*jest na j-tym pietrze i jedzie do gory*/
                            if (prev.start != p.start || j < p.cel) {
                                lista.dodaj(new Pietro(j, 1, 0, 0, true));
                                plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                                plik.newLine();
                            }
                            last = j;
                        }
                    }
                    /*ruch windy w kierunku pietra docelowego (w dol)*/
                    for (j = p.start; j >= p.cel; j--) {
                        /*info: wysiadaja*/
                        if (j == p.cel && started == 1) {
                            /*jezeli na tym pietrze czeka pasazer*/
                            if (p.cel == next.start) {
                                if (next.kierunek > 0) {
                                    lista.dodaj(new Pietro(j, 1, tmp2, p.ilosc, true));
                                }
                                else {
                                    lista.dodaj(new Pietro(j, 1, tmp2, p.ilosc, false));
                                }
                            }
                            /*jezeli kolejny pasazer jedzie z innego pietra niz aktualny*/
                            else if(p.cel < next.start && next.ilosc != 0 && p.start != next.start) {
                                lista.dodaj(new Pietro(j, 1, 0, p.ilosc, true));
                            }
                            else if (p.cel > next.start && next.ilosc != 0 && p.start != next.start) {
                                lista.dodaj(new Pietro(j, 1, 0, p.ilosc, false));
                            }
                            /*jezeli kolejny pasazer jedzie z tego samego pietra co aktualny*/
                            else if(p.cel < next.cel && next.ilosc != 0) {
                                lista.dodaj(new Pietro(j, 1, 0, p.ilosc, true));
                            }
                            else if (p.cel > next.cel && next.ilosc != 0) {
                                lista.dodaj(new Pietro(j, 1, 0, p.ilosc, false));
                            }
                            else lista.dodaj(new Pietro(j, 1, 0, p.ilosc, false));

                            last = j;
                            plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                            plik.newLine();
                            break;
                        }
                        if (last != j) {
                            if (prev.start != p.start || (j < prev.cel && j > p.cel) ) {
                                lista.dodaj(new Pietro(j, 1, 0, 0, false));
                                plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                                plik.newLine();
                            }
                            last = j;
                        }
                    }
                }

                winda1.ustawPietro(p.cel);
            }
        winda1.zapiszDane(plik,lista,1);
        plik.close();
        } catch (IOException e) {}
    }

    public void start(Winda winda1, Winda winda2, ArrayList<Pasazer> gora, ArrayList<Pasazer> dol, int iloscPieter, listaPieter lista) {
        int i = 0;
        /*startujemy na parterze*/
        winda1.ustawPietro(0);
        winda2.ustawPietro(0);

        try {
            BufferedWriter plik = new BufferedWriter(new FileWriter("zapis.txt"));
            int last = -1;
            Pasazer next = new Pasazer(0,0,0);
            Pasazer prev = new Pasazer(0,0,0);
            int j = 0;
            /*PIERWSZA WINDA*/
            /*obsluga kolejnych pasazerow*/
            for (i = 0; i < winda1.pasazerowie.size(); i++) {
                /*obslugiwany pasazer*/
                Pasazer p = winda1.pasazerowie.get(i);
                /*nastepny pasazer do obslugi*/
                if(i < winda1.pasazerowie.size()-1)
                    next = winda1.pasazerowie.get(i+1);
                /*ostatni obsluzony pasazer*/
                if(i > 0)
                    prev = winda1.pasazerowie.get(i-1);

                int m = i;
                int tmp = 0;
                /*laczna ilosc pasazerow wsiadajacych, jezeli winda musiala jechac do nastepnego pasazera*/
                for (m = i; m < winda1.pasazerowie.size(); m++) {
                    if (winda1.pasazerowie.get(m).start != p.start)
                                break;
                    if ((winda1.pasazerowie.get(m).kierunek > 0 && p.kierunek > 0) || winda1.pasazerowie.get(m).kierunek < 0 && p.kierunek < 0) {
                        tmp += winda1.pasazerowie.get(m).ilosc;
                    }
                }
                int tmp2 = 0;
                /*laczna ilosc pasazerow wsiadajacych, jezeli na pietrze docelowym poprzedniego pasazera czekaja inni*/
                if(i < winda1.pasazerowie.size()-1) {
                    for (m = i+1; m < winda1.pasazerowie.size(); m++) {
                        if (winda1.pasazerowie.get(m).start != next.start)
                                break;
                        if ((winda1.pasazerowie.get(m).kierunek > 0 && next.kierunek > 0) || winda1.pasazerowie.get(m).kierunek < 0 && next.kierunek < 0) {
                            tmp2 += winda1.pasazerowie.get(m).ilosc;
                        }
                    }
                }
                /*pasazer wsiadl do windy*/
                int started = 0;
                /*jezeli startowy wyzej niz winda jest aktualnie i jedziemy w gore (caly czas w gore)*/
                if (p.start >= winda1.pietro && p.kierunek > 0) {
                    if (p.kierunek > 0) {
                        for (j = winda1.pietro; j <= p.cel; j++) {
                            /*info: zabieraja sie*/
                            if (j == p.start) {
                                if ((p.start != last && p.start != prev.start) || (prev.kierunek < 0 && p.kierunek > 0 && p.start != last) ) {
                                    lista.dodaj(new Pietro(j, 1, tmp, 0, true));
                                    plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                                    plik.newLine();
                                }
                                started = 1;
                                last = j;
                            }
                            /*info: wysiadaja*/
                            if (j == p.cel && started == 1) {
                                /*jezeli na tym pietrze czeka pasazer*/
                                if (p.cel == next.start) {
                                    if (next.kierunek > 0)  {
                                        lista.dodaj(new Pietro(j, 1, tmp2, p.ilosc, true));
                                    }
                                    else {
                                        lista.dodaj(new Pietro(j, 1, tmp2, p.ilosc, false));
                                    }
                                }
                                /*jezeli kolejny pasazer jedzie z innego pietra niz aktualny*/
                                else if(p.cel < next.start && next.ilosc != 0 && p.start != next.start) {
                                    lista.dodaj(new Pietro(j, 1, 0, p.ilosc, true));
                                }
                                else if (p.cel > next.start && next.ilosc != 0 && p.start != next.start) {
                                    lista.dodaj(new Pietro(j, 1, 0, p.ilosc, false));
                                }
                                /*jezeli kolejny pasazer jedzie z tego samego pietra co aktualny*/
                                else if(p.cel < next.cel && next.ilosc != 0) {
                                    lista.dodaj(new Pietro(j, 1, 0, p.ilosc, true));
                                }
                                else if (p.cel > next.cel && next.ilosc != 0) {
                                    lista.dodaj(new Pietro(j, 1, 0, p.ilosc, false));
                                }
                                else lista.dodaj(new Pietro(j, 1, 0, p.ilosc, false));

                                plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                                plik.newLine();
                                last = j;
                            }
                            /*pasazer obsluzony*/
                            if (last != j) {
                                if (prev.start != p.start || j > prev.cel) {
                                    lista.dodaj(new Pietro(j, 1, 0, 0, true));
                                    plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                                    plik.newLine();
                                }
                                last = j;
                            }
                        }
                    }
                }
                /*jezeli startowy nizej niz winda jest aktualnie i jedziemy potem w gore*/
                else if (p.start < winda1.pietro && p.kierunek > 0) {
                    /*ruch windy w kierunku pietra startowego (zjazd w dol)*/
                    for (j = winda1.pietro; j >= p.start; j--) {
                        /*info: zabieraja sie*/
                        if (p.start == j) {
                            if ((p.start != last && p.start != prev.start) || (prev.kierunek < 0 && p.kierunek > 0 && p.start != last) )  {
                                lista.dodaj(new Pietro(j, 1, tmp, 0, true));
                                plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                                plik.newLine();
                            }
                            started = 1;
                            last = j;
                            break;
                        }
                        if(last != j) {
                            if (prev.start != p.start || j > p.cel) {
                                lista.dodaj(new Pietro(j, 1, 0, 0, false));
                                plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                                plik.newLine();
                            }
                            last = j;
                        }
                    }
                    /*ruch windy w kierunku pietra docelowego (w gore)*/
                    for (j = p.start; j <= p.cel; j++) {
                        /*info: wysiadaja*/
                        if (j == p.cel && started == 1) {
                            /*jezeli na tym pietrze czeka pasazer*/
                            if (p.cel == next.start) {
                                if (next.kierunek > 0) {
                                    lista.dodaj(new Pietro(j, 1, tmp2, p.ilosc, true));
                                }
                                else {
                                    lista.dodaj(new Pietro(j, 1, tmp2, p.ilosc, false));
                                }

                            }
                            /*jezeli kolejny pasazer jedzie z innego pietra niz aktualny*/
                            else if(p.cel < next.start && next.ilosc != 0 && p.start != next.start) {
                                lista.dodaj(new Pietro(j, 1, 0, p.ilosc, true));
                            }
                            else if (p.cel > next.start && next.ilosc != 0 && p.start != next.start) {
                                lista.dodaj(new Pietro(j, 1, 0, p.ilosc, false));
                            }
                            /*jezeli kolejny pasazer jedzie z tego samego pietra co aktualny*/
                            else if(p.cel < next.cel && next.ilosc != 0) {
                                lista.dodaj(new Pietro(j, 1, 0, p.ilosc, true));
                            }
                            else if (p.cel > next.cel && next.ilosc != 0) {
                                lista.dodaj(new Pietro(j, 1, 0, p.ilosc, false));
                            }
                            else lista.dodaj(new Pietro(j, 1, 0, p.ilosc, false));

                            last = j;
                            plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                            plik.newLine();
                            break;
                        }
                        if(last != j) {
                            if (prev.start != p.start || (prev.cel < j && j < p.cel) ) {
                                lista.dodaj(new Pietro(j, 1, 0, 0, true));
                                plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                                plik.newLine();
                            }
                            last = j;
                        }
                    }
                }
                /*jezeli startowy jest nizej niz aktualnie winda, a jedziemy w dol (caly czas w dol)*/
                else if (p.start <= winda1.pietro && p.kierunek < 0) {
                    if (p.kierunek < 0) {
                        for (j = winda1.pietro; j >= p.cel; j--) {
                            /*info: zabieraja sie*/
                            if (j == p.start) {
                                if ((p.start != last && p.start != prev.start) || (prev.kierunek > 0 && p.kierunek < 0 && p.start != last) ) {
                                    lista.dodaj(new Pietro(j, 1, tmp, 0, false));
                                    plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                                    plik.newLine();
                                }
                                started = 1;
                                last = j;
                            }
                            /*info: wysiadaja*/
                            if (j == p.cel && started == 1) {
                                /*jezeli na tym pietrze czeka pasazer*/
                                if (p.cel == next.start) {
                                    if (next.kierunek > 0) {
                                        lista.dodaj(new Pietro(j, 1, tmp2, p.ilosc, true));
                                    }
                                    else {
                                        lista.dodaj(new Pietro(j, 1, tmp2, p.ilosc, false));
                                    }
                                }
                                /*jezeli kolejny pasazer jedzie z innego pietra niz aktualny*/
                                else if(p.cel < next.start && next.ilosc != 0 && p.start != next.start) {
                                    lista.dodaj(new Pietro(j, 1, 0, p.ilosc, true));
                                }
                                else if (p.cel > next.start && next.ilosc != 0 && p.start != next.start) {
                                    lista.dodaj(new Pietro(j, 1, 0, p.ilosc, false));
                                }
                                /*jezeli kolejny pasazer jedzie z tego samego pietra co aktualny*/
                                else if(p.cel < next.cel && next.ilosc != 0) {
                                    lista.dodaj(new Pietro(j, 1, 0, p.ilosc, true));
                                }
                                else if (p.cel > next.cel && next.ilosc != 0) {
                                    lista.dodaj(new Pietro(j, 1, 0, p.ilosc, false));
                                }
                                else lista.dodaj(new Pietro(j, 1, 0, p.ilosc, false));

                                last = j;
                                plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                                plik.newLine();
                            }
                            /*pasazer obsluzony*/
                            if(last != j) {
                                if (prev.start != p.start || j < prev.cel) {
                                    lista.dodaj(new Pietro(j, 1, 0, 0, false));
                                    plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                                    plik.newLine();
                                }
                                last = j;
                            }
                        }
                    }
                }
                /*jezeli startowy jest wyzej niz aktualnie winda, a jedziemy potem w dol*/
                else if (p.start > winda1.pietro && p.kierunek < 0) {
                    /*ruch windy w kierunku pietra poczatkowego (w gore)*/
                    for (j = winda1.pietro; j <= p.start; j++) {
                        /*info: zabieraja sie*/
                        if (p.start == j) {
                            if ((p.start != last && p.start != prev.start) || (prev.kierunek > 0 && p.kierunek < 0 && p.start != last) ) {
                                lista.dodaj(new Pietro(j, 1, tmp, 0, false));
                                plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                                plik.newLine();
                            }
                            started = 1;
                            last = j;
                            break;
                        }
                        if(last != j) {
                            /*jest na j-tym pietrze i jedzie do gory*/
                            if (prev.start != p.start || j < p.cel) {
                                lista.dodaj(new Pietro(j, 1, 0, 0, true));
                                plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                                plik.newLine();
                            }
                            last = j;
                        }
                    }
                    /*ruch windy w kierunku pietra docelowego (w dol)*/
                    for (j = p.start; j >= p.cel; j--) {
                        /*info: wysiadaja*/
                        if (j == p.cel && started == 1) {
                            /*jezeli na tym pietrze czeka pasazer*/
                            if (p.cel == next.start) {
                                if (next.kierunek > 0) {
                                    lista.dodaj(new Pietro(j, 1, tmp2, p.ilosc, true));
                                }
                                else {
                                    lista.dodaj(new Pietro(j, 1, tmp2, p.ilosc, false));
                                }
                            }
                            /*jezeli kolejny pasazer jedzie z innego pietra niz aktualny*/
                            else if(p.cel < next.start && next.ilosc != 0 && p.start != next.start) {
                                lista.dodaj(new Pietro(j, 1, 0, p.ilosc, true));
                            }
                            else if (p.cel > next.start && next.ilosc != 0 && p.start != next.start) {
                                lista.dodaj(new Pietro(j, 1, 0, p.ilosc, false));
                            }
                            /*jezeli kolejny pasazer jedzie z tego samego pietra co aktualny*/
                            else if(p.cel < next.cel && next.ilosc != 0) {
                                lista.dodaj(new Pietro(j, 1, 0, p.ilosc, true));
                            }
                            else if (p.cel > next.cel && next.ilosc != 0) {
                                lista.dodaj(new Pietro(j, 1, 0, p.ilosc, false));
                            }
                            else lista.dodaj(new Pietro(j, 1, 0, p.ilosc, false));

                            last = j;
                            plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                            plik.newLine();
                            break;
                        }
                        if (last != j) {
                            if (prev.start != p.start || (j < prev.cel && j > p.cel) ) {
                                lista.dodaj(new Pietro(j, 1, 0, 0, false));
                                plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                                plik.newLine();
                            }
                            last = j;
                        }
                    }
                }

                winda1.ustawPietro(p.cel);
            }
            /*DRUGA WINDA*/
            /*obsluga kolejnych pasazerow*/
            for (i = 0; i < winda2.pasazerowie.size(); i++) {
                /*obslugiwany pasazer*/
                Pasazer p = winda2.pasazerowie.get(i);
                /*nastepny pasazer do obslugi*/
                if(i < winda2.pasazerowie.size()-1)
                    next = winda2.pasazerowie.get(i+1);
                /*ostatni obsluzony pasazer*/
                if(i > 0)
                    prev = winda2.pasazerowie.get(i-1);

                int m = i;
                int tmp = 0;
                /*laczna ilosc pasazerow wsiadajacych, jezeli winda musiala jechac do nastepnego pasazera*/
                for (m = i; m < winda2.pasazerowie.size(); m++) {
                    if (winda2.pasazerowie.get(m).start != p.start)
                                break;
                    if ((winda2.pasazerowie.get(m).kierunek > 0 && p.kierunek > 0) || winda2.pasazerowie.get(m).kierunek < 0 && p.kierunek < 0) {
                        tmp += winda2.pasazerowie.get(m).ilosc;
                    }
                }
                int tmp2 = 0;
                /*laczna ilosc pasazerow wsiadajacych, jezeli na pietrze docelowym poprzedniego pasazera czekaja inni*/
                if(i < winda2.pasazerowie.size()-1) {
                    for (m = i+1; m < winda2.pasazerowie.size(); m++) {
                        if (winda2.pasazerowie.get(m).start != next.start)
                                break;
                        if ((winda2.pasazerowie.get(m).kierunek > 0 && next.kierunek > 0) || winda2.pasazerowie.get(m).kierunek < 0 && next.kierunek < 0) {
                            tmp2 += winda2.pasazerowie.get(m).ilosc;
                        }
                    }
                }
                /*pasazer wsiadl do windy*/
                int started = 0;
                /*jezeli startowy wyzej niz winda jest aktualnie i jedziemy w gore (caly czas w gore)*/
                if (p.start >= winda2.pietro && p.kierunek > 0) {
                    if (p.kierunek > 0) {
                        for (j = winda2.pietro; j <= p.cel; j++) {
                            /*info: zabieraja sie*/
                            if (j == p.start) {
                                if ((p.start != last && p.start != prev.start) || (prev.kierunek < 0 && p.kierunek > 0 && p.start != last) ) {
                                    lista.dodaj(new Pietro(j, 2, tmp, 0, true));
                                    plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                                    plik.newLine();
                                }
                                started = 1;
                                last = j;
                            }
                            /*info: wysiadaja*/
                            if (j == p.cel && started == 1) {
                                /*jezeli na tym pietrze czeka pasazer*/
                                if (p.cel == next.start) {
                                    if (next.kierunek > 0)  {
                                        lista.dodaj(new Pietro(j, 2, tmp2, p.ilosc, true));
                                    }
                                    else {
                                        lista.dodaj(new Pietro(j, 2, tmp2, p.ilosc, false));
                                    }
                                }
                                /*jezeli kolejny pasazer jedzie z innego pietra niz aktualny*/
                                else if(p.cel < next.start && next.ilosc != 0 && p.start != next.start) {
                                    lista.dodaj(new Pietro(j, 2, 0, p.ilosc, true));
                                }
                                else if (p.cel > next.start && next.ilosc != 0 && p.start != next.start) {
                                    lista.dodaj(new Pietro(j, 2, 0, p.ilosc, false));
                                }
                                /*jezeli kolejny pasazer jedzie z tego samego pietra co aktualny*/
                                else if(p.cel < next.cel && next.ilosc != 0) {
                                    lista.dodaj(new Pietro(j, 2, 0, p.ilosc, true));
                                }
                                else if (p.cel > next.cel && next.ilosc != 0) {
                                    lista.dodaj(new Pietro(j, 2, 0, p.ilosc, false));
                                }
                                else lista.dodaj(new Pietro(j, 2, 0, p.ilosc, false));

                                plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                                plik.newLine();
                                last = j;
                            }
                            /*pasazer obsluzony*/
                            if (last != j) {
                                if (prev.start != p.start || j > prev.cel) {
                                    lista.dodaj(new Pietro(j, 2, 0, 0, true));
                                    plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                                    plik.newLine();
                                }
                                last = j;
                            }
                        }
                    }
                }
                /*jezeli startowy nizej niz winda jest aktualnie i jedziemy potem w gore*/
                else if (p.start < winda2.pietro && p.kierunek > 0) {
                    /*ruch windy w kierunku pietra startowego (zjazd w dol)*/
                    for (j = winda2.pietro; j >= p.start; j--) {
                        /*info: zabieraja sie*/
                        if (p.start == j) {
                            if ((p.start != last && p.start != prev.start) || (prev.kierunek < 0 && p.kierunek > 0 && p.start != last) )  {
                                lista.dodaj(new Pietro(j, 2, tmp, 0, true));
                                plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                                plik.newLine();
                            }
                            started = 1;
                            last = j;
                            break;
                        }
                        if(last != j) {
                            if (prev.start != p.start || j > p.cel) {
                                lista.dodaj(new Pietro(j, 2, 0, 0, false));
                                plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                                plik.newLine();
                            }
                            last = j;
                        }
                    }
                    /*ruch windy w kierunku pietra docelowego (w gore)*/
                    for (j = p.start; j <= p.cel; j++) {
                        /*info: wysiadaja*/
                        if (j == p.cel && started == 1) {
                            /*jezeli na tym pietrze czeka pasazer*/
                            if (p.cel == next.start) {
                                if (next.kierunek > 0) {
                                    lista.dodaj(new Pietro(j, 2, tmp2, p.ilosc, true));
                                }
                                else {
                                    lista.dodaj(new Pietro(j, 2, tmp2, p.ilosc, false));
                                }

                            }
                            /*jezeli kolejny pasazer jedzie z innego pietra niz aktualny*/
                            else if(p.cel < next.start && next.ilosc != 0 && p.start != next.start) {
                                lista.dodaj(new Pietro(j, 2, 0, p.ilosc, true));
                            }
                            else if (p.cel > next.start && next.ilosc != 0 && p.start != next.start) {
                                lista.dodaj(new Pietro(j, 2, 0, p.ilosc, false));
                            }
                            /*jezeli kolejny pasazer jedzie z tego samego pietra co aktualny*/
                            else if(p.cel < next.cel && next.ilosc != 0) {
                                lista.dodaj(new Pietro(j, 2, 0, p.ilosc, true));
                            }
                            else if (p.cel > next.cel && next.ilosc != 0) {
                                lista.dodaj(new Pietro(j, 2, 0, p.ilosc, false));
                            }
                            else lista.dodaj(new Pietro(j, 2, 0, p.ilosc, false));

                            last = j;
                            plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                            plik.newLine();
                            break;
                        }
                        if(last != j) {
                            if (prev.start != p.start || (prev.cel < j && j < p.cel) ) {
                                lista.dodaj(new Pietro(j, 2, 0, 0, true));
                                plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                                plik.newLine();
                            }
                            last = j;
                        }
                    }
                }
                /*jezeli startowy jest nizej niz aktualnie winda, a jedziemy w dol (caly czas w dol)*/
                else if (p.start <= winda2.pietro && p.kierunek < 0) {
                    if (p.kierunek < 0) {
                        for (j = winda2.pietro; j >= p.cel; j--) {
                            /*info: zabieraja sie*/
                            if (j == p.start) {
                                if ((p.start != last && p.start != prev.start) || (prev.kierunek > 0 && p.kierunek < 0 && p.start != last) ) {
                                    lista.dodaj(new Pietro(j, 2, tmp, 0, false));
                                    plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                                    plik.newLine();
                                }
                                started = 1;
                                last = j;
                            }
                            /*info: wysiadaja*/
                            if (j == p.cel && started == 1) {
                                /*jezeli na tym pietrze czeka pasazer*/
                                if (p.cel == next.start) {
                                    if (next.kierunek > 0) {
                                        lista.dodaj(new Pietro(j, 2, tmp2, p.ilosc, true));
                                    }
                                    else {
                                        lista.dodaj(new Pietro(j, 2, tmp2, p.ilosc, false));
                                    }
                                }
                                /*jezeli kolejny pasazer jedzie z innego pietra niz aktualny*/
                                else if(p.cel < next.start && next.ilosc != 0 && p.start != next.start) {
                                    lista.dodaj(new Pietro(j, 2, 0, p.ilosc, true));
                                }
                                else if (p.cel > next.start && next.ilosc != 0 && p.start != next.start) {
                                    lista.dodaj(new Pietro(j, 2, 0, p.ilosc, false));
                                }
                                /*jezeli kolejny pasazer jedzie z tego samego pietra co aktualny*/
                                else if(p.cel < next.cel && next.ilosc != 0) {
                                    lista.dodaj(new Pietro(j, 2, 0, p.ilosc, true));
                                }
                                else if (p.cel > next.cel && next.ilosc != 0) {
                                    lista.dodaj(new Pietro(j, 2, 0, p.ilosc, false));
                                }
                                else lista.dodaj(new Pietro(j, 2, 0, p.ilosc, false));

                                last = j;
                                plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                                plik.newLine();
                            }
                            /*pasazer obsluzony*/
                            if(last != j) {
                                if (prev.start != p.start || j < prev.cel) {
                                    lista.dodaj(new Pietro(j, 2, 0, 0, false));
                                    plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                                    plik.newLine();
                                }
                                last = j;
                            }
                        }
                    }
                }
                /*jezeli startowy jest wyzej niz aktualnie winda, a jedziemy potem w dol*/
                else if (p.start > winda2.pietro && p.kierunek < 0) {
                    /*ruch windy w kierunku pietra poczatkowego (w gore)*/
                    for (j = winda2.pietro; j <= p.start; j++) {
                        /*info: zabieraja sie*/
                        if (p.start == j) {
                            if ((p.start != last && p.start != prev.start) || (prev.kierunek > 0 && p.kierunek < 0 && p.start != last) ) {
                                lista.dodaj(new Pietro(j, 2, tmp, 0, false));
                                plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                                plik.newLine();
                            }
                            started = 1;
                            last = j;
                            break;
                        }
                        if(last != j) {
                            /*jest na j-tym pietrze i jedzie do gory*/
                            if (prev.start != p.start || j < p.cel) {
                                lista.dodaj(new Pietro(j, 2, 0, 0, true));
                                plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                                plik.newLine();
                            }
                            last = j;
                        }
                    }
                    /*ruch windy w kierunku pietra docelowego (w dol)*/
                    for (j = p.start; j >= p.cel; j--) {
                        /*info: wysiadaja*/
                        if (j == p.cel && started == 1) {
                            /*jezeli na tym pietrze czeka pasazer*/
                            if (p.cel == next.start) {
                                if (next.kierunek > 0) {
                                    lista.dodaj(new Pietro(j, 2, tmp2, p.ilosc, true));
                                }
                                else {
                                    lista.dodaj(new Pietro(j, 2, tmp2, p.ilosc, false));
                                }
                            }
                            /*jezeli kolejny pasazer jedzie z innego pietra niz aktualny*/
                            else if(p.cel < next.start && next.ilosc != 0 && p.start != next.start) {
                                lista.dodaj(new Pietro(j, 2, 0, p.ilosc, true));
                            }
                            else if (p.cel > next.start && next.ilosc != 0 && p.start != next.start) {
                                lista.dodaj(new Pietro(j, 2, 0, p.ilosc, false));
                            }
                            /*jezeli kolejny pasazer jedzie z tego samego pietra co aktualny*/
                            else if(p.cel < next.cel && next.ilosc != 0) {
                                lista.dodaj(new Pietro(j, 2, 0, p.ilosc, true));
                            }
                            else if (p.cel > next.cel && next.ilosc != 0) {
                                lista.dodaj(new Pietro(j, 2, 0, p.ilosc, false));
                            }
                            else lista.dodaj(new Pietro(j, 2, 0, p.ilosc, false));

                            last = j;
                            plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                            plik.newLine();
                            break;
                        }
                        if (last != j) {
                            if (prev.start != p.start || (j < prev.cel && j > p.cel) ) {
                                lista.dodaj(new Pietro(j, 2, 0, 0, false));
                                plik.write(lista.lista.get(lista.lista.size() - 1).toString());
                                plik.newLine();
                            }
                            last = j;
                        }
                    }
                }

                winda2.ustawPietro(p.cel);
            }
            plik.newLine();
            winda1.zapiszDane(plik,lista,1);
            plik.newLine();
            plik.newLine();
            winda2.zapiszDane(plik,lista,2);
        plik.close();
        } catch (IOException e) {}
    }
}