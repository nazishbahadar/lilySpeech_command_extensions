import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseWheelListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

public class KeyboardMouseListener implements NativeMouseInputListener, NativeKeyListener, NativeMouseWheelListener {

    private final Consumer<String> sendQuery;
    private StringBuilder typedText = new StringBuilder();


    private final Timer commandsRunningTimer = new Timer(100, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            VoiceTypingApp.commandsRunning = false;
        }
    });

    private final Timer checkTimer = new Timer(700, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String typedTextString = typedText.toString().toLowerCase();
            if (typedTextString.contains("command") || typedTextString.contains("shortcut") || typedTextString.contains("insert")) {
                System.out.println(typedTextString);
                sendQuery.accept(typedTextString);
                VoiceTypingApp.resetLilly(2);
            }
            typedText =  new StringBuilder();
        }
    });

    KeyboardMouseListener(Consumer<String> sendQuery) {
        commandsRunningTimer.setRepeats(false);
        checkTimer.setRepeats(false);
        this.sendQuery = sendQuery;
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        detectQuery(e);
    }

    private void detectQuery(NativeKeyEvent e) {
        if (!VoiceTypingApp.commandsRunning ){
            checkTimer.restart();
            String keyChar = switch (e.getKeyCode()) {
                case NativeKeyEvent.VC_SLASH -> "/";
                case NativeKeyEvent.VC_BACKSPACE, NativeKeyEvent.VC_RIGHT, NativeKeyEvent.VC_LEFT, NativeKeyEvent.VC_DOWN, NativeKeyEvent.VC_UP, NativeKeyEvent.VC_ALT, NativeKeyEvent.VC_META, NativeKeyEvent.VC_CONTROL, NativeKeyEvent.VC_SHIFT, NativeKeyEvent.VC_CAPS_LOCK, NativeKeyEvent.VC_ESCAPE, NativeKeyEvent.VC_TAB, NativeKeyEvent.VC_ENTER ->
                        "";
                case NativeKeyEvent.VC_SPACE -> " ";
                // Handle function keys
                case NativeKeyEvent.VC_F1, NativeKeyEvent.VC_F2, NativeKeyEvent.VC_F3, NativeKeyEvent.VC_F4, NativeKeyEvent.VC_F5, NativeKeyEvent.VC_F6, NativeKeyEvent.VC_F7, NativeKeyEvent.VC_F8, NativeKeyEvent.VC_F9, NativeKeyEvent.VC_F10, NativeKeyEvent.VC_F11, NativeKeyEvent.VC_F12 ->
                        "f" + (e.getKeyCode() - NativeKeyEvent.VC_F1 + 1);
                default -> String.valueOf(NativeKeyEvent.getKeyText(e.getKeyCode()).charAt(0));
            };

            typedText.append(keyChar);

        }else {
            commandsRunningTimer.restart();
        }

    }


    public static void register() {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }
    }

}
