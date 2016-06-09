package chess;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *      create Piece with constructor
 *      getImage returns the image for the piece
 *            
 * @author Rupak
 */


class Piece {

    public static String getImage(int color,int ID) {
 
    String filename = "";
    filename += (color == 1 ? "w" : "b");
    switch (ID) {
        case 1:
            filename += "k";
            break;    
        case 2:
            filename += "q";
            break;
        case 3:
            filename += "r";
            break;
        case 4:
            filename += "n";
            break;
        case 5:
            filename += "b";
            break;
        case 6:
            filename += "p";
            break;
        
    }
    filename += ".png";

    return "image/"+filename;
    }
    
}
