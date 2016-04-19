package dao.implementation;

import java.util.List;

import model.Content;
import org.hibernate.FlushMode;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;

import util.GenericHibernateDaoSupport;
import dao.ContentDAO;

@Repository("contentDao")
public class ContentDAOImplementation extends GenericHibernateDaoSupport implements ContentDAO {

	@Override
	public void save(Content content) throws ConstraintViolationException,
			Exception {
		// TODO Auto-generated method stub
		getHibernateTemplate().getSessionFactory().getCurrentSession().setFlushMode(FlushMode.COMMIT);
		Transaction tx = null;
		try
		{
			tx = getHibernateTemplate().getSessionFactory().getCurrentSession().beginTransaction();
			getHibernateTemplate().saveOrUpdate(content);
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
	public void update(Content content) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Content content) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Content> findContentList(int exam_id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<Content> userList = (List<Content>) getHibernateTemplate().find("from Content  where examId = ? ", exam_id);
		if (userList == null || userList.size() == 0)
		{
			throw new IllegalArgumentException("Content List doesnt exists");
		}
		return userList;
	}

}
