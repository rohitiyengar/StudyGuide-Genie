package bo;

import java.util.List;

import model.Interactions;

import org.hibernate.exception.ConstraintViolationException;

public interface InteractionsBO {
	
	
	void save(Interactions interaction) throws ConstraintViolationException, Exception;
	void update(Interactions interaction);
	void delete(Interactions interaction);
	Interactions findInteractions(String interactionId);
	List<Interactions> findInteractionsForTopic(String studentId, int topicId);
	List<Interactions> findInteractionsForAllTopics(String studentId);
	List<Interactions> findInteractionsForThresholds(double matchPercentage);
	Interactions findInteractionsForTopicAndUrl(String studentId, int topicId, String url);
	


}
