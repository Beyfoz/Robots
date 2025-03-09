package gui;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import log.Logger;

public class MainApplicationFrame extends JFrame {
    private final JDesktopPane desktopPane = new JDesktopPane();
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public MainApplicationFrame() {
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                screenSize.width - inset * 2,
                screenSize.height - inset * 2);

        setContentPane(desktopPane);

        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);

        GameWindow gameWindow = new GameWindow();
        gameWindow.setSize(400, 400);
        addWindow(gameWindow);

        setJMenuBar(new MenuBarFactory(this).createMenuBar());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                if (confirmAndExit()) {
                    fireApplicationExitEvent();
                }
            }
        });

        addApplicationExitListener(evt -> {
            dispose();
        });
    }

    protected LogWindow createLogWindow() {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10, 10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("Протокол работает");
        return logWindow;
    }

    protected void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);

        frame.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                if (!confirmAndExit()) {
                    e.getInternalFrame().setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
                } else {
                    e.getInternalFrame().setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
                }
            }
        });
    }

    public void setLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException | InstantiationException
                 | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }
    }

    public boolean confirmAndExit() {
        int option = JOptionPane.showOptionDialog(
                this,
                "Вы уверены, что хотите выйти?",
                "Подтверждение выхода",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new Object[]{"Да", "Нет"},
                "Нет"
        );

        return option == JOptionPane.YES_OPTION;
    }

    private void fireApplicationExitEvent() {
        support.firePropertyChange("ApplicationExit", false, true);
    }

    public void addApplicationExitListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener("ApplicationExit", listener);
    }

    public void removeApplicationExitListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener("ApplicationExit", listener);
    }
}