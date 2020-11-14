package io.skyfallsdk.chat.colour;

import javax.annotation.concurrent.Immutable;

@Immutable
public interface Colour {

    /**
     * @return The parsable colour for use in ChatComponents.
     */
    String toParsable();
}
