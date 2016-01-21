angular.module('gateway', ['ngMaterial'])
.config(function($httpProvider, $locationProvider) {

	$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
	
	// Tough luck: the default cookie-to-header mechanism is not working for cross-origin requests!
	$httpProvider.defaults.xsrfCookieName = 'XSRF-TOKEN'; // The name of the cookie sent by the server
	$httpProvider.defaults.xsrfHeaderName = 'X-XSRF-TOKEN'; // The default header name picked up 

	//$locationProvider.html5Mode(true).hashPrefix('#');
	
}).controller('navigation',

function($scope, $http, $location) {

	var authenticate = function(credentials, callback) {
console.log("credentials : ", credentials);
		var headers = credentials ? {
			authorization : "Basic "
					+ btoa(credentials.username + ":"
							+ credentials.password)
		} : {};

		$scope.user = ''
		$http.get('user', {
			headers : headers
		}).success(function(data) {
			console.log("data 1 : ", data);
			if (data.role.role === 'ROLE_ADMIN') {
				$scope.authenticated = true;
				$scope.user = data.firstName;
				var origin = location.protocol + "//" + location.host + "/admin/"; 
				window.location.href = origin;
			} else if (data.role.role === 'ROLE_TEACHER') {
				$scope.authenticated = true;
				$scope.user = data.firstName;
				
				var origin = location.protocol + "//" + location.host + "/teacher/"; 
				window.location.href = origin;
				//$location.path("/teacher")
			} else if (data.role.role === 'ROLE_STUDENT') {
				$scope.authenticated = true;
				$scope.user = data.firstName;
				
				var origin = location.protocol + "//" + location.host + "/student/"; 
				window.location.href = origin;
				//$location.path("/student")
			} else if (data.role.role === 'ROLE_PARENTS') {
				$scope.authenticated = true;
				$scope.user = data.firstName
				$location.path("/parents")
			} else if (data.role.role === 'ROLE_USER') {
				$scope.authenticated = true;
				$scope.user = data.firstName
				$location.path("/user")
			} else {
				$scope.authenticated = false;
			}
			callback && callback(true);
		}).error(function() {
			$scope.authenticated = false;
			callback && callback(false);
		});

	}

	authenticate();

	$scope.credentials = {};
	$scope.login = function() {
		authenticate($scope.credentials, function(authenticated) {
			$scope.authenticated = authenticated;
			$scope.error = !authenticated;
		})
	};

	$scope.logout = function() {
		$http.post('logout', {}).success(function() {
			$scope.authenticated = false;
		}).error(function(data) {
			console.log("Logout failed")
			$scope.authenticated = false;
		});
	}

});
