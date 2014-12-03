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

            for (GameEnemy smallGreenEnemies : WingmanX.smallGreenEnemies) {
                if (smallGreenEnemies.spawned == false) {
                    smallGreenEnemies.spawned = true;
                    smallGreenEnemies.show = true;
                    break;
                }
            }

            if (WingmanX.gameTimeCounter > 5) {
                for (GameEnemy smallWhiteEnemies : WingmanX.smallWhiteEnemies) {
                    if (smallWhiteEnemies.spawned == false) {
                        smallWhiteEnemies.spawned = true;
                        smallWhiteEnemies.show = true;
                        break;
                    }
                }
            }

            for (GameEnemy smallGreenEnemies : WingmanX.smallGreenEnemies) {
                if (smallGreenEnemies.show == true && smallGreenEnemies.spawned == true) {
                    smallGreenEnemies.fire();
                }
            }
            
            if(WingmanX.gameTimeCounter > 6){
                for (GameEnemy smallWhiteEnemies : WingmanX.smallWhiteEnemies) {
                if (smallWhiteEnemies.show == true && smallWhiteEnemies.spawned == true) {
                    smallWhiteEnemies.fire();
                }
            }
            }

            if (WingmanX.bossSpawned) {
                WingmanX.boss.spawned = true;
                WingmanX.boss.show = true;
            }
        }
    }
}
