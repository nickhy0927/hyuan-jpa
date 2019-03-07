package com.iss.blog.article.entity;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.iss.blog.tag.entity.Tags;
import com.iss.common.utils.IdEntity;
import com.iss.common.utils.SysContants.IsDelete;

@Entity
@Table(name = "t_b_article")
public class Article extends IdEntity {
	private static final long serialVersionUID = 1L;

	// 文章标题
	private String title;

	// 简介
	private String profile;

	// 文章内容
	private String content;

	// 标签
	private List<Tags> tags;

	public Article() {
	}

	public Article(String title, String profile, String content, List<Tags> tags) {
		this.title = title;
		this.profile = profile;
		this.content = content;
		this.tags = tags;
		this.status = IsDelete.NO;
	}

	@Column(columnDefinition = "varchar(255) comment '文章标题'")
	public String getTitle() {
		return title;
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
	@Basic(fetch = FetchType.LAZY)
	@Column(columnDefinition = "longtext comment '文章正文'")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
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
}
