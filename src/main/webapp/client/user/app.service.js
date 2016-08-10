/**
 * 
 */
userApp.service('EmployeeUpdater', function() {
	this.myFunc = function($scope, $http) {
		/*
		 * $scope.names = [ {name:'Jani',country:'Norway',id:'1'}];
		 */

		var url = "api/getEmployeeDetails";
		$http.get(url).success(function(response) {
			$scope.employeeData = response;
			console.log($scope.employeeData);
			$scope.employeeData.mobileNo = (+ ($scope.employeeData.mobileNo));
			$scope.employeeData.employeeId = (+($scope.employeeData.employeeId));
			console.log($scope.employeeData.mobileNo + " "+$scope.employeeData.employeeId );
		});

		return $scope.employeeData;
	}

	this.update = function($scope, $http) {
		var url = "api/updateAccount?emailId=" + $scope.employeeData.emailId
				+ "&employeeId=" + $scope.employeeData.employeeId
				+ "&firstName=" + $scope.employeeData.firstName
				+ "&lastName=" + $scope.employeeData.lastName
				+ "&middleName=" + $scope.employeeData.middleName
				+ "&managerId=" + $scope.employeeData.managerId
				+ "&mobileNo=" + $scope.employeeData.mobileNo
				+ "&cityId=" + $scope.employeeData.cityId
				+ "&password=" + $scope.employeeData.password;
		$http.get(url).success(function(response) {
			$scope.data = response;
			console.log($scope.data);
		});

		return $scope.names;
	}
});