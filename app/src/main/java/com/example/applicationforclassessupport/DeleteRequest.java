package com.example.applicationforclassessupport;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DeleteRequest extends StringRequest { //RegisterRequest를 이용하여 UserRegister.php 파일에 id, password등의 정보를 보내 회원가입 요청을 보내는 클래스

    final static private String URL = "https://dhzkwkzl2.cafe24.com/ScheduleDelete.php";
    private Map<String, String> parameters;

    public DeleteRequest(String userID, String courseID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null); //해당 요청을 숨겨서 보내주는 코드
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("courseID", courseID);

    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
