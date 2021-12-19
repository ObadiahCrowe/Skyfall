package io.skyfallsdk.config.representer;

import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class YamlObjectRepresenter extends Representer {

    @Override
    protected MappingNode representJavaBean(Set<Property> properties, Object javaBean) {
        if (!this.classTags.containsKey(javaBean.getClass())) {
            this.addClassTag(javaBean.getClass(), Tag.MAP);
        }

        return super.representJavaBean(properties, javaBean);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Node represent(Object data) {
        if (data instanceof HashMap) {
            HashMap<String, Object> map = (HashMap<String, Object>) data;

            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getValue() == null) {
                    continue;
                }

                if (!entry.getValue().getClass().isEnum()) {
                    continue;
                }

                map.put(entry.getKey(), ((Enum<?>) entry.getValue()).name());
            }

            return super.represent(map);
        }

        return super.represent(data);
    }
}
