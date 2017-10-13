package com.athena.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Tommy on 2017/10/12.
 */
public class NameUtil {
    /**
     * To string.
     *
     * @param input the input
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


    /**
     * convert the name of input to camel-style
     *
     * @param input
     * @param isCapitalize if true then capitalize the return value
     * @return
     */
    public static String toCamel(String input, boolean isCapitalize) {
        if (input == null) {
            return null;
        }
        String[] parts = input.split("_");
        StringBuffer result = new StringBuffer();
        for (String part : parts) {
            result.append(StringUtils.capitalize(part));
        }
        if (!isCapitalize) {
            return StringUtils.uncapitalize(result.toString());
        }
        return result.toString();
    }

    /**
     * convert the name of input to camel-style
     *
     * @param input
     * @return
     */
    public static String toCamel(String input) {
        return NameUtil.toCamel(input, false);
    }
}
