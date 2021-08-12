package com.andrew.web;

import java.util.ArrayList;
import java.util.List;

public class CookiesManager {
    private static final List<String> COOKIES_LIST = new ArrayList<>();

    public static List<String> getCookiesList() {
        return COOKIES_LIST;
    }
}
