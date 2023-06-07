//import com.github.kwhat.jnativehook.GlobalScreen;
//import com.github.kwhat.jnativehook.NativeHookException;
//import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
//import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener;
//
//import java.util.function.Consumer;
//
//public class MouseListener2 implements NativeMouseInputListener {
//    private final Consumer<NativeMouseEvent> mouseMovedAction;
////    private final Consumer<NativeMouseEvent> mouseDraggedAction;
//
//    public MouseListener2(Consumer<NativeMouseEvent> mouseMovedAction) {
//        this.mouseMovedAction = mouseMovedAction;
//    }
//
//    public void nativeMouseMoved(NativeMouseEvent e) {
//        if (mouseMovedAction != null) {
//            mouseMovedAction.accept(e);
//        }
//    }
//
//    public void nativeMouseDragged(NativeMouseEvent e) {
////        if (mouseDraggedAction != null) {
////            mouseDraggedAction.accept(e);
////        }
//        if (mouseMovedAction != null) {
//            mouseMovedAction.accept(e);
//        }
//    }
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
