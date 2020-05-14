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
public class MyAlgorytm2 implements Algorytm{

    /*wyszukuje pietro, na ktorym jest najblizszy wzgledem aktualnego polozenia windy pasazer*/
    private ArrayList<Integer> getClosest(ArrayList<Pasazer> listaPasazerow, int pietro, ArrayList<Pasazer> odwiedzone, int kier) {
        ArrayList<Integer> closest = new ArrayList<Integer>();
        /* jezeli na aktualnym pietrze*/

        ArrayList<Integer> lower = new ArrayList<Integer>();
        ArrayList<Integer> higher = new ArrayList<Integer>();

        int najkrocej = Zmienne.iloscPieter;
        int kierunek = 0;
        Pasazer last = new Pasazer(0,0,0);

        if (!odwiedzone.isEmpty())
            last = odwiedzone.get(odwiedzone.size()-1);
        /*jezeli na innych pietrach, to szukamy najblizszego*/
            for (int i = 0; i < listaPasazerow.size() && closest.isEmpty(); i++) {
                Pasazer p = listaPasazerow.get(i);

                if (p.start-last.cel != 0)
                    kierunek = p.start-last.cel;
                else kierunek = kier;

                for (int j = 0; j < Zmienne.iloscPieter; j++) {
                /*wybieramy najblizszy i mozliwie w kierunku jazdy*/
                    /*nizej*/
                    if (p.start+j == pietro && !odwiedzone.contains(p) && j <= najkrocej) {
                        if (!higher.isEmpty()) {
                            while (higher.isEmpty()) higher.remove(0);
                        }
                        /* jezeli nie znaleziono wczesniej blizszego to czyscimy liste pasazerow */
                        if (najkrocej > j && !lower.isEmpty()) {
                            while (!lower.isEmpty()) lower.remove(0);
                        }
                        /* jezeli znaleziony pasazer jest tak samo blisko jak odnalezione wczesniej */
                        else if(!lower.isEmpty()) {
                            /* jezeli kierunek windy ZGODNY z kierunkiem jazdy pasazera */
                            if ( (kierunek > 0 && p.kierunek > 0) || (kierunek < 0 && p.kierunek < 0) ) {
                                if ( (listaPasazerow.get(lower.get(0)).kierunek > 0 && kierunek > 0) || (listaPasazerow.get(lower.get(0)).kierunek < 0 && kierunek < 0) )
                                    lower.add(i);
                                else {
                                    while (!lower.isEmpty()) lower.remove(0);
                                    lower.add(i);
                                }
                            }
                            /* jezeli kierunek windy ROZNY od kierunku jazdy pasazera */
                            else if ( (listaPasazerow.get(lower.get(0)).kierunek > 0 && p.kierunek > 0) || (listaPasazerow.get(lower.get(0)).kierunek < 0 && p.kierunek < 0) )
                            {
                                lower.add(i);
                            }
                        }
                        /* jeżeli nie znaleziono jeszcze żadnych najbliższych pasażerow */
                        if (lower.isEmpty()) {
                            lower.add(i);
                        }

                        najkrocej = j;
                    }
                    /*wyzej*/
                    else if(p.start-j == pietro && !odwiedzone.contains(p) && j <= najkrocej) {
                        if (!lower.isEmpty()) {
                            while (!lower.isEmpty()) lower.remove(0);
                        }
                        if (najkrocej > j && !higher.isEmpty()) {
                            while (higher.isEmpty()) higher.remove(0);
                        }
                        if (!higher.isEmpty()) {
                            if ( (kierunek > 0 && p.kierunek > 0) || (kierunek < 0 && p.kierunek < 0) ) {
                                if ( (listaPasazerow.get(higher.get(0)).kierunek > 0 && kierunek > 0) || (listaPasazerow.get(higher.get(0)).kierunek < 0 && kierunek < 0) )
                                    higher.add(i);
                                else {
                                    while (!higher.isEmpty()) higher.remove(0);
                                    higher.add(i);
                                }
                            }
                            else if ( (listaPasazerow.get(higher.get(0)).kierunek > 0 && p.kierunek > 0) || (listaPasazerow.get(higher.get(0)).kierunek < 0 && p.kierunek <0) )
                            {
                                higher.add(i);
                            }
                        }
                        if (higher.isEmpty()) {
                            higher.add(i);
                        }
                        najkrocej = j;
                    }
                }
            }

        if (kierunek > 0) {
            if (!higher.isEmpty() && listaPasazerow.get(higher.get(0)).start-najkrocej == pietro)
                while (!higher.isEmpty()) closest.add(higher.remove(0));
            else if (!lower.isEmpty() && listaPasazerow.get(lower.get(0)).start+najkrocej == pietro)
                while (!lower.isEmpty()) closest.add(lower.remove(0));
            else while(!higher.isEmpty()) closest.add(higher.remove(0));
        }
        else {
            if (!lower.isEmpty() && listaPasazerow.get(lower.get(0)).start+najkrocej == pietro)
                while (!lower.isEmpty()) closest.add(lower.remove(0));
            else if (!higher.isEmpty() && listaPasazerow.get(higher.get(0)).start-najkrocej == pietro)
                while (!higher.isEmpty()) closest.add(higher.remove(0));
            else while(!lower.isEmpty()) closest.add(lower.remove(0));
        }

        return closest;
    }

