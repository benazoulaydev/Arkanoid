package textlevel.Blocks;


import interfaces.BlockCreator;
import interfaces.Fill;
import textlevel.Color.ColorFill;
import textlevel.Color.ColorsParser;
import textlevel.Color.ImageFill;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Ben Azoulay
 *
 */
public class BlocksDefinitionReader {
    // block values
    private String symbol;
    private int height;
    private int width;
    private int hitPoints;
    private Fill fill1;
    private List<Fill> fills;
    private Color stroke;
    // spacer values
    private String symbolSpacer;
    private int widthSpacer;
    // default values
    private int defaultH;
    private int defaultW;
    private int defaultHitPoints;
    private Fill defaultFill;
    private Color defaultStroke;
    /**.
     * Default Block Def
     */
    public BlocksDefinitionReader() {
        this.defaultH = -1;
        this.defaultW = -1;
        this.defaultHitPoints = -1;
        this.defaultFill = null;
        this.defaultStroke = null;
        this.resetFields();
    }
    /**.
     * field reset
     */
    public void resetFields() {
        this.symbol = " ";
        this.symbolSpacer = " ";
        this.height = this.defaultH;
        this.width = this.defaultW;
        this.widthSpacer = this.defaultW;
        this.hitPoints = this.defaultHitPoints;
        this.fill1 = this.defaultFill;
        this.fills = new ArrayList<Fill>();
        this.stroke = this.defaultStroke;
    }
    /**
     *
     * @return checks if block args are presents
     */
    public boolean checkBlockFields() {
        if (this.symbol != " " && this.height != -1 && this.width != -1
                && this.hitPoints > -1 && this.fill1 != null) {
            return true;
        }
        return false;
    }
    /**
     *
     * @param splitS array of strings
     * @return true/false
     */
    private boolean updateDefault(String[] splitS) {
        for (int i = 1; i < splitS.length; i++) {
            String[] split2 = splitS[i].split(":");
            String field = split2[0];
            switch (field) {
                case "height":
                    try {
                        this.defaultH = Integer.parseInt(split2[1]);
                    } catch (Exception e) {
                        return false;
                    }
                    break;
                case "width":
                    try {
                        this.defaultW = Integer.parseInt(split2[1]);
                    } catch (Exception e) {
                        return false;
                    }
                    break;
                case "hit_points":
                    try {
                        this.defaultHitPoints = Integer.parseInt(split2[1]);
                    } catch (Exception e) {
                        return false;
                    }
                    break;
                case "fill":
                    int index = split2[1].indexOf('(');
                    int endIndex = split2[1].lastIndexOf(')');
                    String type = split2[1].substring(0, index);
                    String info = split2[1].substring(index + 1, endIndex);
                    if (type.equals("image")) {
                        this.defaultFill = new ImageFill(info);
                    }
                    if (type.equals("color")) {
                        Color c = ColorsParser.colorFromString(info);
                        this.defaultFill = new ColorFill(c);
                    }
                    this.fills.add(this.fill1);
                    break;
                case "stroke":
                    int index2 = split2[1].indexOf('(');
                    int endIndex2 = split2[1].lastIndexOf(')');
                    String typeBorder = split2[1].substring(0, index2);
                    String infoBorder = split2[1].substring(index2 + 1, endIndex2);
                    if (typeBorder.equals("color")) {
                        Color c2 = ColorsParser.colorFromString(infoBorder);
                        this.defaultStroke = c2;
                    } else {
                        return false;
                    }
                    break;
                default:
                    break;
            }

        }
        return true;
    }
    /**.
     * part1
     * @param splitS the args
     * @return true/false
     */
    private boolean updateBdefPart1(String[] splitS) {
        for (int i = 1; i < splitS.length; i++) {
            String[] split2 = splitS[i].split(":");
            String field = split2[0];

            switch (field) {
                case "symbol":
                    if (split2[1].length() == 1) {
                        this.symbol = split2[1];
                    } else {
                        return false;
                    }
                    break;
                case "height":
                    try {
                        this.height = Integer.parseInt(split2[1]);
                    } catch (Exception e) {
                        return false;
                    }
                    break;
                case "width":
                    try {
                        this.width = Integer.parseInt(split2[1]);
                    } catch (Exception e) {
                        return false;
                    }
                    break;
                case "hit_points":
                    try {
                        this.hitPoints = Integer.parseInt(split2[1]);
                    } catch (Exception e) {
                        return false;
                    }
                    break;
                case "fill":
                    int index = split2[1].indexOf('(');
                    int endIndex = split2[1].lastIndexOf(')');
                    if (index == -1 || endIndex == -1) {
                        // failed
                        return false;
                    }
                    String type = split2[1].substring(0, index);
                    String info = split2[1].substring(index + 1, endIndex);
                    if (type.equals("image")) {
                        this.fill1 = new ImageFill(info);
                    }
                    if (type.equals("color")) {
                        Color c = ColorsParser.colorFromString(info);
                        this.fill1 = new ColorFill(c);
                    }
                    if (!this.fills.isEmpty()) {
                        this.fills.set(0, this.fill1);
                    } else {
                        this.fills.add(this.fill1);
                    }
                    break;
                case "stroke":
                    int index2 = split2[1].indexOf('(');
                    int endIndex2 = split2[1].lastIndexOf(')');
                    String typeBorder = split2[1].substring(0, index2);
                    String infoBorder = split2[1].substring(index2 + 1, endIndex2);
                    if (typeBorder.equals("color")) {
                        Color c2 = ColorsParser.colorFromString(infoBorder);
                        this.stroke = c2;
                    } else {
                        return false;
                    }
                    break;
                default:
                    break;
            }





            int ind = field.indexOf('-');
            String[] splitF = field.split("-");
            if (splitF[0].equals("fill") && ind != -1) {
                try {
                    int fillNum = Integer.parseInt(splitF[1]);
                    if (fillNum < 1) {
                        // illegal fill number
                        return false;
                    }
                    // get the current fill
                    Fill currentFill = new ColorFill();
                    int index = split2[1].indexOf('(');
                    int endIndex = split2[1].lastIndexOf(')');
                    if (index == -1 || endIndex == -1) {
                        // failed
                        return false;
                    }
                    String typeF = split2[1].substring(0, index);
                    String infoF = split2[1].substring(index + 1, endIndex);
                    if (typeF.equals("image")) {
                        currentFill = new ImageFill(infoF);
                    }
                    if (typeF.equals("color")) {
                        Color c = ColorsParser.colorFromString(infoF);
                        currentFill = new ColorFill(c);
                    }
                    if (fillNum == 1) {
                        this.fill1 = currentFill;
                    }
                    // insert the fill to the list
                    int size;
                    int j;
                    for (j = 0; j < fillNum - 1; j++) {
                        size = this.fills.size();
                        if (j >= size) {
                            // need to add empty fill
                            this.fills.add(new ColorFill());
                        }
                    }
                    // add the fill at the current number
                    size = this.fills.size();
                    if (j >= size) {
                        this.fills.add(currentFill);
                    } else {
                        this.fills.set(j, currentFill);
                    }
                } catch (Exception e) {
                    return false;
                }
            }
        }
        return true;
    }
    /**.
     * part2
     * @param splitS the args
     */
    private void updateBdefPart2(String[] splitS) {
        // update the fills list
        int count = 0;
        int i = 0;
        while (i < this.fills.size()) {
            Fill f = this.fills.get(i);
            if (f.isEmpty()) {
                this.fills.set(count, this.fill1);
            }
            if (f.needToUpdateBorders()) {
                ColorFill cf = (ColorFill) f;
                cf.setHeight(this.height);
                cf.setWidth(this.width);
            }
            count++;
            i++;
        }

        if (this.fills.size() < this.hitPoints) {
            // mean that i need to add more default fills
            int gap = this.hitPoints - this.fills.size();
            for (int j = 0; j < gap; j++) {
                this.fills.add(this.fill1);
            }
        }

    }
    /**.
     * from reader
     * @param reader the buffer reader
     * @return blockfromsymbol or null
     */
    public BlocksFromSymbolsFactory fromReader(BufferedReader reader) {
        BlocksFromSymbolsFactory bf = new BlocksFromSymbolsFactory();
        try {
            String line = reader.readLine();
            boolean check = true;
            while (line != null) {
                String[] splitS = line.split(" ");
                if (splitS[0].equals("default")) {
                    check = this.updateDefault(splitS);
                    if (!check) {
                        return null;
                    }
                }
                if (splitS[0].equals("bdef")) {
                    this.resetFields();
                    check = this.updateBdefPart1(splitS);
                    if (!check) {
                        return null;
                    }
                    if (!this.checkBlockFields()) {
                        // missing a field
                        return null;
                    }
                    this.updateBdefPart2(splitS);
                    BlockCreator bcreator = new DefaultBlockCreator(
                            this.height, this.width, this.hitPoints,
                            this.fills, this.stroke);
                    bf.addBlockCreator(this.symbol, bcreator);
                }
                if (splitS[0].equals("sdef")) {
                    this.resetFields();
                    for (int i = 1; i < splitS.length; i++) {
                        String[] split2 = splitS[i].split(":");
                        String field = split2[0];
                        if (field.equals("symbol")) {
                            if (split2[1].length() == 1) {
                                this.symbolSpacer = split2[1];
                            } else {
                                return null;
                            }
                        }
                        if (field.equals("width")) {
                            try {
                                this.widthSpacer = Integer.parseInt(split2[1]);
                            } catch (Exception e) {
                                return null;
                            }
                        }
                    }
                    // end of for loop
                    if (!(this.widthSpacer != -1 && this.symbolSpacer != " ")) {
                        // missing a field
                        return null;
                    }
                    bf.addSpacer(this.symbolSpacer, this.widthSpacer);
                }
                line = reader.readLine();
            }
            return bf;
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
