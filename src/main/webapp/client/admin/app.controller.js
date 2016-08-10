/**
 * 
 */
adminApp
		.controller('adminController', function($scope, $http) {
			$scope.userMessage = "Hello Admin";
		})
		.controller('addadminCtrl', function($scope, $http) {
			$scope.addAdmin = function() {
				console.log("in add admin");
				var obj = $scope.EmailId
				console.log(obj);
				var url = "api/Admin?emailId=" + obj;

				$http.post(url).success(function(response) {
					console.log(response.message);
					$scope.userMessage = response.message;
					$scope.EmailId = '';
					// window.alert("Added");
				});
			};
		})
		.controller('blacklistCtrl', function($scope, $http) {

			$scope.blacklist = function() {
				var obj = $scope.EmailId
				console.log("in add blacklist");
				var url = "api/blacklist?emailId=" + obj;

				$http.get(url).success(function(response) {
					// $scope.cities = response;
					// windows.alert("BlackListed");
					console.log(response.message);
					$scope.userMessage = response.message;
					$scope.EmailId = '';
				});
			};
		})

		.controller('rmvadminCtrl', function($scope, $http) {

			$scope.rmvAdmin = function() {
				console.log("In remove admin");
				var obj = $scope.EmailId
				var url = "api/Admin?emailId=" + obj;
				$http.get(url).success(function(response) {
					// $scope.cities = response;
					// windows.alert("Removed");
					console.log(response.message);
					$scope.userMessage = response.message;
					$scope.EmailId = '';
				});
			};
		})

		.controller('rmvblacklistCtrl', function($scope, $http) {

			$scope.rmvblacklist = function() {
				console.log("in remove blacklist");
				var obj = $scope.EmailId
				var url = "api/blacklist?emailId=" + obj;

				$http.post(url).success(function(response) {
					// $scope.cities = response;
					// windows.alert("Removed");
					console.log(response.message);
					$scope.userMessage = response.message;
					$scope.EmailId = '';
				});
			};
		})

		.controller('addTopic', function($scope, $http) {
			$scope.insertTopic = function() {

				var url = "api/insertTopic?topicName=" + $scope.topicName;
				console.log("in insertTopic");
				$http.get(url).success(function(response) {
					console.log(response.message);
					$scope.userMessage = response.message;
				});

			};
		})

		.controller('addAuthor', function($scope, $http) {
			console.log("adding author");
			$scope.insertAuthor = function() {
				console.log("adding author");
				var url = "api/insertAuthor?authorName=" + $scope.authorName;

				$http.get(url).success(function(response) {
					console.log(response.message);
					$scope.userMessage = "added successfully";
				});

			};
		})

		.controller(
				'addResourceType',
				function($scope, $http) {
					$scope.insertResourceType = function() {

						var url = "api/insertResourcetype?resourceName="
								+ $scope.resourceName;

						$http.get(url).success(function(response) {
							console.log(response.message);
							$scope.userMessage = response.message;
						});
					};
				})

		.controller(
				'addTitle',
				function($scope, $http) {
					$scope.insertTitle = function() {
						var url = "api/insertTitle?authorId=" + $scope.authorId
								+ "&titleName=" + $scope.title + "&topicId="
								+ $scope.topicId;
						$http.get(url).success(function(response) {
							console.log(response.message);
							$scope.userMessage = response.message;
						});
					};

					$scope.authors = function() {
						var url = "api/getAllAuthor";
						console.log("in authors of add title");
						$http.get(url).success(function(response) {
							$scope.authors = response;
							console.log($scope.authors);
						});
					};
					$scope.authors();
					$scope.topics = function() {
						var url = "api/getAlltopics";
						console.log("in topics of add title");
						$http.get(url).success(function(response) {
							$scope.topics = response;
							console.log($scope.topics);
						});
					};
					$scope.topics();
				})
		.controller(
				'addResource',
				function($scope, $http) {
					$scope.insertResource = function() {

						var url = "api/insertResource?titleId="
								+ $scope.titleId + "&locationId="
								+ $scope.cityId + "&typeId="
								+ $scope.resourceId;

						$http.get(url).success(function(response) {
							console.log(response.message);
							$scope.userMessage = response.message;
						});

					};
					$scope.titles = function() {
						var url = "api/getAllTitles";
						$http.get(url).success(function(response) {
							$scope.titles = response;
							console.log($scope.titles);
						});
					};
					$scope.titles();

					$scope.cities = function() {
						var url = "api/getCity";
						$http.get(url).success(function(response) {
							$scope.cities = response;
							console.log($scope.cities);
						});
					};
					$scope.cities();

					$scope.resourcetype = function() {
						var url = "api/getAllResourceTypes";
						$http.get(url).success(function(response) {
							$scope.resourceTypes = response;
							console.log($scope.resourceTypes);
						});
					};
					$scope.resourcetype();
				})
		.controller(
				'namesCtrl',
				function($scope, $http, EmployeeUpdater) {
					$scope.suggestClicked = false;
					$scope.titleNameSuggested = '';
					$scope.userMessage = '';

					$scope.suggestFriend = function(friendEmailId) {
						var url = "api/suggestBook?fEmailId=" + friendEmailId
								+ "&titleName=" + $scope.titleNameSuggested;
						console.log($scope.friendEmailId);
						$http.get(url).success(function(response) {
							$scope.suggestClicked = false;
						});

					}

					$scope.suggest = function(suggestedName) {
						$scope.suggestClicked = true;
						console.log('hello' + suggestedName);
						$scope.titleNameSuggested = suggestedName;
					}

					$scope.getEmployee = function() {
						EmployeeUpdater.myFunc($scope, $http);

					}
					$scope.updateEmployee = function() {
						EmployeeUpdater.update($scope, $http);
					}
					$scope.getEmployee();
					$scope.buttonClicked = '';
					$scope.getBookLocation = function() {
						var dataLocation = $scope.Name;
						console.log("in getBookByLocation");
						var url = "api/getBookByLocation?locationName="
								+ dataLocation;
						$http.get(url).success(function(response) {
							$scope.data = response;
							console.log(response.data);
							console.log($scope.data);
							$scope.buttonClicked = 'location';
						});
					}

					$scope.getBookTitle = function() {
						var dataTitle = $scope.Name;
						var url = "api/getBookByTitle?titleName=" + dataTitle;
						$http.get(url).success(function(response) {
							$scope.data = response;
							console.log(response.data);
							console.log($scope.data);
							$scope.buttonClicked = 'title';
						});
					}

					$scope.getBookTopic = function() {
						var dataTopic = $scope.Name;
						var url = "api/getBookByTopic?topicName=" + dataTopic;
						// var url = "sample.json";
						$http.get(url).success(function(response) {
							$scope.data = response;
							console.log(response.data);
							console.log($scope.data);
							$scope.buttonClicked = 'topic';
						});
					}
					$scope.update = function(TitleID) {
						console.log("update called on:" + TitleID)
						var url = "api/requestBook?titleId=" + TitleID;
						$http.get(url).success(
								function(response) {
									console.log("in response");
									$scope.data = response;
									console.log($scope.data);
									console.log($scope.buttonClicked);
									$scope.userMessage = "Book request was "
											+ $scope.data.message;
									if ($scope.buttonClicked
											.localeCompare('title') == 0) {
										$scope.getBookTitle();
									} else if ($scope.buttonClicked
											.localeCompare('location') == 0) {
										$scope.getBookLocation();
									} else if ($scope.buttonClicked
											.localeCompare('topic') == 0) {
										$scope.getBookTopic();
									}
								});
					}

				})
		.controller(
				'returnController',
				function($scope, $http) {
					console.log("in return controller");
					$scope.returnResource = function(transactionId, resourceId) {
						console.log(transactionId);
						var url = "api/returnResource?transactionId="
								+ transactionId + "&resourceId=" + resourceId;
						console.log("in borrowedList");
						$http.get(url).success(function(response) {
							$scope.data = response;
							console.log($scope.data);
							$scope.borrowedList();
						});
					}

					$scope.borrowedList = function() {
						var url = "api/borrowedList";
						console.log("in borrowedList");
						$http.get(url).success(function(response) {
							$scope.data = response;
							console.log("borrow response");
							console.log($scope.data[0].resourceId);
						});

					}

					$scope.borrowedList();

				})
		.controller('summaryLocationController', function($scope, $http) {
			$scope.summaryByLocation = function() {
				var url = "api/summaryByLocation";

				$http.get(url).success(function(response) {
					$scope.data = response;
					console.log($scope.cities);
				});
			};

			$scope.summaryByLocation();

		})
		.controller('summaryTopicController', function($scope, $http) {

			$scope.summaryByTopic = function() {
				var url = "api/summaryByTopic";

				$http.get(url).success(function(response) {
					$scope.data = response;
					console.log($scope.cities);
				});
			};
			$scope.summaryByTopic();
		})
		.controller(
				'resourceRequestController',
				function($scope, $http) {

					$scope.getAllRequests = function() {
						var url = "api/getAllProcessingTransactions";

						$http.get(url).success(function(response) {
							$scope.data = response;
						});
					};
					$scope.getAllRequests();
					$scope.accept = function(transactionId, titleId) {
						var url = "api/approveRequest?transactionId="
								+ transactionId + "&titleId=" + titleId;

						$http.get(url).success(function(response) {
							$scope.data = response;
							$scope.getAllRequests();
						});
					};
					$scope.reject = function(transactionId, titleId) {
						var url = "api/rejectRequest?transactionId="
								+ transactionId;

						$http.get(url).success(function(response) {
							$scope.data = response;
							$scope.getAllRequests();
						});
					};
				})
		.controller(
				'borrowedbydateCtrl',
				function($scope, $http) {

					function convertDate(inputFormat) {
						function pad(s) {
							return (s < 10) ? '0' + s : s;
						}
						var d = new Date(inputFormat);
						return [ pad(d.getFullYear()), pad(d.getMonth() + 1),
								d.getDate() ].join('/');
					}

					$scope.borrowedbyDate = function() {
						var startDate = convertDate($scope.startDate);
						var endDate = convertDate($scope.endDate);
						console.log(startDate);
						console.log(endDate);
						var url = "/libraryManagement/api/borrowedListByDate?startDate="
								+ startDate + "&enddate=" + endDate;

						$http.get(url).success(function(response) {
							$scope.data = response;
							// window.alert("Added");
						});
					};
				})
		.controller(
				'borrowedbylocCtrl',
				function($scope, $http) {

					$scope.borrowedbyloc = function() {
						var obj = $scope.location;
						var url = "/libraryManagement/api/borrowedListByLocation?locationName="
								+ obj;

						$http.get(url).success(function(response) {
							$scope.data = response;
							console.log($scope.data);
							// window.alert("Added");
						});
					};
				})
		.controller(
				'borrowedbytitleCtrl',
				function($scope, $http) {

					$scope.borrowedbytitle = function() {
						var obj = $scope.TitleName;
						var url = "/libraryManagement/api/borrowedListByTitle?titleName="
								+ obj;

						$http.get(url).success(function(response) {
							$scope.data = response;
							console.log($scope.data);
							// window.alert("Added");
						});
					};
				}).controller(
				'addCity',
				function($scope, $http) {
					$scope.insertCity = function() {
						// var obj = $scope.TitleName;
						console.log("city: " + $scope.cityName + " "
								+ $scope.stateName + " " + $scope.countryName);
						var url = "/libraryManagement/api/addCity?cityName="
								+ $scope.cityName + "&stateName="
								+ $scope.stateName + "&country="
								+ $scope.countryName;

						$http.get(url).success(function(response) {
							$scope.data = response;
							console.log($scope.data);
							// window.alert("Added");
						});
					}
				}).controller(
						'removeResourceController',
						function($scope, $http) {
							$scope.userMessage = '';
							$scope.removeResource= function() {
								// var obj = $scope.TitleName;
								console.log("resourceId: " + $scope.resourceId);
								var url = "api/removeResource?resourceId="
										+ $scope.resourceId ;

								$http.get(url).success(function(response) {
									$scope.data = response;
									alert($scope.data.message);
									console.log($scope.data);
									$scope.resourceId = '';
									$scope.userMessage = $scope.data.message;
									// window.alert("Added");
								});
							}
						}).controller(
				'StarCtrl',
				['$scope','$http',
						function($scope, $http) {
							console.log("In star controller");
							$scope.mytextarea;
							$scope.rating = 0;
							$scope.display = "false";
							$scope.ratings = [ {
								current : 1,
								max : 5
							} ];
							$scope.getBook = function() {
								var dataLocation = $scope.Name;
								var url = "api/borrowedList";
								$http.get(url).success(function(response) {
									$scope.data = response;

								})
							};

							$scope.getBook();
							$scope.review = function(titleId) {
								$scope.title = titleId;
								$scope.display = "true";
							};

							$scope.getSelectedRating = function(rating) {
								console.log(rating);
							}

							$scope.setMinrate = function() {
								$scope.ratings = [ {
									current : 1,
									max : 5
								} ];
							}

							$scope.setMaxrate = function() {
								$scope.ratings = [ {
									current : 5,
									max : 5
								} ];
							}

							$scope.sendRateandReview = function() {

								alert("Thanks for your rates!\n\nRating: "
										+ $scope.ratings[0].current + "/"
										+ $scope.ratings[0].max);
								var url = "api/insertReview?TitleId?="
										+ $scope.TitleId + "&Rating="
										+ $scope.ratings[0].current
										+ "$Review=" + $scope.mytextarea;
								$http.get(url).success(function(response) {
									$scope.data = response;
									console.log($scope.data);
								});
							};

						} ]);
;

adminApp
		.directive(
				'starRating',
				function() {
					return {
						restrict : 'A',
						template : '<ul class="rating">'
								+ '<li ng-repeat="star in stars" ng-class="star" ng-click="toggle($index)">'
								+ '\u2605' + '</li>' + '</ul>',
						scope : {
							ratingValue : '=',
							max : '=',
							onRatingSelected : '&'
						},
						link : function(scope, elem, attrs) {

							var updateStars = function() {
								scope.stars = [];
								for (var i = 0; i < scope.max; i++) {
									scope.stars.push({
										filled : i < scope.ratingValue
									});
								}
							};

							scope.toggle = function(index) {
								scope.ratingValue = index + 1;
								scope.onRatingSelected({
									rating : index + 1
								});
							};

							scope.$watch('ratingValue',
									function(oldVal, newVal) {
										if (newVal) {
											updateStars();
										}
									});
						}
					}
				})
				
				
				;