package game;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import graphics.Handlers.Counter;
import graphics.Handlers.ScoreTrackingListener;
import graphics.Handlers.HighScoresTable;
import graphics.Handlers.KeyPressStoppableAnimation;

import graphics.Indicators.LevelIndicator;
import java.util.Random;

import interfaces.Animation;
import interfaces.LevelInformation;
import interfaces.Collidable;
import interfaces.Sprite;


import geometry.Bases.Rectangle;
import geometry.Bases.Point;
import geometry.Bases.Velocity;
import geometry.GameObjectsRemover.BallRemover;
import geometry.GameObjectsRemover.BlockRemover;
import geometry.GameObjects.Ball;
import geometry.GameObjects.Block;
import geometry.GameObjects.Paddle;

import graphics.Collections.GameEnvironment;
import graphics.Collections.SpriteCollection;
import graphics.Indicators.LivesIndicator;
import graphics.Indicators.ScoreIndicator;
import graphics.screenWindow.PauseScreen;
import graphics.screenWindow.CountdownAnimation;

import interfaces.Fill;
import textlevel.Color.ColorFill;

/**
 * @author Ben Azoulay
 */
 public class GameLevel implements Animation  {


    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;
    private Sleeper sleeper;
    private int size;
    private Counter counterBlock = new Counter(0);
    private Counter counterBall = new Counter(0);
    private Counter scoreCounter;
    private Counter livesCounter;

    private BlockRemover bjl = new BlockRemover(this, counterBlock);
    private BallRemover ballRmv = new BallRemover(this, counterBall);
    private ScoreTrackingListener scoreTr;

    private int width = 800;
    private int height = 600;
    private boolean paddleExist = false;

    private AnimationRunner runner;
    private boolean running;

    private biuoop.KeyboardSensor keyboard;
    private List<Velocity> veloList = new ArrayList<Velocity>();
    private Paddle paddle;

    private LevelInformation infoOfLevel;
    private HighScoresTable theHighScoreTable;


    private Random rand = new Random();


    /**
     * Constructor.
     * @param levelInfo the level info
     * @param ar the animation runner
     * @param score the score counter
     * @param lives the lives counter
     * @param hs the High Scores Table
     */
    public GameLevel(LevelInformation levelInfo, AnimationRunner ar, Counter score, Counter lives, HighScoresTable hs) {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.sleeper = new Sleeper();
        this.infoOfLevel = levelInfo;
        this.runner = ar;
        this.gui = this.runner.getGui();
        this.livesCounter = lives;
        this.scoreCounter = score;
        this.scoreTr = new ScoreTrackingListener(scoreCounter);
        this.theHighScoreTable = hs;

    }

    /**
     * add collidiable object.
     *
     * @param c collidable object
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }


    /**
     * add sprite object.
     *
     * @param s sprite object
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     *.
     * @return the Counter of lives
     */
    public Counter getLives() {
        return this.livesCounter;
    }

    /**
     *.
     * @return the number of Blocks
     */
    public int getBlocksNb() {
        return this.counterBlock.getValue();
    }
    /**
     *.
     * @param blockNb the number of Blocks
     */
    public void setBlocksNb(int blockNb) {
        this.counterBall.setValue(blockNb);
    }

    /**
     * get width.
     *
     * @return width
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * get height.
     *
     * @return height
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * get counterBall.
     *
     * @return ball remianing
     */
    public Counter getBallLives() {
        return this.counterBall;
    }

    /**
     * get ball remove listener.
     *
     * @return the ball remove listener
     */
    public BallRemover getBallRemoveListener() {
        return this.ballRmv;
    }

    /**
     * get environment var.
     *
     * @return environment var
     */
    public GameEnvironment getEnvironment() {
        return this.environment;
    }

    /**
     * get score listener.
     *
     * @return the score listener
     */
    public ScoreTrackingListener getScoreListener() {
        return this.scoreTr;
    }

    /**
     * get sprite collection.
     *
     * @return sprite collection
     */
    public SpriteCollection getSpriteColl() {
        return this.sprites;
    }

    /**
     * get block remove listener.
     *
     * @return the block remove listener
     */
    public BlockRemover getBlockRemoveListener() {
        return this.bjl;
    }

    /**
     * Initialize a new game: create the Blocks and Ball (and Paddle)
     * and add them to the game.
     */
    public void initialize() {



        // create blocks for the game
        this.createBlocks();

        // add the 4 borders + score
        this.addFourBorders();

        // create Score view for the game
        this.createScore();


        // special boxes for new lives and for killing lives
        //this.specialBlocks();

        // if paddle exist dont display it
        if (!paddleExist) {
            this.newPaddle();
            this.paddleExist = true;
        }



    }



    /**
     * playOneTurn of the game -- start the animation loop.
     */
    public void playOneTurn() {

//        // create balls for the game
//        this.createBalls();
//
//        this.running = true;
//        // use our runner to run the current animation -- which is one turn of
//        // the game.
//        this.runner.run(this);




        // paddle


        // balls
        for (int i = 0; i < this.infoOfLevel.numberOfBalls(); i++) {

            Color randomColor = this.randColor();
            //if black can cause problem with background so change it
            if (randomColor == Color.black) {
                randomColor = Color.BLUE;
            }
            int x1 = this.width / 2;
            int y1 = this.height - (int) this.height / 25 - 10;
            Ball ball1 = new Ball(x1, y1, 6, randomColor);
            Velocity v = this.infoOfLevel.initialBallVelocities().get(i);
            // update the ball velocity
            ball1.setWidthHeight(this.width, this.height);
            ball1.setEnvironment(environment);
            ball1.setVelocity(v);
            ball1.addToGame(this);
            this.getBallLives().increase(1);
            ball1.addHitListener(ballRmv);
            this.veloList.add(v);
        }

        this.runner.run(new CountdownAnimation(2, 3, this.sprites));
        this.running = true;
        this.runner.run(this);
    }


    /**
     * shouldStop.
     *
     * @return boolean
     */
    public Color randColor() {
        int r = rand.nextInt(255);
        int g = rand.nextInt(255);
        int b = rand.nextInt(255);
        return new Color(r, g, b);
    }


    /**
     * shouldStop.
     *
     * @return boolean
     */
    public boolean shouldStop() {
        // if there are no other blocks or balls

        if (ballRmv.getRemainingBalls()  > 0 && bjl.getRemainingBlocks() > 0) {
            return false;
        }
        return true;
    }


    /**
     * the logic from the previous playOneTurn method goes here.
     * the `return` or `break` statements should be replaced with
     * this.running = false;
     * @param d the drawsurface
     * @param dt the dt
     */
    public void doOneFrame(DrawSurface d, double dt) {

        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed(dt);

        // the logic from the previous playOneTurn method goes here.
        // the `return` or `break` statements should be replaced with
        // this.running = false;


        this.keyboard = this.runner.getGui().getKeyboardSensor();

        if (this.keyboard.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(this.keyboard,
                    "space", new PauseScreen(this.keyboard)));
        }
//        if (this.keyboard.isPressed("h")) {
//
//
//            this.runner.run(new KeyPressStoppableAnimation(this.keyboard,
//                    "space", new HighScoresAnimation(this.theHighScoreTable, this.keyboard)));
//        }




    }

    /**
     * remove Collidable from the game.
     *
     * @param c the collidable to remove
     */
    public void removeCollidable(Collidable c) {

        environment.removeCollidable(c);
    }

    /**
     * remove Sprite from the game.
     *
     * @param s the sprite to remove
     */
    public void removeSprite(Sprite s) {
        sprites.removeSprite(s);

    }

    /**
     * add the four borders to the game.
     */
    public void addFourBorders() {
        int borderWidth = 1;
        Color borders = Color.gray;


        //add the 4 borders
        //left
        List<Fill> leftBorderFills = new ArrayList<Fill>();
        leftBorderFills.add(new ColorFill(20, this.height - 15, borders));
        Point p = new Point(0, 35);
        Block border = new Block(p, this.height - 15, 20,
                1, leftBorderFills,  Color.gray);

        border.addToGame(this);
        //up
        List<Fill> upperBorderFills = new ArrayList<Fill>();
        upperBorderFills.add(new ColorFill(width, 20, borders));
        p = new Point(0, 15);
        border = new Block(p, 20, width, 1, upperBorderFills,  Color.gray);
        border.addToGame(this);
        //right
        List<Fill> rightBorderFills = new ArrayList<Fill>();
        rightBorderFills.add(new ColorFill(20, this.height - 20 - 15, borders));
        p = new Point(this.width - 20, 35);
        border = new Block(p, this.height - 20 - 15, 20, 1, rightBorderFills, Color.gray);
        border.addToGame(this);
        //bottom
        List<Fill> bottomBorderFills = new ArrayList<Fill>();
        bottomBorderFills.add(new ColorFill(this.width - 40, 1, borders));
        p = new Point(20, this.height);
        border = new Block(p, 1, this.width - 40, 1, bottomBorderFills, Color.gray);
        border.addToGame(this);
        //border.addHitListener(ballRmv);

    }

    /**
     * create ball for the games.
     * add the velocity of the balls to a list
     */
    public void createBalls() {
        Color[] colors = {Color.red, Color.ORANGE, Color.YELLOW, Color.lightGray, Color.blue, Color.PINK,
                Color.CYAN, Color.magenta, Color.GRAY, Color.black};
        int j = 0;
        int middle = this.width / 2;

        int tmpEven = 0;

        if (this.infoOfLevel.numberOfBalls() % 2 != 0) {
            Ball ballLeft = new Ball(middle , this.height - 50, 4, colors[j]);
            ballLeft.setWidthHeight(this.width, this.height);
            Velocity v1 = this.infoOfLevel.initialBallVelocities().get(0);
            ballLeft.setVelocity(v1);
            ballLeft.setEnvironment(environment);
            ballLeft.addToGame(this);
            this.veloList.add(v1);
            ballLeft.addHitListener(ballRmv);
            tmpEven = 1;
        }


        for (int i = 0 + tmpEven; i < this.infoOfLevel.numberOfBalls(); i += 1) {


            //make sure color does not got more than 10
            if (j >= 10) {
                j = 0;
            }
            Ball ballLeft = new Ball(middle , this.height - 50, 4, colors[j +  tmpEven]);
            ballLeft.setWidthHeight(this.width, this.height);
            Velocity v1 = this.infoOfLevel.initialBallVelocities().get(i);
            ballLeft.setVelocity(v1);
            ballLeft.setEnvironment(environment);
            ballLeft.addToGame(this);
            this.veloList.add(v1);
            ballLeft.addHitListener(ballRmv);


            j++;
        }


        this.counterBall.increase(this.infoOfLevel.numberOfBalls());


        /*
        Ball ball2 = new Ball(250, 300, 3, Color.blue);
        ball2.setWidthHeight(this.width, this.height);
        Velocity v2 = Velocity.fromAngleAndSpeed(260, 4);
        ball2.setVelocity(v2);
        ball2.setEnvironment(environment);
        ball2.addToGame(this);

        this.veloList.add(v2);

        ball2.addHitListener(ballRmv);


        Ball ball3 = new Ball(250, 300, 3, Color.black);
        ball3.setWidthHeight(this.width, this.height);
        Velocity v3 = Velocity.fromAngleAndSpeed(98, 4);
        ball3.setVelocity(v3);
        ball3.setEnvironment(environment);
        ball3.addToGame(this);
        */
        //ball3.addHitListener(ballRmv);


    }

    /**
     * create blocks for the games.
     */
    public void createBlocks() {
        this.sprites.addSprite(this.infoOfLevel.getBackground());
//        //display the first level background
//        if (this.infoOfLevel.levelName().compareTo("Direct Hit") == 0) {
//            this.sprites.addSprite(new DirectHitBackGround(this.infoOfLevel.blocks().get(0).getCollisionRectangle()));
//        } else if (this.infoOfLevel.levelName().compareTo("Wide Easy") == 0) {
//            this.sprites.addSprite(new WideEasyBackGround(this.infoOfLevel.blocks().get(0).getCollisionRectangle()
//                    .getUpperLeft().getY()));
//
//        } else if (this.infoOfLevel.levelName().compareTo("Green 3") == 0) {
//            this.sprites.addSprite(new Green3Background());
//        } else if (this.infoOfLevel.levelName().compareTo("Final Four") == 0) {
//            this.sprites.addSprite(new FinalFourBackground());
//        }

        for (Block obj : this.infoOfLevel.blocks()) {
            obj.addToGame(this);
            obj.addHitListener(bjl);
            counterBlock.increase(1);
            obj.hitRestablish();
        }
       /*
        Color[] colors = {new Color(0, 255, 185), new Color(0, 229, 166),
                new Color(0, 206, 149), new Color(0, 185, 134),
                new Color(0, 166, 120), new Color(0, 149, 108),
                new Color(0, 134, 97), new Color(0, 120, 87)};


        Point startPoint = new Point(this.width - 80, this.height / 2 + this.height / 4);
        for (int i = 0; i < 10; i++) {
            for (int j = 7; j > 7 - i; j--) {

                if (i != 0 && i != 1 && i != 2 && i != 3) {
                    if (i == 9) {
                        Block block = new Block(startPoint, 30, 60, colors[i - 4],
                                2);
                        block.addToGame(this);
                        block.addHitListener(bjl);
                        counterBlock.increase(1);

                    } else {
                        Block block = new Block(startPoint, 30, 60, colors[i - 4],
                                1);
                        block.addToGame(this);
                        block.addHitListener(bjl);
                        counterBlock.increase(1);

                    }
                    startPoint = new Point(startPoint.getX() - 60, startPoint.getY());
                }


            }
            startPoint = new Point(this.width - 80, startPoint.getY() - 30);
        }

        */
    }

    /**
     * create score display for the games.
     */
    public void createScore() {

        Rectangle scoreRe = new Rectangle(new Point(0, 0), 15, this.width, Color.WHITE);
        ScoreIndicator scoreBlock = new ScoreIndicator(scoreRe, this.width, 15, this.scoreCounter);
        LivesIndicator livesBlock = new LivesIndicator(scoreRe, this.width, 15, this.livesCounter);
        LevelIndicator lvl = new LevelIndicator(scoreRe, this.width, 15,
                this.infoOfLevel.levelName());
        scoreBlock.addToGame(this);
        livesBlock.addToGame(this);
        lvl.addToGame(this);

    }

