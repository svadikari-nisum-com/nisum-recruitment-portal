app.controller('interviewFeedbackCtrl',['$scope', '$http','$q', '$window','jobCodeService1', '$timeout', '$rootScope','$log','$state', '$location','profileService', 'blockUI', 
                                        function($scope, $http, $q, $window, jobCodeService1, $timeout, $rootScope, $log, $state, $location,profileService, blockUI) {
	
	$scope.profile = {};
	$scope.interview = {};
	$scope.position = {};
	$scope.feedback = {};
	$scope.interviewFeedback = {};
	$scope.interviewFeedbackData = {};
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
	$scope.disableSubmit=true;
	$scope.hideSubmit = false;
	$scope.interviewFeedback.status="";
	$scope.previousPage = "";
	$scope.selectDropDownDisable = false;
	var interviewDateTime = ""
	var i = 0;
	$scope.rating = [1, 2, 3, 4, 5];
	$scope.showRounds = false;
	
	$scope.init = function() {
		if(jobCodeService1.getjobCode() == undefined || jobCodeService1.getprofileUserId() == undefined) {
			if (_.contains($scope.user.roles,"ROLE_INTERVIEWER")) {
				$state.go("#main");
			} else if(_.contains($scope.user.roles, "ROLE_HR") || _.contains($scope.user.roles, "ROLE_MANAGER")) { 
				$state.go("recruitment.searchProfile");
			} else {
				$state.go("recruitment.interviewManagement");
			}
		}
		$scope.jobcode =jobCodeService1.getjobCode();
		$scope.emailId = jobCodeService1.getprofileUserId();
		$scope.interviewRoundName =  jobCodeService1.getinterviewRound();
		if(jobCodeService1.getPreviousPage() != undefined && jobCodeService1.getPreviousPage() != null)
		{
			$scope.previousPage = jobCodeService1.getPreviousPage();
			
		}
		
	}
	$scope.init();
	
	
	var profile_url = $http.get('resources/profile?emailId='+$scope.emailId);
	var interview_URL = $http.get('resources/interviews?candiateId='+$scope.emailId);
	var position_URL = $http.get('resources/positions?searchKey=jobcode&searchValue='+$scope.jobcode);
	$scope.info = $rootScope.info;
	$scope.roundListInfo = [];
	$q.all([profile_url, interview_URL, position_URL]).then(
			function(response){
			
			$scope.profile = response[0].data[0];
			$scope.interview = response[1].data[0];
			$scope.position = response[2].data[0];
			angular.forEach($scope.interview.rounds, function(ro){
				$scope.roundList.push(ro.roundName);
			})
				$scope.interviewFeedback.rateSkills =[];
				$scope.interviewFeedback.rateSkills.push({"skill":"", "rating":""}); 
			
					$scope.interviewFeedback.roundName = $scope.interviewRoundName;
					$scope.selectDropDownDisable = true;
					$scope.showRounds = false;
					angular.forEach($scope.interview.rounds, function(round){
					if(round.roundName != $scope.interviewRoundName && round.interviewFeedback != null) {
						$scope.roundListInfo.push({"round":round.roundName,"email":round.interviewSchedule.candidateId});
						$scope.showRounds = true;
					}	
						if(round.interviewFeedback != null ) {
								$scope.interviewDateTime = round.interviewSchedule.interviewDateTime;
								$scope.typeOfInterview = round.interviewSchedule.typeOfInterview;
								$scope.interviewRoundName = round.interviewFeedback.roundName;
								$scope.interviewSchedule = round.interviewSchedule;
								$scope.interviewFeedback = round.interviewFeedback;
								if( $scope.interviewFeedback.rateSkills[0].skill  == "" && $scope.interviewFeedback.rateSkills[0].rating == "") {
									$scope.disableSkills = true;
								}
								$scope.submitShow = false;
							} else {
							if (round.interviewFeedback == null ) {
							$scope.interviewDateTime = round.interviewSchedule.interviewDateTime;
							$scope.typeOfInterview = round.interviewSchedule.typeOfInterview;
							$scope.interviewRoundName = round.interviewSchedule.roundName;
							$scope.interviewFeedback.rateSkills =[];
							$scope.addSkills();
							$scope.interviewFeedback.duration = "";
							$scope.interviewFeedback.additionalSkills = "";
							$scope.submitShow = true;
							}
						}
						
 					/*angular.forEach($scope.roundListInfo, function(item){ 
 						if(item.round == $scope.interviewRoundName &&  _.contains($scope.roundListInfo,item.round) && $scope.interview.rounds.length-1 > 0) {
 						  $scope.roundListInfo.pop(item.round);
 						}
 						})*/
					})
					
			},
			function(errorMsg) {
				$log.error("-------"+errorMsg);
			}
		);
	
	$scope.showFeedback = function(obj,obj1) {
				jobCodeService1.setprofileUserId(obj1);
				jobCodeService1.setpreviousRound(obj);
				location.href='#recruitment/showFeedBack';
	};
	
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
		    isFirstOpen: true
	};
	
	$scope.submit = function(){
		blockUI.start("Submitting Feedback...");
		setTimeout(function () {
			var sure = true;
			$scope.interviewFeedback.candidateName = $scope.profile.candidateName;
			$scope.interviewFeedback.interviewerEmail = $scope.interviewSchedule.emailIdInterviewer;
			$scope.interviewFeedback.interviewerName = $scope.interviewSchedule.interviewerName;
		    $scope.interviewFeedback.jobcode = $scope.interviewSchedule.jobcode;
			$scope.interviewFeedback.interviewDateTime = $scope.interviewSchedule.interviewDateTime;
			$scope.interviewFeedback.typeOfInterview = $scope.interviewSchedule.typeOfInterview;
			$scope.interviewFeedback.candidateId = $scope.emailId;
			$scope.interviewFeedback.roundName = $scope.interviewRoundName;
			profileService.addProfilesStatus($scope.emailId,$scope.interviewFeedback.status);
			
			if( $scope.interviewFeedback.rateSkills[0].skill  == "" && $scope.interviewFeedback.rateSkills[0].rating == "") {
				var suresure = $window.confirm('Are you sure you want to submit without rate the skills?');
				if (!suresure) {
					sure = false;
				} 
			}
			
			if(sure) {
				$http.post('resources/interviews/feedback', $scope.interviewFeedback).
				  success(function(data, status) {
					  $scope.cls = 'alert  alert-success';
					  $scope.message = "Feedback Submitted Successfully!";
					  $timeout( function(){ $scope.alHide(); }, 1200);
					  if(_.contains($scope.user.roles,"ROLE_INTERVIEWER")) {
					    	 $location.path("#main");
					    } else if(_.contains($scope.user.roles, "ROLE_HR") || _.contains($scope.user.roles, "ROLE_MANAGER")) { 
					    	$location.path("recruitment/searchProfile");
						} else {
					    	 $location.path("recruitment/interviewManagement");
					    }
					  $scope.reset();				  
					  $log.info("Feedback Submitted Successfully!");
				  }).
				  error(function(data, status) {
					  $scope.cls = 'alert alert-danger alert-error';
					  $scope.message = "Something Went Wrong! Please Try Again!";
					  $timeout( function(){ $scope.alHide(); }, 1000);
					  $log.error("Feedback Submission Failed! --->"+data);
				  });			
			}
			
			if($scope.interviewFeedback.status == "No") {
				profileService.deleteProfile($scope.emailId);
			}
			
			blockUI.stop();
		},1000);
	}
	
	
	$scope.cancelFeedBack = function(){
		
		if(_.contains($scope.user.roles,"ROLE_INTERVIEWER")) {
	    	 $location.path("#main");
	    }   else if(_.contains($scope.user.roles, "ROLE_HR") || _.contains($scope.user.roles, "ROLE_MANAGER")) { 
	    	$location.path("recruitment/searchProfile");
		} else {
	    	 $location.path("recruitment/interviewManagement");
	    }
		
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
	    if(_.contains($scope.user.roles,"ROLE_INTERVIEWER")) {
	    	 $location.path("#main");
	    } else if(_.contains($scope.user.roles, "ROLE_HR") || _.contains($scope.user.roles, "ROLE_MANAGER")) { 
	    	$location.path("recruitment/searchProfile");
		} else {
	    	 $location.path("recruitment/interviewManagement");
	    }
	   
	}
	$scope.filterValue = function($event){
		var value = $event.keyCode ? $event.keyCode : $event.which;
        if(value != 8 && (isNaN(String.fromCharCode(value)) || (parseInt($scope.interviewFeedback.duration + String.fromCharCode(value))) > 60)  || value == 32){
        	$event.preventDefault();
        }
	};
	$scope.reset = function(){
		$scope.interviewFeedback.duration = "";
		$scope.interviewFeedback.additionalSkills = "";
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
			$scope.interviewFeedback.roundName = $scope.interviewRoundName 
		}else{
			$scope.interviewFeedback.rateSkills =[];
			$scope.addSkills();
			//$scope.interviewFeedback.rateSkills.push({"skill":$scope.position.primarySkills[i], "rating":0}); 
			$scope.interviewFeedback.duration = "";
			$scope.interviewFeedback.additionalSkills = "";
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
				$scope.disableFields = false;
				$scope.showSubmitButton();
			}
		}else{
			$scope.interviewFeedback.rateSkills =[];
			for(var i=0; i<$scope.position.primarySkills.length;i++){
				$scope.interviewFeedback.rateSkills.push({"skill":$scope.position.primarySkills[i], "rating":0});
				$scope.interviewFeedback.duration = "";
				$scope.interviewFeedback.additionalSkills = "";
				$scope.disableFields = false;
				$scope.submitShow = true;
			}
		}
	}
	
	$scope.hasRequired = function() {
		return _.contains($scope.user.roles,"ROLE_HR") || _.contains($scope.user.roles,"ROLE_MANAGER")
	}
}]);
