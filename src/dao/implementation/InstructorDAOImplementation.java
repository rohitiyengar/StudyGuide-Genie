package dao.implementation;

import java.util.List;

import org.hibernate.FlushMode;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;

import model.Instructor;
import model.User;
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
		List<Instructor> userList = (List<Instructor>) getHibernateTemplate().find("from Instructor where instructorId = ? ", instructor_id);
		if (userList == null || userList.size() == 0)
		{
			throw new IllegalArgumentException("User doesnt exists");
		}
		return (Instructor) userList.get(0);
	}

}
