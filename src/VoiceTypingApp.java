import com.github.kwhat.jnativehook.GlobalScreen;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VoiceTypingApp {
    private static final Map<String, Integer> KEY_MAP = createKeyMap();
    public static volatile boolean commandsRunning = false;
    private static volatile KeyboardListener keyboardMouseListener;


    public static void main(String[] args) {
        KeyboardListener.register();
        keyboardMouseListener = new KeyboardListener(VoiceTypingApp::runQuery);
        GlobalScreen.addNativeKeyListener(keyboardMouseListener);
    }


    public static void resetLilly(int t) {
        try {
            Robot robot = new Robot();
            robot.delay(500);

            for (int i = 0; i < t; i++) {
                commandsRunning = true;
                robot.keyPress(KeyEvent.VK_WINDOWS);
                robot.keyPress(KeyEvent.VK_T);
                robot.keyRelease(KeyEvent.VK_T);
                robot.keyRelease(KeyEvent.VK_WINDOWS);
                robot.delay(700);
            }
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }


    private static void runQuery(String query) {
        pressKeysFromVoiceInput(query);
    }

    private static Map<String, Integer> createKeyMap() {
        Map<String, Integer> keyMap = new HashMap<>();
        keyMap.put("control", KeyEvent.VK_CONTROL);
        keyMap.put("windows", KeyEvent.VK_WINDOWS);
        keyMap.put("left", KeyEvent.VK_LEFT);
        keyMap.put("right", KeyEvent.VK_RIGHT);
        keyMap.put("up", KeyEvent.VK_UP);
        keyMap.put("down", KeyEvent.VK_DOWN);
        keyMap.put("enter", KeyEvent.VK_ENTER);
        keyMap.put("tab", KeyEvent.VK_TAB);
        keyMap.put("capslock", KeyEvent.VK_CAPS_LOCK);
        keyMap.put("shift", KeyEvent.VK_SHIFT);
        keyMap.put("alt", KeyEvent.VK_ALT);
        keyMap.put("backspace", KeyEvent.VK_BACK_SPACE);
        keyMap.put("escape", KeyEvent.VK_ESCAPE);
        keyMap.put("space", KeyEvent.VK_SPACE);
        keyMap.put("home", KeyEvent.VK_HOME);
        keyMap.put("end", KeyEvent.VK_END);
        keyMap.put("delete", KeyEvent.VK_DELETE);
        keyMap.put("pagedown", KeyEvent.VK_PAGE_DOWN);
        keyMap.put("pageup", KeyEvent.VK_PAGE_UP);

        // Add mappings for function keys
        for (int i = 1; i <= 12; i++) {
            keyMap.put("f" + i, KeyEvent.VK_F1 + i - 1);
        }

        // Add mappings for letters
        for (char letter = 'a'; letter <= 'z'; letter++) {
            keyMap.put(Character.toString(letter), KeyEvent.getExtendedKeyCodeForChar(letter));
        }


        // Add mappings for numbers
        for (char number = '0'; number <= '9'; number++) {
            keyMap.put(Character.toString(number), KeyEvent.getExtendedKeyCodeForChar(number));
        }

        // Add mappings for numbers written in words
        keyMap.put("zero", KeyEvent.VK_0);
        keyMap.put("one", KeyEvent.VK_1);
        keyMap.put("two", KeyEvent.VK_2);
        keyMap.put("three", KeyEvent.VK_3);
        keyMap.put("four", KeyEvent.VK_4);
        keyMap.put("five", KeyEvent.VK_5);
        keyMap.put("six", KeyEvent.VK_6);
        keyMap.put("seven", KeyEvent.VK_7);
        keyMap.put("eight", KeyEvent.VK_8);
        keyMap.put("nine", KeyEvent.VK_9);

        // Add mappings for special characters
        String specialChars = "`~!@#$%^&*()-_=+[{]}\\|;:'\",<.>/?";
        for (char specialChar : specialChars.toCharArray()) {
            keyMap.put(Character.toString(specialChar), KeyEvent.getExtendedKeyCodeForChar(specialChar));
        }

        return keyMap;
    }

    private static void pressBackspace(int times) {
        try {
            Robot robot = new Robot();
            for (int i = 0; i < times; i++) {
                robot.keyPress(KeyEvent.VK_BACK_SPACE);
                robot.keyRelease(KeyEvent.VK_BACK_SPACE);
                robot.delay(20);  // adjust delay as needed
            }
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }


    private static void pressKeysFromVoiceInput(String voiceInput) {
        System.out.println("Current voice input " + voiceInput);
        // Split the voice input based on "shortcut" and "insert"
        List<String> commands = new ArrayList<>();
        Matcher matcher = Pattern.compile("((shortcut|insert|command).+?)(?=shortcut|insert|command|$)").matcher(voiceInput);
        while (matcher.find()) {
            commands.add(matcher.group(1).trim());
        }

        commandsRunning = true;
        int totalCommandLength = 0;
        for (String command : commands) {
            totalCommandLength += command.length();
        }
        totalCommandLength += commands.size() - 1;
        pressBackspace(totalCommandLength);


        for (String command : commands) {
            if (command.startsWith("shortcut ")) {
                String keysString = command.substring("shortcut ".length());
                pressKeys(keysString);
            } else if (command.startsWith("insert ")) {
                String textToInsert = command.substring("insert ".length());
                insertTextFromVoiceInput(textToInsert);
            } else if (command.startsWith("command ")) {
                System.out.println("Current command: " + command);
                String keysString = command.substring("command ".length());
                pressKeys(keysString);
            }
        }
    }

    private static void insertTextFromVoiceInput(String textToInsert) {
        try {
            commandsRunning = true;
            Robot robot = new Robot();

            for (char character : textToInsert.toCharArray()) {
                int keyCode = KeyEvent.getExtendedKeyCodeForChar(character);
                boolean shiftRequired = Character.isUpperCase(character) || isShiftRequiredForSpecialChar(character);

                if (shiftRequired) {
                    robot.keyPress(KeyEvent.VK_SHIFT);
                }

                robot.keyPress(keyCode);
                robot.keyRelease(keyCode);

                if (shiftRequired) {
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                }
            }
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private static boolean isShiftRequiredForSpecialChar(char character) {
        String specialCharsRequiringShift = "~!@#$%^&*()_+{}|:\"<>?";
        return specialCharsRequiringShift.indexOf(character) != -1;
    }






    private static void pressKeys(String keysString) {
        String[] keyCommands = keysString.toLowerCase().split(" ");
        commandsRunning = true;
        try {
            Robot robot = new Robot();
            int repeatCount = 1;
            List<Integer> unreleasedKeys = new ArrayList<>();
            int delay = 20; // Delay in milliseconds

            for (int i = 0; i < keyCommands.length; i++) {
                String key = keyCommands[i];
                Integer keyCode = KEY_MAP.get(key);

                // Check if the key after the current key is "times"
                if (i + 1 < keyCommands.length && keyCommands[i + 1].equals("times")) {
                    try {
                        repeatCount = Integer.parseInt(keyCommands[i + 2]);
                        i += 2; // Skip the next two keys ("times" and the number)
                    } catch (ArrayIndexOutOfBoundsException ignored) {
                        i+=1;
                    } catch (NumberFormatException e) {
                        String keyText = KeyEvent.getKeyText(KEY_MAP.get(keyCommands[i + 2]));
                        repeatCount = Integer.parseInt(keyText);
                        i += 2;
                    }
                }

                if (keyCode != null) {
                    for (int j = 0; j < repeatCount; j++) {
                        robot.keyPress(keyCode);
                        robot.delay(delay);

                        if (repeatCount > 1) {
                            robot.keyRelease(keyCode);
                            robot.delay(delay);
                        } else {
                            unreleasedKeys.add(keyCode);
                        }
                    }
                    repeatCount = 1; // Reset the repeatCount
                }
            }

            // Release unreleased keys
            for (Integer unreleasedKey : unreleasedKeys) {
                robot.keyRelease(unreleasedKey);
                robot.delay(delay);
            }

        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

}

