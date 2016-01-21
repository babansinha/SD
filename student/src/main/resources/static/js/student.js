angular.module('student', []).controller('studentController',

function($scope, $http) {
	
	$http.get('../../user').success(function(data) {
		if (data.firstName) {
			$scope.authenticated = true;
			$scope.user = data.firstName;
			$http.get('/service/').success(function(data) {
				$scope.greeting = data;
			})
		} else {
			$scope.authenticated = false;
		}
	}).error(function() {
		$scope.authenticated = false;
	});

});
