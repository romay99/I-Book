package kr.ac.baekseok.i_book;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import kr.ac.baekseok.i_book.domain.ApiResponse;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<ApiResponse.Book> bookList;
    private Context context;

    public BookAdapter(List<ApiResponse.Book> bookList, Context context) {
        this.bookList = bookList;
        this.context = context;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        if (bookList.size() != 0) {
            ApiResponse.Book book = bookList.get(position);
            holder.titleTextView.setText(book.getTitle());
            holder.authorTextView.setText(book.getAuthors()[0]);

            // 썸네일 이미지 로드, Glide나 Picasso를 사용할 수 있음
            Glide.with(context).load(book.getThumbnail()).into(holder.thumbnailImageView);

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, BookDetailActivity.class);
                intent.putExtra("book", book); // 클릭한 책 정보를 전달
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView contentTextView;
        ImageView thumbnailImageView;
        TextView authorTextView;

        public BookViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            thumbnailImageView = itemView.findViewById(R.id.thumbnailImageView);
            contentTextView = itemView.findViewById(R.id.contentTextView);
            authorTextView = itemView.findViewById(R.id.authorTextView);
        }
    }
}
