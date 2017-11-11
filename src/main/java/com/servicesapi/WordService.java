package com.servicesapi;

import java.util.List;

import org.springframework.stereotype.Service;

import com.entities.Word;

@Service
public interface WordService {
	public boolean save(Word word);
	public boolean saveOrUpdate(Word word);
	public boolean delete(Word word);
	public List<Word> getAllWordsForUser(String username);
	public Word getWordForUser(Word w);
}
