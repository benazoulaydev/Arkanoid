package textlevel.Level;

import interfaces.Fill;
import interfaces.Sprite;
import geometry.GameObjects.Block;

import geometry.Bases.Velocity;

import java.util.ArrayList;
import java.util.List;

import textlevel.Blocks.BlocksDefinitionReader;
import textlevel.Blocks.BlocksFromSymbolsFactory;
import textlevel.Color.ColorFill;
import textlevel.Color.ColorsParser;
import textlevel.Color.ImageFill;
import textlevel.Format.FormatLevel;
import textlevel.Format.FormatLevelHelper;
import interfaces.LevelInformation;

import backgrounds.FillBackGround;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author Ben Azoulay
 *
 */
public class LevelSpecificationReader {
    private String levelName;
    private List<Velocity> initialBallVelocities;
    private Fill backGround;
    private int paddleSpeed;
    private int paddleWidth;
    private String blockDefinitions;
    private double blocksStartX;
    private double blocksStartY;
    private double rowHeight;
    private int numBlocks;
    private List<Block> blocks;
    private int boardWidth;
    private int boardHeight;

    /**.
     * constructor
     * @param width the board width
     * @param height the board height
     */
    public LevelSpecificationReader(int width, int height) {
        this.boardHeight = height;
        this.boardWidth = width;
        this.resetFields();
    }

