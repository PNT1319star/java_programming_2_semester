package org.csjchoisoojong.controllers.tools;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.ResourceBundle;

public class ObservableResourceFactory {
    private ObjectProperty<ResourceBundle> resources = new SimpleObjectProperty<>();
    public ObjectProperty<ResourceBundle> resourcesProperty() {
        return resources;
    }
    public final ResourceBundle getResources() {
        return resourcesProperty().get();
    }
    public final void setResources(ResourceBundle resources) {
        resourcesProperty().set(resources);
    }
    public StringBinding getStringBinding(String key) {
        return new StringBinding() {
            {
                bind(resourcesProperty());
            }
            @Override
            protected String computeValue() {
                return getResources().getString(key);
            }
        };
    }
}
