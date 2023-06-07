//import com.github.kwhat.jnativehook.GlobalScreen;
//
//import javax.swing.*;
//import javax.swing.event.DocumentEvent;
//import javax.swing.event.DocumentListener;
//import java.awt.*;
//import java.awt.event.*;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class VoiceTypingApp2 {
//    private static final Map<String, Integer> KEY_MAP = createKeyMap();
//    private static volatile boolean commandsRunning = false;
//
//    private static volatile MouseListener listener;
//
//    private static volatile boolean isWorking = false;
//
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            JFrame frame = new JFrame("Voice Typing App");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//            // Set the position and size of the window
//            int x = 697; // x-coordinate of the top-left corner
//            int y = 30; // y-coordinate of the top-left corner
//            int width = 300; // width of the window
//            int height = 90; // height of the window
//            frame.setBounds(x, y, width, height);
//
//            JPanel panel = new JPanel();
//            frame.add(panel);
//            panel.setLayout(new BorderLayout());
//
//            JTextField textField = new JTextField();
//            panel.add(textField, BorderLayout.CENTER);
//
//
//            Timer clearTimer = new Timer(2000, new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    String voiceInput = textField.getText().toLowerCase();
//
//                    if (voiceInput.contains("shortcut") || voiceInput.contains("insert")) {
//                        pressKeysFromVoiceInput(voiceInput, textField);
//                        Timer refocusTimer = new Timer(700, new ActionListener() {
//                            @Override
//                            public void actionPerformed(ActionEvent e) {
//                                textField.requestFocus();
//                                pressWindowsHShortcut(2);
//                            }
//                        });
//                        refocusTimer.setRepeats(false);
//                        refocusTimer.start();
//                    }
//
//                    textField.setText("");
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
//                    processTextChange();
//                }
//
//                @Override
//                public void changedUpdate(DocumentEvent e) {
//                    processTextChange();
//                }
//
//                private void processTextChange() {
//                    clearTimer.restart();
//                }
//            });
//
//            textField.addFocusListener(new FocusAdapter() {
//                @Override
//                public void focusLost(FocusEvent e) {
//                    if (!commandsRunning) {
//                        Timer refocusTimer = new Timer(2000, new ActionListener() {
//                            @Override
//                            public void actionPerformed(ActionEvent e) {
//                                textField.requestFocus();
//                            }
//                        });
//                        refocusTimer.setRepeats(false);
//                        refocusTimer.start();
//                    }
//                }
//            });
//
//            frame.setVisible(true);
//        });
//
//        MouseListener.register();
//
//        listener = new MouseListener(e -> {
//            isWorking = true;
//
//        });
//
//        GlobalScreen.addNativeMouseListener(listener);
//        GlobalScreen.addNativeMouseMotionListener(listener);
//
//    }
//
//    private static void pressWindowsHShortcut(int t) {
//        try {
//            Robot robot = new Robot();
//            robot.delay(1000);
//            for (int i = 0; i < t; i++) {
//                robot.keyPress(KeyEvent.VK_WINDOWS);
//                robot.keyPress(KeyEvent.VK_H);
//                robot.keyRelease(KeyEvent.VK_H);
//                robot.keyRelease(KeyEvent.VK_WINDOWS);
//                robot.delay(300);
//            }
//        } catch (AWTException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    private static Map<String, Integer> createKeyMap() {
//        Map<String, Integer> keyMap = new HashMap<>();
//        keyMap.put("control", KeyEvent.VK_CONTROL);
//        keyMap.put("windows", KeyEvent.VK_WINDOWS);
//        keyMap.put("left", KeyEvent.VK_LEFT);
//        keyMap.put("right", KeyEvent.VK_RIGHT);
//        keyMap.put("up", KeyEvent.VK_UP);
//        keyMap.put("down", KeyEvent.VK_DOWN);
//        keyMap.put("enter", KeyEvent.VK_ENTER);
//        keyMap.put("tab", KeyEvent.VK_TAB);
//        keyMap.put("capslock", KeyEvent.VK_CAPS_LOCK);
//        keyMap.put("shift", KeyEvent.VK_SHIFT);
//        keyMap.put("alt", KeyEvent.VK_ALT);
//        keyMap.put("backspace", KeyEvent.VK_BACK_SPACE);
//        keyMap.put("esc", KeyEvent.VK_ESCAPE);
//        keyMap.put("space", KeyEvent.VK_SPACE);
//        keyMap.put("home", KeyEvent.VK_HOME);
//        keyMap.put("end", KeyEvent.VK_END);
//        keyMap.put("delete", KeyEvent.VK_DELETE);
//        keyMap.put("pagedown", KeyEvent.VK_PAGE_DOWN);
//        keyMap.put("pageup", KeyEvent.VK_PAGE_UP);
//
//        // Add mappings for function keys
//        for (int i = 1; i <= 12; i++) {
//            keyMap.put("f" + i, KeyEvent.VK_F1 + i - 1);
//        }
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
//        // Add mappings for numbers written in words
//        keyMap.put("zero", KeyEvent.VK_0);
//        keyMap.put("one", KeyEvent.VK_1);
//        keyMap.put("two", KeyEvent.VK_2);
//        keyMap.put("three", KeyEvent.VK_3);
//        keyMap.put("four", KeyEvent.VK_4);
//        keyMap.put("five", KeyEvent.VK_5);
//        keyMap.put("six", KeyEvent.VK_6);
//        keyMap.put("seven", KeyEvent.VK_7);
//        keyMap.put("eight", KeyEvent.VK_8);
//        keyMap.put("nine", KeyEvent.VK_9);
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
//    private static void pressKeysFromVoiceInput(String voiceInput, JTextField textField) {
//        commandsRunning = true;
//
//        // Perform Alt+Tab
//        try {
//            Robot robot = new Robot();
//            robot.keyPress(KeyEvent.VK_ALT);
//            robot.keyPress(KeyEvent.VK_TAB);
//            robot.keyRelease(KeyEvent.VK_TAB);
//            robot.keyRelease(KeyEvent.VK_ALT);
//
//
//            // Delay for a short interval to allow Alt+Tab to take effect
//            robot.delay(500);
//        } catch (AWTException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("Current voice input " +voiceInput);
//
//
//        // Split the voice input based on "shortcut" and "insert"
//        List<String> commands = new ArrayList<>();
//        Matcher matcher = Pattern.compile("((shortcut|insert).+?)(?=shortcut|insert|$)").matcher(voiceInput);
//        while (matcher.find()) {
//            commands.add(matcher.group(1).trim());
//        }
//
//
//        for (String command : commands) {
//            System.out.println("Current command: " + command);
//            if (command.startsWith("shortcut ")) {
////                System.out.println("Current command: " + command);
//                String keysString = command.substring("shortcut ".length());
//                pressKeys(keysString);
//            } else if (command.startsWith("insert ")) {
////                System.out.println("Current command: " + command);
//                String textToInsert = command.substring("insert ".length());
//                insertTextFromVoiceInput(textToInsert);
//            }
//        }
//
//
//
//        commandsRunning = false;
//    }
//
//    private static void insertTextFromVoiceInput(String textToInsert) {
//        try {
//            Robot robot = new Robot();
//
//            for (char character : textToInsert.toCharArray()) {
//                int keyCode = KeyEvent.getExtendedKeyCodeForChar(character);
//                boolean shiftRequired = Character.isUpperCase(character) || isShiftRequiredForSpecialChar(character);
//
//                if (shiftRequired) {
//                    robot.keyPress(KeyEvent.VK_SHIFT);
//                }
//
//                robot.keyPress(keyCode);
//                robot.keyRelease(keyCode);
//
//                if (shiftRequired) {
//                    robot.keyRelease(KeyEvent.VK_SHIFT);
//                }
//
////                robot.delay(100); // Delay between key presses
//            }
//        } catch (AWTException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static boolean isShiftRequiredForSpecialChar(char character) {
//        String specialCharsRequiringShift = "~!@#$%^&*()_+{}|:\"<>?";
//        return specialCharsRequiringShift.indexOf(character) != -1;
//    }
//
//
//
//
//
//
//    private static void pressKeys(String keysString) {
//        String[] keyCommands = keysString.toLowerCase().split(" ");
//        try {
//            Robot robot = new Robot();
//            int repeatCount = 1;
//            List<Integer> unreleasedKeys = new ArrayList<>();
//            int delay = 100; // Delay in milliseconds
//
//            for (int i = 0; i < keyCommands.length; i++) {
//                String key = keyCommands[i];
//                Integer keyCode = KEY_MAP.get(key);
//
//                // Check if the key after the current key is "times"
//                if (i + 1 < keyCommands.length && keyCommands[i + 1].equals("times")) {
//                    try {
//                        repeatCount = Integer.parseInt(keyCommands[i + 2]);
//                        i += 2; // Skip the next two keys ("times" and the number)
//                    } catch (ArrayIndexOutOfBoundsException ignored) {
//                        i+=1;
//                    } catch (NumberFormatException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                if (keyCode != null) {
//                    for (int j = 0; j < repeatCount; j++) {
//                        robot.keyPress(keyCode);
//                        System.out.println("this"+keyCode);
//                        robot.delay(delay);
//
//                        if (repeatCount > 1) {
//                            robot.keyRelease(keyCode);
//                            robot.delay(delay);
//                        } else {
//                            unreleasedKeys.add(keyCode);
//                        }
//                    }
//                    repeatCount = 1; // Reset the repeatCount
//                }
//            }
//
//            // Release unreleased keys
//            for (Integer unreleasedKey : unreleasedKeys) {
//                robot.keyRelease(unreleasedKey);
//                robot.delay(delay);
//            }
//        } catch (AWTException e) {
//            e.printStackTrace();
//        }
//    }
//}
//
