package xyz.dassiorleando.springbootorientdb.service;

import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import org.springframework.stereotype.Service;
import xyz.dassiorleando.springbootorientdb.domain.Article;

import java.util.ArrayList;
import java.util.List;

/**
 * Article service for basic CRUD operations
 * @author dassiorleando
 */
@Service
public class ArticleService {
    // The orientdb installation folder
    private static String orientDBFolder = System.getenv("ORIENTDB_HOME");

    private final ODatabaseDocumentTx db; // db instance

    ArticleService(ODatabaseDocumentTx oDatabaseDocumentTx) {
        this.db = oDatabaseDocumentTx;
    }

    /**
     * To create an article
     * @param article
     * @return article
     */
    public Article save(Article article) {
        // Specify to use the same db instance for this thread
        ODatabaseRecordThreadLocal.instance().set(db);

        // The Class will be automatically created into Orient Studio
        ODocument doc = new ODocument(Article.class.getSimpleName()); // The entity name is provided as parameter
        doc.field("title", article.getTitle());
        doc.field("content", article.getContent());
        doc.field("author", article.getAuthor());
        doc.save();

        return article;
    }

    /**
     * To update an article
     * @param article
     * @return boolean true if it was successfully updated
     */
    public boolean update(Article article) {
        // Specify to use the same db instance for this thread
        ODatabaseRecordThreadLocal.instance().set(db);

        // Data
        String title = article.getTitle().trim();
        String content = article.getContent();
        String author = article.getAuthor();

        // The sql query
        String query = "update Article set content = '" + content +
                "', author = '" + author + "' where title = '" + title + "'";

        int resultInt = db.command(
                new OCommandSQL(query)).execute();

        if(resultInt != -1) return true;
        return false;
    }

    /**
     * Find a single article by title
     * @param title
     * @return article if found null else
     */
    public Article findOne(String title) {
        // SQL query to have the ones that match
        List<ODocument> results = db.query(
                new OSQLSynchQuery<ODocument>("select * from Article where title = '" + title + "'"));

        // For the sake of the test, pick the first found result
        if(!results.isEmpty()) {
            ODocument oDocument = results.get(0);
            return Article.fromODocument(oDocument);
        }

        return null;
    }

    /**
     * Find all saved articles so far
     * @return
     */
    public List<Article> findAll() {
        // List of resulting article
        List<Article> articles = new ArrayList<>();

        // Load all the articles
        for (ODocument articleDocument : db.browseClass("Article")) {
            Article article = Article.fromODocument(articleDocument);

            articles.add(article);
        }

        return articles;
    }

    /**
     * Delete a single article by its title
     * @param title
     * @return boolean true if it was deleted successfully
     */
    public boolean delete(String title) {
        title = title.trim(); // The title of the article to delete
        int resultInt =  db.command(
                new OCommandSQL("delete * from Article where title = '" + title + "'")).execute();

        if(resultInt != -1) return true;
        return false;
    }
}
