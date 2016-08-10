app.controller('libraryController', function($scope, $http) {
	$scope.options = {
		'onsuccess' : function(response) {
			console.log(response);
		}
	};
	$scope.validate = function() {
		if ($scope.pass === $scope.repass) {
			console.log("passwords match");
			return true;
		} else {
			console.log("passwords donot match");
			return false;
		}
	};

	$scope.getCity = function() {
		var url = "api/getCity";

		$http.get(url).success(function(response) {
			$scope.cities = response;
			console.log(response.data);
			console.log($scope.cities);
		});
	};

}).controller(
		'AuthCtrl',
		[
				'$scope',
				'GoogleSignin',
				'$http',
				function($scope, GoogleSignin, $http) {

					$scope.authenticate = function(emailId, googleId,
							firstName, lastName) {
						var url = "api/GoogleLogin?emailId=" + emailId
								+ "&googleId=" + googleId + "&firstName="
								+ firstName + "&lastName=" + lastName;
						console.log("Sending Request to web server");

						$http.get(url).success(function(response) {
							console.log("response recieved");
							$scope.message = response.text;
							console.log($scope.message);

						});
					};

					$scope.login = function() {
						GoogleSignin.signIn().then(
								function(user) {
									console.log(user);
									console.log("User Signed In")
									var profile = user.getBasicProfile();
									var emailId = profile.Email();
									var firstName = profile.getName();
									var lastName = profile.getFamilyName();
									var googleId = profile.getId();
									console.log(user);
									authenticate(emailId, googleId, firstName,
											lastName);
								}, function(err) {
									alert("Error Occured during login");
									console.log(err);
								});
					};

					$scope.signout = function() {
						var auth2 = gapi.auth2.getAuthInstance();
						auth2.signOut().then(function() {
							console.log('User signed out.');
						});
					};
				} ]);