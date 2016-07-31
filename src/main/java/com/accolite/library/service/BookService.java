package com.accolite.library.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.accolite.library.dao.BookDao;
import com.accolite.library.model.Book;

@Service
public class BookService {
	
	private BookDao obj=new BookDao();
	
	public boolean InsertNewBook(Book book){
     if(obj.InsertBook(book))
    	 return true;
     else	
		return false;
		 
}	
 public int RemoveOldBook(int BookId)
 {
	 return(obj.RemoveBook(BookId));
 }
 
 public List<Book> getBooksLocation(int locationId)
 {
	 List<Book> list=new ArrayList<Book>();
	 list=obj.getAllbooks(locationId);
	 return list;
 }

}
