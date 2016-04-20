package bo;

import java.util.List;

import model.Content;

import org.hibernate.exception.ConstraintViolationException;

public interface ContentBO {
	
	void save(Content content) throws ConstraintViolationException, Exception;
	void update(Content content);
	void delete(Content content);
	List<Content> findContentList(int exam_id);
	Content findContentByTopicId(int topic_id);

}
