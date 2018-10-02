package xyz.dassiorleando.springbootorientdb.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import xyz.dassiorleando.springbootorientdb.domain.Article;
import xyz.dassiorleando.springbootorientdb.service.ArticleService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Article controller for CRUD operations
 * @author dassiorleando
 */
@RestController
public class ArticleResource {
    private final Logger log = LoggerFactory.getLogger(ArticleResource.class);

    private final ArticleService articleService;

    public ArticleResource(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * To create an article
     * @param article
     * @return
     */
    @PostMapping("/article")
    public Article create(@RequestBody @Valid Article article) {
        log.debug("Create an article with the properties {}", article);
        return articleService.save(article);
    }

    /**
     * To update an article
     * @param article
     * @return
     */
    @PutMapping("/article")
    public boolean update(@RequestBody @Valid Article article) {
        log.debug("Update the article of title {} with the properties {}", article.getTitle(), article);
        return articleService.update(article);
    }

    /**
     * Get the list of all articles
     * @return
     */
    @GetMapping("/article")
    public List<Article> list() {
        log.debug("We just get the list of articles one more time");
        return articleService.findAll();
    }

    /**
     * We asynchronously find an article by his title
     * @param title
     * @return
     */
    @GetMapping("/article/{title}")
    public Article findByTitle(@PathVariable @NotNull String title) {
        log.debug("Load the article of title: {}", title);
        return articleService.findOne(title);
    }

    /**
     * Delete an article by its title
     * @param title
     */
    @DeleteMapping("/article/{title}")
    public boolean deleteById(@PathVariable @NotNull String title) {
        log.debug("Delete the article of title: {}", title);
        return articleService.delete(title);
    }
}

