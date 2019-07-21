package textlevel.Color;

import java.awt.Color;
/**
 *
 * @author Ben Azoulay
 *
 */
public class ColorsParser {
    /**
     * parse color definition and return the specified color.
     * if something wrong then return null
     * @param s color string
     * @return the color
     */
    public static Color colorFromString(String s) {
        if (s == null) {
            return null;
        }

        switch (s) {
            case "black": return Color.black;
            case "blue": return Color.blue;
            case "cyan": return Color.cyan;
            case "gray": return Color.gray;
            case "lightGray": return Color.lightGray;
            case "green": return Color.green;
            case "orange": return Color.orange;
            case "pink": return Color.pink;
            case "red": return Color.red;
            case "white": return Color.white;
            case "yellow": return Color.yellow;
            default:
                break;
        }
        int index = s.indexOf('(');
        int endIndex = s.lastIndexOf(')');
        if (index == -1 || endIndex == -1) {
            //failed
            return null;
        }
        String rgb = s.substring(0, index);
        if (rgb.equals("RGB")) {
            String values = s.substring(index + 1, endIndex);
            String[] split2 = values.split(",");
            try {
                int x = Integer.parseInt(split2[0]);
                int y = Integer.parseInt(split2[1]);
                int z = Integer.parseInt(split2[2]);
                return new Color(x, y, z);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }
}
