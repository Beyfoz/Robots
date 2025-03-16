package gui;

import java.awt.Rectangle;

public class WindowState {
    private String windowId;
    private Rectangle bounds;
    private boolean isIcon;

    public WindowState(String windowId, Rectangle bounds, boolean isIcon) {
        this.windowId = windowId;
        this.bounds = bounds;
        this.isIcon = isIcon;
    }

    public String getWindowId() {
        return windowId;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public boolean isIcon() {
        return isIcon;
    }
}