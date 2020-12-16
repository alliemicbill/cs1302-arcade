package cs1302.arcade;

import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.animation.PathTransition;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.geometry.Pos;
import java.lang.String;
import java.util.Random;
import javafx.beans.property.StringProperty;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Application subclass for {@code ArcadeApp}.
 * @version 2019.fa
 */
public class ArcadeApp extends Application {

    Group group = new Group();           // main container
    Stage stage = new Stage();
    GridPane gridPane = new GridPane();
    Image blueCircle = new Image("file:resources/blue.png", 60,60,true,true);
    Image pinkCircle = new Image("file:resources/pink.png",60,60,true,true);
    Button playGame = new Button("PlayGame");
    int con = 0;
    Text who = new Text();
    Text score = new Text();
    StackPane home = new StackPane();
    Image revp2 = new Image("file:resources/reversipretty.png",520,90,false,false);
    ImageView t3 = new ImageView(revp2);
    Button re = new Button("return to Home Page");
    Label turn = new Label("Turn: ");
    Label sco = new Label("Score: ");


    /** {@inheritDoc} */
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        Image revp = new Image("file:resources/reversipretty.png",600,700,false,false);
        Scene scene = new Scene(home);

        Button restart = new Button("Restart");
        ImageView t2 = new ImageView(revp);
        Button exitGame = new Button("exit app");
        StackPane.setAlignment(t2, Pos.CENTER);
        StackPane.setAlignment(playGame, Pos.BOTTOM_LEFT);
        StackPane.setAlignment(exitGame, Pos.BOTTOM_RIGHT);
        home.getChildren().addAll(t2,playGame,exitGame);
        gridpane();
        HBox sub = new HBox(turn, who,restart, re, exitGame);
        HBox sub2 = new HBox(sco,score);
        VBox  allie = new VBox(t3, sub, gridPane, sub2);
        Scene allieScene = new Scene(allie);
        EventHandler<ActionEvent> n = event -> {
            con = 0;
            score.setText("");
            stage.setScene(scene);
        };
        re.setOnAction(n);
        EventHandler<ActionEvent> e = event -> {
            System.exit(0);
        };
        exitGame.setOnAction(e);
        EventHandler<ActionEvent> f = event -> {
            stage.setScene(allieScene);
            stage.setMaxWidth(1280);
            stage.setMaxHeight(720);
            gridpane();
            Runnable r = () -> {
                con = 1;
                gamePlay();
            };
            Thread t = new Thread(r);
            t.setDaemon(true);
            t.start();
        }; //f
        playGame.setOnAction(f);
        EventHandler<ActionEvent> l = event -> {
            con = 0;
            gridpane();
            Runnable r = () -> {
                con = 1;
                gamePlay();
            };
            Thread t = new Thread(r);
            t.setDaemon(true);
            t.start();
        }; //f
        restart.setOnAction(l);
        stage.setScene(scene);
        stage.setMaxWidth(1280);
        stage.setMaxHeight(720);
        stage.setTitle("cs1302-arcade!");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
        group.requestFocus();
    } // start

    String[][] alphabet = new String[][] {
        {"AA", "BA", "CA", "DA", "EA", "FA", "GA", "HA",},
        {"AB", "BB", "CB", "DB", "EB", "FB", "GB", "HB",},
        {"AC", "BC", "CC", "DC", "EC", "FC", "GC", "HC",},
        {"AD", "BD", "CD", "DD", "ED", "FD", "GD", "HD",},
        {"AE", "BE", "CE", "DE", "EE", "FE", "GE", "HE",},
        {"AF", "BF", "CF", "DF", "EF", "FF", "GF", "HF",},
        {"AG", "BG", "CG", "DG", "EG", "FG", "GG", "HG",},
        {"AH", "BH", "CH", "DH", "EH", "FH", "GH", "HH",}
    };

    Rectangle[][] rect = new Rectangle[8][8];


    /**
     *sets the first gridpane.
     */
    public void gridpane() {
        gridPane.setVgap(5.0);
        gridPane.setHgap(5.0);
        gridPane.setGridLinesVisible(true);
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Rectangle rec = new Rectangle();
                rec.setWidth(60);
                rec.setHeight(60);
                rec.setFill(Color.PEACHPUFF);
                rec.setId(alphabet[row][col]);
                rect[row][col] = rec;
                gridPane.add(rect[row][col], col, row);
            }
        }
        gridPane.add(new ImageView(pinkCircle), 3, 3);
        gridPane.add(new ImageView(pinkCircle), 4, 4);
        gridPane.add(new ImageView(blueCircle), 3, 4);
        gridPane.add(new ImageView(blueCircle), 4, 3);
    } //gridpane

    char[][] board;
    int pScore = 0;
    int bScore = 0;
    int remaining;
    int[][] bArray = new int[8][8];
    int[][] pArray = new int[8][8];

    /**sets the board.*/
    public void board() {
        board = new char[][]{
            {'_', '_', '_', '_', '_', '_', '_', '_',},
            {'_', '_', '_', '_', '_', '_', '_', '_',},
            {'_', '_', '_', '_', '_', '_', '_', '_',},
            {'_', '_', '_', 'P', 'B', '_', '_', '_',},
            {'_', '_', '_', 'B', 'P', '_', '_', '_',},
            {'_', '_', '_', '_', '_', '_', '_', '_',},
            {'_', '_', '_', '_', '_', '_', '_', '_',},
            {'_', '_', '_', '_', '_', '_', '_', '_',},
        };
    } //board

    /**
     *Creates the event for the clickable squares.
     *@param array
     *@param player
     *@param opponent
     *@return mouse event
     */
    private EventHandler<?super MouseEvent> createMouseHandler(
        int[][] array, char player,char opponent) {
        return event -> {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    String ch = "Rectangle[id=" + alphabet[i][j];
                    String s = String.valueOf(event.getSource());
                    if (s.startsWith(ch)) {
                        move(array, i, j, player, opponent);
                    } //if
                } //for
            } //for
        };
    } //createmouse event

