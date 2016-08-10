/**
 * 
 */

userApp.config(function($routeProvider) {
    $routeProvider
    .when("/", {
        templateUrl : "./client/user/html/LocationSearch.html"
    })
    .when("/updateAccount", {
        templateUrl : "./client/user/html/updateAccount.html"
    })
    .when("/searchBooks", {
        templateUrl : "./client/user/html/LocationSearch.html"
    })
    .when("/return", {
        templateUrl : "./client/user/html/Return.html"
    })
    .otherwise({
        template : "<h1>Hey</h1><p>Page development is under progress,</p>"
    });
});