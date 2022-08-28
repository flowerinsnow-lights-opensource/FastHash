package online.flowerinsnow.fasthash.util;

import java.util.regex.Pattern;

public final class RegexUtils {
    public static Pattern parseWildcard(String pattern) {
        StringBuilder sb = new StringBuilder(pattern
                // 先将括号转义
                .replace("(", "\\(")
                .replace(")", "\\)")
                // 将\转换为(\\|\/)
                .replace("\\", "(\\\\|\\/)"));
        // 如果不是被转义的\/，也将其转换为(\\|\/)
        // 很怪的算法 我不知道怎么写更好了
        Loop1 : while (true) {
            for (int i = 0; i < sb.length(); i++) {
                if (sb.charAt(i) != '/') {
                    continue;
                }
                if (sb.length() != (i + 1) && sb.charAt(i + 1) == ')') { // 未到末尾并且下一个字符是')' 说明是被转义的字符
                    continue;
                }
                sb.replace(i, i + 1, "(\\\\|\\/)");
                continue Loop1; // 重置遍历
            }
            break; // 没有新的目标了
        }
        return Pattern.compile(sb.toString()
                // 转义剩下的元字符
                .replace("^", "\\^")
                .replace("$", "\\$")
                .replace("*", "\\*")
                .replace("+", "\\+")
                .replace("?", "\\?")
                .replace(".", "\\.")
                .replace("[", "\\[")
                .replace("]", "\\]")
                .replace("{", "\\{")
                .replace("}", "\\}")
                // 换上自己的通配符
                .replace("\\?", ".")
                .replace("\\*", ".*")
        );
    }

    private RegexUtils() {
    }
}
