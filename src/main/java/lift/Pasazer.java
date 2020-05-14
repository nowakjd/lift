/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lift;

/**
 *
 * @author Adam
 *
 * Klasa pasazer... nic dodac nic ujac 
 *
 */
public class Pasazer {

    public int ilosc;
    public int start;
    public int cel;
    public int kierunek;

    public Pasazer(int c_ilosc, int c_start, int c_cel) {
            this.ilosc = c_ilosc;
            this.start = c_start;
            this.cel = c_cel;
            this.kierunek = c_cel - c_start;
        }

    public Pasazer(Pasazer cPasazer) {
            this.ilosc = cPasazer.ilosc;
            this.start = cPasazer.start;
            this.cel = cPasazer.cel;
            this.kierunek = cPasazer.cel - cPasazer.start;
        }

    public String iloscOut(){
        return Integer.toString(this.ilosc);
    }

    public String startOut(){
        return Integer.toString(this.start);
    }

    public String celOut(){
        return Integer.toString(this.cel);
    }

    public String kierunekOut(){
        return Integer.toString(this.kierunek);
    }

    @Override
    public String toString(){
        return Integer.toString(this.ilosc) + " " +Integer.toString(this.start) + " " +
                Integer.toString(this.cel) + " " + Integer.toString(this.kierunek);
    }

}
