package bo;


import model.User;
import org.hibernate.exception.ConstraintViolationException;


public interface UserBO {
	
	void save(User user) throws ConstraintViolationException, Exception;
	void update(User user);
	void delete(User user);
	User findUser(String userName) throws IllegalArgumentException, Exception;

}
