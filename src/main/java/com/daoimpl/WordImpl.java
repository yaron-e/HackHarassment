package com.daoimpl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.daoapi.WordDao;
import com.entities.Word;

@Repository
@Transactional
public class WordImpl implements WordDao{
	
	@Autowired
	SessionFactory session;

	public boolean save(Word word) {
		session.getCurrentSession().save(word);
		return true;
	}

	public boolean saveOrUpdate(Word word) {
		session.getCurrentSession().saveOrUpdate(word);
		return true;	
	}

	public boolean delete(Word word) {
		try{
			session.getCurrentSession().delete(word);
		}catch(Exception ex){
			return false;
		}
		
		return true;
	}

	@SuppressWarnings("unchecked")
	public List<Word> getAllWordsForUser(String username) {
		return (List<Word>) session.getCurrentSession()
				.createQuery("from Word where username=?1")
				.setParameter("1", username)
				.list();
	}

	public Word getWordForUser(Word w) {
		return (Word) session.getCurrentSession()
				.createQuery("from Word where username=?1 and word=?2")
				.setParameter("1", w.getUsername())
				.setParameter("2", w.getWord())
				.uniqueResult();
	}
}
