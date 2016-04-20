package dao.implementation;

import java.util.List;

import model.Exam;

import org.hibernate.FlushMode;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;

import dao.ExamDAO;
import util.GenericHibernateDaoSupport;

@Repository("examDao")
public class ExamDAOImplementation extends GenericHibernateDaoSupport implements ExamDAO {

	@Override
	public void save(Exam exam) throws ConstraintViolationException, Exception {
		// TODO Auto-generated method stub
		getHibernateTemplate().getSessionFactory().getCurrentSession().setFlushMode(FlushMode.COMMIT);
		Transaction tx = null;
		try
		{
			tx = getHibernateTemplate().getSessionFactory().getCurrentSession().beginTransaction();
			getHibernateTemplate().saveOrUpdate(exam);
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
	public void update(Exam exam) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Exam exam) {
		// TODO Auto-generated method stub
		
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Exam findExam(int exam_id) throws IllegalArgumentException{
		// TODO Auto-generated method stub
		List<Exam> userList = (List<Exam>) getHibernateTemplate().find("from Exam  where examId = ? ", (int)exam_id);
		if (userList == null || userList.size() == 0)
		{
			throw new IllegalArgumentException("Exam doesnt exists");
		}
		return (Exam) userList.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Exam> findAllExams() throws IllegalArgumentException {
		// TODO Auto-generated method stub
		List<Exam> userList = (List<Exam>) getHibernateTemplate().find("from Exam order by examId");
		if (userList == null || userList.size() == 0)
		{
			throw new IllegalArgumentException("Exam List doesnt exists");
		}
		return userList;
	}



}
