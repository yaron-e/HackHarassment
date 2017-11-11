package com.daoapi;

import java.util.List;

import com.entities.Word;

public interface WordDao {
	public boolean save(Word word);
	public boolean saveOrUpdate(Word word);
	public boolean delete(Word word);
	public List<Word> getAllWordsForUser(String username);
	public Word getWordForUser(Word w);
}
