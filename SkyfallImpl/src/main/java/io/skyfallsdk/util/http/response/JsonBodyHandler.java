package io.skyfallsdk.util.http.response;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import io.skyfallsdk.Server;
import io.skyfallsdk.util.UtilString;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.http.HttpResponse;
import java.util.function.Supplier;

public class JsonBodyHandler<T> implements HttpResponse.BodyHandler<Supplier<T>> {

    private static final Gson RESPONSE_GSON = new GsonBuilder()
      .registerTypeAdapter(ResponseUuidAtTime.class, new ResponseUuidAtTimeAdapter())
      .setLenient()
      .serializeNulls()
      .setPrettyPrinting()
      .create();

    private final Class<T> clazz;

    public JsonBodyHandler(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public HttpResponse.BodySubscriber<Supplier<T>> apply(HttpResponse.ResponseInfo responseInfo) {
        HttpResponse.BodySubscriber<InputStream> upstream = HttpResponse.BodySubscribers.ofInputStream();

        return HttpResponse.BodySubscribers.mapping(
          upstream,
          stream -> this.toSupplierOfType(stream, this.clazz)
        );
    }

    private Supplier<T> toSupplierOfType(InputStream stream, Class<T> target) {
        return () -> {
            try (InputStream input = stream) {
                return RESPONSE_GSON.fromJson(new JsonReader(new InputStreamReader(input)), target);
            } catch (Exception e) {
                Server.get().getLogger().error(e);
                return null;
            }
        };
    }

    // These are here to allow the response classes to remain package-private and clean
    private static class ResponseUuidAtTimeAdapter implements JsonDeserializer<ResponseUuidAtTime> {

        @Override
        public ResponseUuidAtTime deserialize(JsonElement element, Type type, JsonDeserializationContext ctx) throws JsonParseException {
            JsonObject object = element.getAsJsonObject();

            return new ResponseUuidAtTime(UtilString.uuidFromUndashed(object.get("id").getAsString()), object.get("name").getAsString());
        }
    }
}
