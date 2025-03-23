package gui;

import java.awt.Rectangle;
import java.io.Serializable;


public class WindowState implements Serializable {
    private static final long serialVersionUID = 1L;

    private String windowName;

public class WindowState {
    private String windowId;

    private Rectangle bounds;
    private boolean isCollapsed;



    public WindowState(String windowName, Rectangle bounds, boolean isCollapsed) {
        this.windowName = windowName;

    public WindowState(String windowId, Rectangle bounds, boolean isIcon) {
        this.windowId = windowId;

        this.bounds = bounds;
        this.isCollapsed = isCollapsed;
    }


    public String getWindowName() {
        return windowName;
    }



    public String getWindowId() {
        return windowId;
    }


    public Rectangle getBounds() {
        return bounds;
    }


    public boolean isCollapsed() {
        return isCollapsed;

    public boolean isIcon() {
        return isIcon;

    }
}