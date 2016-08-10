package com.accolite.library.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accolite.library.dao.*;
import com.accolite.library.model.*;

@Service
public class ResourceService {

	@Autowired
	private ResourceDao resourceDao = new ResourceDao();

	public ResourceDao getResourceDao() {
		return resourceDao;
	}

	public void setResourceDao(ResourceDao resourceDao) {
		this.resourceDao = resourceDao;
	}

	public boolean InsertNewBook(Resource book) {
		if (resourceDao.InsertBook(book))
			return true;
		else
			return false;

	}

	public int removeResource(int resourceId) {
		return (resourceDao.removeResource(resourceId));
	}

	public boolean insertAuthor(Author author) {
		return resourceDao.insertAuthor(author);
	}

	public ArrayList<Author> getAllAuthors(){
		return resourceDao.getAllAuthors();
	}
	
	public ArrayList<Resource> getBooksLocation(String locationName) {
		
		ArrayList<Resource> list = createDistinct(resourceDao.getAllBooksLocation(locationName));
		return list;
	}

	public ArrayList<Resource> getBooksTitle(String titleName) {
		ArrayList<Resource> list = createDistinct(resourceDao.getAllBooksTitle(titleName));
		return list;
	
	}

	public boolean insertNewTitle(Title title) {
		if (resourceDao.insertTitle(title))
			return true;
		else
			return false;

	}

	public boolean insertNewReview(Review review) {
		if (resourceDao.insertReview(review))
			return true;
		else
			return false;

	}

	public List<Integer> getReviewTitleId(String email) {
		List<Integer> list = new ArrayList<Integer>();
		list = resourceDao.getAllReviewTitleId(email);
		return list;
	}

	public int updateOldReview(Review review) {
		return (resourceDao.updateReview(review));
	}

	public boolean insertResourceType(ResourceType resourceType) {
		return resourceDao.insertResourceType(resourceType);
		
	}

	public ArrayList<ResourceType> getAllResourceTypes() {
		return resourceDao.getAllResourceTypes();
	}

	public ArrayList<Title> getAllTitles() {
		return resourceDao.getAllTitles();
	}

	public boolean insertTopic(Topic topic) {
		return resourceDao.insertTopic(topic);
	}

	public ArrayList<Topic> getAllTopics() {
		return resourceDao.getAllTopics();
	}

	public ArrayList<Resource> getBooksTopic(String topicName) {
		ArrayList<Resource> list = createDistinct(resourceDao.getAllBooksTopic(topicName));
		return list;
		
	}
	
	private ArrayList<Resource> createDistinct(ArrayList<Resource> originalList){
		
		//creating an arrayList to store distinct titles
		ArrayList<Resource> distinctList = new ArrayList<Resource>();
		//distinct list iterator
		Iterator<Resource> originalIterator = originalList.iterator();
		
		while(originalIterator.hasNext()){
			Resource originalResource = originalIterator.next();
			Iterator<Resource> distinctIterator = distinctList.iterator();
			
			//flag to check of the titleId is already present
			boolean hit = false;
			while(distinctIterator.hasNext()){
				Resource distinctResource = distinctIterator.next();
				
				if(originalResource.getTitleId() == distinctResource.getTitleId()){
					hit = true;
					
					//checking if at least one resource in original list is unallocated when the all previous resources of
					//same titleId are allocated
					if((distinctResource.getAllocated() == 1 )&& (originalResource.getAllocated() == 0)){
						distinctResource.setAllocated(0);
					}
				}
				
			}
			
			if(!hit){
				originalResource.setResourceId(0);
				distinctList.add(originalResource);
			}
			
		}
		
		return distinctList;
	}

}
