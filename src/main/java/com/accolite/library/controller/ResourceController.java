package com.accolite.library.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.accolite.library.model.*;
import com.accolite.library.service.*;

@Controller
public class ResourceController {

	@Autowired
	private ResourceService resourceService;

	@Autowired
	private Author author;

	@Autowired
	private ResourceType resourceType;

	@Autowired
	private UserMessage userMessage;
	
	@Autowired
	private Topic topic;

	@Autowired
	private Resource resource;

	@RequestMapping(value = "/insertResource", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public UserMessage addResource(@RequestParam("titleId") int titleId, @RequestParam("locationId") int locationId,
			@RequestParam("typeId") int typeId) {

		resource.setAllocated(0);
		resource.setLocationId(locationId);
		resource.setTitleId(titleId);
		resource.setTypeId(typeId);

		if (resourceService.InsertNewBook(resource)){
			System.out.println("Added Successfully");
			userMessage.setMessage("Resource successfully added");			
		}
		else{
			System.out.println("error in adding");
			userMessage.setMessage("Resource successfully added");
		}
		return userMessage;
	}

	
	@RequestMapping(value = "/removeResource", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public UserMessage removeResource(@RequestParam("resourceId") int resourceId) {

		if(resourceService.removeResource(resourceId) > 0){
			userMessage.setMessage("Resource removed");
		}
		else{
			userMessage.setMessage("unable to remove resource");
		}
		return userMessage;
	}

	@RequestMapping(value = "/getBookByLocation", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ArrayList<Resource> getBookByLocation(@RequestParam("locationName") String locationName,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String emailId = (String) session.getAttribute("user");
		if (emailId != null) {
			locationName = locationName.toUpperCase();
			ArrayList<Resource> list = new ArrayList<Resource>();
			list = resourceService.getBooksLocation(locationName);
			for (int i = 0; i < list.size(); i++) {
				System.out.println("BookID is" + list.get(i).getResourceId());
				System.out.println("TitleID is" + list.get(i).getTitleId());
				System.out.println("Availabilty is" + list.get(i).getAllocated());
				System.out.println("Location is" + list.get(i).getLocationId());
			}

			return list;
		}
		else{
			try {
				response.sendRedirect("../index.html");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}

	@RequestMapping(value = "/getBookByTopic", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ArrayList<Resource> getBookByTopic(@RequestParam("topicName") String topicName) {

		topicName = topicName.toUpperCase();
		ArrayList<Resource> list = new ArrayList<Resource>();
		list = resourceService.getBooksTopic(topicName);
		for (int i = 0; i < list.size(); i++) {
			System.out.println("BookID is" + list.get(i).getResourceId());
			System.out.println("TitleID is" + list.get(i).getTitleId());
			System.out.println("Availabilty is" + list.get(i).getAllocated());
			System.out.println("Location is" + list.get(i).getLocationId());
		}

		return list;
	}

	@RequestMapping(value = "/insertAuthor", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public UserMessage insertAuthor(@RequestParam("authorName") String authorName, HttpServletRequest request) {
		authorName = authorName.toUpperCase();
		HttpSession session = request.getSession();
		if ((Boolean) session.getAttribute("isAdmin")) {
			author.setAuthorName(authorName.toUpperCase());
			if(resourceService.insertAuthor(author))
			{
				userMessage.setMessage("Author added");
			}
			else {
				userMessage.setMessage("Failed to add author");
			}
			
		}
		else {
			userMessage.setMessage("Failed to add author");
		}
		return userMessage;
	}

	@RequestMapping(value = "/insertTopic", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public UserMessage insertTopic(@RequestParam("topicName") String topicName, HttpServletRequest request) {

		topicName = topicName.toUpperCase();
		HttpSession session = request.getSession();
		if ((Boolean) session.getAttribute("isAdmin")) {
			topic.setTopicName(topicName.toUpperCase());
			if(resourceService.insertTopic(topic)){
				userMessage.setMessage("Topic inserted successfully");
			}
			else {
				userMessage.setMessage("Failed to add topic");
			}
			return userMessage;
		}
		else {
			userMessage.setMessage("Failed to add topic");
			return userMessage;
		}
	}

	@RequestMapping(value = "/insertResourcetype", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public UserMessage insertResourceType(@RequestParam("resourceName") String resourceName, HttpServletRequest request) {
		resourceName = resourceName.toUpperCase();
		HttpSession session = request.getSession();
		if ((Boolean) session.getAttribute("isAdmin")) {
			resourceType.setResourceName(resourceName.toUpperCase());
			if(resourceService.insertResourceType(resourceType)){
				userMessage.setMessage("Resource type succesfully added");
			}
			else {
				userMessage.setMessage("Failed to add resource type");
			}
			return userMessage;
		}
		else {
			userMessage.setMessage("Failed to add resource Type");
			return userMessage;
		}
	}

	@RequestMapping(value = "/getAllAuthor", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ArrayList<Author> getAllAuthors(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if ((Boolean) session.getAttribute("isAdmin")) {
			return resourceService.getAllAuthors();
		} else
			return null;
	}

	@RequestMapping(value = "/getAllResourceTypes", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ArrayList<ResourceType> getAllResourceTypes(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if ((Boolean) session.getAttribute("isAdmin")) {
			return resourceService.getAllResourceTypes();
		} else
			return null;
	}

	@RequestMapping(value = "/getAllTitles", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ArrayList<Title> getAllTitles(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if ((Boolean) session.getAttribute("isAdmin")) {
			return resourceService.getAllTitles();
		} else
			return null;
	}

	@RequestMapping(value = "/getBookByTitle", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ArrayList<Resource> getBookByTitle(@RequestParam("titleName") String titleName) {
		titleName = titleName.toUpperCase();

		ArrayList<Resource> list = new ArrayList<Resource>();
		list = resourceService.getBooksTitle(titleName);
		for (int i = 0; i < list.size(); i++) {
			System.out.println("BookID is" + list.get(i).getResourceId());
			// System.out.println("TitleID is" + list.get(i).getTitleId());
			System.out.println("Availabilty is" + list.get(i).getAllocated());
			// System.out.println("Location is" + list.get(i).getLocationId());
		}

		return list;
	}

	@RequestMapping(value = "/insertTitle", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public UserMessage insertTitle(@RequestParam("authorId") int authorId, @RequestParam("titleName") String titleName,
			@RequestParam("topicId") int topicId) {
		titleName = titleName.toUpperCase();
		Title title = new Title();
		title.setAuthorId(authorId);
		title.setTitleName(titleName);
		title.setTopicId(topicId);
		if (resourceService.insertNewTitle(title)){
			System.out.println("Added Successfully");
			userMessage.setMessage("title inserted successfully");
		}
		else{
			System.out.println("error in adding");
			userMessage.setMessage("Failed to add title");
		}
		return userMessage;
	}

	@RequestMapping(value = "/insertReview", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public void insertReview(@RequestParam("ReviewId") int ReviewId, @RequestParam("TitleId") int TitleId,
			@RequestParam("email") String email, @RequestParam("Rating") int Rating,
			@RequestParam("Review") String Review) {
		Review review = new Review();
		review.setReviewId(ReviewId);
		review.setTitleId(TitleId);
		review.setEmail(email);
		review.setRating(Rating);
		review.setReview(Review);
		if (resourceService.insertNewReview(review))

			System.out.println("Added Successfully");
		else
			System.out.println("error in adding");

	}

	@RequestMapping(value = "/UpdateReview", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public void updateReview(@RequestParam("ReviewId") int ReviewId, @RequestParam("TitleId") int TitleId,
			@RequestParam("email") String email, @RequestParam("Rating") int Rating,
			@RequestParam("Review") String Review) {
		Review review = new Review();
		review.setReviewId(ReviewId);
		review.setTitleId(TitleId);
		review.setEmail(email);
		review.setRating(Rating);
		review.setReview(Review);
		if (resourceService.updateOldReview(review) == 1)

			System.out.println("Added Successfully");
		else
			System.out.println("error in adding");

	}

	@RequestMapping(value = "/GettitleId", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public void getTitleId(@RequestParam("email") String email) {

		resourceService.getReviewTitleId(email);
	}

	@RequestMapping(value = "/getAlltopics", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ArrayList<Topic> getAllTopics(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if ((Boolean) session.getAttribute("isAdmin")) {
			return resourceService.getAllTopics();
		} else
			return null;
	}

}
