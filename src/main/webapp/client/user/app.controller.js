/*userApp.controller('libraryUserController', function($scope,$http) {
    $scope.searchBooks = function () {
        if($scope.getBy === "location"){

        }  
        else if($scope.getBy === "topic"){

        }

    };
     
})*/
userApp.controller('libraryUserController', function($scope, $http) {

	$scope.searchBooks = function() {
		if ($scope.getBy === "location") {

		} else if ($scope.getBy === "topic") {

		}

	};

}).controller('namesCtrl', function($scope, $http, EmployeeUpdater) {
	
	$scope.suggestClicked = false;
	$scope.titleNameSuggested = '';

	$scope.getEmployee = function() {
		EmployeeUpdater.myFunc($scope, $http);

	}
	$scope.updateEmployee = function() {
		EmployeeUpdater.update($scope, $http);
	}
	$scope.getEmployee();
	
	$scope.suggestFriend = function(friendEmailId) {
		var url = "api/suggestBook?fEmailId="
				+ friendEmailId+ "&titleName="
				+ $scope.titleNameSuggested;
		console.log($scope.friendEmailId);
		$http.get(url).success(function(response) {
			$scope.suggestClicked = false;
		});

	}

	$scope.buttonClicked = '';
	
	$scope.suggest = function(suggestedName) {
		$scope.suggestClicked = true;
		console.log('hello' + suggestedName);
		$scope.titleNameSuggested = suggestedName;
	}

	
	$scope.getBookLocation = function() {
		var dataLocation = $scope.Name;
		var url = "api/getBookByLocation?locationName=" + dataLocation;
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
		$http.get(url).success(function(response) {
			$scope.data = response;
			console.log($scope.data);
			console.log($scope.buttonClicked);
			$scope.userMessage = "Book request was "+$scope.data.message;
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
	

}).controller('returnController', function($scope, $http) {
	console.log("in return controller");
	$scope.returnResource = function(transactionId, resourceId) {
		console.log(transactionId);
		var url = "api/returnResource?transactionId="+transactionId+"&resourceId="+resourceId;
		console.log("in borrowedList");
		$http.get(url).success(function(response) {
			$scope.data = response;
			console.log($scope.data);
		});
	}

	$scope.borrowedList = function() {
		var url = "api/borrowedList";
		console.log("in borrowedList");
		$http.get(url).success(function(response) {
			$scope.data = response;
			console.log("borrow response");
//			console.log($scope.data[0].resourceId);
		});

	}

	$scope.borrowedList();

});
