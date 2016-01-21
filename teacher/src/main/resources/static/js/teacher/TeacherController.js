//Define an angular module for our app
var teacherController = angular.module('TeacherController',
		[  ]);

teacherController.controller('TeacherController', function($scope, $rootScope, $location, $http) {
	console.log("hello. . .");
	
	$http.get('../../user').success(function(data) {
		console.log("data : ", data);
		if (data.firstName) {
			$scope.authenticated = true;
			$scope.user = data.firstName
			$http.get('/service/').success(function(data) {
				$scope.greeting = data;
			})
		} else {
			$scope.authenticated = false;
		}
	}).error(function() {
		$scope.authenticated = false;
	});
	
	$scope.home = function() {
		console.log("home. . .");
		$location.url = "/home";
	};
	
	$scope.aboutUs = function() {
		$location.url = "/aboutUs";
	};

});