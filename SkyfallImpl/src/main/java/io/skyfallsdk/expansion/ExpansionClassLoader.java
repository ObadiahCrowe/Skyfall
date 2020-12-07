package io.skyfallsdk.expansion;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import io.skyfallsdk.SkyfallMain;

import javax.annotation.concurrent.ThreadSafe;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

@ThreadSafe
public class ExpansionClassLoader extends URLClassLoader {
    
    private static final Map<String, Class<?>> LOADED_CLASSES = Maps.newConcurrentMap();

    static {
        ClassLoader.registerAsParallelCapable();
    }

    private final Set<Class<?>> heldClasses;

    public ExpansionClassLoader(Path expansion) throws MalformedURLException {
        super(new URL[] { expansion.toUri().toURL() }, SkyfallMain.class.getClassLoader());

        this.heldClasses = Sets.newHashSet();
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return LOADED_CLASSES.computeIfAbsent(name, func -> {
            try {
                Class<?> c = super.findClass(name);

                ExpansionClassLoader.this.heldClasses.add(c);
                return c;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        });
    }


    @Override
    public void close() throws IOException {
        super.close();

        for (Class<?> clazz : this.heldClasses) {
            LOADED_CLASSES.remove(clazz.getName());
        }

        this.heldClasses.clear();
    }
}