    /**.
     * reset the objects fields to null and the primitives to -1
     */
    public void resetFields() {
        this.levelName = null;
        this.initialBallVelocities = new ArrayList<Velocity>();
        this.backGround = null;
        this.paddleSpeed = -1;
        this.paddleWidth = -1;
        this.blockDefinitions = null;
        this.blocksStartX = -1;
        this.blocksStartY = -1;
        this.rowHeight = -1;
        this.numBlocks = -1;
        this.blocks = new ArrayList<Block>();
    }
    /**
     *return true if all the field are legal else false.
     * @return true if all the field are legal else false
     */
    public boolean checkFields() {
        if (this.levelName != null && !(this.initialBallVelocities.isEmpty())
                && this.blockDefinitions != null && this.paddleSpeed > 0
                && this.blocksStartX > 0 && this.backGround != null
                && this.paddleWidth > 0 && this.numBlocks > 0
                && this.rowHeight > 0 && this.blocksStartY > 0
                && !(this.blocks.isEmpty())) {
            return true;
        }
        return false;

    }
    /**
     *
     * @param splitS array of strings from the read line
     * @return true if there was only legal input else false
     */
    private boolean updateSomeFields(String[] splitS) {
        String field = splitS[0];
        switch(field) {
            case "paddle_speed":
                try {
                    this.paddleSpeed = Integer.parseInt(splitS[1]);
                } catch (NumberFormatException e) {
                    return false;
                }
                break;
            case "paddle_width":
                try {
                    this.paddleWidth = Integer.parseInt(splitS[1]);
                } catch (NumberFormatException e) {
                    return false;
                }
                break;
            case "level_name":
                this.levelName = new String(splitS[1]);
                break;
            case "block_definitions":
                this.blockDefinitions = new String(splitS[1]);
                break;
            case "blocks_start_x":
                try {
                    this.blocksStartX = Double.parseDouble(splitS[1]);
                } catch (NumberFormatException e) {
                    return false;
                }
                break;
            case "blocks_start_y":
                try {
                    this.blocksStartY = Double.parseDouble(splitS[1]);
                } catch (NumberFormatException e) {
                    return false;
                }
                break;
            case "row_height":
                try {
                    this.rowHeight = Double.parseDouble(splitS[1]);
                } catch (NumberFormatException e) {
                    return false;
                }
                break;
            case "num_blocks":
                try {
                    this.numBlocks = Integer.parseInt(splitS[1]);
                } catch (NumberFormatException e) {
                    return false;
                }
                break;
            case "ball_velocities":
                String[] split2 = splitS[1].split(" ");
                Velocity v = new Velocity(0, 0);
                for (String s : split2) {
                    String[] split3 = s.split(",");
                    double angle, speed;
                    try {
                        if (split3.length == 2) {
                            angle = Double.parseDouble(split3[0]);
                            speed = Double.parseDouble(split3[1]);
                            v = v.fromAngleAndSpeed(angle, speed);
                            this.initialBallVelocities.add(v);
                        } else {
                            return false;
                        }
                    } catch (Exception e) {
                        return false;
                    }
                }
                break;
            case "background":
                int index = splitS[1].indexOf('(');
                int endIndex = splitS[1].lastIndexOf(')');
                String type = splitS[1].substring(0, index);
                String info = splitS[1].substring(index + 1, endIndex);
                if (type.equals("image")) {
                    this.backGround = new ImageFill(info);
                }
                if (type.equals("color")) {
                    Color c = ColorsParser.colorFromString(info);
                    this.backGround = new ColorFill(this.boardWidth,
                            this.boardHeight, c);
                }
                break;

            default:
                break;
        }

        return true;
    }
    /**
     *
     * @param reader the reader of the file
     * @return list of level information
     */
    public List<LevelInformation> fromReader(BufferedReader reader) {
        List<LevelInformation> levels = new ArrayList<LevelInformation>();
        try {
            String line = reader.readLine();
            while (line != null) {
                String[] splitS = line.split(":");
                String field = splitS[0];
                boolean check = this.updateSomeFields(splitS);
                if (!check) {
                    return null;
                }
                if (field.equals("START_BLOCKS")) {
                    line = reader.readLine();
                    // because of the board left border and the header
                    int x = 1 + (int) this.blocksStartX;
                    int y = 30 + (int) this.blocksStartY;
                    BufferedReader blocksReader = null;
                    BlocksFromSymbolsFactory bFactory = null;
                    try {
                        InputStream is = ClassLoader.getSystemClassLoader()
                                .getResourceAsStream(this.blockDefinitions);
                        blocksReader = new BufferedReader(
                                new InputStreamReader(is));
                        bFactory = new BlocksDefinitionReader()
                                .fromReader(blocksReader);
                        // } catch (FileNotFoundException e) {
                        // System.err.println("Unable to find file");
                    } catch (Exception e) {
                        System.err.println("Failed reading file");
                        e.printStackTrace(System.err);
                    } finally {
                        try {
                            if (blocksReader != null) {
                                blocksReader.close();
                            }
                        } catch (IOException e) {
                            System.err.println("Failed closing file");
                        }
                    }
                    if (bFactory == null) {
                        // in case the blocks definition is not good
                        return null;
                    }
                    while (!line.equals("END_BLOCKS")) {
                        char[] chars = line.toCharArray();
                        for (int k = 0; k < chars.length; k++) {
                            String symbol = String.valueOf(chars[k]);
                            if (bFactory.isBlockSymbol(symbol)) {
                                // block
                                Block b = bFactory.getBlock(symbol, x, y);
                                this.blocks.add(b);
                                x += b.getBlockWidth();
                            }
                            if (bFactory.isSpaceSymbol(symbol)) {
                                // spacer
                                int space = bFactory.getSpaceWidth(symbol);
                                x += space;
                            }
                        }
                        y += this.rowHeight;
                        x = 1 + (int) this.blocksStartX;
                        line = reader.readLine();
                    }
                }
                if (field.equals("END_LEVEL")) {
                    if (!this.checkFields()) {
                        // missing a field
                        return null;
                    }
                    int numberBalls = this.initialBallVelocities.size();
                    Sprite backG = new FillBackGround(this.backGround, 1, 25);
                    FormatLevelHelper fh = new FormatLevelHelper(backG,
                            this.numBlocks, this.blocks,
                            this.boardWidth, this.boardHeight);
                    FormatLevel level = new FormatLevel(numberBalls,
                            this.initialBallVelocities, this.paddleSpeed,
                            this.paddleWidth, this.levelName, fh);
                    levels.add(level);
                    this.resetFields();
                }

                line = reader.readLine();
            }
            return levels;
        } catch (FileNotFoundException e) {
            System.err.println("Unable to find file");
            return null;
        } catch (IOException e) {
            System.err.println("Failed reading file");
            e.printStackTrace(System.err);
            return null;
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file");
            }
        }

    }

}

