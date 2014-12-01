/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wingmanx;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author markfavis
 */
public class gameTimerClass implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

        for (GameEnemy smallEnemies : WingmanX.smallEnemies) {
            if (smallEnemies.spawned == false) {
                smallEnemies.spawned = true;
                smallEnemies.show = true;
                break;
            }
        }
        
        for (GameEnemy smallEnemies : WingmanX.smallEnemies) {
            if (smallEnemies.show == true && smallEnemies.spawned == true) {
                smallEnemies.fire();
            }
        }
    }
}
