package dao.implementation;


import model.Student;

import org.hibernate.FlushMode;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;

import util.GenericHibernateDaoSupport;
import dao.StudentDAO;


@Repository("studentDao")
public class StudentDAOImplementation extends GenericHibernateDaoSupport implements StudentDAO {

	@Override
	public void save(Student student) {
		getHibernateTemplate().getSessionFactory().getCurrentSession().setFlushMode(FlushMode.COMMIT);
		Transaction tx = null;
		try
		{
			tx = getHibernateTemplate().getSessionFactory().getCurrentSession().beginTransaction();
			getHibernateTemplate().save(student);
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
	public void update(Student student) {
		getHibernateTemplate().getSessionFactory().getCurrentSession().setFlushMode(FlushMode.COMMIT);
		getHibernateTemplate().update(student);		
	}

	@Override
	public void delete(Student student) {
		getHibernateTemplate().getSessionFactory().getCurrentSession().setFlushMode(FlushMode.COMMIT);
		getHibernateTemplate().delete(student);

	}

	@Override
	public Student findStudent(String student_id) {
		// TODO Auto-generated method stub
		return null;
	}


}
