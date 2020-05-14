/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lift;

/**
 *
 * @author Adam
 */
public class Pietro {

    public int numerPietra;
    public int numerWindy;
    public int iloscPasazerowWSrodku;
    public int iloscWchodzacych;
    public int iloscWychodzacych;
    public boolean kierunek;

    /*
     *
     * KONSTRUKTOR
     *
     */


    public Pietro(int c_numerPietra, int c_numerWindy, int c_iloscWchodzacych,
                    int c_iloscWychodzacych, boolean c_kierunek){
        this.numerPietra = c_numerPietra;
        this.numerWindy = c_numerWindy;
        //this.iloscPasazerowWSrodku = c_iloscPasazerowWSrodku;
        this.iloscWchodzacych = c_iloscWchodzacych;
        this.iloscWychodzacych = c_iloscWychodzacych;
        this.kierunek = c_kierunek;
    }

    /*
     *
     * WYPISYWANIE
     *
     */

     @Override
    public String toString(){
        String komunikat = "";

        komunikat += "Winda " + this.numerWindy + " jest na pietrze " + this.numerPietra;

        if(this.iloscWychodzacych > 0){
            komunikat += " wysiada z niej " + this.iloscWychodzacych + " osob";
        }

        if(this.iloscWchodzacych > 0){
            komunikat += " zabiera " + this.iloscWchodzacych + " pasazerow";
        }

        if (this.kierunek){
            komunikat += " i jedzie do gory";
        }else{
            komunikat += " i jedzie w dol";
        }

        komunikat += "\n";

        return komunikat;
    }

}
