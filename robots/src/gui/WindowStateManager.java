package gui;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class WindowStateManager {
    private static final String CONFIG_FILE = System.getProperty("user.home") + "/app_config.json";

    public void saveWindowStates(JDesktopPane desktopPane) {
        Map<String, WindowState> windowStates = new HashMap<>();

        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            String windowId = frame.getTitle();
            Rectangle bounds = frame.getBounds();
            boolean isIcon = frame.isIcon();

            windowStates.put(windowId, new WindowState(windowId, bounds, isIcon));
        }

        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            JSONArray jsonArray = new JSONArray();
            for (WindowState state : windowStates.values()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("windowId", state.getWindowId());
                jsonObject.put("x", state.getBounds().x);
                jsonObject.put("y", state.getBounds().y);
                jsonObject.put("width", state.getBounds().width);
                jsonObject.put("height", state.getBounds().height);
                jsonObject.put("isIcon", state.isIcon());
                jsonArray.put(jsonObject);
            }
            writer.write(jsonArray.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadWindowStates(JDesktopPane desktopPane) {
        if (!Files.exists(Paths.get(CONFIG_FILE))) {
            return;
        }

        try {
            String content = new String(Files.readAllBytes(Paths.get(CONFIG_FILE)));
            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String windowId = jsonObject.getString("windowId");
                int x = jsonObject.getInt("x");
                int y = jsonObject.getInt("y");
                int width = jsonObject.getInt("width");
                int height = jsonObject.getInt("height");
                boolean isIcon = jsonObject.getBoolean("isIcon");

                for (JInternalFrame frame : desktopPane.getAllFrames()) {
                    if (frame.getTitle().equals(windowId)) {
                        frame.setBounds(x, y, width, height);
                        try {
                            if (isIcon) {
                                frame.setIcon(true);
                            }
                        } catch (java.beans.PropertyVetoException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}