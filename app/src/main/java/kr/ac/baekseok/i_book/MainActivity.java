package kr.ac.baekseok.i_book;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import kr.ac.baekseok.i_book.domain.ApiResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText searchEditText;
    private Button searchButton;
    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private TextView countView,nullCheckTextView;
    private List<ApiResponse.Book> bookList = new ArrayList<>();
    private Context context;

    private static final String API_KEY = "0ad66dc7c7929da3da3d48f1f320c0ec"; // Kakao API 키

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        recyclerView = findViewById(R.id.recyclerView);
        countView = findViewById(R.id.bookCountView);
        nullCheckTextView = findViewById(R.id.nullCheckTextView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        searchButton.setOnClickListener(v -> searchBooks());
    }

    private void searchBooks() {
        String query = searchEditText.getText().toString().trim();
        if (query.isEmpty()) {
            Toast.makeText(this, "책 제목을 입력하세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        OkHttpClient client = new OkHttpClient();
        String url = "https://dapi.kakao.com/v3/search/book?target=title&query=" + query;  // 검색 URL
        System.out.println("url = " + url);

        Request request = new Request.Builder()
                .url(url)
                .header("Authorization","KakaoAK "+API_KEY) // API Key 추가
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "서버와의 연결에 실패했습니다.", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonResponse = response.body().string();

                    // Gson으로 JSON 파싱
                    Gson gson = new Gson();
                    ApiResponse apiResponse = gson.fromJson(jsonResponse, ApiResponse.class);

                    // UI 업데이트
                    runOnUiThread(() -> {
                        List<ApiResponse.Book> documents = apiResponse.getDocuments();
                        if (documents.isEmpty()) {
                            recyclerView.setVisibility(View.GONE);
                            nullCheckTextView.setVisibility(View.VISIBLE);
                            bookAdapter = null;
                        } else {
                            bookList.clear();
                            countView.setText(documents.size()+" 건의 검색결과가 존재합니다.");
                            bookList.addAll(apiResponse.getDocuments());
                            bookAdapter = new BookAdapter(bookList, context);
                            recyclerView.setAdapter(bookAdapter);
                            bookAdapter.notifyDataSetChanged();

                        }
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(MainActivity.this, "응답 오류: " + response.message(), Toast.LENGTH_SHORT).show());
                }
            }
        });
    }
}
