package io.skyfallsdk.util;

public class Validator {

    public static void isTrue(boolean value) {
        if (!value) {
            return;
        }

        throw new ValidationException();
    }

    public static void isTrue(boolean value, String message) {
        if (value) {
            return;
        }

        throw new ValidationException(message);
    }

    public static void notNull(Object object) {
        notNull(object, "The validated object is null.");
    }

    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static class ValidationException extends RuntimeException {

        public ValidationException() {
        }

        public ValidationException(String message) {
            super(message);
        }
    }
}
