package org.csjchoisoojong.utilities;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.transform.Scale;
import org.csjchoisoojong.data.OrganizationType;

import java.util.Objects;

public class IconGenerator {
    public static Shape convertImageToShape(OrganizationType type, Color color, double size) {
        Image objectImage = IconGenerator.getBuilding(type);
        Path combinedPath = new Path();
        PixelReader pixelReader = objectImage.getPixelReader();
        int width = (int) objectImage.getWidth();
        int height = (int) objectImage.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color pixelColor = pixelReader.getColor(x, y);
                if (!pixelColor.equals(Color.TRANSPARENT)) {
                    // Create a small rectangle at each pixel location
                    combinedPath.getElements().add(new MoveTo(x, y));
                    combinedPath.getElements().add(new LineTo(x + 1, y));
                    combinedPath.getElements().add(new LineTo(x + 1, y + 1));
                    combinedPath.getElements().add(new LineTo(x, y + 1));
                    combinedPath.getElements().add(new ClosePath());
                }
            }
        }

        combinedPath.setFill(color);
        combinedPath.setStroke(color);

        // Calculate scaling factors based on desired size and original image size
        double scaleX = size / width;
        double scaleY = size / height;

        // Apply scaling to combinedPath
        Scale scale = new Scale(scaleX, scaleY, 0, 0);
        combinedPath.getTransforms().add(scale);

        return combinedPath;
    }

    private static Image getBuilding(OrganizationType type) {
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
