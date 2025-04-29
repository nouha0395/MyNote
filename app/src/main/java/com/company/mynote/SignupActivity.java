package com.company.mynote;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    private EditText nameSignUp, emailSignUp, passwordSignUp, confirmPasswordSignUp;
    private Button signupButton;
    private TextView toLogin;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);  // تأكد من أن اسم ملف XML صحيح

        // ربط عناصر الواجهة
        nameSignUp = findViewById(R.id.nameSignUp);
        emailSignUp = findViewById(R.id.emailSignUp);
        passwordSignUp = findViewById(R.id.passwordSignUp);
        confirmPasswordSignUp = findViewById(R.id.confirmPasswordSignUp);
        signupButton = findViewById(R.id.signupButton);
        toLogin = findViewById(R.id.toLogin);

        // إنشاء نسخة من كائن قاعدة البيانات
        databaseHelper = new DatabaseHelper(this);

        // عند الضغط على زر إنشاء الحساب
        signupButton.setOnClickListener(v -> registerUser());

        // عند الضغط على رابط الذهاب إلى صفحة تسجيل الدخول
        toLogin.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
            startActivity(intent);
        });
    }

    private void registerUser() {
        String name = nameSignUp.getText().toString().trim();
        String email = emailSignUp.getText().toString().trim();
        String password = passwordSignUp.getText().toString().trim();
        String confirmPassword = confirmPasswordSignUp.getText().toString().trim();

        // التحقق من الحقول
        if (TextUtils.isEmpty(name)) {
            nameSignUp.setError("يرجى إدخال اسمك");
            nameSignUp.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            emailSignUp.setError("يرجى إدخال البريد الإلكتروني");
            emailSignUp.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordSignUp.setError("يرجى إدخال كلمة المرور");
            passwordSignUp.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            confirmPasswordSignUp.setError("يرجى تأكيد كلمة المرور");
            confirmPasswordSignUp.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)) {
            confirmPasswordSignUp.setError("كلمة المرور غير متطابقة");
            confirmPasswordSignUp.requestFocus();
            return;
        }

        if (password.length() < 6) {
            passwordSignUp.setError("كلمة المرور يجب أن تحتوي على 6 أحرف على الأقل");
            passwordSignUp.requestFocus();
            return;
        }

        // إضافة المستخدم إلى قاعدة البيانات
        boolean isInserted = databaseHelper.addUser(name, email, password);

        if (isInserted) {
            Toast.makeText(this, "تم إنشاء الحساب بنجاح!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SignupActivity.this, MainActivity.class);  // بعد النجاح اذهب إلى MainActivity
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "فشل إنشاء الحساب! البريد الإلكتروني قد يكون مستخدم مسبقًا.", Toast.LENGTH_SHORT).show();
        }
    }
}
