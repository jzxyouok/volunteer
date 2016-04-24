package com.comiyun.core.util;

import org.springframework.util.Assert;

import java.security.SecureRandom;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author ydwcn
 * @ClassName: StringUtils
 * @date 2014-6-18 下午1:34:21
 */
public class StringUtils {
    protected static final SecureRandom secureRandom = new SecureRandom();
    public static final String FOLDER_SEPARATOR = "/";
    public static final String WINDOWS_FOLDER_SEPARATOR = "\\";
    public static final String TOP_PATH = "..";
    public static final String CURRENT_PATH = ".";

    public static String replaceString(String source, Map<String, Object> args)
            throws Exception {
        int startIndex = 0;
        int openIndex = source.indexOf("${", startIndex);
        if (openIndex == -1) {
            return source;
        }

        int closeIndex = source.indexOf('}', startIndex);
        if ((closeIndex == -1) || (openIndex > closeIndex)) {
            return source;
        }

        StringBuffer sb = new StringBuffer();
        sb.append(source.substring(startIndex, openIndex));
        while (true) {
            String key = source.substring(openIndex + 2, closeIndex);
            Object val = args.get(key);
            if (val != null)
                sb.append(val);
            else {
                throw new Exception("Property[" + key + "] not found!");
            }

            startIndex = closeIndex + 1;
            openIndex = source.indexOf("${", startIndex);
            if (openIndex == -1) {
                sb.append(source.substring(startIndex));
                break;
            }

            closeIndex = source.indexOf('}', startIndex);
            if ((closeIndex == -1) || (openIndex > closeIndex)) {
                sb.append(source.substring(startIndex));
                break;
            }
            sb.append(source.substring(startIndex, openIndex));
        }
        return sb.toString();
    }

    public static String replaceMacro(String source, String macro, String value) {
        int startIndex = 0;
        int openIndex = source.indexOf("${", startIndex);
        if (openIndex == -1) {
            return source;
        }

        int closeIndex = source.indexOf('}', startIndex);
        if ((closeIndex == -1) || (openIndex > closeIndex)) {
            return source;
        }

        StringBuffer sb = new StringBuffer();
        sb.append(source.substring(startIndex, openIndex));
        while (true) {
            String key = source.substring(openIndex + 2, closeIndex);
            if (key.equals(macro))
                sb.append(value);
            else {
                sb.append("${").append(key).append("}");
            }
            startIndex = closeIndex + 1;
            openIndex = source.indexOf("${", startIndex);
            if (openIndex == -1) {
                sb.append(source.substring(startIndex));
                break;
            }

            closeIndex = source.indexOf('}', startIndex);
            if ((closeIndex == -1) || (openIndex > closeIndex)) {
                sb.append(source.substring(startIndex));
                break;
            }
            sb.append(source.substring(startIndex, openIndex));
        }
        return sb.toString();
    }

    public static boolean hasLength(String str) {
        return (str != null) && (str.length() > 0);
    }

