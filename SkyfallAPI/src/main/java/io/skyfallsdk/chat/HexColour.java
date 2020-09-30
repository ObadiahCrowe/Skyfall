package io.skyfallsdk.chat;

public class HexColour implements Colour {

    private static final String HEX_FORMAT = "#%02x%02x%02x";

    private final int[] rgb;

    public HexColour(int r, int g, int b) {
        this.rgb = new int[] {
          r, g, b
        };
    }

    public HexColour(String hexCode) {
        int rawHex = Integer.parseInt(hexCode.replace("#", ""));

        this.rgb = new int[] {
          (rawHex & 0xFF0000) >> 16,
          (rawHex & 0xFF00) >> 8,
          (rawHex & 0xFF)
        };
    }

    public int getRed() {
        return this.rgb[0];
    }

    public void setRed(int red) {
        this.rgb[0] = red;
    }

    public int getGreen() {
        return this.rgb[1];
    }

    public void setGreen(int green) {
        this.rgb[1] = green;
    }

    public int getBlue() {
        return this.rgb[2];
    }

    public void setBlue(int blue) {
        this.rgb[2] = blue;
    }

    public int[] getRGB() {
        return this.rgb;
    }

    @Override
    public String toParsable() {
        return String.format(HEX_FORMAT, this.rgb[0], this.rgb[1], this.rgb[2]);
    }
}
