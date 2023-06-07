import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseWheelListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

public class KeyboardMouseListener implements NativeMouseInputListener, NativeKeyListener, NativeMouseWheelListener {

    private final Consumer<String> sendQuery;
    private StringBuilder typedText = new StringBuilder();

//    JTextField textField;
//
//    boolean isMouseInactivityTimerRunning = false;



//    private final Timer keyboardInactivityTimer = new Timer(2000, new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            if (!mouseInactivityTimer.isRunning()){
//                sendQuery.accept(false, textField);
//            }
//        }
//    });

    private final Timer commandsRunningTimer = new Timer(100, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            VoiceTypingApp.commandsRunning = false;
        }
    });

//    private final Timer resetLily = new Timer(20, new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            VoiceTypingApp.resetLily();
//        }
//    });

    private final Timer checkTimer = new Timer(700, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String typedTextString = typedText.toString().toLowerCase();
            if (typedTextString.contains("command") || typedTextString.contains("shortcut") || typedTextString.contains("insert")) {
                System.out.println(typedTextString);
                sendQuery.accept(typedTextString);
            }
            VoiceTypingApp.resetLily();
//            System.out.println("check");
            typedText =  new StringBuilder();
        }
    });

//    private final Timer mouseInactivityTimer = new Timer(2000, new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            if (!keyboardInactivityTimer.isRunning()){
//                isMouseInactivityTimerRunning = true;
//                sendQuery.accept(false, textField);
//                isMouseInactivityTimerRunning = false;
//            }
//        }
//    }); The electromagnetic waves command controls dash

    KeyboardMouseListener(Consumer<String> sendQuery) {
//        mouseInactivityTimer.setRepeats(false);
//        keyboardInactivityTimer.setRepeats(false);
        commandsRunningTimer.setRepeats(false);
        checkTimer.setRepeats(false);
//        this.textField = textField;
        this.sendQuery = sendQuery;
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        detectQuery(e);
    }

    private void detectQuery(NativeKeyEvent e) {
        checkTimer.restart();
        if (!VoiceTypingApp.commandsRunning && NativeKeyEvent.getKeyText(e.getKeyCode()).toLowerCase() != "space"){
//            if (!keyboardInactivityTimer.isRunning()){
//                Robot robot = null;
//                try {
//                    robot = new Robot();
//                } catch (AWTException e) {
//                    throw new RuntimeException(e);
//                }
//                robot.delay(300); // to avoid pressing unintentional shortcuts due to alt tab running in background
//            }
//            keyboardInactivityTimer.restart();
            char keyChar = NativeKeyEvent.getKeyText(e.getKeyCode()).charAt(0);
            System.out.println(NativeKeyEvent.getKeyText(e.getKeyCode()).toLowerCase());
            typedText.append(keyChar);
        }else {
            commandsRunningTimer.restart();
        }

    }

//    public void nativeKeyReleased(NativeKeyEvent e) {
//        keyboardActive();
//    }
//
//    public void nativeMouseMoved(NativeMouseEvent e) {
//        mouseActive();
//    }
//
//    public void nativeMouseDragged(NativeMouseEvent e) {
//        mouseActive();
//    }
//
//    public void nativeMouseClicked(NativeMouseEvent e) {
//        mouseActive();
//    }
//
//    public void nativeMousePressed(NativeMouseEvent e) {
//        mouseActive();
//    }
//
//    public void nativeMouseReleased(NativeMouseEvent e) {
//        mouseActive();
//    }
//
//    public void nativeMouseWheelMoved(NativeMouseWheelEvent e) {
//        mouseActive();
//    }

//    public void mouseActive(){
//
//        if (isMouseInactivityTimerRunning){
//            return;
//        }
//        mouseInactivityTimer.restart();
//        sendQuery.accept(true, textField);
//
//    }

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