    /* Sortuje kolejne pietra wg kolejnosci odwiedzania (kryterium wyboru pietra jest odleglosc wzgledem aktualnego polozenia windy) */
    public void sortuj(ArrayList<Pasazer> listaPasazerow, ArrayList<Pasazer> gora, ArrayList<Pasazer> dol, Winda winda1) {

        Queue kolejka = new LinkedList();
        int pietroCel = 0;
        int kierunek = 0;
        ArrayList<Pasazer> odwiedzone = new ArrayList<Pasazer>();
        /*sortowanie w kolejnosci wg pieter*/
        Collections.sort( listaPasazerow, new MyComparator23());
        /*sprawdzanie kolejnych pasazerow*/
        for (int i = 0; i < listaPasazerow.size(); i++) {
                /*szukamy pasazerow na najblizszych pietrach*/
                ArrayList<Integer> k = getClosest(listaPasazerow, pietroCel, odwiedzone, kierunek);

                int tmp = k.size();
                for (int j = 0; j<tmp; j++) {
                    kolejka.add(new Pasazer(listaPasazerow.get(k.get(0)).ilosc, listaPasazerow.get(k.get(0)).start, listaPasazerow.get(k.get(0)).cel));
                    /*przy kolejnym poszukiwaniu najwiekszej ilosci pasazerow pietro nie bedzie uwzgledniane*/
                    odwiedzone.add(listaPasazerow.get(k.get(0)));
                    pietroCel = listaPasazerow.get(k.get(0)).cel;
                    k.remove(0);                 
                }                
                if (odwiedzone.size()>0 && odwiedzone.get(odwiedzone.size()-1).cel > odwiedzone.get(odwiedzone.size()-1).start)
                    kierunek = 1;
                else if (odwiedzone.size()>0 && odwiedzone.get(odwiedzone.size()-1).cel < odwiedzone.get(odwiedzone.size()-1).start)
                    kierunek = -1;
                else kierunek = 0;

        }
        /*zapisanie danych sortowania do windy*/
        while (!kolejka.isEmpty()) {
            winda1.dodajPasazera((Pasazer)kolejka.remove());
        }

    }
    /*sortowanie wstepne dla dwoch wind*/
    public void sortuj(ArrayList<Pasazer> listaPasazerow, ArrayList<Pasazer> gora, ArrayList<Pasazer> dol, Winda winda1, Winda winda2) {

        Queue kolejka1 = new LinkedList();
        Queue kolejka2 = new LinkedList();
        int pietroCel1 = 0;
        int pietroCel2 = Zmienne.iloscPieter;
        int kierunek1 = 0;
        int kierunek2 = 0;
        ArrayList<Pasazer> odwiedzone = new ArrayList<Pasazer>();
        /*sortowanie w kolejnosci wg pieter*/
        Collections.sort( listaPasazerow, new MyComparator23());
        /*sprawdzanie kolejnych pasazerow*/
        for (int i = 0; i < listaPasazerow.size(); i++) {
                /*szukamy pasazerow na najblizszych pietrach*/                
                /*PIERWSZA WINDA*/
                ArrayList<Integer> k1 = getClosest(listaPasazerow, pietroCel1, odwiedzone, kierunek1);
                int tmp1 = k1.size();
                for (int j = 0; j<tmp1; j++) {
                    kolejka1.add(new Pasazer(listaPasazerow.get(k1.get(0)).ilosc, listaPasazerow.get(k1.get(0)).start, listaPasazerow.get(k1.get(0)).cel));
                    /*przy kolejnym poszukiwaniu najwiekszej ilosci pasazerow pietro nie bedzie uwzgledniane*/
                    odwiedzone.add(listaPasazerow.get(k1.get(0)));
                    pietroCel1 = listaPasazerow.get(k1.get(0)).cel;
                    k1.remove(0);
                }
                
                /*DRUGA WINDA*/
                ArrayList<Integer> k2 = getClosest(listaPasazerow, pietroCel2, odwiedzone, kierunek2);
                int tmp2 = k2.size();
                for (int j = 0; j<tmp2; j++) {
                    kolejka2.add(new Pasazer(listaPasazerow.get(k2.get(0)).ilosc, listaPasazerow.get(k2.get(0)).start, listaPasazerow.get(k2.get(0)).cel));
                    /*przy kolejnym poszukiwaniu najwiekszej ilosci pasazerow pietro nie bedzie uwzgledniane*/
                    odwiedzone.add(listaPasazerow.get(k2.get(0)));
                    pietroCel2 = listaPasazerow.get(k2.get(0)).cel;
                    k2.remove(0);
                }
                if (odwiedzone.size()>0 &&  odwiedzone.get(odwiedzone.size()-1).cel > odwiedzone.get(odwiedzone.size()-1).start)
                    kierunek2 = 1;
                else if (odwiedzone.size()>0 && odwiedzone.get(odwiedzone.size()-1).cel < odwiedzone.get(odwiedzone.size()-1).start)
                    kierunek2 = -1;
                else kierunek2 = 0;

        }
        /*zapisanie danych sortowania do windy*/
        while (!kolejka1.isEmpty()) {
            winda1.dodajPasazera((Pasazer)kolejka1.remove());
        }        
        while (!kolejka2.isEmpty()) {
            winda2.dodajPasazera((Pasazer)kolejka2.remove());
        }
/*        for (int i = 0; i < winda1.pasazerowie.size(); i++)
            System.out.println("1 Pieterko: " +winda1.pasazerowie.get(i).iloscOut() + " -- " + winda1.pasazerowie.get(i).startOut() + " -> " + winda1.pasazerowie.get(i).celOut());
        for (int i = 0; i < winda2.pasazerowie.size(); i++)
            System.out.println("2 Pieterko: " +winda2.pasazerowie.get(i).iloscOut() + " -- " + winda2.pasazerowie.get(i).startOut() + " -> " + winda2.pasazerowie.get(i).celOut());
*/
    }
    /*Obrazuje przejazd windy przez kolejne pietra przy wykorzystaniu Algorytmu2*/
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
        winda2.ustawPietro(Zmienne.iloscPieter);

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