//    /**
//     * create special blocks for the game for new live and kill live.
//     */
//    public void specialBlocks() {
//
//        Point killingPoint = new Point(this.width / 100 + 40, this.height / 2 + this.height / 4);
//
//        //killing block
//        Block killingBlock = new Block(killingPoint, 30, 60, new Color(200, 0, 0),
//                -1);
//        killingBlock.addToGame(this);
//
//
//        Point livingPoint = new Point(this.width - 100, this.height / 2 + this.height / 4);
//        //living block
//        Block livingBlock = new Block(livingPoint, 30, 60, new Color(3, 143, 249),
//                -2);
//        livingBlock.addToGame(this);
//
//
//    }

    /**
     * create the paddle.
     */
    public void newPaddle() {

        double x = (this.width / 2) - (this.infoOfLevel.paddleWidth() / 2);
        double y = this.height - (int) this.height / 25;
        Point paddlePoint = new Point(x, y);
        Paddle paddleT = new Paddle(paddlePoint, this.infoOfLevel.paddleWidth(),
                (int) this.height / 20 - 10, this.infoOfLevel.paddleSpeed(),
                this.runner.getGui(), this, Color.yellow);
        this.paddle = paddleT;
        this.paddle.addToGame(this);
        this.paddle.setPaddleSpeed(this.infoOfLevel.paddleSpeed());
    }


}
