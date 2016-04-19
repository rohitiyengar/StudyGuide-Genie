package dao.implementation;


import java.util.List;

import model.Student;
import model.User;

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
			getHibernateTemplate().saveOrUpdate(student);
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
	public Student findStudent(String student_id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		List<Student> studList = (List<Student>) getHibernateTemplate().find("from Student where studentId = ? ", student_id);
		if (studList == null || studList.size() == 0)
		{
			throw new IllegalArgumentException("Student doesnt exists");
		}
		return (Student) studList.get(0);
	}


}
