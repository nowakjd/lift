/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lift;

import java.util.Comparator;

/**
 *
 * @author Adam
 *
 * do sortowania kontenerów... nie miałem lepszego pomnysłu
 *
 */
public class MyComparator implements Comparator<Pasazer> {

    public int compare(Pasazer pasazer_a, Pasazer pasazer_b) {
                if (pasazer_a.start < pasazer_b.start) {
                return -1;
        }
                else if (pasazer_a.start > pasazer_b.start) {
               return 1;
        }
            return 0;
    }
}