/**
 *shows where the player can move to.
 *@param array
 *@param player
 *@param opponent
 */
    public void showClickableSpaces(int[][] array, char player, char opponent) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (array[i][j] == 1 ) {
                    if (player == 'P') {
                        rect[i][j].setFill(Color.HOTPINK);
                    } else {
                        rect[i][j].setFill(Color.DEEPSKYBLUE);
                    } //if
                    rect[i][j].setDisable(false);
                    rect[i][j].setOnMouseClicked(
                        createMouseHandler(array, player,opponent));

                } //if
            } //for
        } //for
    } //ShowclickableSpaces

    /**
     *checks if the game has ended.
     *@param pArray
     *@param bArray
     *@return int
     */
    public int gameResult(int [][] pArray, int[][] bArray) {
        updateScores();
        if (remaining == 0) {
            if (pScore > bScore) {
                return 1;
            } else if (bScore > pScore) {
                return -1;
            } else {
                return 0; //Draw
            } //if
        } //if
        if (pScore == 0 || bScore == 0) {
            if (pScore > 0) {
                return 1;
            } else if (bScore > 0) {
                return -1;
            } //if
        } //if
        int check = 0;
        for (int i = 0; i < 8; i++) {
            for (int k = 0; k < 8; k++) {
                if (pArray[i][k] == 1 || bArray[i][k] == 1 ) {
                    check = 1;
                } //if
            } //for
        } //for
        if (check == 0) {
            if (pScore > bScore) {
                return 1;
            } else if (bScore > pScore) {
                return -1;
            } else {
                return 0; //Draw
            } //if
        } //if
        return -2;
    } //gameResult

    /**
     *finds all the places the player could move to.
     *@param player
     *@param opponent
     *@param array
     */
    private void findClickableSpace(char player, char opponent, int[][] array) {
        for (int a = 0; a < 8; a++) {
            for (int b = 0; b < 8; b++) {

                if (board[a][b] == opponent) {
                    int A = a;
                    int B = b;
                    if (a - 1 >= 0 && b - 1 >= 0 && board[a - 1][b - 1] == '_') {
                        a = a + 1;
                        b = b + 1;
                        while (a < 7 && b < 7 && board[a][b] == opponent) {
                            a++;
                            b++;
                        } //while
                        if (a < 8 && b < 8 && board[a][b] == player) {
                            array[A - 1 ][B - 1] = 1;
                        } //if
                    } //if
                    a = A;
                    b = B;
                    if (a - 1 >= 0 && board[a - 1][b] == '_') {
                        a = a + 1;
                        while (a < 7 && board[a][b] == opponent) {
                            a++;
                        } //while
                        if (a < 8 && board[a][b] == player) {
                            array[A - 1][B] = 1;
                        } //if
                    } //if
                    a = A;
                    if (a - 1 >= 0 && b + 1 < 8 && board[a - 1][b + 1] == '_') {
                        a = a + 1;
                        b = b - 1;
                        while (a < 7 && b > 0 && board[a][b] == opponent) {
                            a++;
                            b--;
                        } //while
                        if (a <= 7 && b >= 0 && board[a][b] == player) {
                            array[A - 1][B + 1] = 1;
                        } //if
                    }  //if
                    a = A;
                    b = B;
                    if (b - 1 >= 0 && board[a][b - 1] == '_') {
                        b = b + 1;
                        while (b < 7 && board[a][b] == opponent) {
                            b++;
                        } //while
                        if (b < 8 && board[a][b] == player) {
                            array[A][B - 1] = 1;
                        } //if
                    } //if
                    b = B;
                    a = A;
                } //if
            } //for
        } //for
        findClickableSpacePart2(player, opponent, array);
    } // findClickableSpace

    /**shows who wins.*/
    public void win() {
        String w = "";
        if (pScore > bScore) {
            w = "PINK WINS!";
        } else if (bScore > pScore) {
            w = "BLUE WINS!";
        } else {
            w = "IT'S A DRAW. NOONE WINS :(";
        } //if
        score.setText("Pink = " + pScore + ": Blue = " + bScore + "      " + w);
    } //win

    /**
     *finds all the places the player could move to.
     *@param player
     *@param opponent
     *@param array
     */
    private void findClickableSpacePart2(char player, char opponent, int[][] array) {
        for (int a = 0; a < 8; a++) {
            for (int b = 0; b < 8; b++) {

                if (board[a][b] == opponent) {
                    int A = a;
                    int B = b;

                    if (b + 1 <= 7 && board[a][b + 1] == '_') {
                        b = b - 1;
                        while (b > 0 && board[a][b] == opponent) {
                            b--;
                        } //while
                        if (b >= 0 && board[a][b] == player) {
                            array[A][B + 1] = 1;
                        } //if
                    } //if
                    b = B;
                    if (a + 1 <= 7 && b - 1 >= 0 && board[a + 1][b - 1] == '_') {
                        a = a - 1;
                        b = b + 1;
                        while (a > 0 && b < 7 && board[a][b] == opponent) {
                            a--;
                            b++;
                        } //while
                        if (a >= 0 && b <= 7 && board[a][b] == player) {
                            array[A + 1][B - 1] = 1;
                        } //if
                    } //if
                    a = A;
                    b = B;
                    if (a + 1 <= 7 && board[a + 1][b] == '_') {
                        a = a - 1;
                        while (a > 0 && board[a][b] == opponent) {
                            a--;
                        } //while
                        if (a >= 0 && board[a][b] == player) {
                            array[A + 1][B] = 1;
                        } //if
                    } //if
                    a = A;
                    if (a + 1 <= 7 && b + 1 <= 7 && board[a + 1][b + 1] == '_') {
                        a = a - 1;
                        b = b - 1;
                        while (a > 0 && b > 0 && board[a][b] == opponent) {
                            a--;
                            b--;
                        } //while
                        if (a >= 0 && b >= 0 && board[a][b] == player) {
                            array[A + 1][B + 1] = 1;
                        } //if
                    } //if
                    a = A;
                    b = B;
                } //if
            } //for
        } //for
    } //findclickablespace part 2

    /**
     *places the correct image into the gird based of od the players move.
     *@param player
     */
    public void setPic(char player) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (player == 'P') {
                    if (board[i][j] == 'P') {
                        gridPane.add(new ImageView(pinkCircle), j, i);
                    } //if
                } else {
                    if (board[i][j] == 'B') {
                        gridPane.add(new ImageView(blueCircle), j, i);
                    } //if
                } //if
            } //for
        } //for
        if (player == 'P') {
            act = 0;
        } else {
            act = 1;
        } //if
    } //setPic

