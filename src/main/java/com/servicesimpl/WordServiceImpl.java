package com.servicesimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daoapi.WordDao;
import com.entities.Word;
import com.servicesapi.WordService;

@Service
public class WordServiceImpl implements WordService{

	@Autowired
	WordDao wordDao;
	
	public boolean save(Word word) {
		return wordDao.save(word);
	}

	public boolean saveOrUpdate(Word word) {
		return wordDao.saveOrUpdate(word);
	}

	public boolean delete(Word word) {
		return wordDao.delete(word);
	}

	public List<Word> getAllWordsForUser(String username) {
		return wordDao.getAllWordsForUser(username);
	}

	public Word getWordForUser(Word w) {
		return wordDao.getWordForUser(w);
	}
}
