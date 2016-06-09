package chess;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * Created by Rupak on 6/2/2016.
 */

public class Tile extends ImageView {
    int player;
    BoardNController controller;
    int x,y;
    Tile t;
    int Piece;

    public Tile(int player,int x,int y,BoardNController controller) {
        super();
        this.player = player;
        this.controller=controller;
        this.x=x;
        this.y=y;
        this.setLayoutX(x*50);
        this.setLayoutY(y*50);
        t=this;
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //System.out.println("#"+x+" "+y);
                controller.selectTile(t);
            }
        });
    }

    public Tile(int color, int Piece)
    {
        player = color;
        this.Piece = Piece;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

}
