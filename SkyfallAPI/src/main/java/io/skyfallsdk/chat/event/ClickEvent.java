package io.skyfallsdk.chat.event;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.skyfallsdk.chat.ChatComponent;

import javax.annotation.concurrent.Immutable;

@Immutable
public final class ClickEvent {

    private final ClickType type;
    private final ChatComponent value;

    /**
     * Instantiates a new ClickEvent given a specific type and the ChatComponent's value.
     *
     * @param type Type of the event.
     * @param value Associated value for this event.
     */
    public ClickEvent(ClickType type, ChatComponent value) {
        this.type = type;
        this.value = value;
    }

    /**
     * Instantiates a new ClickEvent given a specific type and the string's value which is then turned into a ChatComponent.
     *
     * @param type Type of the event.
     * @param value Associated value for this event.
     */
    public ClickEvent(ClickType type, String value) {
        this.type = type;
        this.value = ChatComponent.from(value);
    }

    /**
     * @return Type of this event.
     */
    public ClickType getType() {
        return this.type;
    }

    /**
     * @return Associated value of this event.
     */
    public ChatComponent getValue() {
        return this.value;
    }

    /**
     * @return The event as a json object.
     */
    public JsonObject toJson() {
        JsonObject object = new JsonObject();

        object.addProperty("action", this.type.name());
        object.addProperty("value", this.value.toJson().toString());

        return object;
    }

    @Override
    public String toString() {
        return this.toJson().toString().replace("\\\\", "\\");
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || object == this || object.getClass() != this.getClass()) {
            return object == this;
        }

        return this.toJson().equals(((ClickEvent) object).toJson());
    }

    /**
     * Creates a new ClickEvent from a json string.
     *
     * @param json Json string.
     *
     * @return Instantiated ClickEvent.
     */
    public static ClickEvent fromJson(String json) {
        JsonObject object = (JsonObject) JsonParser.parseString(json);

        return fromJson(object);
    }

    /**
     * Creates a new ClickEvent from a json object.
     *
     * @param json Json object.
     *
     * @return Instantiated ClickEvent.
     */
    public static ClickEvent fromJson(JsonObject json) {
        return new ClickEvent(
          ClickType.valueOf(json.get("action").getAsString().toUpperCase()),
          json.get("value").getAsString()
        );
    }
}
