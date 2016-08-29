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
	var User_URL = 'resources/user?emailId='+$scope.useremailId;
	
	$http.get(User_URL).success(function(data, status, headers, config) {
		$scope.userRoles = data[0].roles;
		if(_.contains($scope.userRoles, "ROLE_HR") || _.contains($scope.userRoles, "ROLE_RECRUITER") || _.contains($scope.userRoles, "ROLE_ADMIN") ||  _.contains($scope.userRoles, "ROLE_MANAGER")){
			    dashboardService.getScheduleData().then(function (data){
				$scope.showScheduleData = data;
					if(data == "" || data == null || data == undefined){
						$scope.hideNoInterviewMsg = false;
					}
			    }).catch(function(msg){
			 	$log.error(msg);
				$scope.hideNoInterviewMsg = false;
			    });
		} else if(_.contains($scope.userRoles, "ROLE_INTERVIEWER")) {
			$http.get('resources/getInterviewByInterviewer?interviewerEmail='+$scope.useremailId).success(function(data, status, headers, config) {
				var showScheduleData =[];
				var today = new Date();
				var tomorrow = new Date(today);
				tomorrow.setDate(today.getDate()+4);
				angular.forEach(data, function(obj){
					angular.forEach(obj.rounds, function(obj2){
						var dbDate = new Date(obj2.interviewSchedule.interviewDateTime);
						if(dbDate >= today){
							showScheduleData.push({"cname":obj.candidateName,"currentPositionId":obj.currentPositionId,"email":obj.candidateEmail, "round":obj2.interviewSchedule.roundName, "date":dbDate, "interviewId":obj.interviewerId,"interviewerEmail":obj.interviewerEmail});
					    }
					})
				});
				$scope.showScheduleData = showScheduleData;
				if(data==undefined || data == null || data.length == 0){
					$scope.hideNoInterviewMsg = false;
				}
			}).error(function(data, status, headers, config) {
				$log.error(data);
			})
			
		} else {
			$scope.hideNoInterviewMsg = false;
		}
	}).error(function(data, status, headers, config) {
		$log.error(data);
	})

	/*$scope.showInterview = function(obj, obj2) {
		jobCodeService1.setjobCode(obj);
		jobCodeService1.setinterviewRound(obj2);
		location.href='#recruitment/showInterview';
		
	};*/
	$scope.feedback = function(obj, obj2,obj3,obj4,obj5) {
		   if(sessionStorage.userId == obj3 ) {
			   jobCodeService1.setjobCode(obj);
				jobCodeService1.setprofileUserId(obj2); 
				jobCodeService1.setPreviousPage("#main");
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
