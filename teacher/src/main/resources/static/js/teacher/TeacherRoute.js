//Define an angular module for our app
var teacherRoute = angular.module('TeacherRoute', [ 'ngRoute' ]);
teacherRoute.config(
				function($routeProvider, $httpProvider) {//, $locationProvider) {
					//$locationProvider.html5Mode(true);

					$routeProvider.when('/home', {
						templateUrl : 'home.html',
						controller : 'TeacherController'
					}).when('/header.html', {
						templateUrl : 'header.html',
						controller : 'TeacherController'
					}).when('/aboutUs', {
						templateUrl : 'about-us.html',
						controller : 'TeacherController'
					}).when('/services', {
						templateUrl : 'services.html',
						controller : 'TeacherController'
					}).when('/portfolio', {
						templateUrl : 'portfolio.html',
						controller : 'TeacherController'
					}).when('/blog', {
						templateUrl : 'blog.html',
						controller : 'TeacherController'
					}).when('/contactUs', {
						templateUrl : 'contact-us.html',
						controller : 'TeacherController'
					}).when('/copywrite', {
						templateUrl : 'copy-write-terms.html',
						controller : 'TeacherController'
					}).otherwise({
						redirectTo : '/'
					});

					//$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

				}).run(function() {
				/*.run(function(auth) {

			// Initialize auth module with the home page and login/logout path
			// respectively
			auth.init('/', 'login', 'logout');*/

		});