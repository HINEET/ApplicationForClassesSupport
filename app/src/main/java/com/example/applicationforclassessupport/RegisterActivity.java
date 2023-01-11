package com.example.applicationforclassessupport;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;


public class RegisterActivity extends AppCompatActivity {


    private ArrayAdapter adapter;
    private Spinner spinner;
    private String userID;
    private String userPassword;
    private String userGender;
    private String userMajor;
    private String userEmail;
    private AlertDialog dialog;
    private boolean validate = false; //사용가능한 id 인지 체크

//    EditText idText,passwordText,emailText;
//    Button registerButton;
//    RadioGroup genderGroup;
//    DBHelper DB;

//    DBHelper helper;
//    SQLiteDatabase db;
//    EditText edit_id, edit_pass,emailText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        spinner = (Spinner) findViewById(R.id.majorSpinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.major, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final EditText idText = (EditText) findViewById(R.id.idText1);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText1);
        final EditText emailText = (EditText) findViewById(R.id.emailText);

        RadioGroup genderGroup = (RadioGroup) findViewById(R.id.genderGroup);
        int genderGroupID = genderGroup.getCheckedRadioButtonId();
        userGender = ((RadioButton) findViewById(genderGroupID)).getText().toString();

        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int i) {
                RadioButton genderButton = (RadioButton) findViewById(i);
                userGender = genderButton.getText().toString();
            }
        });

        final Button validateButton = (Button) findViewById(R.id.validateButton);
        validateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String userID = idText.getText().toString();
                if (validate)
                {
                    return;
                }
                if (userID.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("아이디는 빈 칸일 수 없습니다.")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success"); //해당과정이 정상적으로 수행했는지 그 레스폰스의 값을 의미
                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                            if(success)
                            {
                                dialog = builder.setMessage("사용할 수 있는 아이디입니다.")
                                        .setPositiveButton("확인",null)
                                        .create();
                                dialog.show();
                                idText.setEnabled(false);
                                validate = true;
                                idText.setBackgroundColor(getResources().getColor(R.color.colorGray));
                                validateButton.setBackgroundColor(getResources().getColor(R.color.colorGray));
                            }
                            else{
                                dialog = builder.setMessage("사용할 수 없는 아이디 입니다.")
                                        .setNegativeButton("확인",null)
                                        .create();
                                dialog.show();
                            }
                        } catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
                ValidateRequest validateRequest = new ValidateRequest(userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(validateRequest);

            }
        });

        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String userID = idText.getText().toString();
                String userPassword = passwordText.getText().toString();
                String userMajor = spinner.getSelectedItem().toString();
                String userEmail = emailText.getText().toString();

                if(!validate)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("먼저 중복 체크를 해주세요")
                            .setNegativeButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }

                if(userID.equals("") || userPassword.equals("") || userMajor.equals("") || userEmail.equals("") || userGender.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("빈 칸 없이 입력해주세요")
                            .setNegativeButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success)
                            {
//                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
//                                dialog = builder.setMessage("회원등록에 성공했습니다.")
//                                        .setPositiveButton("확인",null)
//                                        .create();
//                                dialog.show();
                                Toast.makeText(RegisterActivity.this,"회원가입에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("회원등록에 실패했습니다.")
                                        .setNegativeButton("확인",null)
                                        .create();
                                dialog.show();
                            }
                        } catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userGender, userMajor, userEmail, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });

        }

        @Override
        protected void onStop() {
            super.onStop();
            if(dialog != null)
            {
                dialog.dismiss();
                dialog = null;
            }
        }



//        idText = findViewById(R.id.idText);
//        passwordText = findViewById(R.id.passwordText);
//        emailText = findViewById(R.id.emailText);
//        registerButton = findViewById(R.id.registerButton);
//        genderGroup = findViewById(R.id.genderGroup);
//
//        registerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String user = idText.getText().toString();
//                String pass = passwordText.getText().toString();
//                String email = emailText.getText().toString();
//
//
//                if(TextUtils.isEmpty(user) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(email))
//                    Toast.makeText(RegisterActivity.this,"All fields Required",Toast.LENGTH_SHORT).show();
//                else{
//                    if(pass.equals(pass)){
//                        Boolean checkuser = DB.checkusername(user);
//                        if(checkuser == false){
//                            Boolean insert = DB.insertData(user,pass);
//                            if(insert==true){
//                                Toast.makeText(RegisterActivity.this,"Register Successfully", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                                startActivity(intent);
//                            }else{
//                                Toast.makeText(RegisterActivity.this,"Registration Failed", Toast.LENGTH_SHORT).show();
//                            }
//                        }else{
//                            Toast.makeText(RegisterActivity.this,"User already Exists",Toast.LENGTH_SHORT).show();
//                        }
//                    }else{
//                        Toast.makeText(RegisterActivity.this, "Password are not matching",Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//
//            }
//        });

//        helper = new DBHelper(this);
//        try {
//            db = helper.getWritableDatabase();
//        } catch (SQLiteException ex) {
//            db = helper.getReadableDatabase();
//        }
//        edit_id = (EditText) findViewById(R.id.idText);
//        edit_pass = (EditText) findViewById(R.id.passwordText);
//        emailText = findViewById(R.id.emailText);
//    }
//
//
//    public void enroll(View target) {
//        String id = edit_id.getText().toString();
//        String pass = edit_pass.getText().toString();
//        String email = emailText.getText().toString();
//        if(TextUtils.isEmpty(id) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(email))
//        {
//            Toast.makeText(RegisterActivity.this,"빈칸을 전부 채워주십시오.",Toast.LENGTH_SHORT).show();
//        }else{
//            db.execSQL("INSERT INTO user VALUES (null, '" + id + "', '" + pass + "');");
//            Toast.makeText(getApplicationContext(), "성공적으로 추가되었습니다.", Toast.LENGTH_SHORT).show();
//            edit_id.setText("");
//            edit_pass.setText("");
//            Intent registerIntent = new Intent(RegisterActivity.this, LoginActivity.class);
//            RegisterActivity.this.startActivity(registerIntent);
//        }
//        db.execSQL("INSERT INTO user VALUES (null, '" + id + "', '" + pass + "');");
//        Toast.makeText(getApplicationContext(), "성공적으로 추가되었음", Toast.LENGTH_SHORT).show();
//        edit_id.setText("");
//        edit_pass.setText("");
    

}