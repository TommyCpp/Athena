package com.athena.util;

import java.util.List;

/**
 * Created by 吴钟扬 on 2017/9/11.
 */
public class ListUtil {
    public static boolean genericTypeIs(List<?> list, Class c) {
        return list.size() == 0 || list.get(0).getClass().equals(c);
    }
}
