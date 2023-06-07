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
                VoiceTypingApp.resetLilly(2);
            }
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
//    }); This is quite a good thing command control /

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
        if (!VoiceTypingApp.commandsRunning ){
//            if (!keyboardInactivityTimer.isRunning()){These are electromagnetic waves control command control /
//                Robot robot = null;
//                try {
//                    robot = new Robot();
//                } catch (AWTException e) {
//                    throw new RuntimeException(e);
//                }
//                robot.delay(300); // to avoid pressing unintentional shortcuts due to alt tab running in background
//            }
//            keyboardInactivityTimer.restart();
            checkTimer.restart();
            String keyChar;
            switch (e.getKeyCode()) {
                case NativeKeyEvent.VC_SLASH:
                    keyChar = "/";
                    break;
                case NativeKeyEvent.VC_BACKSPACE:
                    keyChar = "";
                    break;
                case NativeKeyEvent.VC_ENTER:
                    keyChar = "";
                    break;
                case NativeKeyEvent.VC_SPACE:
                    keyChar = " ";
                    break;
                case NativeKeyEvent.VC_TAB:
                    keyChar = "";
                    break;
                case NativeKeyEvent.VC_ESCAPE:
                    keyChar = "";
                    break;
                case NativeKeyEvent.VC_CAPS_LOCK:
                    keyChar = "";
                    break;
                case NativeKeyEvent.VC_SHIFT:
                    keyChar = "";
                    break;
                case NativeKeyEvent.VC_CONTROL:
                    keyChar = "";
                    break;
                case NativeKeyEvent.VC_META:
                    keyChar = "";
                    break;
                case NativeKeyEvent.VC_ALT:
                    keyChar = "";
                    break;
                case NativeKeyEvent.VC_UP:
                    keyChar = "";
                    break;
                case NativeKeyEvent.VC_DOWN:
                    keyChar = "";
                    break;
                case NativeKeyEvent.VC_LEFT:
                    keyChar = "";
                    break;
                case NativeKeyEvent.VC_RIGHT:
                    keyChar = "";
                    break;
                // Handle function keys
                case NativeKeyEvent.VC_F1:
                case NativeKeyEvent.VC_F2:
                case NativeKeyEvent.VC_F3:
                case NativeKeyEvent.VC_F4:
                case NativeKeyEvent.VC_F5:
                case NativeKeyEvent.VC_F6:
                case NativeKeyEvent.VC_F7:
                case NativeKeyEvent.VC_F8:
                case NativeKeyEvent.VC_F9:
                case NativeKeyEvent.VC_F10:
                case NativeKeyEvent.VC_F11:
                case NativeKeyEvent.VC_F12:
                    keyChar = "f" + (e.getKeyCode() - NativeKeyEvent.VC_F1 + 1);
                    break;
                default:
                    keyChar = String.valueOf(NativeKeyEvent.getKeyText(e.getKeyCode()).charAt(0));
                    break;
            }

            typedText.append(keyChar);

        }else {
            commandsRunningTimer.restart();
        }

    }

//    public void nativeKeyReleased(NativeKeyEvent e) {This is electromagnetic wave control command control /
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
