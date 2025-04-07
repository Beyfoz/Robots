package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

public class GameVisualizer extends JPanel {
    private final RobotModel model;
    private final Timer timer;
    private static final int MARGIN = 20;

    public GameVisualizer(RobotModel model) {
        this.model = model;
        this.timer = new Timer(10, e -> {
            model.update(getSize());
            repaint();
        });

        setDoubleBuffered(true);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point target = convertMouseToGameCoords(e.getPoint());
                model.setTargetPosition(target, getSize());
            }
        });

        timer.start();
    }

    private Point convertMouseToGameCoords(Point mousePoint) {
        int x = Math.max(MARGIN, Math.min(mousePoint.x, getWidth() - MARGIN));
        int y = Math.max(MARGIN, Math.min(mousePoint.y, getHeight() - MARGIN));
        return new Point(x, y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setColor(new Color(240, 240, 240));
        g2d.drawRect(MARGIN, MARGIN, getWidth() - 2*MARGIN, getHeight() - 2*MARGIN);

        drawRobot(g2d, (int)model.getRobotX(), (int)model.getRobotY(), model.getDirection());
        drawTarget(g2d, model.getTargetX(), model.getTargetY());
    }

    private void drawRobot(Graphics2D g, int x, int y, double direction) {
        AffineTransform t = AffineTransform.getRotateInstance(direction, x, y);
        g.setTransform(t);
        g.setColor(Color.MAGENTA);
        fillOval(g, x, y, 30, 10);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 30, 10);
        g.setColor(Color.WHITE);
        fillOval(g, x + 10, y, 5, 5);
        g.setTransform(new AffineTransform());
    }

    private void drawTarget(Graphics2D g, int x, int y) {
        g.setColor(new Color(50, 200, 50));
        fillOval(g, x, y, 8, 8);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 8, 8);
    }

    private static void fillOval(Graphics g, int x, int y, int width, int height) {
        g.fillOval(x - width/2, y - height/2, width, height);
    }

    private static void drawOval(Graphics g, int x, int y, int width, int height) {
        g.drawOval(x - width/2, y - height/2, width, height);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(600, 500);
    }
}