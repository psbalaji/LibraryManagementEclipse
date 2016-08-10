app.config(function($routeProvider) {
    $routeProvider
    .when("/", {
        templateUrl : "./client/login/login.html"
    })
    .otherwise({
        template : "<h1>None</h1><p>Nothing has been selected,</p>"
    });
});

app.config(['GoogleSigninProvider', function(GoogleSigninProvider) {
     GoogleSigninProvider.init({
        client_id: '999739768442-0rctm8u0igcqtr96dppr2ftur64jhp18.apps.googleusercontent.com',
     });
}]);