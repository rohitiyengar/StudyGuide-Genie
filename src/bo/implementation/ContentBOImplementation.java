package bo.implementation;

import java.util.List;

import model.Content;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.ContentDAO;
import bo.ContentBO;

@Service("contentBo")
@Transactional
public class ContentBOImplementation implements ContentBO{

	@Autowired
	ContentDAO contentDao;
	
	@Override
	public void save(Content content) throws ConstraintViolationException,
			Exception {
		// TODO Auto-generated method stub
		contentDao.save(content);
	}

	@Override
	public void update(Content content) {
		// TODO Auto-generated method stub
		contentDao.update(content);
	}

	@Override
	public void delete(Content content) {
		// TODO Auto-generated method stub
		contentDao.delete(content);
	}

	@Override
	public List<Content> findContentList(int exam_id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return contentDao.findContentList(exam_id);
	}

}
