package chess;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * FXML Controller class
 *
 * @author Rupak
 */
public class BoardNController {

    public VBox vB1;
    public VBox vB2;
    public Label info;
    public Label info2;

    public boolean endGame=false;
    public boolean isCastlingAvailable=true;
    public boolean isCastlingAvailable1 = true;
    public boolean castling = false;
    public boolean castling1 = false;


    @FXML
    private AnchorPane grid;
    private Tile[][] tiles=new Tile[8][8];


    boolean pieceSelection;
    int sx,sy;

    Image blankb,blankw;
    Image sl;
    Image sg;
    ImageView selectionBorder;

    List<ImageView> CellToMove;
    List<Tile> CaptureMove;
    List<Tile> FreeCellToMove;

    SocWriter socWriter;

    int currentPlayer;
    boolean otherPlayerMoving=true;


    /**
     * Initializes the controller class.
     */
    public void init() {
        pieceSelection =true;
        selectionBorder=new ImageView();
        selectionBorder.setVisible(false);
        FreeCellToMove =new ArrayList<>();
        CaptureMove = new ArrayList<>();
        CellToMove =new ArrayList<>();


        grid.getChildren().add(selectionBorder);


        blankb=new Image(getClass().getResourceAsStream("image/blankb.png"));
        blankw=new Image(getClass().getResourceAsStream("image/blankw.png"));
        sl=new Image(getClass().getResourceAsStream("image/sl.png"),50,50,true,true);
        sg=new Image(getClass().getResourceAsStream("image/sg.png"),50,50,true,true);

        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                tiles[i][j]=new Tile(0,i,j,this);
                grid.getChildren().addAll(tiles[i][j]);
                if((i+j)%2==0) tiles[i][j].setImage(blankb);
                else  tiles[i][j].setImage(blankw);
            }

        }
        for(int i=0;i<8;i++){
            tiles[i][1].player=1;
            tiles[i][1].Piece=6;
            tiles[i][1].setImage(new Image(getClass().getResourceAsStream(Piece.getImage(1,6)),50,50,true,true));
            tiles[i][6].player=-1;
            tiles[i][6].Piece=6;
            tiles[i][6].setImage(new Image(getClass().getResourceAsStream(Piece.getImage(-1,6)),50,50,true,true));

            if(i==0 || i==7)
            {
                tiles[i][0].player=1;
                tiles[i][0].Piece=3;
                tiles[i][0].setImage(new Image(getClass().getResourceAsStream(Piece.getImage(1,3)),50,50,true,true));
                tiles[i][7].player=-1;
                tiles[i][7].Piece=3;
                tiles[i][7].setImage(new Image(getClass().getResourceAsStream(Piece.getImage(-1,3)),50,50,true,true));
            }

            if(i==1 || i==6)
            {
                tiles[i][0].player=1;
                tiles[i][0].Piece=4;
                tiles[i][0].setImage(new Image(getClass().getResourceAsStream(Piece.getImage(1,4)),50,50,true,true));
                tiles[i][7].player=-1;
                tiles[i][7].Piece=4;
                tiles[i][7].setImage(new Image(getClass().getResourceAsStream(Piece.getImage(-1,4)),50,50,true,true));
            }

            if(i==2 || i==5)
            {
                tiles[i][0].player=1;
                tiles[i][0].Piece=5;
                tiles[i][0].setImage(new Image(getClass().getResourceAsStream(Piece.getImage(1,5)),50,50,true,true));
                tiles[i][7].player=-1;
                tiles[i][7].Piece=5;
                tiles[i][7].setImage(new Image(getClass().getResourceAsStream(Piece.getImage(-1,5)),50,50,true,true));
            }
            else if(i==3)
            {
                tiles[i][0].player=1;
                tiles[i][0].Piece=2;
                tiles[i][0].setImage(new Image(getClass().getResourceAsStream(Piece.getImage(1,2)),50,50,true,true));
                tiles[i][7].player=-1;
                tiles[i][7].Piece=2;
                tiles[i][7].setImage(new Image(getClass().getResourceAsStream(Piece.getImage(-1,2)),50,50,true,true));
            }
            else if(i==4)
            {
                tiles[i][0].player=1;
                tiles[i][0].Piece=1;
                tiles[i][0].setImage(new Image(getClass().getResourceAsStream(Piece.getImage(1,1)),50,50,true,true));
                tiles[i][7].player=-1;
                tiles[i][7].Piece=1;
                tiles[i][7].setImage(new Image(getClass().getResourceAsStream(Piece.getImage(-1,1)),50,50,true,true));
            }
        }

    }
    public void selectTile(Tile t) {

        if(endGame)return;
        if(otherPlayerMoving) return;

        boolean isCaptured=false;
        int x = t.x;
        int y = t.y;

        if (pieceSelection) {
            if(t.player==currentPlayer){
                pieceSelection = false;
                sx = x;
                sy = y;
                FreeCellToMove.clear();
                CaptureMove.clear();

                movement(t);

                for(Tile t1: FreeCellToMove){
                    ImageView iv=new ImageView();
                    CellToMove.add(iv);
                    iv.setLayoutX(t1.x*50);
                    iv.setLayoutY(t1.y*50);
                    iv.setImage(sg);
                    grid.getChildren().add(iv);
                    //CellToMove.add(iv);
                }
                for(Tile t1: CaptureMove){
                    ImageView iv=new ImageView();
                    CellToMove.add(iv);
                    iv.setLayoutX(t1.x*50);
                    iv.setLayoutY(t1.y*50);
                    iv.setImage(sl);
                    grid.getChildren().add(iv);
                    //.add(iv);
                }
            }
        } else {
            if(t.x == sx && t.y==sy){
                pieceSelection=true;
                for(ImageView iv: CellToMove) grid.getChildren().remove(iv);
                CellToMove.clear();
                FreeCellToMove.clear();
                CaptureMove.clear();
                return;
            }
            if(!FreeCellToMove.contains(t) && !CaptureMove.contains(t)) return;

            if((sx+sy)%2==0) tiles[sx][sy].setImage(blankb);
            else  tiles[sx][sy].setImage(blankw);

            int CaptureID = -2;
            int color = -2;
            if(CaptureMove.contains(t))
            {
                CaptureID = t.Piece;
                color = t.player;
                isCaptured=true;

            }

            tiles[sx][sy].player=0;
            t.setPlayer(currentPlayer);
            t.Piece=tiles[sx][sy].Piece;
            tiles[sx][sy].Piece=0;
            t.setImage((new Image(getClass().getResourceAsStream(Piece.getImage(t.player,t.Piece)),50,50,true,true)));
            pieceSelection = true;
            for(ImageView iv: CellToMove) grid.getChildren().remove(iv);
            CellToMove.clear();
            FreeCellToMove.clear();
            CaptureMove.clear();
            if(KingCheck(currentPlayer)){
                tiles[sx][sy].Piece=t.Piece;
                tiles[sx][sy].setImage(t.getImage());
                tiles[sx][sy].player=currentPlayer;

                if(isCaptured)
                {
                    t.setPlayer(-currentPlayer);
                    t.Piece = CaptureID;
                    t.setImage((new Image(getClass().getResourceAsStream(Piece.getImage(color,CaptureID)),50,50,true,true)));
                    isCaptured=false;
                }
                else
                {
                    t.setPlayer(0);
                    t.Piece = 0;
                    if ((t.x + t.y) % 2 == 0) t.setImage(blankb);
                    else t.setImage(blankw);
                }
                if(!endGame)info2.setText("Invalid Move");

            }
            else{

                info2.setText(" ");
                info.setText(" ");

                if(t.Piece==1 && ((sx-x)==2 || (sx-x)==-2))
                {
                    if(castling1)
                    {
                        tiles[7][t.y].player=0;
                        tiles[7][t.y].Piece=0;
                        if((7+t.y)%2==0) tiles[7][t.y].setImage(blankb);
                        else  tiles[7][t.y].setImage(blankw);
                        tiles[5][t.y].player=t.player;
                        tiles[5][t.y].Piece=3;
                        tiles[5][t.y].setImage((new Image(getClass().getResourceAsStream(Piece.getImage(t.player,3)),50,50,true,true)));
                        socWriter.write(7+" "+t.y+" "+5+" "+t.y);
                    }
                    else if(castling)
                    {
                        tiles[0][t.y].player=0;
                        tiles[0][t.y].Piece=0;
                        if((0+t.y)%2==0) tiles[0][t.y].setImage(blankb);
                        else  tiles[0][t.y].setImage(blankw);
                        tiles[3][t.y].player=t.player;
                        tiles[3][t.y].Piece=3;
                        tiles[3][t.y].setImage((new Image(getClass().getResourceAsStream(Piece.getImage(t.player,3)),50,50,true,true)));
                        socWriter.write(0+" "+t.y+" "+3+" "+t.y);
                    }
                }

                if(t.Piece == 6 && (t.y == 7 || t.y == 0))
                {
                    t.Piece = 2;
                    t.setImage((new Image(getClass().getResourceAsStream(Piece.getImage(t.player,t.Piece)),50,50,true,true)));
                }

                if(KingCheck(-currentPlayer)){
                    socWriter.write("$kingcheck");
                    if(check_mate(-currentPlayer)){
                        endGame=true;
                        info.setText("Check Mate ! You Win !!!");
                        info2.setText(" ");
                    }

                }
                if(isCaptured){
                    if(color==-1) vB1.getChildren().add(new ImageView(new Image(getClass().getResourceAsStream(Piece.getImage(color,CaptureID)),30,30,true,true)));
                    else vB2.getChildren().add(new ImageView(new Image(getClass().getResourceAsStream(Piece.getImage(color,CaptureID)),30,30,true,true)));
                    socWriter.write("* "+color+" "+CaptureID);

                }
                socWriter.write(sx+" "+sy+" "+x+" "+y);
                if(endGame)
                    socWriter.write("#check_mate");
                else
                    info2.setText("Opponent's Move");
                otherPlayerMoving=true;
            }
        }
    }
    void addCapure(String str){
        int color=Integer.parseInt(str.split(" ")[1]);
        int CaptureID=Integer.parseInt(str.split(" ")[2]);
        if(color==-1) vB1.getChildren().add(new ImageView(new Image(getClass().getResourceAsStream(Piece.getImage(color,CaptureID)),30,30,true,true)));
        else vB2.getChildren().add(new ImageView(new Image(getClass().getResourceAsStream(Piece.getImage(color,CaptureID)),30,30,true,true)));



    }
    void opCheckMate(){
        endGame=true;
        info.setText("Check Mate ! You Lose !!!");
        info2.setText(" ");
    }
    void check(){info.setText("Your king is on check!!");}
    void movement(Tile t) {
        FreeCellToMove.clear();
        CaptureMove.clear();

        int X = t.x;
        int Y = t.y;

        switch (t.Piece) {
            case 1: {
                if(isCastlingAvailable && t.player==1)
                {
                    if(tiles[3][t.y].player ==0 && tiles[2][t.y].player ==0 && tiles[1][t.y].player ==0 && tiles[0][t.y].Piece ==3)
                    {
                        castling = true;
                        FreeCellToMove.add(tiles[2][t.y]);
                    }
                    if(tiles[5][t.y].player ==0 && tiles[6][t.y].player ==0 && tiles[7][t.y].Piece ==3)
                    {
                        castling1 = true;
                        FreeCellToMove.add(tiles[6][t.y]);
                    }
                }

                if(isCastlingAvailable1 && t.player==-1)
                {
                    if(tiles[3][t.y].player ==0 && tiles[2][t.y].player ==0 && tiles[1][t.y].player ==0 && tiles[0][t.y].Piece ==3)
                    {
                        castling = true;
                        FreeCellToMove.add(tiles[2][t.y]);
                    }
                    if(tiles[5][t.y].player ==0 && tiles[6][t.y].player ==0 && tiles[7][t.y].Piece ==3)
                    {
                        castling1 = true;
                        FreeCellToMove.add(tiles[6][t.y]);
                    }
                }

                int a, b;

                a = X + 1;
                b = Y;
                if (a >= 0 && a < 8 && b >= 0 && b < 8) {
                    if (tiles[a][b].player == 0) FreeCellToMove.add(tiles[a][b]);
                    if (tiles[a][b].player == -t.player) CaptureMove.add(tiles[a][b]);
                }

                a = X - 1;
                b = Y;
                if (a >= 0 && a < 8 && b >= 0 && b < 8) {
                    if (tiles[a][b].player == 0) FreeCellToMove.add(tiles[a][b]);
                    if (tiles[a][b].player == -t.player) CaptureMove.add(tiles[a][b]);
                }

                a = X;
                b = Y + 1;
                if (a >= 0 && a < 8 && b >= 0 && b < 8) {
                    if (tiles[a][b].player == 0) FreeCellToMove.add(tiles[a][b]);
                    if (tiles[a][b].player == -t.player) CaptureMove.add(tiles[a][b]);
                }

                a = X;
                b = Y - 1;
                if (a >= 0 && a < 8 && b >= 0 && b < 8) {
                    if (tiles[a][b].player == 0) FreeCellToMove.add(tiles[a][b]);
                    if (tiles[a][b].player == -t.player) CaptureMove.add(tiles[a][b]);
                }

                a = X + 1;
                b = Y + 1;
                if (a >= 0 && a < 8 && b >= 0 && b < 8) {
                    if (tiles[a][b].player == 0) FreeCellToMove.add(tiles[a][b]);
                    if (tiles[a][b].player == -t.player) CaptureMove.add(tiles[a][b]);
                }

                a = X + 1;
                b = Y - 1;
                if (a >= 0 && a < 8 && b >= 0 && b < 8) {
                    if (tiles[a][b].player == 0) FreeCellToMove.add(tiles[a][b]);
                    if (tiles[a][b].player == -t.player) CaptureMove.add(tiles[a][b]);
                }

                b = Y + 1;
                a = X - 1;
                if (a >= 0 && a < 8 && b >= 0 && b < 8) {
                    if (tiles[a][b].player == 0) FreeCellToMove.add(tiles[a][b]);
                    if (tiles[a][b].player == -t.player) CaptureMove.add(tiles[a][b]);
                }


                a = X - 1;
                b = Y - 1;
                if (a >= 0 && a < 8 && b >= 0 && b < 8) {
                    if (tiles[a][b].player == 0) FreeCellToMove.add(tiles[a][b]);
                    if (tiles[a][b].player == -t.player) CaptureMove.add(tiles[a][b]);
                }
                if(t.player == 1)isCastlingAvailable = false;
                else isCastlingAvailable1 = false;

            }
            break;
            case 2:
                //queen movement
            {
                for (int i = X + 1; i < 8; i++) {
                    if (tiles[i][Y].player == 0) FreeCellToMove.add(tiles[i][Y]);
                    if (tiles[i][Y].player == -t.player) {
                        CaptureMove.add(tiles[i][Y]);
                        break;
                    }
                    if (tiles[i][Y].player == t.player) break;
                }
                for (int i = X - 1; i >= 0; i--) {
                    if (tiles[i][Y].player == 0) FreeCellToMove.add(tiles[i][Y]);
                    if (tiles[i][Y].player == -t.player) {
                        CaptureMove.add(tiles[i][Y]);
                        break;
                    }
                    if (tiles[i][Y].player == t.player) break;
                }
                for (int i = Y + 1; i < 8; i++) {
                    if (tiles[X][i].player == 0) FreeCellToMove.add(tiles[X][i]);
                    if (tiles[X][i].player == -t.player) {
                        CaptureMove.add(tiles[X][i]);
                        break;
                    }
                    if (tiles[X][i].player == t.player) break;
                }
                for (int i = Y - 1; i >= 0; i--) {
                    if (tiles[X][i].player == 0) FreeCellToMove.add(tiles[X][i]);
                    if (tiles[X][i].player == -t.player) {
                        CaptureMove.add(tiles[X][i]);
                        break;
                    }
                    if (tiles[X][i].player == t.player) break;
                }
                for (int i = X - 1, j = Y - 1; i >= 0 && j >= 0; i--, j--) {
                    if (tiles[i][j].player == 0) FreeCellToMove.add(tiles[i][j]);
                    if (tiles[i][j].player == -t.player) {
                        CaptureMove.add(tiles[i][j]);
                        break;
                    }
                    if (tiles[i][j].player == t.player) break;
                }
                for (int i = X + 1, j = Y - 1; i < 8 && j >= 0; i++, j--) {
                    if (tiles[i][j].player == 0) FreeCellToMove.add(tiles[i][j]);
                    if (tiles[i][j].player == -t.player) {
                        CaptureMove.add(tiles[i][j]);
                        break;
                    }
                    if (tiles[i][j].player == t.player) break;
                }
                for (int i = X - 1, j = Y + 1; i >= 0 && j < 8; i--, j++) {
                    if (tiles[i][j].player == 0) FreeCellToMove.add(tiles[i][j]);
                    if (tiles[i][j].player == -t.player) {
                        CaptureMove.add(tiles[i][j]);
                        break;
                    }
                    if (tiles[i][j].player == t.player) break;
                }
                for (int i = X + 1, j = Y + 1; i < 8 && j < 8; i++, j++) {
                    if (tiles[i][j].player == 0) FreeCellToMove.add(tiles[i][j]);
                    if (tiles[i][j].player == -t.player) {
                        CaptureMove.add(tiles[i][j]);
                        break;
                    }
                    if (tiles[i][j].player == t.player) break;
                }
        }
                break;

            case 3:
                //rook movement
            {
                for (int i = X + 1; i < 8; i++) {
                    if (tiles[i][Y].player == 0) FreeCellToMove.add(tiles[i][Y]);
                    if (tiles[i][Y].player == -t.player) {
                        CaptureMove.add(tiles[i][Y]);
                        break;
                    }
                    if (tiles[i][Y].player == t.player) break;
                }
                for (int i = X - 1; i >= 0; i--) {
                    if (tiles[i][Y].player == 0) FreeCellToMove.add(tiles[i][Y]);
                    if (tiles[i][Y].player == -t.player) {
                        CaptureMove.add(tiles[i][Y]);
                        break;
                    }
                    if (tiles[i][Y].player == t.player) break;
                }
                for (int i = Y + 1; i < 8; i++) {
                    if (tiles[X][i].player == 0) FreeCellToMove.add(tiles[X][i]);
                    if (tiles[X][i].player == -t.player) {
                        CaptureMove.add(tiles[X][i]);
                        break;
                    }
                    if (tiles[X][i].player == t.player) break;
                }
                for (int i = Y - 1; i >= 0; i--) {
                    if (tiles[X][i].player == 0) FreeCellToMove.add(tiles[X][i]);
                    if (tiles[X][i].player == -t.player) {
                        CaptureMove.add(tiles[X][i]);
                        break;
                    }
                    if (tiles[X][i].player == t.player) break;
                }
            }
            break;

            case 4:
            {
                //knight movement

                int a,b;
                a = X - 2;
                b = Y - 1;
                if (a >= 0 && a < 8 && b >= 0 && b < 8) {
                    if (tiles[a][b].player == 0) FreeCellToMove.add(tiles[a][b]);
                    if (tiles[a][b].player == -t.player) CaptureMove.add(tiles[a][b]);
                }

                a = X - 1;
                b = Y - 2;
                if (a >= 0 && a < 8 && b >= 0 && b < 8) {
                    if (tiles[a][b].player == 0) FreeCellToMove.add(tiles[a][b]);
                    if (tiles[a][b].player == -t.player) CaptureMove.add(tiles[a][b]);
                }

                a = X + 1;
                b = Y - 2;
                if (a >= 0 && a < 8 && b >= 0 && b < 8) {
                    if (tiles[a][b].player == 0) FreeCellToMove.add(tiles[a][b]);
                    if (tiles[a][b].player == -t.player) CaptureMove.add(tiles[a][b]);
                }

                a = X + 2;
                b = Y - 1;
                if (a >= 0 && a < 8 && b >= 0 && b < 8) {
                    if (tiles[a][b].player == 0) FreeCellToMove.add(tiles[a][b]);
                    if (tiles[a][b].player == -t.player) CaptureMove.add(tiles[a][b]);
                }

                a = X + 2;
                b = Y + 1;
                if (a >= 0 && a < 8 && b >= 0 && b < 8) {
                    if (tiles[a][b].player == 0) FreeCellToMove.add(tiles[a][b]);
                    if (tiles[a][b].player == -t.player) CaptureMove.add(tiles[a][b]);
                }

                a = X + 1;
                b = Y + 2;
                if (a >= 0 && a < 8 && b >= 0 && b < 8) {
                    if (tiles[a][b].player == 0) FreeCellToMove.add(tiles[a][b]);
                    if (tiles[a][b].player == -t.player) CaptureMove.add(tiles[a][b]);
                }

                b = Y + 2;
                a = X - 1;
                if (a >= 0 && a < 8 && b >= 0 && b < 8) {
                    if (tiles[a][b].player == 0) FreeCellToMove.add(tiles[a][b]);
                    if (tiles[a][b].player == -t.player) CaptureMove.add(tiles[a][b]);
                }


                a = X - 2;
                b = Y + 1;
                if (a >= 0 && a < 8 && b >= 0 && b < 8) {
                    if (tiles[a][b].player == 0) FreeCellToMove.add(tiles[a][b]);
                    if (tiles[a][b].player == -t.player) CaptureMove.add(tiles[a][b]);
                }
                break;
        }

            case 5:
            {   //bishop movement
                for (int i = X - 1, j = Y - 1; i >= 0 && j >= 0; i--, j--) {
                    if (tiles[i][j].player == 0) FreeCellToMove.add(tiles[i][j]);
                    if (tiles[i][j].player == -t.player) {
                        CaptureMove.add(tiles[i][j]);
                        break;
                    }
                    if (tiles[i][j].player == t.player) break;
                }
                for (int i = X + 1, j = Y - 1; i < 8 && j >= 0; i++, j--) {
                    if (tiles[i][j].player == 0) FreeCellToMove.add(tiles[i][j]);
                    if (tiles[i][j].player == -t.player) {
                        CaptureMove.add(tiles[i][j]);
                        break;
                    }
                    if (tiles[i][j].player == t.player) break;
                }
                for (int i = X - 1, j = Y + 1; i >= 0 && j < 8; i--, j++) {
                    if (tiles[i][j].player == 0) FreeCellToMove.add(tiles[i][j]);
                    if (tiles[i][j].player == -t.player) {
                        CaptureMove.add(tiles[i][j]);
                        break;
                    }
                    if (tiles[i][j].player == t.player) break;
                }
                for (int i = X + 1, j = Y + 1; i < 8 && j < 8; i++, j++) {
                    if (tiles[i][j].player == 0) FreeCellToMove.add(tiles[i][j]);
                    if (tiles[i][j].player == -t.player) {
                        CaptureMove.add(tiles[i][j]);
                        break;
                    }
                    if (tiles[i][j].player == t.player) break;
                }
            }
            break;

            case 6:
                //pawn movement
            {
                if(t.player==1)
                {
                    if (tiles[X][Y+1].player == 0)FreeCellToMove.add(tiles[X][Y+1]);
                    if(X !=7 && tiles[X+1][Y+1].player == -t.player)CaptureMove.add(tiles[X+1][Y+1]);
                    if(X !=0 && tiles[X-1][Y+1].player == -t.player)CaptureMove.add(tiles[X-1][Y+1]);
                    if(Y==1 && tiles[X][Y+2].player == 0) FreeCellToMove.add(tiles[X][Y+2]);

                }
                else
                {
                    if (tiles[X][Y-1].player == 0)FreeCellToMove.add(tiles[X][Y-1]);
                    if(X !=7 && tiles[X+1][Y-1].player == -t.player)CaptureMove.add(tiles[X+1][Y-1]);
                    if(X!=0 && tiles[X-1][Y-1].player == -t.player)CaptureMove.add(tiles[X-1][Y-1]);
                    if(Y==6 && tiles[X][Y-2].player == 0) FreeCellToMove.add(tiles[X][Y-2]);
                }
            }
        }

    }
    public boolean KingCheck(int pl) {
        Tile king = null;
        List<Tile>arr = new ArrayList<>();
        for(Tile []tR: tiles)
        {
            for( Tile t: tR)
            {
                if(t.player== -pl){arr.add(t);}
                else if(t.Piece == 1 && t.player==pl)
                    king = t;
            }
        }
        for(Tile t: arr) {
            int X = t.x;
            int Y = t.y;

            switch (t.Piece) {
                case 1: {

                    if((king.x==X+1)&&(king.y==Y+1)) {
                        arr.clear();
                        return true;
                    }
                    if((king.x==X+1)&&(king.y==Y)) {
                        arr.clear();
                        return true;
                    }
                    if((king.x==X+1)&&(king.y==Y-1)){
                        arr.clear();
                        return true;
                    }

                    if((king.x==X)&&(king.y==Y+1)) {
                        arr.clear();
                        return true;
                    }
                    if((king.x==X)&&(king.y==Y-1)){
                        arr.clear();
                        return true;
                    }

                    if((king.x==X-1)&&(king.y==Y+1)){
                        arr.clear();
                        return true;
                    }
                    if((king.x==X-1)&&(king.y==Y)){
                        arr.clear();
                        return true;
                    }
                    if((king.x==X-1)&&(king.y==Y-1)){
                        arr.clear();
                        return true;
                    }
                }
                break;
                case 2:
                    //queen movement
                {
                    for (int i = X + 1; i < 8; i++) {
                        if((king.x==i)&&(king.y==Y)){
                            arr.clear();
                            return true;
                        }
                        if (tiles[i][Y].player == -t.player)break;
                        if (tiles[i][Y].player == t.player) break;
                    }
                    for (int i = X - 1; i >= 0; i--) {
                        if((king.x==i)&&(king.y==Y)){
                            arr.clear();
                            return true;
                        }
                        if (tiles[i][Y].player == -t.player)break;
                        if (tiles[i][Y].player == t.player) break;
                    }
                    for (int i = Y + 1; i < 8; i++) {
                        if((king.x==X)&&(king.y==i)) {
                            arr.clear();
                            System.out.println(t.Piece+2);
                            return true;
                        }
                        if (tiles[X][i].player == -t.player)break;
                        if (tiles[X][i].player == t.player) break;
                    }
                    for (int i = Y - 1; i >= 0; i--) {
                        if((king.x==X)&&(king.y==i)) {
                            arr.clear();
                            System.out.println(t.Piece+3);
                            return true;
                        }
                        if (tiles[X][i].player == -t.player)break;
                        if (tiles[X][i].player == t.player) break;
                    }
                    for (int i = X - 1, j = Y - 1; i >= 0 && j >= 0; i--, j--) {
                        if((king.x==i)&&(king.y==j)){
                            arr.clear();
                            System.out.println(t.Piece+4);
                            return true;
                        }
                        if (tiles[i][j].player == -t.player)break;
                        if (tiles[i][j].player == t.player) break;
                    }
                    for (int i = X + 1, j = Y - 1; i < 8 && j >= 0; i++, j--) {
                        if((king.x==i)&&(king.y==j)){
                            arr.clear();
                            System.out.println(t.Piece+5);
                            return true;
                        }
                        if (tiles[i][j].player == -t.player)break;
                        if (tiles[i][j].player == t.player) break;
                    }
                    for (int i = X - 1, j = Y + 1; i >= 0 && j < 8; i--, j++) {
                        if((king.x==i)&&(king.y==j)) {
                            arr.clear();
                            System.out.println(t.Piece+6);
                            return true;
                        }
                        if (tiles[i][j].player == -t.player)break;
                        if (tiles[i][j].player == t.player) break;
                    }
                    for (int i = X + 1, j = Y + 1; i < 8 && j < 8; i++, j++) {
                        if((king.x==i)&&(king.y==j)){
                            arr.clear();
                            System.out.println(t.Piece+7);
                            return true;
                        }
                        if (tiles[i][j].player == -t.player)break;
                        if (tiles[i][j].player == t.player) break;
                    }
                }
                break;

                case 3:
                    //rook movement
                {
                    for (int i = X + 1; i < 8; i++) {
                        if((king.x==i)&&(king.y==Y)){
                            arr.clear();
                            System.out.println(t.Piece);
                            return true;
                        }
                        if (tiles[i][Y].player == -t.player)break;
                        if (tiles[i][Y].player == t.player) break;
                    }
                    for (int i = X - 1; i >= 0; i--) {
                        if((king.x==i)&&(king.y==Y)) {
                            arr.clear();
                            System.out.println(t.Piece);
                            return true;
                        }
                        if (tiles[i][Y].player == -t.player)break;
                        if (tiles[i][Y].player == t.player) break;
                    }
                    for (int i = Y + 1; i < 8; i++) {
                        if((king.x==X)&&(king.y==i)) {
                            arr.clear();
                            System.out.println(t.Piece);
                            return true;
                        }
                        if (tiles[X][i].player == -t.player)break;
                        if (tiles[X][i].player == t.player) break;
                    }
                    for (int i = Y - 1; i >= 0; i--) {
                        if((king.x==X)&&(king.y==i)) {
                            arr.clear();
                            System.out.println(t.Piece);
                            return true;
                        }
                        if (tiles[X][i].player == -t.player)break;
                        if (tiles[X][i].player == t.player) break;
                    }
                }
                break;

                case 4:
                {
                    //knight movement

                    int a,b;
                    a = X - 2;
                    b = Y - 1;
                    if (a >= 0 && a < 8 && b >= 0 && b < 8) {
                        if((king.x==a)&&(king.y==b)) {
                            arr.clear();
                            System.out.println(t.Piece);
                            return true;
                        }
                    }

                    a = X - 1;
                    b = Y - 2;
                    if (a >= 0 && a < 8 && b >= 0 && b < 8) {
                        if((king.x==a)&&(king.y==b)) {
                            arr.clear();
                            System.out.println(t.Piece);
                            return true;
                        }
                    }


                    a = X + 1;
                    b = Y - 2;
                    if (a >= 0 && a < 8 && b >= 0 && b < 8) {
                        if((king.x==a)&&(king.y==b)) {
                            arr.clear();
                            System.out.println(t.Piece);
                            return true;
                        }
                    }

                    a = X + 2;
                    b = Y - 1;
                    if (a >= 0 && a < 8 && b >= 0 && b < 8) {
                        if((king.x==a)&&(king.y==b)) {
                            arr.clear();
                            System.out.println(t.Piece);
                            return true;
                        }
                    }


                    a = X + 2;
                    b = Y + 1;
                    if (a >= 0 && a < 8 && b >= 0 && b < 8) {
                        if((king.x==a)&&(king.y==b)) {
                            arr.clear();
                            System.out.println(t.Piece);
                            return true;
                        }
                    }


                    a = X + 1;
                    b = Y + 2;
                    if (a >= 0 && a < 8 && b >= 0 && b < 8) {
                        if((king.x==a)&&(king.y==b))
                        {
                            arr.clear();
                            System.out.println(t.Piece);
                            return true;
                        }
                    }

                    b = Y + 2;
                    a = X - 1;
                    if (a >= 0 && a < 8 && b >= 0 && b < 8) {
                        if((king.x==a)&&(king.y==b))
                        {
                            arr.clear();
                            System.out.println(t.Piece);
                            return true;
                        }
                    }


                    a = X - 2;
                    b = Y + 1;
                    if (a >= 0 && a < 8 && b >= 0 && b < 8) {
                        if((king.x==a)&&(king.y==b))
                        {
                            arr.clear();
                            System.out.println(t.Piece);
                            return true;
                        }
                    }
                    break;
                }

                case 5:
                {   //bishop movement
                    for (int i = X - 1, j = Y - 1; i >= 0 && j >= 0; i--, j--) {

                        if((king.x==i)&&(king.y==j)) {
                            arr.clear();
                            System.out.println(t.Piece);
                            return true;
                        }
                        if (tiles[i][j].player == -t.player)break;
                        if (tiles[i][j].player == t.player) break;

                    }
                    for (int i = X + 1, j = Y - 1; i < 8 && j >= 0; i++, j--) {
                        if((king.x==i)&&(king.y==j)) {
                            arr.clear();
                            System.out.println(t.Piece);
                            return true;
                        }
                        if (tiles[i][j].player == -t.player)break;
                        if (tiles[i][j].player == t.player) break;
                    }
                    for (int i = X - 1, j = Y + 1; i >= 0 && j < 8; i--, j++) {
                        if((king.x==i)&&(king.y==j)) {
                            arr.clear();
                            System.out.println(t.Piece);
                            return true;
                        }
                        if (tiles[i][j].player == -t.player)break;
                        if (tiles[i][j].player == t.player) break;
                    }
                    for (int i = X + 1, j = Y + 1; i < 8 && j < 8; i++, j++) {
                        if((king.x==i)&&(king.y==j)) {
                            arr.clear();
                            System.out.println(t.Piece);
                            return true;
                        }
                        if (tiles[i][j].player == -t.player)break;
                        if (tiles[i][j].player == t.player) break;
                    }
                }
                break;

                case 6:
                    //pawn movement
                {
                    if(X !=7 && tiles[X+1][Y+t.player]== king) {
                        arr.clear();
                        System.out.println(t.Piece);
                        return true;
                    }
                    if(X !=0 && tiles[X-1][Y+t.player]== king ){
                        arr.clear();
                        System.out.println(t.Piece);
                        return true;
                    }
                }
            }


        }
        return false;
    }



    public boolean check_mate(int pl){
        List<Tile>arr = new ArrayList<>();

        for(Tile []tR: tiles)
        {
            for( Tile t: tR)
            {
                if(t.player==pl){arr.add(t);}
            }
        }
        for(Tile t: arr) {
            boolean flag=false;

            int X = t.x;
            int Y = t.y;
            int p=t.player;
            int c=t.Piece;


            movement(t);
            t.player=0;
            t.Piece=0;
            for(Tile t1:FreeCellToMove){
                t1.player=p;
                t1.Piece=c;

                if(!KingCheck(pl)) flag=true;

                t1.player=0;
                t1.Piece=0;

                if(flag) break;
            }
            t.player=p;
            t.Piece=c;
            if(flag) {
                FreeCellToMove.clear();
                CaptureMove.clear();
                return false;
            }
            t.player=0;
            t.Piece=0;

            for(Tile t1:CaptureMove){
                int p1=t1.player;
                int c1=t1.Piece;
                t1.player=p;
                t1.Piece=c;
                //if(c==1) System.out.println("King move "+t1.x+" "+t1.y);
                if(!KingCheck(pl)) flag=true;
                t1.player=p1;
                t1.Piece=c1;
                if(flag) break;
            }

            t.player=p;
            t.Piece=c;
            FreeCellToMove.clear();
            CaptureMove.clear();
            if(flag) {
                return  false;
            }
        }
        return true;
    }
    public void create_game_click(ActionEvent actionEvent) {
        try {
            Server server=new Server(this);
            socWriter=server.socWriter;
            currentPlayer=1;
            otherPlayerMoving=true;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void join_game_click(ActionEvent actionEvent) {
        try {
            Client client =new Client(this);
            socWriter= client.socWriter;
            currentPlayer=-1;
            otherPlayerMoving=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setSocWriter(SocWriter socWriter) {
        this.socWriter = socWriter;
        otherPlayerMoving=false;
    }
    public void updateOtherPlayer(String str){
            sx = Integer.parseInt(str.split(" ")[0]);
            sy = Integer.parseInt(str.split(" ")[1]);
            int x = Integer.parseInt(str.split(" ")[2]);
            int y = Integer.parseInt(str.split(" ")[3]);
            Tile t = tiles[x][y];
            if ((sx + sy) % 2 == 0) tiles[sx][sy].setImage(blankb);
            else tiles[sx][sy].setImage(blankw);
            //System.out.println(x + " " + y);
            tiles[sx][sy].player = 0;
            t.setPlayer(-currentPlayer);
            t.Piece = tiles[sx][sy].Piece;
            tiles[sx][sy].Piece = 0;
            t.setImage((new Image(getClass().getResourceAsStream(Piece.getImage(t.player, t.Piece)), 50, 50, true, true)));
            otherPlayerMoving = false;

            if (t.Piece == 6 && (t.y == 7 || t.y == 0)) {
                t.Piece = 2;
                t.setImage((new Image(getClass().getResourceAsStream(Piece.getImage(t.player, t.Piece)), 50, 50, true, true)));
            }

            info2.setText("Your Move");
    }
}
