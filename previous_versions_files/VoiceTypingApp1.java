//import javax.swing.*;
//import javax.swing.event.DocumentEvent;
//import javax.swing.event.DocumentListener;
//import java.awt.*;
//import java.awt.datatransfer.StringSelection;
//import java.awt.event.*;
//import java.util.HashMap;
//import java.util.Map;
//
//public class VoiceTypingApp1 {
//    private static final Map<String, Integer> KEY_MAP = createKeyMap();
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            JFrame frame = new JFrame("Voice Typing App");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setSize(400, 200);
//
//            JPanel panel = new JPanel();
//            frame.add(panel);
//            panel.setLayout(new BorderLayout());
//
//            JTextField textField = new JTextField();
//            panel.add(textField, BorderLayout.CENTER);
//
//            Timer clearTimer = new Timer(2000, new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    String voiceInput = textField.getText();
//                    System.out.println("Current voice input: " + voiceInput);
//                    pressKeysFromVoiceInput(voiceInput.toLowerCase());
//                    textField.setText("");
//                    Timer refocusTimer = new Timer(700, new ActionListener() {
//                        @Override
//                        public void actionPerformed(ActionEvent e) {
//                            textField.requestFocus();
//                        }
//                    });
//                    refocusTimer.setRepeats(false);
//                    refocusTimer.start();
//                }
//            });
//            clearTimer.setRepeats(false);
//
//            textField.getDocument().addDocumentListener(new DocumentListener() {
//                @Override
//                public void insertUpdate(DocumentEvent e) {
//                    processTextChange();
//                }
//
//                @Override
//                public void removeUpdate(DocumentEvent e) {
//                }
//
//                @Override
//                public void changedUpdate(DocumentEvent e) {
//                    processTextChange();
//                }
//
//                private void processTextChange() {
//                    clearTimer.restart();
//
//                    String voiceInput = textField.getText();
////                    System.out.println("Current voice input: " + voiceInput);
//
////                    if (voiceInput.equalsIgnoreCase("previous desktop")) {
////                        try {
////                            Robot robot = new Robot();
////
////                            // Press Ctrl+Windows+Left Arrow
////                            robot.keyPress(KeyEvent.VK_CONTROL);
////                            robot.keyPress(KeyEvent.VK_WINDOWS);
////                            robot.keyPress(KeyEvent.VK_LEFT);
////
////                            // Release Ctrl+Windows+Left Arrow
////                            robot.keyRelease(KeyEvent.VK_LEFT);
////                            robot.keyRelease(KeyEvent.VK_WINDOWS);
////                            robot.keyRelease(KeyEvent.VK_CONTROL);
////
////                        } catch (AWTException e) {
////                            e.printStackTrace();
////                        }
////                    }
////                    pressKeysFromVoiceInput(voiceInput);
////                    Timer refocusTimer = new Timer(500, new ActionListener() {
////                        @Override
////                        public void actionPerformed(ActionEvent e) {
////                            textField.requestFocus();
////                        }
////                    });
////                    refocusTimer.setRepeats(false);
////                    refocusTimer.start();
//                }
//            });
//
//            textField.addFocusListener(new FocusAdapter() {
//                @Override
//                public void focusLost(FocusEvent e) {
//                    Timer refocusTimer = new Timer(2000, new ActionListener() {
//                        @Override
//                        public void actionPerformed(ActionEvent e) {
//                            textField.requestFocus();
//                        }
//                    });
//                    refocusTimer.setRepeats(false);
//                    refocusTimer.start();
//                }
//            });
//
//            frame.setVisible(true);
//        });
//    }
//
//    private static Map<String, Integer> createKeyMap() {
//        Map<String, Integer> keyMap = new HashMap<>();
//        keyMap.put("control", KeyEvent.VK_CONTROL);
//        keyMap.put("windows", KeyEvent.VK_WINDOWS);
//        keyMap.put("left", KeyEvent.VK_LEFT);
//        keyMap.put("right", KeyEvent.VK_RIGHT);
//        keyMap.put("up", KeyEvent.VK_UP);
//        keyMap.put("down", KeyEvent.VK_DOWN);        keyMap.put("enter", KeyEvent.VK_ENTER);
//        keyMap.put("tab", KeyEvent.VK_TAB);
//        keyMap.put("capslock", KeyEvent.VK_CAPS_LOCK);
//        keyMap.put("shift", KeyEvent.VK_SHIFT);
//        keyMap.put("alt", KeyEvent.VK_ALT);
//        keyMap.put("backspace", KeyEvent.VK_BACK_SPACE);
//        keyMap.put("esc", KeyEvent.VK_ESCAPE);
//        keyMap.put("space", KeyEvent.VK_SPACE);
//
//        // Add mappings for letters
//        for (char letter = 'a'; letter <= 'z'; letter++) {
//            keyMap.put(Character.toString(letter), KeyEvent.getExtendedKeyCodeForChar(letter));
//        }
//
//        // Add mappings for numbers
//        for (char number = '0'; number <= '9'; number++) {
//            keyMap.put(Character.toString(number), KeyEvent.getExtendedKeyCodeForChar(number));
//        }
//
//        // Add mappings for special characters
//        String specialChars = "`~!@#$%^&*()-_=+[{]}\\|;:'\",<.>/?";
//        for (char specialChar : specialChars.toCharArray()) {
//            keyMap.put(Character.toString(specialChar), KeyEvent.getExtendedKeyCodeForChar(specialChar));
//        }
//
//        return keyMap;
//    }
//
//    private static void pressKeysFromVoiceInput(String voiceInput) {
//        if (voiceInput.startsWith("shortcut ")) {
//            System.out.println("Current voice input: " + voiceInput);
//            String keysString = voiceInput.substring("shortcut ".length());
//            pressKeys(keysString);
//        } else if (voiceInput.startsWith("insert ")) {
//            System.out.println("Current voice input: " + voiceInput);
//            String textToInsert = voiceInput.substring("insert ".length());
//            insertTextFromVoiceInput(textToInsert);
//        }
//
//    }
//
//    private static void insertTextFromVoiceInput(String textToInsert) {
//        try {
//            Robot robot = new Robot();
//
//            // Perform Alt+Tab
//            robot.keyPress(KeyEvent.VK_ALT);
//            robot.keyPress(KeyEvent.VK_TAB);
//            robot.keyRelease(KeyEvent.VK_TAB);
//            robot.keyRelease(KeyEvent.VK_ALT);
//
//            // Delay for a short interval to allow Alt+Tab to take effect
//            robot.delay(500);
//
//            // Paste the text
//            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(textToInsert), null);
//            robot.keyPress(KeyEvent.VK_CONTROL);
//            robot.keyPress(KeyEvent.VK_V);
//            robot.keyRelease(KeyEvent.VK_V);
//            robot.keyRelease(KeyEvent.VK_CONTROL);
//
//        } catch (AWTException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void pressKeys(String keysString) {
//        String[] keys = keysString.toLowerCase().split(" ");
//        try {
//            Robot robot = new Robot();
//            // Press Alt+Tab
//            robot.keyPress(KeyEvent.VK_ALT);
//            robot.keyPress(KeyEvent.VK_TAB);
//
//            // Release Alt+Tab
//            robot.keyRelease(KeyEvent.VK_TAB);
//            robot.keyRelease(KeyEvent.VK_ALT);
//
//            // Delay for a short interval to allow Alt+Tab to take effect
//            robot.delay(500);
//
//            for (String key : keys) {
//                Integer keyCode = KEY_MAP.get(key);
//                if (keyCode != null) {
//                    robot.keyPress(keyCode);
//                }
//            }
//
//            for (String key : keys) {
//                Integer keyCode = KEY_MAP.get(key);
//                if (keyCode != null) {
//                    robot.keyRelease(keyCode);
//                }
//            }
//
//        } catch (AWTException e) {
//            e.printStackTrace();
//        }
//    }
//
//}
