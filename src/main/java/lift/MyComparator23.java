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
public class MyComparator23 implements Comparator<Pasazer> {

    public int compare(Pasazer pasazer_a, Pasazer pasazer_b) {
        if (pasazer_a.start < pasazer_b.start) {
            return -1;
        }
        else if (pasazer_a.start > pasazer_b.start) {
           return 1;
        }
        else if (pasazer_a.start == pasazer_b.start) {
            
            /* jezeli jazda W GORE oraz obaj W TYM SAMYM kierunku */            
            if (pasazer_a.start < pasazer_a.cel && pasazer_b.start < pasazer_b.cel) {
                //System.out.println("JAZDA1");
                if (pasazer_a.cel < pasazer_b.cel) {
                    //System.out.println("-1 pasazer: " + pasazer_a + " drugi: " + pasazer_b);
                    return -1;
                }
                else if (pasazer_a.cel > pasazer_b.cel) {
                    //System.out.println("1 pasazer: " + pasazer_b + " drugi: " + pasazer_a);
                    return 1;
                }
            }            
            /* jezeli jazda W DOL oraz obaj W TYM SAMYM kierunku */
            else if (pasazer_a.start > pasazer_a.cel && pasazer_b.start > pasazer_b.cel) {
                //System.out.println("JAZDA2");
                if (pasazer_a.cel < pasazer_b.cel) {
                    return 1;
                }
                if (pasazer_a.cel > pasazer_b.cel) {
                    return -1;
                }
            }
            /* jezeli obaj W ROZNYCH kierunkach */
            else if (pasazer_a.start > pasazer_a.cel && pasazer_b.start < pasazer_b.cel) {
                //System.out.println("JAZDA3");
                return -1;
            }
            else if (pasazer_a.start < pasazer_a.cel && pasazer_b.start > pasazer_b.cel) {
                //System.out.println("JAZDA4");
                return 1;
            }
        }
        return 0;
    }
}
