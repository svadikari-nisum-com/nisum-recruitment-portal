app.controller('interviewFeedbackCtrl',['$scope', '$http','$q', '$window','jobCodeService1', '$timeout', '$rootScope','$log','$state', '$location','profileService', 'blockUI', 
                                        function($scope, $http, $q, $window, jobCodeService1, $timeout, $rootScope, $log, $state, $location,profileService, blockUI) {
	
	$scope.profile = {};
	$scope.interview = {};
	$scope.position = {};
	$scope.feedback = {};
	$scope.interviewFeedback = {};
	$scope.interviewSchedule = {};
	$scope.ratings=[];
	$scope.message = "";
	$scope.round = {};
	$scope.disableFields = false;
	$scope.disableStatus = false;
	$scope.disableSkills = false;
	$scope.submitShow = true;
	$scope.info = {};
	$scope.roundList = [];
	$scope.disableSchedule = true;
	$scope.hideSubmit = false;
	$scope.interviewFeedback.status="";
	$scope.previousPage = "recruitment.interviewManagement";
	$scope.selectDropDownDisable = false;
	var i = 0;
	$scope.rating = [1, 2, 3, 4, 5];
	
	
	$scope.init = function() {
		if(jobCodeService1.getjobCode() == undefined || jobCodeService1.getprofileUserId() == undefined) {
			$state.go("recruitment.interviewManagement");
		}
		$scope.jobcode =jobCodeService1.getjobCode();
		$scope.emailId = jobCodeService1.getprofileUserId();
		if(jobCodeService1.getPreviousPage() != undefined && jobCodeService1.getPreviousPage() != null)
		{
			$scope.previousPage = jobCodeService1.getPreviousPage();
			
		}
	}
	$scope.init();
	

	
	
	var profile_url = $http.get('resources/profile?emailId='+$scope.emailId);
	var interview_URL = $http.get('resources/getInterviewByParam?candiateId='+$scope.emailId);
	var position_URL = $http.get('resources/searchPositionsBasedOnJobCode?jobcode='+$scope.jobcode);
	$scope.info = $rootScope.info;
	$q.all([profile_url, interview_URL, position_URL]).then(
			function(response){
			$scope.profile = response[0].data[0];
			$scope.interview = response[1].data[0];
			$scope.position = response[2].data;
			angular.forEach($scope.interview.rounds, function(ro){
				$scope.roundList.push(ro.roundName);
			})
				$scope.interviewFeedback.rateSkills =[];
				$scope.interviewFeedback.rateSkills.push({"skill":"", "rating":""}); 
				/*for(var i=0; i<$scope.position.primarySkills.length;i++){
					$scope.interviewFeedback.rateSkills.push({"skill":$scope.position.primarySkills[i], "rating":0}); 
				}*/
				
				if(_.contains($scope.user.roles,"ROLE_INTERVIEWER") || _.contains($scope.user.roles,"ROLE_MANAGER") )
				{
					$scope.interviewFeedback.roundName = $scope.interview.rounds[$scope.interview.rounds.length-1].roundName;
					$scope.selectDropDownDisable = true;
					var interviewLastRoundInfo = $scope.interview.rounds[$scope.interview.rounds.length-1];
					if(interviewLastRoundInfo.interviewFeedback != undefined){
						$scope.disableFields = true;
						$scope.submitShow = false;
						$scope.disableSchedule = false;
						$scope.interviewFeedback = interviewLastRoundInfo.interviewFeedback;
						$scope.interviewSchedule = interviewLastRoundInfo.interviewSchedule;
						$scope.interviewFeedback.roundName = interviewLastRoundInfo.interviewFeedback.roundName;
					}else{
						$scope.interviewFeedback.rateSkills =[];
						$scope.addSkills();
						for(i=0; i<$scope.position.primarySkills.length; i++){
							//$scope.interviewFeedback.rateSkills.push({"skill":$scope.position.primarySkills[i], "rating":0}); 
							$scope.interviewFeedback.duration = "";
							$scope.interviewFeedback.additionalSkills = "";
							$scope.interviewFeedback.strengths = "";
							$scope.interviewFeedback.improvement = "";
							$scope.disableFields = false;
							$scope.submitShow = true;
							$scope.interviewSchedule = interviewLastRoundInfo.interviewSchedule;
						}
					}
				}
			},
			function(errorMsg) {
				$log.error("-------"+errorMsg);
			}
		);
	
	$scope.max = 10;
	$scope.hoveringOver = function(value) {
    $scope.overStar = value;
    $scope.percent = 100 * (value / $scope.max);
	};
	
	$scope.addSkills = function() {
		$scope.interviewFeedback.rateSkills.push({"skill":"", "rating":""});
	}
	
	$scope.removeSkills = function(index) {
		if($scope.interviewFeedback.rateSkills.length > 0 && index < $scope.interviewFeedback.rateSkills.length) {
			($scope.interviewFeedback.rateSkills).splice(index, 1);
			$scope['showaddskill_' + index ] = false;
		}
	}
	
	$scope.showAddSkills = function(rs, index) {
		if(rs) {
			if(rs.skill && rs.skill.length > 0 && rs.rating && rs.rating.toString().length > 0) {
				 $scope['showaddskill_' + index ] = true;
			}
		}
	}
	
	$scope.checkFBStatus = function() {
		return false;
	}
	
	$scope.status = {
		    isFirstOpen: true,
	};
	
	$scope.submit = function(){
		blockUI.start("Submitting Feedback...");
		setTimeout(function () {
			$scope.interviewFeedback.candidateName = $scope.profile.candidateName;
			$scope.interviewFeedback.interviewerEmail = $scope.interviewSchedule.emailIdInterviewer;
			$scope.interviewFeedback.interviewerName = $scope.interviewSchedule.interviewerName;
		    $scope.interviewFeedback.jobcode = $scope.interviewSchedule.jobcode;
			$scope.interviewFeedback.interviewDateTime = $scope.interviewSchedule.interviewDateTime;
			$scope.interviewFeedback.typeOfInterview = $scope.interviewSchedule.typeOfInterview;
			$scope.interviewFeedback.candidateId = $scope.emailId;
			
			profileService.addProfilesStatus($scope.emailId,$scope.interviewFeedback.status);
			
			$http.post('resources/interviewFeedback', $scope.interviewFeedback).
			  success(function(data, status) {
				  $scope.cls = 'alert  alert-success';
				  $scope.message = "Feedback Submitted Successfully!";
				  $timeout( function(){ $scope.alHide(); }, 1000);
				  $scope.reset();				  
				  $log.info("Feedback Submitted Successfully!");
			  }).
			  error(function(data, status) {
				  $scope.cls = 'alert alert-danger alert-error';
				  $scope.message = "Something Went Wrong! Please Try Again!";
				  $timeout( function(){ $scope.alHide(); }, 5000);
				  $log.error("Feedback Submission Failed! --->"+data);
			  });
			blockUI.stop();
		},3000);
	}
	
	$scope.showSubmitButton = function()
	{
		if(angular.isDefined($scope.interview.rounds) && $scope.interview.rounds != null && $scope.interview.rounds[$scope.interview.rounds.length-1].interviewSchedule.emailIdInterviewer == $scope.useremailId)
		{
			$scope.submitShow = true; 
		}else
		{
			$scope.submitShow = false; 
		}
	}
	
	$scope.alHide =  function(){
	    $scope.message = "";
	    $scope.cls = '';
	    $location.path("recruitment/interviewManagement");
	}
	
	$scope.reset = function(){
		$scope.interviewFeedback.duration = "";
		$scope.interviewFeedback.additionalSkills = "";
		$scope.interviewFeedback.strengths = "";
		$scope.interviewFeedback.improvement = "";
		$scope.disableFields = false;
		$scope.submitShow = true;
		$scope.interviewSchedule = "";
	}
	
	$scope.setfeedbackData = function(roundName){
		if($scope.interview.rounds!= undefined){
			if(_.contains($scope.roundList,roundName)){
		angular.forEach($scope.interview.rounds, function(round){
			if(round.interviewSchedule.roundName == "Hr Round"){
				$scope.disableSkills = true;
			}
		if(round.interviewSchedule.roundName == roundName)	{
		if(round.interviewFeedback != undefined){
			$scope.disableFields = true;
			$scope.submitShow = false;
			$scope.disableSchedule = false;
			$scope.interviewFeedback = round.interviewFeedback;
			$scope.interviewSchedule = round.interviewSchedule;
			$scope.interviewFeedback.roundName = round.interviewFeedback.roundName;
		}else{
			$scope.interviewFeedback.rateSkills =[];
			$scope.addSkills();
				//$scope.interviewFeedback.rateSkills.push({"skill":$scope.position.primarySkills[i], "rating":0}); 
			$scope.interviewFeedback.duration = "";
			$scope.interviewFeedback.additionalSkills = "";
			$scope.interviewFeedback.strengths = "";
			$scope.interviewFeedback.improvement = "";
			$scope.disableFields = false;
			$scope.showSubmitButton();
			$scope.interviewSchedule = round.interviewSchedule;
		}
		}else if(round.interviewSchedule == null){
			$scope.interviewFeedback.rateSkills =[];
			for(var i=0; i<$scope.position.primarySkills.length;i++){
				$scope.interviewFeedback.rateSkills.push({"skill":$scope.position.primarySkills[i], "rating":0});
			}
				$scope.interviewFeedback.duration = "";
				$scope.interviewFeedback.additionalSkills = "";
				$scope.interviewFeedback.strengths = "";
				$scope.interviewFeedback.improvement = "";
				$scope.disableFields = false;
				$scope.showSubmitButton();
		} 
		})
			}else{
				$scope.message = "Please Schedule Interview for this Round";
				$scope.disableFields = true;
				$scope.interviewSchedule = {};
				$scope.interviewFeedback.rateSkills =[];
				$scope.addSkills();
				$scope.interviewFeedback.duration = "";
				$scope.interviewFeedback.additionalSkills = "";
				$scope.interviewFeedback.strengths = "";
				$scope.interviewFeedback.improvement = "";
				$scope.disableFields = false;
				$scope.showSubmitButton();
				
				/*for(var i=0; i<$scope.position.primarySkills.length;i++){
					$scope.interviewFeedback.rateSkills.push({"skill":$scope.position.primarySkills[i], "rating":0}); 
					$scope.interviewFeedback.duration = "";
					$scope.interviewFeedback.additionalSkills = "";
					$scope.interviewFeedback.strengths = "";
					$scope.interviewFeedback.improvement = "";
					$scope.disableFields = false;
					$scope.submitShow = true;
				}*/
			}
		}else{
			$scope.interviewFeedback.rateSkills =[];
			for(var i=0; i<$scope.position.primarySkills.length;i++){
				$scope.interviewFeedback.rateSkills.push({"skill":$scope.position.primarySkills[i], "rating":0});
				$scope.interviewFeedback.duration = "";
				$scope.interviewFeedback.additionalSkills = "";
				$scope.interviewFeedback.strengths = "";
				$scope.interviewFeedback.improvement = "";
				$scope.disableFields = false;
				$scope.submitShow = true;
			}
		}
	}
}]);
