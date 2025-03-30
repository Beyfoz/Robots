package gui;

import java.awt.Rectangle;

public class WindowState {
    private String windowName;
    private Rectangle bounds;
    private boolean isIcon;


    public WindowState(String windowName, Rectangle bounds, boolean isIcon) {
        this.windowName = windowName;
        this.bounds = bounds;
        this.isIcon = isIcon;
    }


    public String getWindowName() {
        return windowName;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public boolean isIcon() {
        return isIcon;
    }
}