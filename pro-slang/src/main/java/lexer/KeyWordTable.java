package lexer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class KeyWordTable {

    private static final Map<String, TOKEN> keyWords = new HashMap<>() {{
        put("and", TOKEN.AND);
        put("not", TOKEN.NOT);
    }};
    private static final Set<String> keyWordNames = keyWords.keySet();


    public static TOKEN symbol(String keyWord) {
        return keyWords.getOrDefault(keyWord, TOKEN.EOF);
    }

    public static int keywordMatchCount(String inputKeyword) {
        int count = 0;
        for (var keyword : keyWordNames) {
            if (!keyword.equals("") && keyword.startsWith(inputKeyword))
                count++;
        }
        return count;
    }


}

