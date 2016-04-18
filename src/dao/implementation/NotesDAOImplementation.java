package dao.implementation;

import java.util.List;

import org.hibernate.FlushMode;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;

import util.GenericHibernateDaoSupport;
import dao.NotesDAO;


import model.Notes;
import model.Topic;


@Repository("notesDao")
public class NotesDAOImplementation  extends GenericHibernateDaoSupport implements NotesDAO
{

	@Override
	public void save(Notes notes) throws ConstraintViolationException,
			Exception {
		getHibernateTemplate().getSessionFactory().getCurrentSession().setFlushMode(FlushMode.COMMIT);
		Transaction tx = null;
		try
		{
			tx = getHibernateTemplate().getSessionFactory().getCurrentSession().beginTransaction();
			getHibernateTemplate().saveOrUpdate(notes);
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
	public void update(Notes notes) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Notes notes) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Notes findNotes(String studentId, int topicid)
			throws IllegalArgumentException, Exception {
		// TODO Auto-generated method stub
		List<Notes> userList = (List<Notes>) getHibernateTemplate().find("from Notes  where studentId = ? and topicid = ? ", (String)studentId, (int)topicid);
		if (userList == null || userList.size() == 0)
		{
			throw new IllegalArgumentException("Notes doesnt exists");
		}
		return (Notes) userList.get(0);

	}

	@Override
	public Notes findNotes(String notesId) throws IllegalArgumentException,
			Exception {
		List<Notes> userList = (List<Notes>) getHibernateTemplate().find("from Notes  where notesId = ? ", notesId);
		if (userList == null || userList.size() == 0)
		{
			throw new IllegalArgumentException("Notes doesnt exists");
		}
		return (Notes) userList.get(0);

	}

}