/**
 *does the players move.
 *@param array
 *@param x
 *@param y
 *@param player
 *@param opponent
 */
    public void move(int[][] array, int x, int y, char player, char opponent) {
        int c = x, d = y;
        board[c][d] = player;
        int C = c, D = d;

        if (c - 1 >= 0 && d - 1 >= 0 && board[c - 1][d - 1] == opponent) {
            c = c - 1;
            d = d - 1;
            while (c > 0 && d > 0 && board[c][d] == opponent) {
                c--;
                d--;
            } //while
            if (c >= 0 && d >= 0 && board[c][d] == player) {
                while (c != C - 1 && d != D - 1) {
                    board[++c][++d] = player;
                } //while
            } //if
        } //if
        c = C;
        d = D;
        if (c - 1 >= 0 && board[c - 1][d] == opponent) {
            c = c - 1;
            while (c > 0 && board[c][d] == opponent) {
                c--;
            } //while
            if (c >= 0 && board[c][d] == player) {
                while (c != C - 1) {
                    board[++c][d] = player;
                } //while
            } //if
        } //if
        c = C;
        if (c - 1 >= 0 && d + 1 < 8 && board[c - 1][d + 1] == opponent) {
            c = c - 1;
            d = d + 1;
            while (c > 0 && d < 7 && board[c][d] == opponent) {
                c--;
                d++;
            } //while
            if (c >= 0 && d < 8 && board[c][d] == player) {
                while (c != C - 1 && d != D + 1) {
                    board[++c][--d] = player;
                } //while
            } //if
        } //if
        c = C;
        d = D;
        if (d - 1 >= 0 && board[c][d - 1] == opponent) {
            d = d - 1;
            while (d > 0 && board[c][d] == opponent) {
                d--;
            } //while
            if (d >= 0 && board[c][d] == player) {
                while (d != D - 1) {
                    board[c][++d] = player;
                } //while
            } //if
        } //if
        movePart2(array, x, y, player, opponent);
    } //move