    public static boolean hasText(String str) {
        int strLen;
        if ((str == null) || ((strLen = str.length()) == 0))
            return false;
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static String trimLeadingWhitespace(String str) {
        if (str.length() == 0) {
            return str;
        }
        StringBuffer buf = new StringBuffer(str);
        while ((buf.length() > 0) && (Character.isWhitespace(buf.charAt(0)))) {
            buf.deleteCharAt(0);
        }
        return buf.toString();
    }

    public static String trimTrailingWhitespace(String str) {
        if (str.length() == 0) {
            return str;
        }
        StringBuffer buf = new StringBuffer(str);
        while ((buf.length() > 0)
                && (Character.isWhitespace(buf.charAt(buf.length() - 1)))) {
            buf.deleteCharAt(buf.length() - 1);
        }
        return buf.toString();
    }

    public static boolean startsWithIgnoreCase(String str, String prefix) {
        if ((str == null) || (prefix == null)) {
            return false;
        }
        if (str.startsWith(prefix)) {
            return true;
        }
        if (str.length() < prefix.length()) {
            return false;
        }
        String lcStr = str.substring(0, prefix.length()).toLowerCase();
        String lcPrefix = prefix.toLowerCase();
        return lcStr.equals(lcPrefix);
    }

    public static int countOccurrencesOf(String str, String sub) {
        if ((str == null) || (sub == null) || (str.length() == 0)
                || (sub.length() == 0)) {
            return 0;
        }
        int count = 0;
        int pos = 0;
        int idx = 0;
        while ((idx = str.indexOf(sub, pos)) != -1) {
            count++;
            pos = idx + sub.length();
        }
        return count;
    }

    public static String replace(String inString, String oldPattern,
                                 String newPattern) {
        if (inString == null) {
            return null;
        }
        if ((oldPattern == null) || (newPattern == null)) {
            return inString;
        }

        StringBuffer sbuf = new StringBuffer();

        int pos = 0;
        int index = inString.indexOf(oldPattern);

        int patLen = oldPattern.length();
        while (index >= 0) {
            sbuf.append(inString.substring(pos, index));
            sbuf.append(newPattern);
            pos = index + patLen;
            index = inString.indexOf(oldPattern, pos);
        }
        sbuf.append(inString.substring(pos));

        return sbuf.toString();
    }

    public static String delete(String inString, String pattern) {
        return replace(inString, pattern, "");
    }

    public static String deleteAny(String inString, String charsToDelete) {
        if ((inString == null) || (charsToDelete == null)) {
            return inString;
        }
        StringBuffer out = new StringBuffer();
        for (int i = 0; i < inString.length(); i++) {
            char c = inString.charAt(i);
            if (charsToDelete.indexOf(c) == -1) {
                out.append(c);
            }
        }
        return out.toString();
    }

    public static String unqualify(String qualifiedName) {
        return unqualify(qualifiedName, '.');
    }

    public static String unqualify(String qualifiedName, char separator) {
        return qualifiedName
                .substring(qualifiedName.lastIndexOf(separator) + 1);
    }

    public static String capitalize(String str) {
        return changeFirstCharacterCase(str, true);
    }

    public static String uncapitalize(String str) {
        return changeFirstCharacterCase(str, false);
    }

    private static String changeFirstCharacterCase(String str,
                                                   boolean capitalize) {
        if ((str == null) || (str.length() == 0)) {
            return str;
        }
        StringBuffer buf = new StringBuffer(str.length());
        if (capitalize)
            buf.append(Character.toUpperCase(str.charAt(0)));
        else {
            buf.append(Character.toLowerCase(str.charAt(0)));
        }
        buf.append(str.substring(1));
        return buf.toString();
    }

    public static String getFilename(String path) {
        int separatorIndex = path.lastIndexOf("/");
        return separatorIndex != -1 ? path.substring(separatorIndex + 1) : path;
    }

    public static String applyRelativePath(String path, String relativePath) {
        int separatorIndex = path.lastIndexOf("/");
        if (separatorIndex != -1) {
            String newPath = path.substring(0, separatorIndex);
            if (!relativePath.startsWith("/")) {
                newPath = newPath + "/";
            }
            return newPath + relativePath;
        }
        return relativePath;
    }

    public static String cleanPath(String path) {
        String pathToUse = replace(path, "\\", "/");
        String[] pathArray = delimitedListToStringArray(pathToUse, "/");
        List<String> pathElements = new LinkedList<String>();
        int tops = 0;
        for (int i = pathArray.length - 1; i >= 0; i--) {
            if (".".equals(pathArray[i]))
                continue;
            if ("..".equals(pathArray[i])) {
                tops++;
            } else if (tops > 0)
                tops--;
            else {
                pathElements.add(0, pathArray[i]);
            }
        }

        return collectionToDelimitedString(pathElements, "/");
    }

    public static boolean pathEquals(String path1, String path2) {
        return cleanPath(path1).equals(cleanPath(path2));
    }

    public static Locale parseLocaleString(String localeString) {
        String[] parts = tokenizeToStringArray(localeString, "_ ", false, false);
        String language = parts.length > 0 ? parts[0] : "";
        String country = parts.length > 1 ? parts[1] : "";
        String variant = parts.length > 2 ? parts[2] : "";
        return language.length() > 0 ? new Locale(language, country, variant)
                : null;
    }

    public static String[] addStringToArray(String[] arr, String str) {
        String[] newArr = new String[arr.length + 1];
        System.arraycopy(arr, 0, newArr, 0, arr.length);
        newArr[arr.length] = str;
        return newArr;
    }

    public static String[] addStringArrToArray(String[] arr, String[] strs) {
        String[] resultArr = arr;
        if ((strs != null) && (strs.length > 0)) {
            for (int i = 0; i < strs.length; i++) {
                resultArr = addStringToArray(resultArr, strs[i]);
            }
        }
        return resultArr;
    }

    public static String[] sortStringArray(String[] source) {
        if (source == null) {
            return new String[0];
        }
        Arrays.sort(source);
        return source;
    }

    public static String[] split(String toSplit, String delimiter) {
        Assert.hasLength(toSplit, "Cannot split a null or empty string");
        Assert.hasLength(delimiter,
                "Cannot use a null or empty delimiter to split a string");
        int offset = toSplit.indexOf(delimiter);
        if (offset < 0) {
            return null;
        }
        String beforeDelimiter = toSplit.substring(0, offset);
        String afterDelimiter = toSplit.substring(offset + delimiter.length());
        return new String[]{beforeDelimiter, afterDelimiter};
    }

    public static Properties splitArrayElementsIntoProperties(String[] array,
                                                              String delimiter) {
        return splitArrayElementsIntoProperties(array, delimiter, null);
    }

    public static Properties splitArrayElementsIntoProperties(String[] array,
                                                              String delimiter, String charsToDelete) {
        if ((array == null) || (array.length == 0)) {
            return null;
        }

        Properties result = new Properties();
        for (int i = 0; i < array.length; i++) {
            String element = array[i];
            if (charsToDelete != null) {
                element = deleteAny(array[i], charsToDelete);
            }
            String[] splittedElement = split(element, delimiter);
            if (splittedElement == null) {
                continue;
            }
            result.setProperty(splittedElement[0].trim(),
                    splittedElement[1].trim());
        }
        return result;
    }

    public static String[] tokenizeToStringArray(String str, String delimiters) {
        return tokenizeToStringArray(str, delimiters, true, true);
    }

    public static String[] tokenizeToStringArray(String str, String delimiters,
                                                 boolean trimTokens, boolean ignoreEmptyTokens) {
        StringTokenizer st = new StringTokenizer(str, delimiters);
        List<String> tokens = new ArrayList<String>();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (trimTokens) {
                token = token.trim();
            }
            if ((!ignoreEmptyTokens) || (token.length() > 0)) {
                tokens.add(token);
            }
        }
        return (String[]) tokens.toArray(new String[tokens.size()]);
    }

