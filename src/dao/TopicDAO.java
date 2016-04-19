package dao;

import model.Topic;

import org.hibernate.exception.ConstraintViolationException;

public interface TopicDAO {
	
	
	void save(Topic topic) throws ConstraintViolationException, Exception;
	void update(Topic topic);
	void delete(Topic topic);
	Topic findTopicById(int topicId) throws IllegalArgumentException, Exception;
	Topic findTopicByName(String topicName) throws IllegalArgumentException, Exception;
}
