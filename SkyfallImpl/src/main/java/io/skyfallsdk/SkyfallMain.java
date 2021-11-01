package io.skyfallsdk;

import io.sentry.Sentry;

public class SkyfallMain {

    private static SkyfallServer server;

    public static void main(String[] args) {
        server = new SkyfallServer();
    }
}
