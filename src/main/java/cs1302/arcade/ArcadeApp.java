package cs1302.arcade;

import java.util.Random;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;


/**
 * Application subclass for {@code ArcadeApp}.
 * @version 2019.fa
 */
public class ArcadeApp extends Application {

    Group group = new Group();           // main container
    Random rng = new Random();           // random number generator
    Rectangle r = new Rectangle(20, 20); // some rectangle

    GridPane gridpane;
    Image background = new image(resources:);
    ImageView bg = new imageView(background);
    Image blueCircle = new Image(resources:);
    ImageView blue = new ImageView(blueCircle);
    Image pinkCircle = new image(resources:);

    /**
     * Return a mouse event handler that moves to the rectangle to a random
     * position any time a mouse event is generated by the associated node.
     * @return the mouse event handler
     */
    private EventHandler<? super MouseEvent> createMouseHandler() {
        return event -> {
            System.out.println(event);
            r.setX(rng.nextDouble() * (640 - r.getWidth()));
            r.setY(rng.nextDouble() * (480 - r.getHeight()));
        };
    } // createMouseHandler

    /**
     * Return a key event handler that moves to the rectangle to the left
     * or the right depending on what key event is generated by the associated
     * node.
     * @return the key event handler
     */
    private EventHandler<? super KeyEvent> createKeyHandler() {
        return event -> {
            System.out.println(event);
            switch (event.getCode()) {
            case LEFT:  // KeyCode.LEFT
                r.setX(r.getX() - 10.0);
                break;
            case RIGHT: // KeyCode.RIGHT
                r.setX(r.getX() + 10.0);
                break;
            default:
                // do nothing
            } // switch
            // TODO bounds checking
        };
    } // createKeyHandler

    /** {@inheritDoc} */
    @Override
    public void start(Stage stage) {

        /* You are allowed to rewrite this start method, add other methods,
         * files, classes, etc., as needed. This currently contains some
         * simple sample code for mouse and keyboard interactions with a node
         * (rectangle) in a group.
         */

        r.setX(50);                                // 50px in the x direction (right)
        r.setY(50);                                // 50ps in the y direction (down)
        group.getChildren().add(r);                // add to main container
        r.setOnMouseClicked(createMouseHandler()); // clicks on the rectangle move it randomly
        group.setOnKeyPressed(createKeyHandler()); // left-right key presses move the rectangle


        Scene scene = new Scene(group, 640, 480);
        stage.setTitle("cs1302-arcade!");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();

        // the group must request input focus to receive key events
        // @see https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Node.html#requestFocus--
        group.requestFocus();


    } // start

    /**
     *returns the scene that is the start of the game.
     *@return vbox homescene
     */
    public VBox homeScene() {
        Button playGame = new Button("PlayGame");
        VBox home = new VBox(playGame);
        return home;
    } //homeScene

    /**
     *returns the vbox that sets the scene to set up the players.
     *@return vbox
     */
    public VBox setPlayers() {
        Label bluePlayer = new Label("Blue Player:");
        TextField blueName = new TextField();
        Label pinkPlayer = new Label("pink Player:");
        TextField pinkName = new TextField();
        Button ready = new Button("Ready");
        VBox playerSet = new VBox(bluePlayer, blueName, pinkPlayer, pinkName, ready);
        return playerSet;
    } //setPlayers

    /**
     *returns the game scene.
     *@return vbox
     */
    public VBox gamePage() {
Label bluePlayer = new Label("Blue Player:" + bName);
Label pinkPlayer = new Label("pink Player:" + pName);
Label turn = new Label("Who's turn is it? " + whoTurn);
        gridPane = gridpane();
        VBox gamep = new VBox(bluePlayer, pinkPlayer, turn, gridPane);
        return gamep;
    } //gamePage

    /**
     *sets the first gridpane.
     */
    public void gridpane() {

        gridPane.addRow(0, bg, bg, bg, bg, bg, bg, bg, bg);
        gridPane.addRow(1, bg, bg, bg, bg, bg, bg, bg, bg);
        gridPane.addRow(2, bg, bg, bg, bg, bg, bg, bg, bg);
        gridPane.addRow(3, bg, bg, bg, blue, pink, bg, bg, bg);
        gridPane.addRow(4, bg, bg, bg, pink, blue, bg, bg, bg);
        gridPane.addRow(5, bg, bg, bg, bg, bg, bg, bg, bg);
        gridPane.addRow(6, bg, bg, bg, bg, bg, bg, bg, bg);
        gridPane.addRow(7, bg, bg, bg, bg, bg, bg, bg, bg);
    } //gridpane

