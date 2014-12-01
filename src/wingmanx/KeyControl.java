/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wingmanx;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author markfavis
 */
public class KeyControl extends KeyAdapter {

    public void keyPressed(KeyEvent e) {
        WingmanX.gameEvents.setValue(e);
    }
}
