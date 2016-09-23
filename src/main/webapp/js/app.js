var app = angular.module('erApp', ['ngTagsInput','ngGrid','ngRoute','angularFileUpload','ngMaterial','blockUI', 'ui.utils.masks', 'ui.router', 'ui.grid','ui.grid.pagination', 'xeditable','ui.bootstrap', 'ui.bootstrap.datetimepicker', 'ui.select','ngSanitize','ngNotify','components','fcsa-number']);

app.config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {
    
	$urlRouterProvider.otherwise('/');
        
    $stateProvider
    .state('main', {url:'/', views: {'': {templateUrl: 'views/index.html', controller: 'dashboardCtrl'}},
    	resolve : {
        	permission: function(authorizationService,$route) {
        		return authorizationService.permissionCheck(["ROLE_HR","ROLE_RECRUITER","ROLE_INTERVIEWER","ROLE_MANAGER","ROLE_ADMIN","ROLE_USER"]);
               }
        }
    })
    .state('viewUser', {url:'/viewUser', views: {'': {templateUrl: 'views/viewUser.html', controller: 'editUserCtrl'}},
    	resolve : {
    		permission: function(authorizationService,$route) {
    			return authorizationService.permissionCheck(["ROLE_HR","ROLE_RECRUITER","ROLE_INTERVIEWER","ROLE_MANAGER","ROLE_ADMIN", "ROLE_USER","ROLE_LOCATIONHEAD"]);
            }
    	}})
    .state('routeForUnauthorizedAccess', {url:'/routeForUnauthorizedAccess', views: {'': {templateUrl: 'views/index.html'}}})
    
    .state('reportInfo', {url:'/reportInfo', views: {'': {templateUrl: 'views/reportInfo.html', controller: 'highChatCtrl'}},
    	resolve : {
    		permission: function(authorizationService,$route) {
    			return authorizationService.permissionCheck(["ROLE_HR","ROLE_RECRUITER","ROLE_INTERVIEWER","ROLE_MANAGER"]);
            }
    	}})  
		
		 
    .state('report', {url:'/report', views: {'': {templateUrl: 'views/report.html', controller: 'reportManagementCtrl'}},
    	resolve : {
    		permission: function(authorizationService,$route) {
    			return authorizationService.permissionCheck(["ROLE_HR","ROLE_RECRUITER","ROLE_INTERVIEWER","ROLE_MANAGER","ROLE_ADMIN"]);
            }
    	}
    })
     .state('searchReport', {url:'/searchReport', views: {'': {templateUrl: 'views/searchReport.html', controller: 'searchReportCntrl'}},
    	resolve : {
    		permission: function(authorizationService,$route) {
    			return authorizationService.permissionCheck(["ROLE_HR","ROLE_RECRUITER","ROLE_INTERVIEWER","ROLE_MANAGER","ROLE_ADMIN"]);
            }
    	}
    })
   .state('offer', {url:'/offer',abstract:true, views: {'': {templateUrl: 'views/offer/offer.html', controller: 'offerManagementCtrl'}},
    	resolve : {
    		permission: function(authorizationService,$route) {
    			return authorizationService.permissionCheck(["ROLE_HR","ROLE_RECRUITER","ROLE_INTERVIEWER","ROLE_MANAGER","ROLE_ADMIN"]);
            }
    	}
    })
   .state('offer.list', {url:'', views: {'': {templateUrl: 'views/offer/candidatesList.html', controller: 'offerManagementCtrl'}},
	   resolve : {
    		permission: function(authorizationService,$route) {
    			return authorizationService.permissionCheck(["ROLE_HR","ROLE_RECRUITER","ROLE_INTERVIEWER","ROLE_MANAGER","ROLE_ADMIN"]);
            }
    	}
    })
   .state('offer.createOffer', {url:'/createOffer', views: {'': {templateUrl: 'views/offer/createOffer.html', controller: 'createOfferCtrl'}},
    	resolve : {
    		permission: function(authorizationService,$route) {
    			return authorizationService.permissionCheck(["ROLE_HR","ROLE_RECRUITER","ROLE_INTERVIEWER","ROLE_MANAGER","ROLE_ADMIN"]);
            }
    	}
    })
        
}]);

app.config(function(blockUIConfig) {
	  blockUIConfig.message = 'Loading...';
});

app.directive('numbersOnly', function(){
	   return {
	     require: 'ngModel',
	     link: function(scope, element, attrs, modelCtrl) {
	       modelCtrl.$parsers.push(function (inputValue) {
	           if (inputValue == undefined) return '' 
	           var transformedInput = inputValue.replace(/[^0-9]/g, ''); 
	           if (transformedInput!=inputValue) {
	              modelCtrl.$setViewValue(transformedInput);
	              modelCtrl.$render();
	           }         

	           return transformedInput;         
	       });
	     }
	   };
	});

app.run(function(editableOptions) {
  editableOptions.theme = 'bs3';
});

app.directive('uiSelectRequired', function() {
	  return {
	    require: 'ngModel',
	    link: function(scope, elm, attrs, ctrl) {
	      ctrl.$validators.uiSelectRequired = function(modelValue, viewValue) {

	        var determineVal;
	        if (angular.isArray(modelValue)) {
	          determineVal = modelValue;
	        } else if (angular.isArray(viewValue)) {
	          determineVal = viewValue;
	        } else {
	          return false;
	        }

	        return determineVal.length > 0;
	      };
	    }
	  };
});

app.factory('navService', function() {
	var nav = {};
	nav.setActiveTab = function(value) {
		sessionStorage.setItem('active-navtab', value);
	}

	nav.getActiveTab = function() {
		return sessionStorage.getItem('active-navtab') == undefined ? "Dashboard" : sessionStorage.getItem('active-navtab');
	}
	return nav;

});

app.run(function($rootScope) {
	$rootScope.areEquals = function(str1, str2) {
		if ((str1 === null || str1 === undefined) && (str2 === null || str2 === undefined)) return true;
		if ((str1 === null || str1 === undefined) && (str2 != null || str2 != undefined)) return false;
		if ((str2 === null || str2 === undefined) && (str1 != null || str1 != undefined)) return false;
		return (str1.toLocaleLowerCase().trim() === str2.toLocaleLowerCase().trim());
	}
});


app.filter('stringArrayFilter', function() {
  return function(myArray) {
    return myArray.join(', ');
  };
});

app.factory('convertArray2Json', function() {
	var obj = {};
	obj.convertArrayOfStringsToGridFriendlyJSON = function(colName, arr) {
		var out = [];
    	arr.forEach(function(entry, index){
    		var obj = {};
    		obj['index'] = index;
    		obj[colName] = entry;
    		out.push(obj);
    	});
    	return out;
	}
	return obj;
});

app.run(function($rootScope,$document) {
		  var d = new Date();
		  var n = d.getTime();  

		    $rootScope.idleEndTime = n+(30*60*1000); //set end time to 30 min
		    $document.find('body').on('mousemove keydown DOMMouseScroll mousewheel mousedown touchstart', checkAndResetIdle); //monitor events

		    function checkAndResetIdle() {
		      var d = new Date();
		      var n = d.getTime(); 

		        if (n>$rootScope.idleEndTime) {
		            $document.find('body').off('mousemove keydown DOMMouseScroll mousewheel mousedown touchstart'); //un-monitor events
		            //$location.search('IntendedURL',$location.absUrl()).path('/login'); //terminate by sending to login page
		            document.location.href = 'logout.html';
		            alert('Session ended due to inactivity please login again');
		        }
		        else  {
		            $rootScope.idleEndTime = n+(30*60*1000); 
		        }
		    }
		});