    /**
     *shows the clickable areas on the grid.
     */
    public void clickableSpace() {

    } //clickableSpace

    /**
     *figures out whos turn it is and does the following actions.
     */
    public void spotPicked() {
    } //spotPicked

    /**
     *edits the gridpane following the turn.
     */
    public void showMove(String color, int c, int r) {
        if (color.equals("Blue")) {
            gridpane.add(blue, c, r);
        } else if (color.equals("Pink")) {
            gridpane.add(pink, c, r);
        } //if
    } //showMove
    public char[][] board;
    int PScore, BScore, remaining;
	public void Board(){
	    board = new char[][]{
            {'_','_','_','_','_','_','_','_',},
            {'_','_','_','_','_','_','_','_',},
            {'_','_','_','_','_','_','_','_',},
            {'_','_','_','W','B','_','_','_',},
            {'_','_','_','B','W','_','_','_',},
            {'_','_','_','_','_','_','_','_',},
            {'_','_','_','_','_','_','_','_',},
            {'_','_','_','_','_','_','_','_',},
        };
    } //board
	int[][] pinkButtons = new int[8][8];
    int[][] blueButtons = new int[8][8];

    public void showPlaceableLocations(int[][] buttons, char player, char opponent){
        for(int i = 0; i < 8; i++) {
        	for (int j = 0; j < 8; j++) {
        		if (buttons[i][j] == 1 ) {
        			//add a gridpane button
        		 	buttons[i][j] = 0;
        		} //if
        	} //for
        } //for
    } //displayPlaces

	public int[][] clickableSpace(int[][] buttons, char player, char opponent) {
		for (int a = 0; a < 8; a++) {
            for (int b = 0; b < 8; b++) {
            	if (board[a][b] == opponent) {
            		int A = a;
            		int B = b;
            		if (a - 1 >= 0 && b - 1 >= 0 && board[a - 1][b - 1] == '_') {
                        a = a + 1;
                        b = b + 1;
                        while (a < 7 && b < 7 && board[a][b] == opponent) {a++;b++;} //while
                        if (a < 8 && a < 8 && board[a][b] == player) {
                        	buttons[A - 1 ][B - 1] = 1;
                        } //if
                    } //if
                    a = A;
                    b = B;
                    if (a - 1 >= 0 && board[a - 1][b] == '_') {
                        a = a + 1;
                        while (a < 7 && board[a][b] == opponent) { a++;} //while
                        if (a < 8 && board[a][b] == player) {
                        	buttons[A - 1][B] = 1;
                        } //if
                    } //if
                    a = A;
                    if (a - 1 >= 0 && b + 1 < 8 && board[a - 1][b + 1] == '_') {
                        a = a + 1;
                        b = b - 1;
                        while (a < 7 && b >0 && board[a][b] == opponent) {a++;b--;} //while
                        if (a < 8 && b >= 0 && board[a][b] == player) {
                        	buttons[A - 1][B + 1] = 1;
                        } //if
                    }  //if
                    a = A;
                    b = B;
                    if (b - 1 >= 0 && board[a][b - 1] == '_') {
                        b = b + 1;
                        while (b < 7 && board[a][b] == opponent) {b++;} //while
                        if (b < 8 && board[a][b] == player) {
                        	buttons[A][B-1] = 1;
                        } //if
                    } //if
                    b = B;
                    if (b + 1 < 8 && board[a][b + 1] == '_') {
                        b = b - 1;
                        while (b > 0 && board[a][b] == opponent) {b--;} //while
                        if (b >= 0 && board[a][b] == player) {
                        	buttons[A][B+1] = 1;
                        } //if
                    } //if
                    b = B;
                    if (a + 1 < 8 && b - 1 >= 0 && board[a + 1][b - 1] == '_') {
                        a = a - 1;
                        b = b + 1;
                        while (a > 0 && b < 7 && board[a][b] == opponent) {a--;b++;} //while
                        if (a >= 0 && b < 8 && board[a][b] == player) {
                        	buttons[A+1][B-1] = 1;
                        } //if
                    } //if
                    a = A;
                    b = B;
                    if (a + 1 < 8 && board[a + 1][b] == '_') {
                        a = a - 1;
                        while (a > 0 && board[a][b] == opponent) {a--;} //while
                        if (a >= 0 && board[a][b] == player) {
                        	buttons[A + 1][B] = 1;
                        } //if
                    } //if
                    a = A;
                    if (a + 1 < 8 && b + 1 < 8 && board[a + 1][b + 1] == '_'){
                        a = a - 1;
                        b = b - 1;
                        while (a > 0 && b > 0 && board[a][b] == opponent) {a--;b--;} //while
                        if(a >= 0 && b >= 0 && board[a][b] == player) {
                        	buttons[A+1][B+1] = 1;
                        } //if
                    } //if
                    a = A;
                    b = B;
                } //if
            } //for
        } //for
        return buttons;
	} //clickableSpace

