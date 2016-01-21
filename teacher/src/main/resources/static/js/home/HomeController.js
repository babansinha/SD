//Define an angular module for our app
var homeController = angular.module('homeController', [ ]);

homeController.controller('homeController', function($scope, $rootScope, auth) {
	$rootScope.initialized = true;
	$scope.user = {};

	resourceCallback = function(data) {
		$scope.user = data;
	}

});