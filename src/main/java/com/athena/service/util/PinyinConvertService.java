package com.athena.service.util;

import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import org.springframework.stereotype.Service;

/**
 * Created by tommy on 2017/5/11.
 */
@Service
public class PinyinConvertService {

    /**
     * Instantiates a new Pinyin convert service.
     */
    public PinyinConvertService() {
    }

    /**
     * Gets pin yin.
     *
     * @param s the s
     * @return the pin yin
     */
    public String getPinYin(String s) {
        try {
            return PinyinHelper.convertToPinyinString(s, ",", PinyinFormat.WITHOUT_TONE);
        } catch (PinyinException e) {
            return this.handlePinyinException(e);
        }
    }

    /**
     * Gets short pin yin.
     *
     * @param s the s
     * @return the short pin yin
     */
    public String getShortPinYin(String s) {
        try {
            return PinyinHelper.getShortPinyin(s);
        } catch (PinyinException e) {
            return this.handlePinyinException(e);
        }
    }

    private String handlePinyinException(PinyinException exception) {
        return "";
    }


}
