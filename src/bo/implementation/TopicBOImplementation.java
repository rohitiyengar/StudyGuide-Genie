package bo.implementation;

import model.Topic;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.TopicDAO;
import bo.TopicBO;


@Service("topicBo")
@Transactional
public class TopicBOImplementation implements TopicBO {
	
	@Autowired
	TopicDAO topicDao;

	@Override
	public void save(Topic topic) throws ConstraintViolationException,
			Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Topic topic) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Topic topic) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public Topic findTopicByName(String topicName) throws IllegalArgumentException, Exception {
		// TODO Auto-generated method stub
		return topicDao.findTopicByName(topicName);
	}

	@Override
	public Topic findTopicById(int topicId)throws IllegalArgumentException, Exception {
		// TODO Auto-generated method stub
		return topicDao.findTopicById(topicId);
	}

}
