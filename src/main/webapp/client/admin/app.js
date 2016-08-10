adminApp.config(function($routeProvider) {
	$routeProvider.when("/", {
		templateUrl : "./client/admin/html/search.html"
	}).when("/search", {
		templateUrl : "./client/admin/html/search.html"
	}).when("/addCity", {
		templateUrl : "./client/admin/html/AddCity.html"
	}).when("/review", {
		templateUrl : "./client/admin/html/Review.html"
	}).when("/updateAccount", {
		templateUrl : "./client/admin/html/updateAccount.html"
	}).when("/return", {
		templateUrl : "./client/admin/html/return.html"
	}).when("/resourceRequests", {
		templateUrl : "./client/admin/html/ResourceRequest.html"
	}).when("/removeResource", {
		templateUrl : "./client/admin/html/removeResource.html"
	}).when("/addAdmin", {
		templateUrl : "./client/admin/html/AddAdmin.html"
	}).when("/removeAdmin", {
		templateUrl : "./client/admin/html/RemoveAdmin.html"
	}).when("/addBlacklist", {
		templateUrl : "./client/admin/html/BlackList.html"
	}).when("/removeBlacklist", {
		templateUrl : "./client/admin/html/RemoveBlackList.html"
	}).when("/addAuthor", {
		templateUrl : "client/admin/html/addAuthor.html"
	}).when("/addResource", {
		templateUrl : "client/admin/html/addResource.html"
	}).when("/addResourceType", {
		templateUrl : "client/admin/html/addResourceType.html"
	}).when("/addTitle", {
		templateUrl : "client/admin/html/addTitle.html"
	}).when("/addTopic", {
		templateUrl : "client/admin/html/addTopic.html"
	}).when("/sumByLocation", {
		templateUrl : "client/admin/html/summaryLocation.html"
	}).when("/sumByTopic", {
		templateUrl : "client/admin/html/summaryTopic.html"
	}).when("/bod", {
		templateUrl : "./client/admin/html/bod.html"
	}).when("/borrowByTitle", {
		templateUrl : "client/admin/html/borrowByTitle.html"
	}).when("/borrowByLocation", {
		templateUrl : "client/admin/html/borrowByLocation.html"
	}).when("/borrowByDate", {
		templateUrl : "client/admin/html/borrowByDate.html"
	}).otherwise({
		template : "<h1>None</h1><p>Nothing has been selected,</p>"
	});
});