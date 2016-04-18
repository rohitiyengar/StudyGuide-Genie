package dao.implementation;

import java.util.List;

import model.Topic;
import model.User;

import org.hibernate.Query;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;

import util.GenericHibernateDaoSupport;
import dao.TopicDAO;


@Repository("topicDao")
public class TopicDAOImplementation extends GenericHibernateDaoSupport implements TopicDAO  {

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
	public Topic findTopicById(String topicId) throws IllegalArgumentException, Exception {
		// TODO Auto-generated method stub
		List<Topic> userList = (List<Topic>) getHibernateTemplate().find("from Topic  where topicId = ? ", topicId);
		if (userList == null || userList.size() == 0)
		{
			throw new IllegalArgumentException("Topic doesnt exists");
		}
		return (Topic) userList.get(0);

	}

	@Override
	public Topic findTopicByName(String topicName) throws IllegalArgumentException, Exception {
		// TODO Auto-generated method stub
		List<Topic> userList = (List<Topic>) getHibernateTemplate().find("from Topic where topicName = ? ", topicName);
		if (userList == null || userList.size() == 0)
		{
			throw new IllegalArgumentException("Topic doesnt exists");
		}
		return (Topic) userList.get(0);
	}

	

	
}
