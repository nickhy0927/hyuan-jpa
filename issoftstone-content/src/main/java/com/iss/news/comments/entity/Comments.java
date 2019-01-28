package com.iss.news.comments.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.iss.common.utils.IdEntity;

@Entity
@Table(name = "t_d_comments")
public class Comments extends IdEntity {

	/**
	 * 评论者ID
	 */
	private String reviewer;

	/**
	 * 评论标题
	 */
	private String title;

	/**
	 * 评论内容
	 */
	private String content;

	/**
	 * 评论时间
	 */
	private Date commentDate;

	/**
	 * 新闻ID
	 */
	private String newsId;

	@Column(columnDefinition = "varchar(64) comment '评论者ID'")
	public String getReviewer() {
		return reviewer;
	}

	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}

	@Column(columnDefinition = "varchar(256) comment '评论标题'")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Lob
	@Basic
	@Column(columnDefinition = "text comment '评论内容'")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(columnDefinition = "datetime comment '评论时间'")
	public Date getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}

	@Column(columnDefinition = "varchar(64) comment '新闻ID'")
	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}

}
