package com.athena.util;

import java.util.List;

/**
 * Created by 吴钟扬 on 2017/9/11.
 */
public class ListUtil {
    /**
     * determine whether the type of element of list is c
     *
     * <b>if the list is empty, then return true</b>
     *
     * */
    public static boolean genericTypeIs(List<?> list, Class c) {
        return list.isEmpty() || list.get(0).getClass().equals(c);
    }
}
