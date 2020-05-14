/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lift;

import java.util.ArrayList;

/**
 *
 * @author Apapus
 */
public class Zmienne {

   public static int iloscPieter;

   public static int iloscWind;

   public static Algorytm uzywanyAlgorytm;
   public static double predkosc;

   public static double szybkosc; /*szybkosc przejazdu miedzy pietrami*/
   public static double otwarte; /*jak dlugo trwa wejscie/wyjscie z windy pojedynczego pasazera*/
   public static double opoznienie; /*ile czekac, zanim zamkna sie drzwi*/
   public static ArrayList<Pasazer> listaPasazerow= new ArrayList<Pasazer>();
   public static ArrayList<Pietro> listapieterjeden = new ArrayList<Pietro>();
   public static ArrayList<Pietro> listapieterdwa = new ArrayList<Pietro>();
   //metoda, która wymusza, by nie było nulli


   static listaPieter lista = new listaPieter();
   @Override
   public String toString(){
       System.out.println("ilosc pieter: " + iloscPieter + "ilosc wind: " + iloscWind + "Algorytm: " + uzywanyAlgorytm.toString());
       return null;
   }

}
