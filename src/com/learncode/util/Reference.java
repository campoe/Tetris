package com.learncode.util;

import java.awt.*;

/**
 * Created by Coen on 14-7-2017.
 */
public class Reference {

    public static String[] getFontNames() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    }

    public static String getExistingFontName(String fontName) {
        String[] fontNames = getFontNames();
        for (int i = 0; i < fontNames.length; i++) {
            if (fontName.equals(fontNames[i])) {
                return fontName;
            }
        }
        return fontNames[0];
    }

    public static void main(String[] args) {
        String[] fontNames = getFontNames();
        for (int i = 0; i < fontNames.length; i++) {
            System.out.println(fontNames[i]);
        }
    }

}
