package com.company.mynote;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SigninActivity extends AppCompatActivity {

    private EditText emailLogin, passwordLogin;
    private Button loginButton;
    private TextView toSignUp;
    private LinearLayout signupRoot;

    private DatabaseHelper databaseHelper; // ربط بقاعدة البيانات

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin); // تأكد من اسم XML

        // ربط العناصر
        emailLogin = findViewById(R.id.emailLogin);
        passwordLogin = findViewById(R.id.passwordLogin);
        loginButton = findViewById(R.id.loginButton);
        toSignUp = findViewById(R.id.toSignUp);
        signupRoot = findViewById(R.id.signupRoot);

        databaseHelper = new DatabaseHelper(this); // إنشاء نسخة من قاعدة البيانات

        // عند الضغط على زر تسجيل الدخول
        loginButton.setOnClickListener(v -> loginUser());

        // عند الضغط على الذهاب لإنشاء حساب
        toSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(SigninActivity.this, SignupActivity.class);
            startActivity(intent);
        });
    }

    private void loginUser() {
        String email = emailLogin.getText().toString().trim();
        String password = passwordLogin.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            emailLogin.setError("يرجى إدخال البريد الإلكتروني");
            emailLogin.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordLogin.setError("يرجى إدخال كلمة المرور");
            passwordLogin.requestFocus();
            return;
        }

        // التحقق من وجود المستخدم في قاعدة البيانات
        boolean isUserExist = databaseHelper.checkUser(email, password);

        if (isUserExist) {
            Toast.makeText(this, "تم تسجيل الدخول بنجاح!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SigninActivity.this, MainActivity.class); // ادخله إلى الصفحة الرئيسية
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "البريد الإلكتروني أو كلمة المرور غير صحيحة!", Toast.LENGTH_SHORT).show();
        }
    }
}
