package io.skyfallsdk.chat;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.skyfallsdk.chat.colour.Colour;
import io.skyfallsdk.chat.event.ClickEvent;
import io.skyfallsdk.chat.event.HoverEvent;

import java.util.List;

public class ChatComponent implements Cloneable {

    private final String text;

    private volatile boolean[] formats = new boolean[6];
    private Colour colour;
    private String translate;

    private String insertion;
    private String selector;

    private ClickEvent clickEvent;
    private HoverEvent hoverEvent;

    private final List<ChatComponent> extra;

    public ChatComponent(String text) {
        this.text = text;
        this.extra = Lists.newArrayList();
    }

    public ChatComponent(ChatComponent component) {
        this.text = component.getText();

        this.formats = component.getFormats();
        this.colour = component.getColour();
        this.translate = component.getTranslate();

        this.insertion = component.getInsertion();
        this.selector = component.getSelector();

        this.clickEvent = component.getClickEvent();
        this.hoverEvent = component.getHoverEvent();

        this.extra = component.getExtra();
    }

    private boolean[] getFormats() {
        return this.formats;
    }

    public String getText() {
        return this.text;
    }

    public boolean isBold() {
        return this.formats[0];
    }

    public boolean isItalic() {
        return this.formats[1];
    }

    public boolean isUnderlined() {
        return this.formats[2];
    }

    public boolean isStruckthrough() {
        return this.formats[3];
    }

    public boolean isObfuscated() {
        return this.formats[4];
    }

    public boolean isReset() {
        return this.formats[5];
    }

    public ChatComponent addFormats(ChatFormat... formats) {
        for (ChatFormat format : formats) {
            this.addFormats(format);
        }

        return this;
    }

    public ChatComponent addFormat(ChatFormat format) {
        this.formats[format.ordinal()] = true;

        return this;
    }

    public Colour getColour() {
        return this.colour;
    }

    public ChatComponent setColour(Colour colour) {
        this.colour = colour;

        return this;
    }

    public String getInsertion() {
        return this.insertion;
    }

    public ChatComponent setInsertion(String insertion) {
        this.insertion = insertion;

        return this;
    }

    public String getSelector() {
        return this.selector;
    }

    public ChatComponent setSelector(String selector) {
        this.selector = selector;

        return this;
    }

    public String getTranslate() {
        return this.translate;
    }

    public ChatComponent setTranslate(String translate) {
        this.translate = translate;

        return this;
    }

    public ClickEvent getClickEvent() {
        return this.clickEvent;
    }

    public ChatComponent setClickEvent(ClickEvent clickEvent) {
        this.clickEvent = clickEvent;

        return this;
    }

    public HoverEvent getHoverEvent() {
        return this.hoverEvent;
    }

    public ChatComponent setHoverEvent(HoverEvent hoverEvent) {
        this.hoverEvent = hoverEvent;

        return this;
    }

    public ChatComponent addExtra(ChatComponent component) {
        this.extra.add(component);

        return this;
    }

    public List<ChatComponent> getExtra() {
        return this.extra;
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();

        String text = this.text;

        // This keeps unicode working
        if (text != null) {
            StringBuilder builder = new StringBuilder();
            for (char c : text.toCharArray()) {
                if (Character.UnicodeBlock.of(c) != Character.UnicodeBlock.BASIC_LATIN) {
                    StringBuilder hex = new StringBuilder(Integer.toHexString(c));
                    while (hex.length() < 4) {
                        hex.insert(0, "0");
                    }

                    hex.insert(0, "\\u");
                    builder.append(hex);
                    continue;
                }
                builder.append(c);
            }

            json.addProperty("text", builder.toString());
        }

        if (this.isBold()) {
            json.addProperty("bold", this.isBold());
        }

        if (this.isItalic()) {
            json.addProperty("italic", this.isItalic());
        }

        if (this.isUnderlined()) {
            json.addProperty("underlined", this.isUnderlined());
        }

        if (this.isStruckthrough()) {
            json.addProperty("strikethrough", this.isStruckthrough());
        }

        if (this.isObfuscated()) {
            json.addProperty("obfuscated", this.isObfuscated());
        }

        if (this.isReset()) {
            json.addProperty("reset", this.isReset());
        }

        Colour colour = this.getColour();
        if (colour != null) {
            json.addProperty("color", colour.toParsable().toLowerCase());
        }

        String translate  = this.getTranslate();
        if (translate != null) {
            json.addProperty("translate", translate);
        }

        String insertion = this.getInsertion();
        if (insertion != null) {
            json.addProperty("insertion", insertion);
        }

        String selector = this.getSelector();
        if (selector != null) {
            json.addProperty("selector", selector);
        }

        if (this.extra.size() > 0) {
            JsonArray array = new JsonArray();

            this.extra.forEach(c -> array.add(c.toJson()));
            json.add("extra", array);
        }

        HoverEvent hoverEvent = this.getHoverEvent();
        if (hoverEvent != null) {
            json.add("hoverEvent", hoverEvent.toJson());
        }

        ClickEvent clickEvent = this.getClickEvent();
        if (clickEvent != null) {
            json.add("clickEvent", clickEvent.toJson());
        }

        return json;
    }

    @Override
    public String toString() {
        return this.toJson().toString().replace("\\\\", "\\");
    }

    @Override
    protected ChatComponent clone() throws CloneNotSupportedException {
        return (ChatComponent) super.clone();
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || object == this || object.getClass() != this.getClass()) {
            return object == this;
        }

        return this.toJson().equals(((ChatComponent) object).toJson());
    }

    public static ChatComponent from(String text) {
        return new ChatComponent(text);
    }

    public static ChatComponent fromJson(String json) {
        return null;

        //return new ChatComponent(GSON.fromJson(json, ChatComponent.class));
    }

    public static ChatComponent empty() {
        return new ChatComponent("");
    }
}
