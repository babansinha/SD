/**
 * @ngdoc function
 * @name translateApp.directive:LanguageSelectDirective
 * @description
 * # LanguageSelectDirective
 * Directive to append language select and set its view and behavior
 */
angular.module('demoApp')
  .directive('ngTranslateLanguageSelect', function (LocaleService) {
    'use strict';

    return {
      restrict: 'A',
      replace: true,
      template: ''+
        '<div class="language-select" ng-if="visible">'+
          '<label>'+
            '{{"directives.language-select.Language" | translate}}:'+
            '<select ng-model="currentLocaleDisplayName"'+
              'ng-options="localesDisplayName for localesDisplayName in localesDisplayNames"'+
              'ng-change="changeLanguage(currentLocaleDisplayName)">'+
            '</select>'+
          '</label>'+
        '</div>'+
      '',
      controller: function ($scope) {
    	if (LocaleService.getLocaleDisplayName() === undefined) {
    		LocaleService.setLocaleByDisplayName('English');
    	} 
    	
    	$scope.currentLocaleDisplayName = LocaleService.getLocaleDisplayName();
        $scope.localesDisplayNames = LocaleService.getLocalesDisplayNames();
        $scope.visible = $scope.localesDisplayNames &&
        $scope.localesDisplayNames.length > 1;
        console.log("$scope.currentLocaleDisplayName :", $scope.currentLocaleDisplayName);
        console.log("LocaleService ", LocaleService);

        $scope.changeLanguage = function (locale) {
        	console.log("locale : ", locale);
          LocaleService.setLocaleByDisplayName(locale);
        };
      }
    };
  });
