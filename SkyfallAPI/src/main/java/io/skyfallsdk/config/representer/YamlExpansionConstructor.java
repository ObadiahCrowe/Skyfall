package io.skyfallsdk.config.representer;

import com.google.common.collect.Maps;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Node;

import java.util.Map;

public class YamlExpansionConstructor extends Constructor {

    private static final Map<String, Class<?>> CLASS_MAPPINGS = Maps.newHashMap();

    private final Class<?> rootClass;

    public YamlExpansionConstructor(Class<?> root) {
        super(root);

        this.rootClass = root;
    }

    @Override
    protected Class<?> getClassForNode(Node node) {
        String name = node.getTag().getClassName();
        Class<?> clazz = CLASS_MAPPINGS.computeIfAbsent(name, (claz) -> {
            try {
                return Class.forName(name, true, this.rootClass.getClassLoader());
            } catch (ClassNotFoundException e) {
                return null;
            }
        });

        if (clazz == null) {
            return super.getClassForNode(node);
        }

        return clazz;
    }
}
