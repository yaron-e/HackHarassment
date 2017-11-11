package com.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.entities.Users;
import com.entities.Word;
import com.google.gson.JsonArray;
import com.servicesapi.UsersService;
import com.servicesapi.WordService;
import com.sun.webkit.SharedBuffer;

import org.json.*;

import com.Shared.*;
/**
 * Servlet implementation class WebService
 */
@Controller
@RequestMapping("WebService")
public class WebService extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	WordService wordService;
	
	@Autowired
	UsersService userService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WebService() {
        super();
    }
    
    
    /**
     * Gets a list of all the words for a user
     * 
     *  Required variables:
     *  	username : required : Username of the user for whom to get the list of words
     *  
     *  Return: a JSON list with the key word 'list' and an array of words user has saved. 
     *  
     *  		"ERROR: username is empty!" = Indicated that the request did not pass a username value
     */
    @RequestMapping(value="/getAllWordForUser", method = RequestMethod.GET)
	public void getAllWordForUser(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	System.out.println("In WebService.getAllWordForUser");
    	
    	String username = request.getParameter("username");

    	if (StringUtil.isBlank(username)) {//User name variable is empty
    		response.getWriter().write("ERROR: username is empty!");
    		return;
    	} else {
    		Users u = userService.get(username);
    		if (u == null) {//This user does not exist
    			//Need to create an account for this user
    			u = new Users();
    			u.setUserName(username);
    			userService.saveOrUpdate(u);   
    			
    			//Return empty list
    			JSONObject obj = new JSONObject();
    			obj.put("list", new JsonArray());
    			response.getWriter().write(obj.toString());
    			return;
    		} else {//If user exists, get all the words they have saved into the DB
    			java.util.List<Word> words = wordService.getAllWordsForUser(username);
    			JSONArray array = new JSONArray(words);
    			JSONObject obj = new JSONObject();
    			obj.put("list", array);
    			response.getWriter().write(obj.toString());
    			return;
    		}
    	}
    	
    }
    

    /**
     * Adds a new word to the DB.
     * 
     * Requires variables:
     * 		username : required : with the username of the user of the twitter account.
     * 		word 	 : required : a single word that the specified user does not want to see anymore. 
     * 		platform : Optional : The name of the platform using 
     * 
     * @param request
     * @param response
     * @throws IOException 
     */
    @RequestMapping(value="/addNewWord", method = RequestMethod.GET)
	public void addNewWord(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	System.out.println("In WebService.addNewWord");
    	
    	String username = request.getParameter("username");
    	String word = request.getParameter("word");
    	String platform = request.getParameter("platform");
    	
    	
    	if (StringUtil.isBlank(username)) {//User name variable is empty
    		response.getWriter().write("ERROR: username is empty!");
    		return;
    	} else if (StringUtil.isBlank(word)) {//Word is empty
    		response.getWriter().write("ERROR: word is empty!");
    		return;
    	} else {//Everything is good, can continue    		
    		Users u = userService.get(username);//Check if the user exists
    		if (u == null) {//This user does not exist
    			//Need to create an account for this user
    			u = new Users();
    			u.setUserName(username);
    			userService.saveOrUpdate(u);    			
    		}
    		
    		Word w = new Word();
    		w.setUsername(username);
    		w.setWord(word);
    		w.setPlatform(platform);
    		
    		//Check if word already exists for this user
    		Word result = wordService.getWordForUser(w);
    		if (result == null) {
    			wordService.save(w);
    		} else {
    			System.out.println("Word already in DB for this user.");
    		}
    		response.getWriter().write("WebService.addNewWord = Saved word = '" + word + "' for user ='" + username + "'.");
    		return;
    	}   	    	
	}
    
    
    /**
     * Checks if a passed in sentence has any bad words that the user does not want to see.
     * 
     * Requires variables:
     * 		username : required : The username of the user to check sentence for.
     * 		sentence : required : The sentence that needs to be checked
     * 
     * @return	GOOD = the sentence does NOT have any bad words
     * 			BAD  = Found bad words that the user does not want to see
     * 			"ERROR: username is empty!" = Did not pass a valid value for the username variable
     * 			"ERROR: username is sentence!" = Did not pass a valid value for the sentence variable
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value="/checkWord", method = RequestMethod.GET)
	public void checkWord(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	System.out.println("In WebService.checkWord");
    	
    	String username = request.getParameter("username");
    	String sentence = request.getParameter("sentence");
    	
    	//Check that all variables are not empty
    	if (StringUtil.isBlank(username)) {//User name variable is empty
    		response.getWriter().write("ERROR: username is empty!");
    		return;
    	} else if (StringUtil.isBlank(sentence)) {
    		response.getWriter().write("ERROR: sentence is empty!");
    	} else {
    		Users u = userService.get(username);
    		if (u == null) {//This user does not exist
    			response.getWriter().write("500");	
    			System.out.println("WebService.checkWord = User with username '" + username + "' does not exist.");
    			return;
    		} else {//User does exist
    			List<Word> words = wordService.getAllWordsForUser(username);
    			
    			sentence = sentence.replaceAll("[^a-zA-Z0-9]", " ");
    			
    			for (Word w : words) {
    				if (sentence.contains(w.getWord())) {
    					response.getWriter().write("BAD");
    					System.out.println("WebService.checkWord = Found bad word = '" + w.getWord() + "' for user = '" + w.getUsername() + "'." );
    					return;
    				}
    			}    			
    			response.getWriter().write("GOOD");
    			return;
    		}
    	}    	
    }

    
    
    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
}