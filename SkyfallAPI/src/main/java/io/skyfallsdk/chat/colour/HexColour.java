package io.skyfallsdk.chat.colour;

import io.skyfallsdk.protocol.ProtocolVersion;
import io.skyfallsdk.protocol.annotation.ProtocolSupported;

@ProtocolSupported(from = ProtocolVersion.v1_16)
public class HexColour implements Colour {

    private static final String HEX_FORMAT = "#%02x%02x%02x";

    private final int[] rgb;

    private HexColour(int[] rgb) {
        this.rgb = rgb;
    }

    private HexColour(String hexCode) {
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

    public static HexColour of(String hexCode) {
        return new HexColour(hexCode);
    }

    public static HexColour of(int[] rgb) {
        return new HexColour(rgb);
    }

    public static HexColour of(int r, int g, int b) {
        return new HexColour(new int[] { r, g, b });
    }
}
