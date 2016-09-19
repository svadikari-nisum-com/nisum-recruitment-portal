app.controller("dashboardCtrl", ['$scope', '$http', '$upload','$filter', '$timeout','$q', '$rootScope', '$log', 'jobCodeService1', 'dashboardService','infoService', 'navService', function($scope, $http, $upload, $filter, $timeout, $q, $rootScope,$log, jobCodeService1, dashboardService,infoService, navService) {
	
	$scope.positionData = {};
	$scope.info = $rootScope.info;
	$scope.showScheduleData = [];
	$scope.hideNoPositionsMsg = true;
	$scope.hideNoInterviewMsg = true;
	$scope.useremailId = sessionStorage.userId;
	
	dashboardService.getPositionData()
	.then(function(data){
		$scope.positionData = data;
		if(data == "" || data == null || data == undefined){
			$scope.hideNoPositionsMsg = false;
		}else{
			$scope.hideNoPositionsMsg = true;
		}
	}).catch(
	function(msg){
		$log.error(msg);
	});
	//var User_URL = 'resources/user?emailId='+$scope.useremailId;
	
	/*$http.get(User_URL).success(function(data, status, headers, config) {
		$scope.userRoles = data[0].roles;*/
			    dashboardService.getScheduleData($scope.useremailId).then(function (data){
				$scope.showScheduleData = data;
				/*if(_.contains($scope.userRoles, "ROLE_INTERVIEWER")) {
					$scope.showScheduleData = data;
				}*/
					if(data == "" || data == null || data == undefined){
						$scope.hideNoInterviewMsg = false;
					}
			    }).catch(function(msg){
			 	$log.error(msg);
				$scope.hideNoInterviewMsg = false;
			    });
	/*}).error(function(data, status, headers, config) {
		$log.error(data);
	})*/
	
	$scope.feedback = function(obj, obj2,obj3,obj4,obj5) {
		   if(sessionStorage.userId == obj3 ) {
			   jobCodeService1.setjobCode(obj);
				jobCodeService1.setprofileUserId(obj2); 
				jobCodeService1.setPreviousPage("#main");
				jobCodeService1.setinterviewRound(obj5);
				location.href='#recruitment/interviewFeedback';
		   } else {
			    jobCodeService1.setjobCode(obj4);
				jobCodeService1.setinterviewRound(obj5);
				location.href='#recruitment/showInterview';
		   }
		};
	
	$scope.setActiveTab = function(value) {
		navService.setActiveTab(value);
	}
	
	$scope.getActiveTab = function() {
		return navService.getActiveTab();
	}
	
}]);
