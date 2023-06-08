//import com.github.kwhat.jnativehook.GlobalScreen;
//import com.github.kwhat.jnativehook.NativeHookException;
//import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
//import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener;
//
//
//import javax.swing.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.function.Consumer;
//import java.util.function.BiConsumer;
//
//
//public class MouseListener implements NativeMouseInputListener {
////    private final Consumer<NativeMouseEvent> mouseMovedAction;
//    private final BiConsumer<Boolean, JTextField> toggleIsWorkingAction;
//
//    JTextField textField;
//
//    boolean isMouseInactivityTimerRunning = false;
//
//    private final Timer mouseInactivityTimer = new Timer(1000, new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            isMouseInactivityTimerRunning = true;
//            toggleIsWorkingAction.accept(false, textField);
//            isMouseInactivityTimerRunning = false;
//        }
//    });
//
//
//
//    public MouseListener(BiConsumer<Boolean, JTextField> toggleIsWorkingAction, JTextField textField) {
//        mouseInactivityTimer.setRepeats(false);
//        this.textField = textField;
//        this.toggleIsWorkingAction = toggleIsWorkingAction;
//    }
//
//
//
//    public void nativeMouseMoved(NativeMouseEvent e) {
//        toggleIsWorking();
//    }
//
//    private void toggleIsWorking() {
//        if (isMouseInactivityTimerRunning){
//            return;
//        }
//        mouseInactivityTimer.restart();
//        toggleIsWorkingAction.accept(true, textField);
//    }
//
//    public void nativeMouseDragged(NativeMouseEvent e) {
//        toggleIsWorking();
//    }
//
//
//    public static void register() {
//        try {
//            GlobalScreen.registerNativeHook();
//        } catch (NativeHookException ex) {
//            System.err.println("There was a problem registering the native hook.");
//            System.err.println(ex.getMessage());
//            System.exit(1);
//        }
//    }
//}
