package kr.ac.baekseok.i_book.domain;

import android.util.Log;

import java.io.Serializable;
import java.util.List;

public class ApiResponse {

    private Meta meta;
    private List<Book> documents;

    public List<Book> getDocuments() {
        return documents;
    }

    public static class Meta {
        private int total_count;
        private int pageable_count;
        private boolean is_end;
    }

    public Meta getMeta() {
        return meta;
    }

    public static class Book implements Serializable {
        private String title;
        private String thumbnail;
        private String url;
        private String datetime;
        private String[] authors;
        private String contents;

        // Getter와 Setter

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public String[] getAuthors() {
            return authors;
        }

        public void setAuthors(String[] author) {
            this.authors = author;
        }

        public String getContents() {
            return contents;
        }

        public void setContents(String content) {
            this.contents = content;
        }
    }
}