    public static String[] delimitedListToStringArray(String str,
                                                      String delimiter) {
        if (str == null) {
            return new String[0];
        }
        if (delimiter == null) {
            return new String[]{str};
        }

        List<String> result = new ArrayList<String>();
        int pos = 0;
        int delPos = 0;
        while ((delPos = str.indexOf(delimiter, pos)) != -1) {
            result.add(str.substring(pos, delPos));
            pos = delPos + delimiter.length();
        }
        if ((str.length() > 0) && (pos <= str.length())) {
            result.add(str.substring(pos));
        }

        return (String[]) result.toArray(new String[result.size()]);
    }

    public static String[] commaDelimitedListToStringArray(String str) {
        return delimitedListToStringArray(str, ",");
    }

    public static Set<String> commaDelimitedListToSet(String str) {
        Set<String> set = new TreeSet<String>();
        String[] tokens = commaDelimitedListToStringArray(str);
        for (int i = 0; i < tokens.length; i++) {
            set.add(tokens[i]);
        }
        return set;
    }

    public static String arrayToDelimitedString(Object[] arr, String delim) {
        if (arr == null) {
            return "";
        }

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) {
                sb.append(delim);
            }
            sb.append(arr[i]);
        }
        return sb.toString();
    }

    public static String collectionToDelimitedString(Collection<String> coll,
                                                     String delim, String prefix, String suffix) {
        if (coll == null) {
            return "";
        }

        StringBuffer sb = new StringBuffer();
        Iterator<String> it = coll.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (i > 0) {
                sb.append(delim);
            }
            sb.append(prefix).append(it.next()).append(suffix);
            i++;
        }
        return sb.toString();
    }

    public static String collectionToDelimitedString(Collection<String> coll,
                                                     String delim) {
        return collectionToDelimitedString(coll, delim, "", "");
    }

    public static String arrayToCommaDelimitedString(Object[] arr) {
        return arrayToDelimitedString(arr, ",");
    }

    public static String collectionToCommaDelimitedString(Collection<String> coll) {
        return collectionToDelimitedString(coll, ",");
    }

    public static String getRandomString(int len) {
        byte[] bytes = new byte[len];
        secureRandom.nextBytes(bytes);
        String randomString = new String(bytes);
        return randomString;
    }

    public static boolean isCharacterOrNum(String value) {
        return Pattern.matches("[a-zA-Z_0-9]*", value);
    }

    public static boolean isNum(String value) {
        if (value == null)
            return false;
        try {
            Integer.parseInt(value);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public static boolean isUTF8(String str) {
        if (str == null)
            return true;
        for (int i = 0; i < str.length(); i++) {
            char aChar = str.charAt(i);
            if (Character.getType(aChar) == 16) {
                continue;
            }
            Character.UnicodeBlock ub = Character.UnicodeBlock.of(aChar);
            if ((!ub.equals(Character.UnicodeBlock.BASIC_LATIN))
                    && (!ub.equals(Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 处理字符串为第一个字符大写
     *
     * @param @param  newStr
     * @param @return
     * @return String
     * @throws
     * @Title: makeFirstLetterUpperCase
     */
    public static String makeFirstLetterUpperCase(String newStr) {
        if (newStr.length() == 0) {
            return newStr;
        }
        char[] oneChar = new char[1];
        oneChar[0] = newStr.charAt(0);
        String firstChar = new String(oneChar);
        return new StringBuilder().append(firstChar.toUpperCase())
                .append(newStr.substring(1)).toString();
    }

}
