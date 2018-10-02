package xyz.dassiorleando.springbootorientdb.domain;

import com.orientechnologies.orient.core.record.impl.ODocument;

import javax.validation.constraints.NotNull;

/**
 * The Article model
 * @author dassiorleando
 */
public class Article {
    @NotNull
    private String title; // Supposing the title is unique, just for testing purpose
    private String content;
    private String author;

    public Article() {
    }

    public static Article fromODocument(ODocument oDocument) {
        Article article = new Article();
        article.title = oDocument.field("title");
        article.content = oDocument.field("content");
        article.author = oDocument.field("author");

        return article;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                "content='" + content + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
