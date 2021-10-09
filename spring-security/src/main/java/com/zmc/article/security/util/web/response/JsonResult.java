package com.zmc.article.security.util.web.response;

import net.sf.json.JSONObject;

public class JsonResult {

    public static void createResult(Object obj) {
        ResponseUtil.writeJson(obj.toString());
    }

    public static void success() {
        JSONObject json = new JSONObject();
        json.put("success", true);
        createResult(json);
    }

    public static void success(Object data) {
        JSONObject json = new JSONObject();
        json.put("success", true);
        json.put("data", data);
        createResult(json);
    }

    public static void error() {
        JSONObject json = new JSONObject();
        json.put("success", false);
        createResult(json);
    }

    public static void error(int code, String msg) {
        JSONObject json = new JSONObject();
        json.put("success", false);
        json.put("code", code);
        json.put("msg", msg);
        createResult(json);
    }

    public static void error(String msg) {
        JSONObject json = new JSONObject();
        json.put("success", false);
        json.put("code", 500);
        json.put("msg", msg);
        createResult(json);
    }
}
