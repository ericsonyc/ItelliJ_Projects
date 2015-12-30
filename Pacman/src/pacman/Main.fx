/*
 * Main.fx
 *
 * Created on 2008-12-20, 12:02:26
 */

package pacman;

import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pacman.Maze;
import pacman.MazeData;

/**
 * @author Henry Zhang
 * http://www.javafxgame.com
 */

var s = Stage{
    title: "PAC-MAN by Henry Zhang http://www.javafxgame.com"
    width: MazeData.calcGridX(MazeData.GRID_SIZE + 2)
    height: MazeData.calcGridY(MazeData.GRID_SIZE + 5)
    scene: Scene{ 
            content: [ Maze {}
            ]
           }

}
