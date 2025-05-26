package gui.components;

import gui.robot.RobotModel;
import gui.visualization.GameVisualizer;

import javax.swing.*;
import java.util.ResourceBundle;

public class GameWindow extends JInternalFrame {
    public GameWindow(RobotModel model) {
        super(ResourceBundle.getBundle("gui.messages").getString("game.window.title"), true, true, true, true);
        setName("GameWindow");
        GameVisualizer visualizer = new GameVisualizer(model);
        add(visualizer);

        setSize(600, 500);
        setLocation(10, 10);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    public void updateTitle(String title) {
        setTitle(title);
    }
}