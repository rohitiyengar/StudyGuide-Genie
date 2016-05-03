package dao.implementation;

import java.util.List;

import model.Interactions;

import org.hibernate.FlushMode;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;

import util.GenericHibernateDaoSupport;
import dao.InteractionsDAO;


@Repository("interactionsDao")
public class InteractionsDAOImplementation extends GenericHibernateDaoSupport implements InteractionsDAO {

	@Override
	public void save(Interactions interaction)
			throws ConstraintViolationException, Exception {
		// TODO Auto-generated method stub
				getHibernateTemplate().getSessionFactory().getCurrentSession().setFlushMode(FlushMode.COMMIT);
				Transaction tx = null;
				try
				{
					tx = getHibernateTemplate().getSessionFactory().getCurrentSession().beginTransaction();
					getHibernateTemplate().saveOrUpdate(interaction);
					tx.commit();
					
				}
				catch(ConstraintViolationException cvexception)
				{
					if(tx != null)
					{
						tx.rollback();
					}
					throw cvexception;
				}
				catch(Exception exception)
				{
					if(tx != null)
					{
						tx.rollback();
					}
					throw exception;
					
				}
		
	}

	@Override
	public void update(Interactions interaction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Interactions interaction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Interactions findInteractions(String interactionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Interactions> findInteractionsForTopic(String studentId,
			int topicId) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<Interactions> userList = (List<Interactions>) getHibernateTemplate().find("from Interactions  where studentId = ?  and topicId = ?", studentId, topicId);
		if (userList == null || userList.size() == 0)
		{
			throw new IllegalArgumentException("Interactions List doesnt exists");
		}
		return userList;
	}

	@Override
	public List<Interactions> findInteractionsForAllTopics(String studentId) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<Interactions> userList = (List<Interactions>) getHibernateTemplate().find("from Interactions  where studentId = ?  order by topicId", studentId);
		if (userList == null || userList.size() == 0)
		{
			throw new IllegalArgumentException("Interactions List doesnt exists");
		}
		return userList;		
	}

	@Override
	public List<Interactions> findInteractionsForThresholds(
			double matchPercentage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Interactions findInteractionsForTopicAndUrl(String studentId, int topicId, String url) {
		@SuppressWarnings("unchecked")
		List<Interactions> userList = (List<Interactions>) getHibernateTemplate().find("from Interactions  where studentId = ?  and topicId = ? and url = ?", studentId, topicId, url);
		if (userList == null || userList.size() == 0)
		{
			throw new IllegalArgumentException("Interactions List doesnt exists");
		}
		return userList.get(0);		
	}

}
