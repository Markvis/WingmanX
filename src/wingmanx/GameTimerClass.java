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
public class GameTimerClass implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

        if (WingmanX.gameStart == true) {
            WingmanX.gameTimeCounter += 1;

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

            if (WingmanX.bossSpawned) {
                WingmanX.boss.spawned = true;
                WingmanX.boss.show = true;
            }
        }
    }
}
