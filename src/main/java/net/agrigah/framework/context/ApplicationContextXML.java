package net.agrigah.framework.context;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ApplicationContextXML {

    private Map<String, Object> beans = new HashMap<>();

    public ApplicationContextXML(String configFile) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            //Document doc = builder.parse(new File(configFile));
            Document doc = builder.parse(
                    ApplicationContextXML.class.getClassLoader().getResourceAsStream(configFile)
            );
            doc.getDocumentElement().normalize();

            NodeList beanList = doc.getElementsByTagName("bean");

            // Step 1: instantiate all beans
            for (int i = 0; i < beanList.getLength(); i++) {
                Element el = (Element) beanList.item(i);
                String id = el.getAttribute("id");
                String className = el.getAttribute("class");
                Object instance = Class.forName(className).getDeclaredConstructor().newInstance();
                beans.put(id, instance);
            }

            // Step 2: inject properties
            for (int i = 0; i < beanList.getLength(); i++) {
                Element el = (Element) beanList.item(i);
                String id = el.getAttribute("id");
                Object instance = beans.get(id);

                NodeList properties = el.getElementsByTagName("property");
                for (int j = 0; j < properties.getLength(); j++) {
                    Element prop = (Element) properties.item(j);
                    String propName = prop.getAttribute("name");
                    String propRef = prop.getAttribute("ref");

                    Object dependency = beans.get(propRef);
                    if (dependency != null) {
                        String setterName = "set" + Character.toUpperCase(propName.charAt(0)) + propName.substring(1);
                        for (Method method : instance.getClass().getMethods()) {
                            if (method.getName().equals(setterName) && method.getParameterCount() == 1) {
                                method.invoke(instance, dependency);
                                break;
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to load XML context: " + e.getMessage(), e);
        }
    }

    public Object getBean(String id) {
        return beans.get(id);
    }

    public <T> T getBean(String id, Class<T> clazz) {
        return clazz.cast(beans.get(id));
    }
}