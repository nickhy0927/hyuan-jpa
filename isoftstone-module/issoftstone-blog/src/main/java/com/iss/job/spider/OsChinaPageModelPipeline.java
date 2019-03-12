package com.iss.job.spider;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iss.blog.article.entity.Article;
import com.iss.blog.article.service.ArticleService;
import com.iss.blog.tag.entity.Tags;
import com.iss.blog.tag.service.TagsService;
import com.iss.common.exception.ServiceException;
import com.iss.common.spring.SpringContextHolder;
import com.iss.common.utils.SysContants.IsDelete;

import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

/**
 * @author Hyuan
 */
public class OsChinaPageModelPipeline implements PageModelPipeline<OsChianBlog> {

    private static ArticleService articleService;

    private static TagsService tagsService;

    static {
        articleService = SpringContextHolder.getBean(ArticleService.class);
        tagsService = SpringContextHolder.getBean(TagsService.class);
    }

    private static List<Article> articles = Lists.newArrayList();

    @Override
    public void process(OsChianBlog blog, Task task) {
        try {
            if (articles.size() > 5) {
                articleService.saveBatch(articles);
                articles.clear();
            }
            Article article = new Article();
            article.setTitle(blog.getTitle());
            List<String> tags = blog.getTags();
            if (tags.size() > 0) {
                List<Tags> tagsList = Lists.newArrayList();
                Map<String, Object> paramMap = Maps.newConcurrentMap();
                for (String tag : tags) {
                    paramMap.put("tag_eq", tag);
                    List<Tags> list = tagsService.queryByMap(paramMap);
                    if (list.size() > 0) {
                        tagsList.add(list.get(0));
                    } else {
                        Tags obj = new Tags();
                        obj.setTag(tag);
                        obj.setStatus(IsDelete.NO);
                        tagsService.saveEntity(obj);
                        tagsList.add(obj);
                    }
                }
                String content = "";
                if (StringUtils.isNotEmpty(blog.getContent())) {
                    content = blog.getContent().replaceAll("<div class=\"ad-wrap\"(([\\s\\S])*?)<\\/div>", "");
                }
                article.setProfile(Article.stripHtml(content).substring(0, 100) + "...");
                article.setContent(!StringUtils.isNotEmpty(content) ? new byte[0] : content.getBytes());
                article.setStatus(IsDelete.NO);
                article.setAuthor(blog.getAuthor());
                article.setTags(tagsList);
            }
            Integer hashCode = article.hashCode();
            Map<String, Object> params = Maps.newConcurrentMap();
            params.put("hashCode_eq", hashCode);
            System.out.println(article);
            List<Article> list = articleService.queryByMap(params);
            if (list.size() == 0) {
                article.setHashCode(hashCode);
                articles.add(article);
            }

            File file = new File("d:/blog-text/");
            if (!file.exists()) {
                file.mkdirs();
            }
            String path = file.getAbsolutePath() + "/" + blog.getTitle().trim() + ".txt";
            File f = new File(path);
            FileWriter fw;
            BufferedWriter bw;
            bw = null;
            try {
                if (!f.exists()) {
                    f.createNewFile();
                }
                // true表示可以追加新内容
                fw = new FileWriter(f.getAbsoluteFile(), true);
                bw = new BufferedWriter(fw);
                bw.write(blog.getContent());
                bw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (ServiceException e) {
        }
    }
}
