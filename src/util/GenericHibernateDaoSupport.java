package util;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

public class GenericHibernateDaoSupport extends HibernateDaoSupport {
	
	@Autowired
	public void setSessionFactoryBean(SessionFactory sessionFactory)
	{
		setSessionFactory(sessionFactory);
	}

}
