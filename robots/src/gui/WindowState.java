package gui;

import java.awt.Rectangle;

public class WindowState {
    private String windowName; // Используем windowName вместо windowId
    private Rectangle bounds;
    private boolean isIcon;

    // Конструктор
    public WindowState(String windowName, Rectangle bounds, boolean isIcon) {
        this.windowName = windowName;
        this.bounds = bounds;
        this.isIcon = isIcon;
    }

    // Геттер для windowName
    public String getWindowName() {
        return windowName; // Возвращаем windowName
    }

    // Геттер для bounds
    public Rectangle getBounds() {
        return bounds; // Возвращаем bounds
    }

    // Геттер для isIcon
    public boolean isIcon() {
        return isIcon; // Возвращаем isIcon
    }
}