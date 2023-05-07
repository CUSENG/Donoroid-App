package com.asazing.loginui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText inputEmail, inputPass; // Giriş sayfasının Editext değişkenlerini bildiriyoruz

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide(); // ActionBar'ı gizliyorum.
        inputEmail = findViewById(R.id.inputEmailLogin); // inputEmail değişkenine bir öğe atıyorum
        inputPass = findViewById(R.id.inputPasswordLogin); // inputPass değişkenine bir öğe atıyorum
    }

    public void onLoginClick(View View){ // Kullanıcı kaydı sayfasını başlatmak için yöntem
        startActivity(new Intent(this,RegisterActivity.class)); // Aktiviteyi başlatıyorum.
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay); // Aktivite değişimine animasyon veriyorum.
    }

    public void onLoginAuth(View View){ // Kullanıcı adı ve şifre doğrulama yöntemi
        if (!isEmptyText(inputEmail) && !isEmptyText(inputPass)) { // Alanların dolu olup olmadığını doğruluyorum.
            if (isEmail(inputEmail.getText().toString())){
                Intent intent= new Intent(getApplicationContext(), Dashboard.class);
                startActivity(intent); //geçici kod bloğu -Ata
              //  auth("http://192.168.1.13/api/login/auth.php"); // auth yöntemini çağırıyorum ve API URL'sini gönderiyorum.
            }

        }else{ // Herhangi bir alan boşsa, aşağıdaki mesajı içeren bir Toast bildirim mesajı gösteriyorum.
            Toast.makeText(MainActivity.this, "Tüm Alanları Girin", Toast.LENGTH_SHORT).show();
        }
    }

    private void auth(String URL){ // API URL'sini alan kimlik doğrulama yöntemi
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() { // URL ile bir String yanıtı istiyorum.
            @Override
            public void onResponse(String response) { // Özel istek yöntemi
                if(!response.isEmpty()){
                    Intent intent= new Intent(getApplicationContext(), Dashboard.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this, "Kullanıcı adı veya şifre YANLIŞ", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener(){
            public void onErrorResponse(VolleyError error){ // Bağlantı hatası yanıtı
                System.out.println("Hata : " + error.toString() );
                Toast.makeText(MainActivity.this, "Hata: " + error.toString(), Toast.LENGTH_SHORT).show();
            }

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError { // Parametreleri almak için yöntem
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", inputEmail.getText().toString());
                params.put("pass", inputPass.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest); // İstemi RequestQueue'ye ekliyorum.
    }

    //bir e-posta olup olmadığını kontrol etme yöntemi
    public  boolean isEmail(String email) {
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true;
        } else {
            Toast.makeText(MainActivity.this, "Geçerli bir e-posta giriniz", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    //editext'in boş olup olmadığını kontrol etme yöntemi
    public  boolean isEmptyText(EditText str){
        String values = str.getText().toString().trim();
        if(TextUtils.isEmpty(values)){
            str.setError("Gerekli alan");
            str.requestFocus();
            return true;
        }
        else{
            return false;
        }
    }


    }
