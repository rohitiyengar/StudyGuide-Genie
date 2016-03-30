package bo.implementation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import org.springframework.transaction.annotation.Transactional;
import org.hibernate.exception.ConstraintViolationException;

import model.User;
import bo.UserBO;
import dao.UserDAO;


@Service("userBo")
@Transactional
public class UserBOImplementation implements UserBO{
	
	@Autowired
	UserDAO userDao;
	
	public void setUserDao(UserDAO userDao)
	{
		this.userDao = userDao;
	}

	@Override
	public void save(User user) throws ConstraintViolationException, Exception
	{
		userDao.save(user);
	}

	@Override
	public void update(User user) {
		userDao.update(user);		
	}

	@Override
	public void delete(User user) {
		userDao.delete(user);
		
	}

	@Override
	public User findUser(String userName) {
		// TODO Auto-generated method stub
		return userDao.findUser(userName);
	}

}
