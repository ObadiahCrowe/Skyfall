package io.skyfallsdk.util.http.response;

import com.google.common.collect.Lists;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import io.skyfallsdk.Server;
import io.skyfallsdk.player.PlayerProperties;
import io.skyfallsdk.util.UtilString;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class JsonBodyHandler<T> implements HttpResponse.BodyHandler<Supplier<T>> {

    public static final Gson RESPONSE_GSON = new GsonBuilder()
      .registerTypeAdapter(ResponseUuidAtTime.class, new ResponseUuidAtTimeAdapter())
      .registerTypeAdapter(ResponseUuidToProperties.class, new ResponseUuidToPropertiesAdapter())
      .registerTypeAdapter(ResponseNameHistoryAdapter.TYPE_TOKEN.getRawType(), new ResponseNameHistoryAdapter())
      .registerTypeAdapter(ResponseNameToUuidAdapter.TYPE_TOKEN.getRawType(), new ResponseNameToUuidAdapter())
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
            try (InputStream input = stream; JsonReader reader = new JsonReader(new InputStreamReader(input))) {
                return RESPONSE_GSON.fromJson(reader, target);
            } catch (Exception e) {
                Server.get().getLogger().error(e);
                throw new RuntimeException(e);
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

    private static class ResponseUuidToPropertiesAdapter implements JsonDeserializer<ResponseUuidToProperties> {

        @Override
        public ResponseUuidToProperties deserialize(JsonElement element, Type type, JsonDeserializationContext ctx) throws JsonParseException {
            JsonObject object = element.getAsJsonObject();
            JsonObject properties = object.get("properties").getAsJsonArray().get(0).getAsJsonObject();

            return new ResponseUuidToProperties(
              UUID.fromString(object.get("id").getAsString().replaceFirst(
                "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5"
              )),
              object.get("name").getAsString(),
              properties.has("signature") ? new PlayerProperties(
                properties.get("name").getAsString(),
                properties.get("value").getAsString(),
                properties.get("signature").getAsString()
              ) : new PlayerProperties(
                properties.get("name").getAsString(),
                properties.get("value").getAsString()
              )
            );
        }
    }

    public static class ResponseNameHistoryAdapter implements JsonDeserializer<List<ResponseNameHistory>> {

        public static final TypeToken<List<ResponseNameHistory>> TYPE_TOKEN = new TypeToken<>() {};

        @Override
        public List<ResponseNameHistory> deserialize(JsonElement element, Type type, JsonDeserializationContext ctx) throws JsonParseException {
            JsonArray array = element.getAsJsonArray();
            List<ResponseNameHistory> histories = Lists.newArrayList();

            for (int i = 0; i < array.size(); i++) {
                JsonObject object = array.get(i).getAsJsonObject();

                histories.add(object.has("changedToAt") ? new ResponseNameHistory(object.get("name").getAsString(), object.get("changedToAt").getAsLong()) :
                  new ResponseNameHistory(object.get("name").getAsString()));
            }

            return histories;
        }
    }

    public static class ResponseNameToUuidAdapter implements JsonDeserializer<List<ResponseNameToUuid>> {

        public static final TypeToken<List<ResponseNameToUuid>> TYPE_TOKEN = new TypeToken<>() {};

        @Override
        public List<ResponseNameToUuid> deserialize(JsonElement element, Type type, JsonDeserializationContext ctx) throws JsonParseException {
            JsonArray array = element.getAsJsonArray();
            List<ResponseNameToUuid> uuids = Lists.newArrayList();

            for (int i = 0; i < array.size(); i++) {
                JsonObject object = array.get(i).getAsJsonObject();

                uuids.add(new ResponseNameToUuid(
                  UUID.fromString(object.get("id").getAsString().replaceFirst(
                    "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5"
                  )),
                  object.get("name").getAsString(),
                  object.has("legacy") && object.get("legacy").getAsBoolean(),
                  object.has("demo") && object.get("demo").getAsBoolean()
                ));
            }

            return uuids;
        }
    }
}