/**
 *does the players move.
 *@param array
 *@param x
 *@param y
 *@param player
 *@param opponent
 */
    public void movePart2(int[][] array, int x, int y, char player, char opponent) {
        int c = x, d = y;
        board[c][d] = player;
        int C = c, D = d;
        if (d + 1 < 8 && board[c][d + 1] == opponent) {
            d = d + 1;
            while (d < 7 && board[c][d] == opponent) {
                d++;
            } //while
            if (d < 8 && board[c][d] == player) {
                while (d != D + 1) {
                    board[c][--d] = player;
                    //add image to grid pane
                } //if
            } //if
        } //if
        d = D;
        if (c + 1 < 8 && d - 1 >= 0 && board[c + 1][d - 1] == opponent) {
            c = c + 1;
            d = d - 1;
            while (c < 7 && d > 0 && board[c][d] == opponent) {
                c++;
                d--;
            } //while
            if (c < 8 && d >= 0 && board[c][d] == player) {
                while (c != C + 1 && d != D - 1) {
                    board[--c][++d] = player;
                    //add image to gridpane
                } //while
            } //if
        } //if
        c = C;
        d = D;
        if (c + 1 < 8 && board[c + 1][d] == opponent) {
            c = c + 1;
            while (c < 7 && board[c][d] == opponent) {
                c++; } //while
            if (c < 8 && board[c][d] == player) {
                while (c != C + 1) {
                    board[--c][d] = player;
                    //add image to grid pane
                } //while
            } //if
        } //if
        c = C;
        if (c + 1 < 8 && d + 1 < 8 && board[c + 1][d + 1] == opponent) {
            c = c + 1;
            d = d + 1;
            while (c < 7 && d < 7 && board[c][d] == opponent) {
                c++;
                d++; } //while
            if (c < 8 && d < 8 && board[c][d] == player) {
                while (c != C + 1 && d != D + 1) {
                    board[--c][--d] = player;
                    //add image to grid pane
                } //while
            } //if
        } //if
        setPic(player);
    } //MOVEpart2

    /**updates the score.*/
    public void updateScores() {
        pScore = 0;
        bScore = 0;
        remaining = 0;
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (board[i][j] == 'P') {
                    pScore++;
                } else if (board[i][j] == 'B') {
                    bScore++;
                } else {
                    remaining++;
                } //if
            } //for
        } //for
        String p = String.valueOf(pScore);
        String bs = String.valueOf(bScore);
        score.setText("Pink = " + p + ": Blue = " + bs);
    } //updateScores

    /**
     *runs a skip check .
     *@param array
     *@return true or false
     */
    public Boolean check(int[][] array) {
        int check = 0;
        Boolean b = false;
        for (int i = 0; i < 8; i++) {
            for (int k = 0; k < 8; k++) {
                if (array[i][k] == 1 ) {
                    check = 1;
                } //if
            } //for
        } //for
        if (check == 0) {
            b = true;
        }
        return b;
    } //check

    /** refills and disables the rectangles.*/
    public   void fill() {
        for (int i = 0; i < 8; i++) {
            for (int k = 0; k < 8; k++) {
                rect[i][k].setFill(Color.PEACHPUFF);
                rect[i][k].setDisable(true);
            } //for
        } //for
    } //fill

