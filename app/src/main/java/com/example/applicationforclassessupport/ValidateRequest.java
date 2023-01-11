package com.example.applicationforclassessupport;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class ValidateRequest extends StringRequest { //유저 id를 UserValidate로 보냄으로서 회원가입이 가능한지 요청을 보내는 클래스

    final static private String URL = "http://dhzkwkzl2.cafe24.com/UserValidate.php";
    private Map<String, String> parameters;

    public ValidateRequest(String userID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null); //해당 요청을 숨겨서 보내주는 코드
        parameters = new HashMap<>();
        parameters.put("userID", userID);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}