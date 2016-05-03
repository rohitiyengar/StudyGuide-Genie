package bo.implementation;

import java.util.List;

import model.Interactions;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.InteractionsDAO;
import bo.InteractionsBO;



@Service("interactionsBo")
@Transactional
public class InteractionsBOImplementation implements InteractionsBO{
	
	@Autowired
	InteractionsDAO interactionsDao;

	@Override
	public void save(Interactions interaction)
			throws ConstraintViolationException, Exception {
		interactionsDao.save(interaction);
		
	}

	@Override
	public void update(Interactions interaction) {
		// TODO Auto-generated method stub
		interactionsDao.update(interaction);
		
	}

	@Override
	public void delete(Interactions interaction) {
		// TODO Auto-generated method stub
		interactionsDao.delete(interaction);
	}

	@Override
	public Interactions findInteractions(String interactionId) {
		// TODO Auto-generated method stub
		return interactionsDao.findInteractions(interactionId);
	}

	@Override
	public List<Interactions> findInteractionsForTopic(String studentId,
			int topicId) {
		// TODO Auto-generated method stub
		return interactionsDao.findInteractionsForTopic(studentId, topicId);
	}

	@Override
	public List<Interactions> findInteractionsForAllTopics(String studentId) {
		// TODO Auto-generated method stub
		return interactionsDao.findInteractionsForAllTopics(studentId);
	}

	@Override
	public List<Interactions> findInteractionsForThresholds(
			double matchPercentage) {
		// TODO Auto-generated method stub
		return interactionsDao.findInteractionsForThresholds(matchPercentage);
	}

	@Override
	public Interactions findInteractionsForTopicAndUrl(String studentId,
			int topicId, String url) {
		// TODO Auto-generated method stub
		return interactionsDao.findInteractionsForTopicAndUrl(studentId, topicId, url);
	}

}
