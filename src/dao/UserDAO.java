package dao;

import model.User;

public interface UserDAO {
	
	void save(User user);
	void update(User user);
	void delete(User user);
	User findUser(String userName);


}
