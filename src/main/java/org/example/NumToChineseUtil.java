package org.example;

import com.netease.lowcode.core.annotation.NaslLogic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class NumToChineseUtil {
    public static String transformer(double n) {
        String[] fraction = new String[]{"角", "分"};
        String[] digit = new String[]{"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
        String[][] unit = new String[][]{{"元", "万", "亿"}, {"", "拾", "佰", "仟"}};
        String head = n < 0.0 ? "负" : "";
        n = Math.abs(n);
        String s = "";
        BigDecimal bigDecimal = new BigDecimal(Double.valueOf(n).toString());
        String nStr = bigDecimal.toString();
        String[] split = nStr.split("\\.");
        String p;
        int j;
        if (split.length > 1) {
            String decimalStr = split[1];
            if (decimalStr.length() > 2) {
                decimalStr = decimalStr.substring(0, 2);
            }

            Integer integer = Integer.valueOf(decimalStr);
            p = "";
            for(j = 0; j < decimalStr.length() && j < fraction.length; ++j) {
                p = digit[integer % 10] + fraction[decimalStr.length() - j - 1] + p;
                integer = integer / 10;
            }

            s = p.replaceAll("(零.)+", "") + s;
        }

        if (s.isEmpty()) {
            s = "整";
        }

        int integerPart = (int)Math.floor(n);
        for(int i = 0; i < unit[0].length && integerPart > 0; ++i) {
            p = "";
            for(j = 0; j < unit[1].length && n > 0.0; ++j) {
                p = digit[integerPart % 10] + unit[1][j] + p;
                integerPart /= 10;
            }

            s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i] + s;
        }

        return head + s.replaceAll("(零.)*零元", "元").replaceFirst("(零.)+", "").replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");
    }

    @NaslLogic
    public static String NumToChinese(double n){
        return transformer(n);
    }
}
