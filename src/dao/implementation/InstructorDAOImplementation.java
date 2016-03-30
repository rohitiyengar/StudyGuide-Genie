package dao.implementation;

import org.hibernate.FlushMode;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;

import model.Instructor;
import dao.InstructorDAO;
import util.GenericHibernateDaoSupport;


@Repository("instructorDao")
public class InstructorDAOImplementation extends GenericHibernateDaoSupport implements InstructorDAO {

	@Override
	public void save(Instructor instructor) {
		getHibernateTemplate().getSessionFactory().getCurrentSession().setFlushMode(FlushMode.COMMIT);
		Transaction tx = null;
		try
		{
			tx = getHibernateTemplate().getSessionFactory().getCurrentSession().beginTransaction();
			getHibernateTemplate().save(instructor);
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
	public void update(Instructor instructor) {
		getHibernateTemplate().getSessionFactory().getCurrentSession().setFlushMode(FlushMode.COMMIT);
		getHibernateTemplate().update(instructor);		
		
	}

	@Override
	public void delete(Instructor instructor) {
		getHibernateTemplate().getSessionFactory().getCurrentSession().setFlushMode(FlushMode.COMMIT);
		getHibernateTemplate().delete(instructor);
	}

	@Override
	public Instructor findInstructor(String instructor_id) {
		// TODO Auto-generated method stub
		return null;
	}

}
