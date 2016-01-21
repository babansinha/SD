angular.module("demoApp", 
		['LoginController', 
		 'LoginRoute', 
		 'homeController', 
		 'footerDirective', 
		 'LoadingInterceptor', 
		 'ErrorController', 
		 'ErrorRoute',
		 'LocaleService',
		 	//'ngAnimate',
		    'ngCookies',
		    //'ngResource',
		    //'ngRoute',
		    //'ngSanitize',
		    //'ngTouch',
		    'pascalprecht.translate',
		    'tmh.dynamicLocale'
		    
		 ])
.config(['$httpProvider', function($httpProvider) {
    $httpProvider.interceptors.push('LoadingInterceptor');
	$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
	$httpProvider.defaults.withCredentials = true;
	// Tough luck: the default cookie-to-header mechanism is not working for cross-origin requests!
	$httpProvider.defaults.xsrfCookieName = 'CSRF-TOKEN'; // The name of the cookie sent by the server
	$httpProvider.defaults.xsrfHeaderName = 'X-CSRF-TOKEN'; // The default header name picked up by Spring Security
}])
.constant('DEBUG_MODE', true)
.constant('VERSION_TAG', new Date().getTime())
.constant('LOCALES', {
  'locales': {
    'RU': 'Русский',
    'en_US': 'English',
    'hi': 'हिंदी'
  },
  'preferredLocale': 'en_US'
})
// Angular debug info
.config(function ($compileProvider, DEBUG_MODE) {
  if (!DEBUG_MODE) {
      $compileProvider.debugInfoEnabled(false);// disables AngularJS debug info
   }
})
// Angular Translate
.config(function ($translateProvider, DEBUG_MODE, LOCALES) {
    if (DEBUG_MODE) {
      $translateProvider.useMissingTranslationHandlerLog();// warns about missing translates
}

  $translateProvider.useStaticFilesLoader({
	  prefix: 'resources/locale-',
	  suffix: '.json'
  });
    $translateProvider.preferredLanguage(LOCALES.preferredLocale);
    $translateProvider.useLocalStorage();
})

// Angular Dynamic Locale
.config(function (tmhDynamicLocaleProvider) {
	tmhDynamicLocaleProvider.localeLocationPattern('js/framework/locale/angular-locale_{{locale}}.js');
});