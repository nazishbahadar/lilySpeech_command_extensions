//import com.github.kwhat.jnativehook.GlobalScreen;
//import com.github.kwhat.jnativehook.NativeHookException;
//import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
//import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener;
//
//
//public class MouseListener1 implements NativeMouseInputListener  {
//
////    public void nativeMouseClicked(NativeMouseEvent e) {
////        System.out.println("Mouse Clicked: " + e.getClickCount());
////
////    }
////
////    public void nativeMousePressed(NativeMouseEvent e) {
////        System.out.println("Mouse Pressed: " + e.getButton());
////    }
////
////    public void nativeMouseReleased(NativeMouseEvent e) {
////        System.out.println("Mouse Released: " + e.getButton());
////    }
//
//    public void nativeMouseMoved(NativeMouseEvent e) {
//        System.out.println("Mouse Moved: " + e.getX() + ", " + e.getY());
//    }
//
//    public void nativeMouseDragged(NativeMouseEvent e) {
//        System.out.println("Mouse Dragged: " + e.getX() + ", " + e.getY());
//    }
//
//    public static void MouseListener() {
//        try {
//            GlobalScreen.registerNativeHook();
//        }
//        catch (NativeHookException ex) {
//            System.err.println("There was a problem registering the native hook.");
//            System.err.println(ex.getMessage());
//
//            System.exit(1);
//        }
//
//    }
//}
