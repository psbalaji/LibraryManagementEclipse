package com.library.controller;

import java.util.ArrayList;
import java.util.List;

import com.accolite.library.model.Book;
import com.accolite.library.service.BookService;

public class BookController {
	BookService bookservice=new BookService();
	
	public void InsertBook(){
		Book book=new Book();
		
		book.setAllocated("Available");
		book.setLocationId(1);
		book.setTitleId(1);
		if(bookservice.InsertNewBook(book))
			System.out.println("Added Successfully");
		else
			System.out.println("error in adding");
		
}
	public void RemoveBook()
	{
		bookservice.RemoveOldBook(1);
	}
	
	public void getBookListLocation()
	{
		List<Book> list=new ArrayList<Book>();
		list=bookservice.getBooksLocation(1);
		for(int i=0;i<list.size();i++)
		{
			System.out.println("BookID is" + list.get(i).getBookId());
			System.out.println("TitleID is" + list.get(i).getTitleId());
			System.out.println("Availabilty is" + list.get(i).getAllocated());
			System.out.println("Location is" + list.get(i).getLocationId());
		}
		
	}
	
	public static void main(String args[])
	{
		BookController b=new BookController();
		b.InsertBook();
	}
	
	

}
