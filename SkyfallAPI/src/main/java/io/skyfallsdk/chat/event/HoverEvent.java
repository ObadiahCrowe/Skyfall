package io.skyfallsdk.chat.event;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.skyfallsdk.chat.ChatComponent;

public final class HoverEvent {

    private final HoverType type;
    private final ChatComponent value;

    /**
     * Instantiates a new HoverEvent given a specific type and the ChatComponent's value.
     *
     * @param type Type of the event.
     * @param value Associated value for this event.
     */
    public HoverEvent(HoverType type, ChatComponent value) {
        this.type = type;
        this.value = value;
    }

    /**
     * Instantiates a new HoverEvent given a specific type and the string's value which is then turned into a ChatComponent.
     *
     * @param type Type of the event.
     * @param value Associated value for this event.
     */
    public HoverEvent(HoverType type, String value) {
        this.type = type;
        this.value = ChatComponent.from(value);
    }

    /**
     * @return Type of this event.
     */
    public HoverType getType() {
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

        object.addProperty("action", this.type.name().toLowerCase());
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

        return this.toJson().equals(((HoverEvent) object).toJson());
    }

    /**
     * Creates a new HoverEvent from a json string.
     *
     * @param json Json string.
     *
     * @return Instantiated HoverEvent.
     */
    public static HoverEvent fromJson(String json) {
        JsonObject object = (JsonObject) JsonParser.parseString(json);

        return fromJson(object);
    }

    /**
     * Creates a new HoverEvent from a json object.
     *
     * @param json Json object.
     *
     * @return Instantiated HoverEvent.
     */
    public static HoverEvent fromJson(JsonObject json) {
        return new HoverEvent(
          HoverType.valueOf(json.get("action").getAsString().toUpperCase()),
          json.get("value").getAsString()
        );
    }
}
