package com.iss.news.content.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.iss.common.utils.IdEntity;
import com.iss.news.section.entity.Section;

@Entity
@Table(name = "t_d_news")
@SuppressWarnings("serial")
public class News extends IdEntity {

	/**
	 * 提交时间
	 */
	private Date submitDate;

	private String author;

	private String title;

	private String content;

	/**
	 * 新闻版块ID
	 */
	private Section section;

	/**
	 * 关键字
	 */
	private String keyWords;

	/**
	 * 点击次数
	 */
	private Integer clickTime;

	private byte[] picture;

	@Column(columnDefinition = "datetime comment '提交时间'")
	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	@Column(columnDefinition = "varchar(32) comment '发布人'")
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Column(columnDefinition = "varchar(128) comment '新闻标题'")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(columnDefinition = "longtext comment '新闻内容'")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@ManyToOne
	@JoinColumn(name = "selection_id", columnDefinition = "varchar(64) comment '新闻版块ID'")
	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	@Column(columnDefinition = "varchar(128) comment '关键字'")
	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	@Column(columnDefinition = "int comment '点击次数'")
	public Integer getClickTime() {
		return clickTime;
	}

	public void setClickTime(Integer clickTime) {
		this.clickTime = clickTime;
	}

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "image", columnDefinition = "BLOB", nullable = true)
	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

}
