package com.iss.blog.article.entity;

import com.iss.blog.tag.entity.Tags;
import com.iss.common.utils.IdEntity;

import javax.persistence.*;
import java.util.List;

/**
 * @author Hyuan
 */
@Entity
@SuppressWarnings("serial")
@Table(name = "t_b_article")
public class Article extends IdEntity {

    /**
     * 文章标题
     */
    private String title;

    /**
     * 简介
     */
    private String profile;

    /**
     * 文章内容
     */
    private byte[] content;

    private String contents;

    /**
     * 标签
     */
    private List<Tags> tags;

    /**
     * 作者
     */
    private String author;

    public Article() {
    }

    private Integer hashCode;

    @Column(columnDefinition = "varchar(255) comment '文章标题'")
    public String getTitle() {
        return title;
    }

    public Integer getHashCode() {
        return hashCode;
    }

    public void setHashCode(Integer hashCode) {
        this.hashCode = hashCode;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(columnDefinition = "varchar(512) comment '文章简介'")
    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    @Lob
    @Column
    @Basic(fetch = FetchType.LAZY)
    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @ManyToMany
    @JoinTable(name = "t_b_art_tags", joinColumns = @JoinColumn(name = "art_id", columnDefinition = "varchar(64) comment '博客ID'"), inverseJoinColumns = @JoinColumn(name = "tag_id", columnDefinition = "varchar(64) comment '标签ID'"))
    public List<Tags> getTags() {
        return tags;
    }

    public void setTags(List<Tags> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Article [title=" + title + ", profile=" + profile + ", tags=" + tags + "]";
    }

    @Override
    public int hashCode() {
        int result = getTitle().hashCode();
        result = 29 * result;
        return result;
    }

    @Transient
    public String getContents() {
        if (content.length > 0) {
            contents = new String(content);
        }
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    @Column(columnDefinition = "varchar(64) comment '博客作者'")
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public static String stripHtml(String content) {
        // <p>段落替换为换行
        content = content.replaceAll("<p .*?>", "\r\n");
        // <br><br/>替换为换行
        content = content.replaceAll("<br\\s*/?>", "\r\n");
        // 去掉其它的<>之间的东西
        content = content.replaceAll("\\<.*?>", "");
        // 去掉空格
        content = content.replaceAll(" ", "");
        content = content.replaceAll("&nbsp;", "");
        return content;
    }

}
