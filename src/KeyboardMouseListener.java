import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseWheelEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseWheelListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.BiConsumer;

public class KeyboardMouseListener implements NativeMouseInputListener, NativeKeyListener, NativeMouseWheelListener {

    private final BiConsumer<Boolean, JTextField> toggleIsWorkingAction;

    JTextField textField;

    boolean isMouseInactivityTimerRunning = false;



    private final Timer keyboardInactivityTimer = new Timer(2000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!mouseInactivityTimer.isRunning()){
                toggleIsWorkingAction.accept(false, textField);
            }
        }
    });

    private final Timer commandsRunningTimer = new Timer(100, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            VoiceTypingApp.commandsRunning = false;
        }
    });

    private final Timer mouseInactivityTimer = new Timer(2000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!keyboardInactivityTimer.isRunning()){
                isMouseInactivityTimerRunning = true;
                toggleIsWorkingAction.accept(false, textField);
                isMouseInactivityTimerRunning = false;
            }
        }
    });

    KeyboardMouseListener(BiConsumer<Boolean, JTextField> toggleIsWorkingAction, JTextField textField) {
        mouseInactivityTimer.setRepeats(false);
        keyboardInactivityTimer.setRepeats(false);
        commandsRunningTimer.setRepeats(false);
        this.textField = textField;
        this.toggleIsWorkingAction = toggleIsWorkingAction;
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        keyboardActive();
    }

    private void keyboardActive() {
        if (!VoiceTypingApp.commandsRunning){
            if (!keyboardInactivityTimer.isRunning()){
                Robot robot = null;
                try {
                    robot = new Robot();
                } catch (AWTException e) {
                    throw new RuntimeException(e);
                }
                robot.delay(300); // to avoid pressing unintentional shortcuts due to alt tab running in background
            }
            keyboardInactivityTimer.restart();
            toggleIsWorkingAction.accept(true, textField);
        }else {
            commandsRunningTimer.restart();
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        keyboardActive();
    }

    public void nativeMouseMoved(NativeMouseEvent e) {
        mouseActive();
    }

    public void nativeMouseDragged(NativeMouseEvent e) {
        mouseActive();
    }

    public void nativeMouseClicked(NativeMouseEvent e) {
        mouseActive();
    }

    public void nativeMousePressed(NativeMouseEvent e) {
        mouseActive();
    }

    public void nativeMouseReleased(NativeMouseEvent e) {
        mouseActive();
    }

    public void nativeMouseWheelMoved(NativeMouseWheelEvent e) {
        mouseActive();
    }

    public void mouseActive(){

        if (isMouseInactivityTimerRunning){
            return;
        }
        mouseInactivityTimer.restart();
        toggleIsWorkingAction.accept(true, textField);

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
