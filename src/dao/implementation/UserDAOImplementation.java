package dao.implementation;



import java.util.List;

import org.hibernate.FlushMode;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;


import model.User;
import dao.UserDAO;
import util.GenericHibernateDaoSupport;

import org.hibernate.exception.ConstraintViolationException;

@Repository("userDao")
public class UserDAOImplementation extends GenericHibernateDaoSupport implements UserDAO {

	@Override
	public void save(User user) {
		getHibernateTemplate().getSessionFactory().getCurrentSession().setFlushMode(FlushMode.COMMIT);
		Transaction tx = null;
		try
		{
			tx = getHibernateTemplate().getSessionFactory().getCurrentSession().beginTransaction();
			getHibernateTemplate().saveOrUpdate(user);
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
	public void update(User user) {
		getHibernateTemplate().getSessionFactory().getCurrentSession().setFlushMode(FlushMode.COMMIT);
		getHibernateTemplate().update(user);		
	}

	@Override
	public void delete(User user) {
		getHibernateTemplate().delete(user);
	}

	@Override
	public User findUser(String userName) throws IllegalArgumentException, Exception {
		List<User> userList = (List<User>) getHibernateTemplate().find("from User where username = ? ", userName);
		if (userList == null || userList.size() == 0)
		{
			throw new IllegalArgumentException("User doesnt exists");
		}
		return (User) userList.get(0);
	}

}
