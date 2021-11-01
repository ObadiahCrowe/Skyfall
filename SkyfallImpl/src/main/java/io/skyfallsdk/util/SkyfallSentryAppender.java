package io.skyfallsdk.util;

import io.sentry.Sentry;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

@Plugin(name = "SkyfallSentryAppender", category = Core.CATEGORY_NAME, elementType = Appender.ELEMENT_TYPE)
public class SkyfallSentryAppender extends AbstractAppender {

    public static boolean usingSentry = false;

    protected SkyfallSentryAppender() {
        super("SkyfallSentryAppender", null, null, false, Property.EMPTY_ARRAY);
    }

    @Override
    public void append(LogEvent event) {
        if (usingSentry) {
            Throwable thrown = event.getThrown();
            if (thrown != null) {
                Sentry.captureException(thrown);
                System.out.println(event.getMessage());
            }
        }
    }

    @PluginFactory
    public static SkyfallSentryAppender createAppender() {
        return new SkyfallSentryAppender();
    }
}
