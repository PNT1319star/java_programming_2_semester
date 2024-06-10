package org.csjchoisoojong.utilities;

import javafx.scene.image.Image;
import org.csjchoisoojong.data.OrganizationType;

import java.util.Objects;

public class IconGenerator {
    public static Image getBuilding(OrganizationType type) {
        return switch (type) {
            case PUBLIC ->
                    new Image(Objects.requireNonNull(IconGenerator.class.getResource("/image/public.png")).toExternalForm());
            case GOVERNMENT ->
                    new Image(Objects.requireNonNull(IconGenerator.class.getResource("/image/government.png")).toExternalForm());
            case TRUST ->
                    new Image(Objects.requireNonNull(IconGenerator.class.getResource("/image/trust.png")).toExternalForm());
            case PRIVATE_LIMITED_COMPANY ->
                    new Image(Objects.requireNonNull(IconGenerator.class.getResource("/image/limited.png")).toExternalForm());
            case OPEN_JOINT_STOCK_COMPANY ->
                    new Image(Objects.requireNonNull(IconGenerator.class.getResource("/image/stock.png")).toExternalForm());
        };
    }
}
