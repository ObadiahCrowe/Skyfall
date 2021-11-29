package io.skyfallsdk.util.http.response;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;

import java.net.http.HttpResponse;
import java.util.List;

public class PlainTextBodyHandler implements HttpResponse.BodyHandler<List<String>> {

    @Override
    public HttpResponse.BodySubscriber<List<String>> apply(HttpResponse.ResponseInfo responseInfo) {
        List<String> lines = Lists.newArrayList();

        return HttpResponse.BodySubscribers.mapping(
          HttpResponse.BodySubscribers.ofString(Charsets.UTF_8),
          str -> {
              lines.addAll(List.of(str.split("\n")));

              return lines;
          }
        );
    }
}
