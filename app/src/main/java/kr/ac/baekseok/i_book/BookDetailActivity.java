package kr.ac.baekseok.i_book;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import kr.ac.baekseok.i_book.domain.ApiResponse;


public class BookDetailActivity extends AppCompatActivity {

    private TextView titleTextView, authorTextView, urlTextView, contentView;
    private ImageView thumbnailImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        titleTextView = findViewById(R.id.titleTextView);
        authorTextView = findViewById(R.id.authorTextView);
        urlTextView = findViewById(R.id.urlTextView);
        thumbnailImageView = findViewById(R.id.thumbnailImageView);
        contentView = findViewById(R.id.contentTextView);

        // Intent로 전달받은 책 정보
        ApiResponse.Book selectedBook = (ApiResponse.Book) getIntent().getSerializableExtra("book");

        // 책 정보 세팅
        if (selectedBook != null) {
            titleTextView.setText(selectedBook.getTitle());
            authorTextView.setText(selectedBook.getAuthors()[0]);
            urlTextView.setText(selectedBook.getUrl());
            contentView.setText(selectedBook.getContents());

            // Glide로 썸네일 이미지 로드
            Glide.with(this)
                    .load(selectedBook.getThumbnail())
                    .into(thumbnailImageView);
        }

        urlTextView.setOnClickListener(v -> {
            String url = selectedBook.getUrl();
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        });

    }
}