/**
 * This gets the clicable spaces.
 *@param player
 *@param opponent
 *@param array
 *@return array
 */
    public  int[][] getClickableSpaces(char player, char opponent, int[][]array) {
        for (int i = 0; i < 8; i++) {
            for (int k = 0; k < 8; k++) {
                array[i][k] = 0;
            } //for
        } //for
        findClickableSpace(player, opponent, array);
        return array;
    } //getclickablespaces

    Boolean skip = true;
    int act = 0;

    /** this is the loop for the game reversi.*/
    public void gamePlay() {
        board();
        int result;
        act = 0;
        while (con == 1) {
            skip = false;

            bArray = getClickableSpaces('B', 'P', bArray);
            pArray = getClickableSpaces('P', 'B', pArray);

            result = gameResult(pArray, bArray);

            if (result != -2) {
                win();
                break;
            }
            skip = check(bArray);
            if (!skip) {
                fill();
                who.setText("Blue              ");
                while (act == 0) {
                    showClickableSpaces(bArray, 'B', 'P');
                    updateScores();
                    if (con == 0) {
                        break;
                    } //if
                } //while
            } //if
            skip = false;

            pArray = getClickableSpaces('P', 'B', pArray);
            bArray = getClickableSpaces('B', 'P', bArray);
            result = gameResult(pArray, bArray);

            if (result != -2) {
                win();
                break;
            }
            skip = check(pArray);
            if (!skip) {
                fill();
                who.setText("Pink              ");
                while (act == 1) {
                    showClickableSpaces(pArray, 'P', 'B');
                    updateScores();
                    if (con == 0) {
                        break;
                    } //if
                } //while
            } //if
        } //while
    } //gamePlay
} // ArcadeApp
