package com.athena.util;

/**
 * Created by Tommy on 2017/10/12.
 */
public class NameUtil {
    /**
     * @param input
     * @return convert a camel-style name to _style
     */
    public static String to_(String input) {
        if (input == null)
            return null;
        if (input.toLowerCase().equals(input)) {
            // if input is _ style
            return input;
        }
        char[] chars = input.toCharArray();
        StringBuffer stringBuffer = new StringBuffer();
        for (char character : chars) {
            if (Character.isUpperCase(character)) {
                stringBuffer.append('_');
                stringBuffer.append(Character.toLowerCase(character));
            } else {
                stringBuffer.append(character);
            }
        }
        //if the first is _ then delete
        if (stringBuffer.charAt(0) == '_') {
            return stringBuffer.substring(1);
        } else {
            return stringBuffer.toString();
        }
    }
}
