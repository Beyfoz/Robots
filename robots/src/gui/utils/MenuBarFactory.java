package gui.utils;

import gui.components.MainApplicationFrame;
import log.Logger;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.Locale;
import java.util.ResourceBundle;

public class MenuBarFactory {
    private final MainApplicationFrame frame;
    private ResourceBundle messages;

    public MenuBarFactory(MainApplicationFrame frame) {
        this.frame = frame;
        this.messages = ResourceBundle.getBundle("gui.messages", frame.getCurrentLocale());
    }

    public JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = createFileMenu();
        JMenu lookAndFeelMenu = createLookAndFeelMenu();
        JMenu testMenu = createTestMenu();
        JMenu languageMenu = createLanguageMenu();

        menuBar.add(fileMenu);
        menuBar.add(lookAndFeelMenu);
        menuBar.add(testMenu);
        menuBar.add(languageMenu);

        return menuBar;
    }

    private JMenu createFileMenu() {
        JMenu fileMenu = new JMenu(messages.getString("file.menu"));
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem exitMenuItem = new JMenuItem(messages.getString("file.exit"), KeyEvent.VK_X);
        exitMenuItem.addActionListener((event) -> {
            frame.dispatchEvent(new java.awt.event.WindowEvent(frame, java.awt.event.WindowEvent.WINDOW_CLOSING));
        });
        fileMenu.add(exitMenuItem);

        return fileMenu;
    }

    private JMenu createLookAndFeelMenu() {
        JMenu lookAndFeelMenu = new JMenu(messages.getString("laf.menu"));
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(
                messages.getString("laf.menu"));

        JMenuItem systemLookAndFeel = new JMenuItem(messages.getString("laf.system"), KeyEvent.VK_S);
        systemLookAndFeel.addActionListener((event) -> {
            frame.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            frame.invalidate();
        });
        lookAndFeelMenu.add(systemLookAndFeel);

        JMenuItem crossplatformLookAndFeel = new JMenuItem(messages.getString("laf.crossplatform"), KeyEvent.VK_U);
        crossplatformLookAndFeel.addActionListener((event) -> {
            frame.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            frame.invalidate();
        });
        lookAndFeelMenu.add(crossplatformLookAndFeel);

        return lookAndFeelMenu;
    }

    private JMenu createTestMenu() {
        JMenu testMenu = new JMenu(messages.getString("test.menu"));
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription(
                messages.getString("test.menu"));

        JMenuItem addLogMessageItem = new JMenuItem(messages.getString("test.log_message"), KeyEvent.VK_S);
        addLogMessageItem.addActionListener((event) -> {
            Logger.debug(messages.getString("log.new_line"));
        });
        testMenu.add(addLogMessageItem);

        return testMenu;
    }

    private JMenu createLanguageMenu() {
        JMenu languageMenu = new JMenu(messages.getString("language.menu"));
        languageMenu.setMnemonic(KeyEvent.VK_L);

        JMenuItem russianItem = new JMenuItem(messages.getString("language.russian"), KeyEvent.VK_R);
        russianItem.addActionListener((event) -> {
            frame.setLocale(new Locale("ru"));
            frame.refreshUI();
        });
        languageMenu.add(russianItem);

        JMenuItem englishItem = new JMenuItem(messages.getString("language.english"), KeyEvent.VK_E);
        englishItem.addActionListener((event) -> {
            frame.setLocale(new Locale("en"));
            frame.refreshUI();
        });
        languageMenu.add(englishItem);

        return languageMenu;
    }
}