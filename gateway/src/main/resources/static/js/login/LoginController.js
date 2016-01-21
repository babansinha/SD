//Define an angular module for our app
var loginController = angular.module('LoginController',
		[ 'auth' ]);

loginController.controller('LoginController', function($scope, $rootScope, $location,
		auth) {
	$rootScope.initialized = true;
	$scope.credentials = {};

	$scope.authenticated = function() {
		return auth.authenticated;
	}
    
	if (auth.authenticated) {
		$location.path("/home");
	}
	
	$scope.login = function() {
		auth.authenticate($scope.credentials, function(authenticated, data) {
			if (authenticated) {
				$scope.error = false;
				//$location.path("/home");
				$scope.redirect(data);
			} else {
				console.log("Login failed")
				$scope.error = true;
			}
		})
	};

	$scope.logout = auth.clear;

	$scope.showSignInPage = function($scope, $location) {
		$location.url = "/login";
	}
	
	$scope.redirect = function(data) {
		if (data.role.role === 'ROLE_ADMIN') {
			var origin = location.protocol + "//" + location.host + "/admin/"; 
			window.location.href = origin;
		} else if (data.role.role === 'ROLE_TEACHER') {
			var origin = location.protocol + "//" + location.host + "/teacher/"; 
			window.location.href = origin;
		} else if (data.role.role === 'ROLE_STUDENT') {
			var origin = location.protocol + "//" + location.host + "/student/"; 
			window.location.href = origin;
		} else if (data.role.role === 'ROLE_PARENTS') {
			var origin = location.protocol + "//" + location.host + "/parents/"; 
			window.location.href = origin;
		} else if (data.role.role === 'ROLE_USER') {
			var origin = location.protocol + "//" + location.host + "/user/"; 
			window.location.href = origin;
		} else {
			$scope.authenticated = false;
		}
	}

});