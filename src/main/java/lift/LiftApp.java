/*
 * LiftApp.java
 */

package lift;

import java.io.IOException;
import java.util.ArrayList;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class LiftApp extends SingleFrameApplication {


    //public static ArrayList<Pasazer> listaPasazerow= new ArrayList<Pasazer>();
    public static ArrayList<Pasazer> pasazerowieGora= new ArrayList<Pasazer>();
    public static ArrayList<Pasazer> pasazerowieDol= new ArrayList<Pasazer>();
    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        //show(new LiftView1());
        show(new WidokPanel());
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of LiftApp
     */
    public static LiftApp getApplication() {
        return Application.getInstance(LiftApp.class);
    }

    public static void startuj(){

        Winda test = new Winda();
        Winda test2 = new Winda();


        /*
         * operacje na windach
         *
         */

        if (Zmienne.iloscWind == 2)
        Zmienne.uzywanyAlgorytm.sortuj(Zmienne.listaPasazerow, pasazerowieGora, pasazerowieDol, test, test2);
        else if (Zmienne.iloscWind==1)
            Zmienne.uzywanyAlgorytm.sortuj(Zmienne.listaPasazerow, pasazerowieGora, pasazerowieDol, test);
        /*
         *
         * MUSIAŁEM DODAĆ lista DO FUNKCJI START
         *
         */

         if (Zmienne.iloscWind == 2)
            Zmienne.uzywanyAlgorytm.start(test, test2, pasazerowieGora, pasazerowieDol, Zmienne.iloscPieter, Zmienne.lista);
         else if (Zmienne.iloscWind==1)
             Zmienne.uzywanyAlgorytm.start(test, pasazerowieGora, pasazerowieDol, Zmienne.iloscPieter, Zmienne.lista);
        
    }
    /**
     * Main method launching the application.
     */
    public static void main(String[] args) throws IOException {

        /*
         *
         *
         * Klasa startowa. Uruchamia interfejs, implementuje rodzaj algorytmu. Będzie musiała jeszcze z interfejsu z czytywać
         * ilość pięter i wind
         *
         *

         */


        launch(LiftApp.class, args);
        

        //startuj();




    }
    
}
