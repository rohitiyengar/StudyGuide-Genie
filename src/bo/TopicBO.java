package bo;

import org.hibernate.exception.ConstraintViolationException;

import model.Topic;

public interface TopicBO {
	
	void save(Topic topic) throws ConstraintViolationException, Exception;
	void update(Topic topic);
	void delete(Topic topic);
	Topic findTopicByName(String topicName) throws IllegalArgumentException, Exception;
	Topic findTopicById(String topicId) throws IllegalArgumentException, Exception;
	}
