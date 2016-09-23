app.controller("showFeedBackCtrl", ['$scope', '$http', '$upload','$filter', '$timeout','$q', '$rootScope', '$log','$location', 'jobCodeService1', function($scope, $http, $upload, $filter, $timeout, $q, $rootScope,$log,$location, jobCodeService1) {
	
	$scope.info = $rootScope.info;
	$scope.interviewData = {};
	$scope.roundNames = jobCodeService1.getpreviousRound();
	$scope.interviewSetData = [];
	$scope.interviewFeedbackSetData = [];
	$scope.emailId = jobCodeService1.getprofileUserId();
	
	var getSchedule_url = 'resources/interviews?candiateId='+$scope.emailId;
	
	$http.get(getSchedule_url).success(function(data, status, headers, config) {
		$scope.interviewData = data[0];
		angular.forEach($scope.interviewData.rounds, function(obj){
			if(obj.interviewSchedule.roundName == $scope.roundNames ){
				$scope.interviewSetData.push(obj.interviewSchedule);
				$scope.interviewFeedbackSetData.push(obj.interviewFeedback);
			}
		})
	}).error(function(data, status, headers, config) {
		$log.error(data);
	});
	
	$scope.backFeedBack = function(){
   	 $location.path("recruitment/interviewFeedback");
    };
}]);
