package com.company.view;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration.getDefaultOfSize;

public class ViewManager {
    private static final Map<Integer, View> views = new HashMap<>();

    static {
        views.put(0, new StartView());
        views.put(1, new WeatherOverview());
    }

    private static final String APP_TITLE = "Weather App";
    private static final Integer DEFAULT_TERMINAL_LENGTH = 201;
    private static final Integer DEFAULT_TERMINAL_WIDTH = 101;
    private static final Integer DEFAULT_TERMINAL_FONT_SIZE = 10;
    private static final DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory()
            .setInitialTerminalSize(new TerminalSize(DEFAULT_TERMINAL_LENGTH, DEFAULT_TERMINAL_WIDTH))
            .setTerminalEmulatorFontConfiguration(getDefaultOfSize(DEFAULT_TERMINAL_FONT_SIZE))
            .setTerminalEmulatorTitle(APP_TITLE);

    private static Terminal terminal;
    private static TextGraphics textGraphics;

    public ViewManager() {
        try {
            terminal = defaultTerminalFactory.createTerminal();
            textGraphics = terminal.newTextGraphics();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initTerminal() throws IOException {
        textGraphics.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
        terminal.setBackgroundColor(TextColor.ANSI.GREEN_BRIGHT);
        terminal.enterPrivateMode();
        terminal.clearScreen();
        terminal.setCursorVisible(false);
    }

    public void display() {
        try {
            initTerminal();
            views.get(0).display();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (terminal != null) {
                try {
                    terminal.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Terminal getTerminal() {
        return terminal;
    }

    public static TextGraphics getTextGraphics() {
        return textGraphics;
    }
}
