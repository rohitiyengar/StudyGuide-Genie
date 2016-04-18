package model;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.*;
import org.springframework.context.annotation.Scope;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;




@Scope("session")
@Entity
@Table(name="topic", catalog="public", uniqueConstraints = {
		@UniqueConstraint(columnNames = "topic_id") })

public class Topic implements Serializable {
	
	private static final long serialVersionUID = 1L;


	private int topicId;
	private int parentTopicId;
	private int chapterNumber;
	private String topicName;
	
	
	
	
	@Id
	@Column(name = "topic_id", unique = true, nullable = false)
	public int getTopicId() {
		return topicId;
	}
	
	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}
	
	@Column(name = "parent_topic_id", nullable = true)
	public int getParentTopicId() {
	return parentTopicId;
	}
	
	public void setParentTopicId(int parentTopicId) {
		this.parentTopicId = parentTopicId;
	}
	
	@Column(name = "chapter_num", nullable = true)
	public int getChapterNumber() {
		return chapterNumber;
	}
	
	
	public void setChapterNumber(int chapterNumber) {
		this.chapterNumber = chapterNumber;
	}
	
	@Column(name = "topic_name", nullable = true)
	public String getTopicName() {
		return topicName;
	}
	
	
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	

}
