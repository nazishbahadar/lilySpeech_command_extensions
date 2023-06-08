//import com.github.kwhat.jnativehook.GlobalScreen;
//import com.github.kwhat.jnativehook.NativeHookException;
//import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
//import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.function.BiConsumer;
//
//
//public class KeyboardListener implements NativeKeyListener {
//
//    private final BiConsumer<Boolean, JTextField> toggleIsWorkingAction;
//
//    JTextField textField;
//
//    private final Timer keyboardInactivityTimer = new Timer(2000, new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            toggleIsWorkingAction.accept(false, textField);
//        }
//    });
//
//    private final Timer commandsRunningTimer = new Timer(100, new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            VoiceTypingApp.commandsRunning = false;
//        }
//    });
//
//
//    public KeyboardListener(BiConsumer<Boolean, JTextField> toggleIsWorkingAction, JTextField textField) {
//        keyboardInactivityTimer.setRepeats(false);
//        keyboardInactivityTimer.stop();
//        commandsRunningTimer.setRepeats(false);
//        this.textField = textField;
//        this.toggleIsWorkingAction = toggleIsWorkingAction;
//    }
//
//    public void nativeKeyPressed(NativeKeyEvent e) {
//        toggleIsWorking();
//    }
//
//    private void toggleIsWorking() {
//        if (!VoiceTypingApp.commandsRunning){
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
//            toggleIsWorkingAction.accept(true, textField);
//        }else {
//            commandsRunningTimer.restart();
//        }
//    }
//
//    public void nativeKeyReleased(NativeKeyEvent e) {
//        toggleIsWorking();
//    }
//
////    public void nativeKeyTyped(NativeKeyEvent e) {
////        System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
////    }
//
//    public static void register() {
//        try {
//            GlobalScreen.registerNativeHook();
//        } catch (NativeHookException ex) {
//            System.err.println("There was a problem registering the native hook.");
//            System.err.println(ex.getMessage());
//
//            System.exit(1);
//        }
//    }
//}
