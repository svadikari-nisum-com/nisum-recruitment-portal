app.controller('scheduleInterviewCtrl',['$scope', '$http', 'jobCodeService1', '$timeout','$filter','$q', '$log', '$rootScope','blockUI','clientService','interviewService','$state', '$location','userService','profileService', 
                                        function($scope, $http, jobCodeService1, $timeout,$filter, $q, $log, $rootScope, blockUI, clientService, interviewService,$state,$location,userService,profileService) {
	$scope.data = {};
	$scope.sel = {};
	$scope.candidate = {};
	$scope.interview = {};
	$scope.selectedpLocation={};
	$scope.interviewClient = "";
	$scope.sel.selectedLocation = "";
	$scope.interview.interviewLocation = "";
	$scope.sel.selectedtypeOfInterview = "";
	$scope.interview.typeOfInterview = "";
	$scope.interview.interviewDateTime = "";
	$scope.roundFilter = "";
	$scope.intCheck = {};
	$scope.interviewerData = {};
	$scope.interviewLoad = {};
	$scope.interviewers = {};
	$scope.technicalRound1 ={};
	$scope.hideRound=true;
	$scope.filterValue = true;
	$scope.interviewerNames = [];
	$scope.interviewerTimeslots = [];
	$scope.interviewerTimeslot = {};
	$scope.hidePrvDateMsg = true;
	$scope.jobCodeSel = "";
	$scope.info = $rootScope.info;
	$scope.pskills = [];
	$scope.pskills = $scope.info.skills;
	$scope.Names = {};
	$scope.names = {};
	$scope.skills = {};
	$scope.interviewschedule = {};
	var candidatejc;
	$scope.disableDate = true;
	$scope.scheduleButnHide = true;
	$scope.scheduleButnDis = true;
	$scope.message = "";
	$scope.errormessage = true;
	$scope.interviewerInfo ={};
	$scope.position = {};
	$scope.techRounds = {};
	$scope.skills = [];
	$scope.skillset = [];
	$scope.profile = {};
	$scope.hrNames = [];
	$scope.managersNames = [];
	$scope.previousPage = "recruitment.interviewManagement";
	$scope.init = function() {
		if(jobCodeService1.getjobCode() == undefined || jobCodeService1.getprofileUserId() == undefined) {
			$state.go("recruitment.interviewManagement");
		}
		$scope.emailId = jobCodeService1.getprofileUserId();
		$scope.jobcode = jobCodeService1.getjobCode();
		
		if(jobCodeService1.getPreviousPage() != undefined && jobCodeService1.getPreviousPage() != null)
		{
			$scope.previousPage = jobCodeService1.getPreviousPage();
			
		}
	}
	$scope.init();
	
	var URL = $http.get('resources/profile?emailId='+$scope.emailId);
	var URL1 = 'resources/profile?emailId='+$scope.emailId;
	
	
	profileService.getProfileById($scope.emailId).then(
		function(response){
		$scope.profile = response[0];
		}).catch(
		function(errorMsg) {
			$log.error("Failed! ---> "+errorMsg);
		}
	);
	$scope.usersInfo=[];
	$scope.managerInfo=[];
	$scope.hrInfo=[];
	$scope.getInterviewerInfo = function(){
		$http.get(URL1).success(function(data, status, headers, config) {
		$scope.candidate =data[0];
		//$scope.candidatejc = data[0].jobcodeProfile;
		var IR_Round='resources/positions?searchKey=jobcode&searchValue='+$scope.jobCodeSel;
		
		if($scope.jobCodeSel)
		{
			$http.get(IR_Round).success(function(data2, status, headers, config) {
				$scope.interviewClient = data2[0].client;
				
				clientService.getClientByName($scope.interviewClient).then(function(data){
					$scope.clientDetails = data;
				});
				
				var rounds =[];	
				angular.forEach(data2[0].interviewRounds, function(value, key) {
					 rounds.push(value.toString());
				});
				 $scope.candidate.interviewRounds=rounds;
				}).error(function(data, status, headers, config) {
					$log.error(data);
				});
		}
		}).error(function(data, status, headers, config) {
			$log.error(data);
		});
	}
	
	
	
	
	$scope.getInterviewers = function(round,functionalGroup,role){
		userService.getInterviewers(round,functionalGroup,role).then(function (data){
			$scope.usersInfo = data;
			$scope.interviewerNames = [];
			angular.forEach(data,function(user){
				$scope.interviewerNames.push({'name':user.name,"emailId":user.emailId,"count":user.noOfRoundsScheduled});
			})
		});
	}
	
	$scope.setRounds = function(round){
		
		//Get interview details
		//1.If HR Round set  hrNames 
		//2.If Manager Round  set managersNames
		//3.Load user based on functional group and round name
		if( round == "Hr Round" )
		{
			$scope.getInterviewers(round,"","ROLE_HR");

		}else if (round == "Manager Round")
		{
			$scope.getInterviewers(round,"","ROLE_MANAGER");
		}
		else 
		{
			$scope.interviewerNames = [];
			$scope.getInterviewers(round,$scope.position.functionalGroup,"ROLE_INTERVIEWER");
		}
		
    }

	$scope.getJobCodeRound = function(){
		if($scope.jobCodeSel!==""){
			$scope.reset();
			var rounds_URL = 'resources/positions?searchKey=jobcode&searchValue='+$scope.jobCodeSel;
			$http.get(rounds_URL).success(function(data, status, headers, config) {
				$scope.position = data[0];
			}).error(function(data, status, headers, config) {
		        $log.error(data);
			})
			 $scope.getInterviewerInfo();
		}
	}
	
	$scope.schedule = function(){
		blockUI.start("Scheduling..");
		setTimeout(function () {
		
		$scope.interviewschedule.interviewerName = $scope.interviewerData.name;
		$scope.interviewschedule.jobcode = $scope.jobCodeSel;
		$scope.interviewschedule.typeOfInterview = $scope.sel.selectedtypeOfInterview;
		$scope.interviewschedule.interviewLocation = $scope.sel.selectedLocation;
		$scope.interviewschedule.interviewDateTime = $scope.data.date;
		$scope.interviewschedule.candidateId = $scope.candidate.emailId;
		$scope.interviewschedule.candidateName = $scope.candidate.candidateName;
		
		//$scope.interviewschedule.cureentPositinId = $scope.jobCodeSel;
		$http.post('resources/interviews/schedule', $scope.interviewschedule).
		  success(function(data, status, headers, config) {
			  
			  $http.get('resources/profile?emailId='+$scope.emailId).success(function(data, status, headers, config) {
					$scope.candidate =data[0];
					
					$scope.candidate.status = "In Schedule Process";
					
					profileService.updateProfiles($scope.candidate).then(function(msg){
			        	//$scope.sendNotification(msg,'/searchProfile');
						$log.info(msg);
			        })
			  })
		
			  $scope.cls = 'alert alert-success alert-error';
			  $scope.message = "Scheduled successfully";
			  $timeout( function(){ $scope.alHide(); }, 5000);
			  $log.info("Interview Scheduled!!");
				$scope.reset();
				$scope.jobCodeSel="";
				$scope.interviewschedule.roundName="";
				$scope.data.date="";
				$location.path($scope.previousPage.replace(".", "/"));
		  }).
		  error(function(data, status, headers, config) {
			  $scope.cls = 'alert alert-danger alert-error';
			  $scope.message = "Something wrong, try again";
			  $timeout( function(){ $scope.alHide(); }, 1000);
			 $log.error("failed=="+data);
		  });
		blockUI.stop();
		},1000);	
	}
	
	
	$scope.filterInterviewersByCandidateTime = function(newDate){
		day = $filter('date')(newDate, 'dd/MM/yy');
		var scheduleDate = newDate; 
		var toDay = new Date(); 
		var interviewers = $scope.usersInfo;
		if(toDay >= scheduleDate){
	           $scope.hidePrvDateMsg = false;
	           $scope.interviewerData = {};
	   		   $scope.data.date = "";
	   		   $scope.interviewschedule.interviewerName = "";
	   		   $scope.setRounds($scope.interviewschedule.roundName);
	           return;
	    }else{
	          $scope.hidePrvDateMsg = true;
	    }
		selectedDay = $filter('date')(newDate, 'EEEE');
		$scope.interviewerNames = [];
		$scope.interviewerData = {};
		$scope.interviewschedule.interviewerName = "";
		angular.forEach(interviewers, function(interviewer) {
			
			angular.forEach(interviewer.timeSlots, function(timeslot)  {
				if(timeslot.day == selectedDay) {
					$scope.interviewerTimeslot = timeslot;
					var intSchDate = new Date(timeslot.time);
					// Interviewer Timings
					var intDate = new Date();
					var intHours = new Date(timeslot.time).getHours();
					var intMinutes = new Date(timeslot.time).getMinutes();
					
					intDate.setHours(intHours, intMinutes)
					var time = (timeslot.hour).split('.');
					var intToDate = new Date();
					if(time[0]) {
						intToDate.setHours(intHours + Number(time[0]));
					}
					if(time[1]) {
						intToDate.setMinutes(intMinutes + Number(time[1]));
					}else{
						intToDate.setMinutes(intMinutes);
					}
					// Candidate Timings
					var canDate = new Date();
					var canHours = newDate.getHours();
					var canMinutes = newDate.getMinutes();
					canDate.setHours(canHours, canMinutes)
					
					// Interviewer From Time
					var intFromDateTime = new Date(timeslot.fromDate);
					// Interviewer To Time
					var intToDateTime = new Date(timeslot.toDate);
					if(timeslot.isNotAvailable) {
						if(!(intFromDateTime <= newDate && newDate < intToDateTime)) {
							if(canDate >= intDate  && canDate < intToDate) {
								$log.info("Interviewer is available");
								$scope.interviewerNames.push({'name':interviewer.name,"emailId":interviewer.emailId,"count":interviewer.noOfRoundsScheduled});
								//$scope.interviewerData = {};
								//$scope.interviewerData = interviewer;
							}
						}
					}else {
						if(canDate >= intDate  && canDate < intToDate) {
							$log.info("Interviewer is available");
							$scope.interviewerNames.push({'name':interviewer.name,"emailId":interviewer.emailId,"count":interviewer.noOfRoundsScheduled});
							$scope.interviewerData = {};
							//$scope.interviewerData = interviewer;
						}
					}
				}
			});
		});
		
		if($scope.interviewerNames.length <= 0){
			showTimeslotError();
		}
	}

	function showTimeslotError() {
		$scope.message = "Interviewer is not available on this date.";
		 $scope.cls = 'alert alert-danger alert-error';
		 $scope.showMsg = true;
		 $timeout( function(){ $scope.alHide(); }, 4000);
		//$scope.interviewschedule.interviewerName = "";
		//$scope.interviewschedule.emailIdInterviewer = "";
		//$scope.interviewschedule.interviewerMobileNumber = "";	
		$scope.interviewerData = {};
		$scope.data.date = "";
		$scope.setRounds($scope.interviewschedule.roundName);
	}
	
	
	$scope.setvalues = function(emailId) 
	{
		var deferred = $q.defer();
		deferred.promise.then(function(){
			$scope.disableDate = false;
			$scope.interviewschedule.emailIdInterviewer = $scope.interviewerData.emailId;
			$scope.interviewschedule.interviewerMobileNumber = $scope.interviewerData.mobileNumber;
			$scope.interviewschedule.skypeId = $scope.interviewerData.skypeId;
			$scope.sel.selectedLocation = $scope.interviewerData.location;
			//$scope.data.date="";
			
			angular.forEach($scope.interviewerData.timeSlots, function(timeSlot) {
				if(timeSlot.hour) {
					
					// Interviewer From Time
					var intFromDateTime = new Date(timeSlot.fromDate);
					intFromDateTime.setHours(0,0,0,0)
					
					// Interviewer To Time
					var intToDateTime = new Date(timeSlot.toDate);
					intToDateTime.setHours(0,0,0,0)
					
					timeSlot.showAvailablity = true;
					
					var curDate = new Date();
					curDate.setHours(0,0,0,0)
					
					if(intFromDateTime <= curDate && curDate < intToDateTime) {
						timeSlot.showAvailablity = false;
					}
					
					var time = (timeSlot.hour).split('.');
					var hour = time[0];
					var min = time[1];
					var date = new Date(timeSlot.time);
					if(hour) {
						date.setHours(date.getHours() + Number(hour));
					}
					if(min) {
						date.setMinutes(date.getMinutes() + Number(min));
					}
					timeSlot.toTime = date;
				}
			});
			
		});
		if( $scope.interviewschedule.roundName == "Hr Round" )
		{
			$scope.interviewerData = $scope.hrInfo.filter(function ( obj ) {
			    return obj.emailId == emailId;
			})[0];
			deferred.resolve();
		}else if ( $scope.interviewschedule.roundName == "Manager Round" )
		{
			$scope.interviewerData = $scope.managerInfo.filter(function ( obj ) {
			    return obj.emailId == emailId;
			})[0];
			deferred.resolve();
		}else
		{
			userService.getUserById(emailId).then(function (data){
				$scope.interviewerData = data[0];
				deferred.resolve();
			});
				
		}
		
	}
	$scope.alHide =  function(){
		$scope.errormessage = true;
	    $scope.message = "";
	    $scope.cls = '';
	}
	
	$scope.onLoad = function(){
		var User_URL='resources/user?emailId='+sessionStorage.userId;
		var interviewSchDetails_URL= 'resources/interviews?interviewerEmail='+sessionStorage.userId+'&jobCode='+$scope.jobcode;
		
		$http.get(User_URL).success(function(data, status, headers, config) {
			$scope.getInterviewerInfo();
			$scope.userRoles = data[0].roles;
			$scope.interviewerData = data[0];
			if(_.contains($scope.userRoles, "ROLE_INTERVIEWER")){
				$http.get(interviewSchDetails_URL).success(function(data1, status, headers, config) {
					var i=_.findIndex(data1,function(data){
						return data.candidateEmail == $scope.emailId; 
					});
					$scope.jobCodeSel = data1[i].currentPositionId;
					var j = data1[i].rounds.length-1;
					$scope.interviewschedule.roundName = data1[i].rounds[j].roundName;
					$scope.interviewschedule.interviewerName = data1[i].rounds[j].interviewSchedule.interviewerName;
					$scope.interviewschedule.interviewerMobileNumber = data1[i].rounds[j].interviewSchedule.interviewerMobileNumber;
					$scope.interviewschedule.emailIdInterviewer = $scope.emailId;
					$scope.interviewschedule.skypeId = data[0].skypeId;
					$scope.sel.selectedtypeOfInterview = data1[i].rounds[j].interviewSchedule.typeOfInterview;
					$scope.sel.selectedLocation = data[0].location;
					$scope.interviewschedule.additionalNotes = data1[i].rounds[j].interviewSchedule.additionalNotes;
					$scope.disableDate = false;
					
					//$scope.roundInfo();
					var day = new Date(data1[i].rounds[j].interviewSchedule.interviewDateTime);
					$scope.data.date = day;
					if(data1[i].rounds[j].interviewFeedback===null){
						$scope.disableDate = false;
						$scope.scheduleButnHide = false;
						$scope.scheduleButnDis = false;
					}
				})
			}
		});
		userService.getUserByRole("ROLE_HR").then(function (data){
			
			$scope.hrInfo=data;
			angular.forEach(data,function(user){
				
				$scope.hrNames.push({'name':user.name,"emailId":user.emailId});
			})
			
		});
		userService.getUserByRole("ROLE_MANAGER").then(function (data){
			
			$scope.managerInfo=data;
			angular.forEach(data,function(user){
				$scope.managersNames.push({'name':user.name,"emailId":user.emailId});
			})
		});
	}
	
	$scope.onLoad();
	
	$scope.interviewDetails ={};
	$scope.interviewDetails.rounds = [];
	
	$scope.roundInfo = function(){
		var InterviewDetailsURL = 'resources/interviews?candiateId='+$scope.emailId;
		$scope.interviewDetails = {};
		$scope.reset();		
		
		interviewService.getInterviewDetailsByCandidateId($scope.emailId).then(function(data){
				if(angular.isDefined(data) && angular.isObject(data)){
					$scope.interviewDetail = data;
					var index =$scope.interviewDetail.progress.indexOf('Scheduled');
					var round =$filter('limitTo')($scope.interviewDetail.progress, index-1, 0);
					if(index !== -1 && (round !== $scope.interviewschedule.roundName)){
						 $scope.showMsg=true;
						  $scope.cls = 'alert alert-danger alert-error';
						  $scope.message = round +" is scheduled, need to be feedback submitted.";
						  $timeout( function(){ $scope.alHide(); }, 5000);
						  return;
					}
					
					var roundCheck =_.find($scope.interviewDetail.rounds,function(name){
						return name.roundName == $scope.interviewschedule.roundName; 
					});
					
					if(roundCheck===undefined){
						$scope.disableDate = false;
						$scope.scheduleButnDis = false;
					}
					
					angular.forEach($scope.interviewDetail.rounds, function(round){
						if(round.roundName === $scope.interviewschedule.roundName){
							$scope.interviewschedule = round.interviewSchedule;
							$scope.sel.selectedLocation = round.interviewSchedule.interviewLocation;
							$scope.sel.selectedtypeOfInterview = round.interviewSchedule.typeOfInterview;
							
							if(angular.isUndefined($scope.interviewerNames))
							{
								$scope.interviewerNames = [];
								$scope.interviewerNames.push({'name':$scope.interviewschedule.interviewerName,"emailId":$scope.interviewschedule.emailIdInterviewer});
							}
							$scope.interviewschedule.interviewerName = $scope.interviewschedule.emailIdInterviewer;
							$scope.setvalues($scope.interviewschedule.emailIdInterviewer);
							var day = new Date(round.interviewSchedule.interviewDateTime);
							$scope.data.date = day;
							if(round.interviewFeedback===null){
								$scope.disableDate = false;
								$scope.scheduleButnHide = false;
								$scope.scheduleButnDis = false;
							}
							else{
									$scope.cls = 'alert alert-danger alert-error';
									$scope.message = round.roundName +" is already done.";
									$timeout( function(){ $scope.alHide(); }, 5000);							
							}
						}
					});
				}else{
					$scope.scheduleButnDis = false;
				}
		}).catch(function(msg){
			  $scope.cls = 'alert alert-danger alert-error';
			  $scope.message = msg;
			  $timeout( function(){ $scope.alHide(); }, 1000);
		});
	}

	$scope.reset = function(){
		$scope.disableDate = true;
		$scope.scheduleButnHide = true;
		$scope.scheduleButnDis = true;
		/*$scope.jobCodeSel="";
		$scope.interviewschedule.roundName="";
		$scope.data.date="";*/
		$scope.interviewschedule.interviewerName="";
		$scope.interviewschedule.interviewerMobileNumber ="";
		$scope.interviewschedule.emailIdInterviewer = "";
		$scope.interviewschedule.skypeId="";
		$scope.sel.selectedtypeOfInterview="";
		$scope.sel.selectedLocation="";
		$scope.interviewschedule.additionalNotes="";
		$scope.interviewerData.timeSlots="";
	}
	$scope.reSchedule = function(){
		blockUI.start("Scheduling..");
		setTimeout(function () {
		
		$scope.interviewschedule.interviewerName = $scope.interviewerData.name;
		$scope.interviewschedule.jobcode = $scope.jobCodeSel;
		$scope.interviewschedule.typeOfInterview = $scope.sel.selectedtypeOfInterview;
		$scope.interviewschedule.interviewLocation = $scope.sel.selectedLocation;
		$scope.interviewschedule.interviewDateTime = $scope.data.date;
		$scope.interviewschedule.candidateId = $scope.candidate.emailId;
		$scope.interviewschedule.candidateName = $scope.candidate.candidateName;
		$scope.interviewschedule.candidateSkills = $scope.candidate.primarySkills;
		$http.put('resources/interviews/schedule', $scope.interviewschedule).
		  success(function(data, status, headers, config) {
			  $scope.cls = 'alert alert-success alert-error';
			  $scope.message = "Re-scheduled successfully";
			  $timeout( function(){ $scope.alHide(); }, 5000);
			  $log.info("Interview Re-Scheduled!!");
				$scope.reset();
				$scope.jobCodeSel="";
				$scope.interviewschedule.roundName="";
				$scope.data.date="";
				$location.path($scope.previousPage.replace(".", "/"));
		  }).
		  error(function(data, status, headers, config) {
			  $scope.cls = 'alert alert-danger alert-error';
			  $scope.message = "Something wrong, try again";
			  $timeout( function(){ $scope.alHide(); }, 1000);
			 $log.error("failed=="+data);
		  });
		blockUI.stop();
		},1000);
		//blockUI.stop();
	}
}]);