	//point is where they click
	public void placeMove(int x, int y, char player, char opponent) {
        int c = x, d = y;
        //put the picture in the grid
        board[c][d] = player;
        int C = c, D = d;

        if (c - 1 >= 0 && d - 1 >= 0 && board[c - 1][d - 1] == opponent) {
            c = c - 1; d = d - 1;
            while (c > 0 && d > 0 && board[c][d] == opponent) {c--; d--;} //while
            if (c >= 0 && d >= 0 && board[c][d] == player) {
            	while(c != C - 1 && d != D - 1) {
            		board[++c][++d] = player;
            	    //add image to grid
            	} //while
            } //if
        } //if
        c = C;
        d = D;
        if (c - 1 >= 0 && board[c-1][d] == opponent) {
            c = c - 1;
            while (c > 0 && board[c][d] == opponent) { c--;} //while
            if (c >= 0 && board[c][d] == player) {
            	while (c != C - 1) {
            		board[++c][d] = player;
                    //add image to grid
            	} //while
           	} //if
        } //if
        c = C;
        if (c - 1 >= 0 && d + 1 < 8 && board[c - 1][d + 1] == opponent) {
            c = c - 1;
            d = d + 1;
            while (c > 0 && d < 7 && board[c][d] == opponent) {c--; d++;} //while
            if (c >= 0 && d < 8 && board[c][d] == player) {
            	while (c != C - 1 && d != D + 1) {
            		board[++c][--d] = player;
                    //add image to grid
            	} //while
            } //if
        } //if
        c = C;
        d = D;
        if (d - 1 >= 0 && board[c][d - 1] == opponent) {
            d = d - 1;
            while (d > 0 && board[c][d] == opponent) {d--;} //while
            if (d >= 0 && board[c][d] == player) {
            	while (d != D-1) {
            		board[c][++d] = player;
                    //add image to grid pane
            	} //while
            } //if
        } //if
        d = D;
        if (d + 1 < 8 && board[c][d+1] == opponent) {
            d = d + 1;
            while (d < 7 && board[c][d] == opponent) {d++;} //while
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
            while (c < 7 && d > 0 && board[c][d] == opponent) {c++;d--;} //while
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
            while (c < 7 && board[c][d] == opponent) {c++;} //while
            if (c < 8 && board[c][d] == player) {
            	while(c != C + 1) {
            		board[--c][d] = player;
                    //add image to grid pane
            	} //while
            } //if
        } //if
        c = C;

        if (c + 1 < 8 && d + 1 < 8 && board[c + 1][d + 1] == opponent) {
            c = c + 1;
            d = d + 1;
            while (c < 7 && d < 7 && board[c][d] == opponent) {c++;d++;} //while
            if (c < 8 && d < 8 && board[c][d] == player) {
            	while (c != C + 1 && d != D + 1) {
            		board[--c][--d] = player;
            		//add image to grid pane
            	} //while
            } //if
        } //if
    } //move

	public void updateScores(){
        PScore = 0;
        BScore = 0;
        remaining = 0;
        for (int i = 0; i < 8; ++i) {
            for (int j=0; j < 8; ++j) {
                if(board[i][j]=='P') {
                	PScore++;
                } else if(board[i][j]=='B') {
                	BScore++;
                } else {
                	remaining++;
                } //if
            } //for
        } //for
    } //updateScores
} // ArcadeApp
