app.controller("createPositionCtrl", ['$scope', '$http', '$upload','$filter', '$timeout','$q', '$rootScope', '$log','$location','appConstants','clientService','positionService','userService','designationService', 
                                      			function($scope, $http, $upload, $filter, $timeout, $q, $rootScope,$log,$location,appConstants,clientService, positionService,userService,designationService) {
	$scope.jbDisabled = true;
    $scope.position ={};
    $scope.position.jobProfile="";
   
    
    var ran = Math.floor((Math.random()*999)+1);
    var desgn = "";
    var sclient = "";
    var sloc = "";
   
	$scope.position.primarySkills = {};
	$scope.position.interviewRounds = {};
	//$scope.position.interviewRounds = ['Technical Round 1','Technical Round 2','Manager Round', 'Hr Round'];
	$scope.position.designation = "";
	$scope.position.minExpYear = "";
	$scope.position.maxExpYear = "";
	$scope.position.location = "";
	$scope.position.client = "";
	$scope.position.hiringManager = "";
	$scope.position.functionalGroup = "";
	$scope.position.priority = "";
	$scope.position.jobType = "";
	$scope.position.salary = "";
	$scope.interviewer= "";
	$scope.interviewers = {};
	$scope.position.noOfPositions = "";
	
	$scope.enableDisbleButton = true;
	$scope.emailId ="";
	$scope.roles = [];
	$scope.message = "";
	$scope.info = $rootScope.info;
	$scope.pskills = [];
	$scope.interview=[];
	$scope.data = {};
	$scope.multipleDemo = {};
	$scope.clients = {};
	$scope.client=[];
	$scope.designation1=[];
	$scope.skill=[];
	$scope.experience=[];
	$scope.designations={};
	$scope.managers = [];
	$scope.minExpYear=[];
	$scope.maxExpYear=[];
	$scope.recruitmentData = [];
	$scope.functionalGroups = ["DEV","QA","NOC","SUPPORT"];

	$scope.pskills=$rootScope.info.skills;
	$scope.interview=$scope.info.interviewRounds;
	$scope.functionalGroups = $scope.info.FunctionalTeam;
	$scope.locationHeads = [];
	
	  $scope.getData = function() {
		  
		  $scope.deg  =_.find($scope.designations,function(obj){
				return obj.designation == $scope.position.designation; 
			});
		  $scope.skill.length = 0;
		  angular.forEach($scope.deg.skills,function(deg){
				$scope.skill.push(deg);
			})
			$scope.position.primarySkills=$scope.skill;
			$scope.position.minExpYear = $scope.deg.minExpYear;
			$scope.position.maxExpYear = $scope.deg.maxExpYear;
		};
		$scope.validate =  function(){
			$scope.alHide();		
		    if(parseInt($scope.position.maxExpYear)<parseInt($scope.position.minExpYear)){		    	
			    $scope.cls=appConstants.ERROR_CLASS;
			    $scope.position.maxExpYear="";
			    $timeout( function(){ $scope.alHide(); }, 5000);
		    }
		}	
		$scope.validateMinExp =  function(){
			$scope.alHide();		
		    if(parseInt($scope.position.maxExpYear)<parseInt($scope.position.minExpYear)){		    	
			    $scope.cls=appConstants.ERROR_CLASS;
			    $scope.position.minExpYear="";
			    $timeout( function(){ $scope.alHide(); }, 5000);
		    }
		}
		
	designationService.getDesignation().then(function(data){
		$scope.designations=data;
		angular.forEach($scope.designations,function(deg){
			$scope.designation1.push(deg.designation);
		})
		angular.forEach($scope.designations,function(deg){
			$scope.minExpYear.push(deg.minExpYear);
		})
		angular.forEach($scope.designations,function(deg){
			$scope.maxExpYear.push(deg.maxExpYear);
		})
	}).catch(function(msg){
		$scope.message=msg;
		 $scope.cls=appConstants.ERROR_CLASS;
		 $timeout( function(){ $scope.alHide(); }, 5000);
	});
	clientService.getClientInfo()
		.then(function(data){
				$scope.clients = data;
				angular.forEach($scope.clients,function(cl){
					$scope.client.push(cl.clientName);
				})
			  });
	 /*userService.getCurrentUser().then(function (data){
    angular.forEach(data,function(userrs){
       $scope.emailId  = userrs.emailId;
       $scope.roles = userrs.roles[0];
    })
});*/
	$scope.getManagers = function(){
		
		
		$scope.managers = [];
		userService.getUserByRole("ROLE_MANAGER").then(function (data){
			
			angular.forEach(data,function(user) {
				if(user.emailId == sessionStorage.userId ) {
			          $scope.position.hiringManager = user.emailId;
		           }
				
				$scope.managers.push({"emailId":user.emailId,"name":user.name});
			})
			
		});
	}	
	$scope.getManagers();
	$scope.getLocationHeads = function(){
		
		
		$scope.locationHeads = [];
		userService.getUserByRole("ROLE_LOCATIONHEAD").then(function (data){
			
			angular.forEach(data,function(user) {		
				
				$scope.locationHeads.push({"emailId":user.emailId,"name":user.name});
			})
			
		});
	}	
	$scope.getLocationHeads();
		/*$scope.names = [];
		$scope.object = [];
		$scope.filterNames = [];
		clientService.getClientByName(ClientName)
		.then(function(data1){
				$scope.interviewers = data1[0].interviewers;
				techrounds = [$scope.interviewers.technicalRound1, $scope.interviewers.technicalRound2];
				
				angular.forEach(techrounds,function(techround){
					$scope.object = techround;
					angular.forEach($scope.object,function(clientName){
						
					$scope.names.push(clientName.name);
					$scope.filterNames = _.uniq($scope.names);
				   })
				})
				
			  });
		userService.getUserDetailsByClientName(ClientName)
		.then(function(data){
			$scope.users = data;
			angular.forEach($scope.users,function(user){
				if(user.roles.indexOf("ROLE_MANAGER") >= 0 )
				$scope.managers.push(user.name);
			})
			
		  });*/

		
	userService.getUserByRole("ROLE_RECRUITER").then(function (data){
		    angular.forEach(data,function(userr) {
			           if(userr.emailId == sessionStorage.userId) {
				          $scope.position.interviewer = userr.name;
			           }
			           $scope.recruitmentData.push(userr.name);
		    })
	}).catch(function(message) {
	   $log.error(message)
   });
    $scope.loadRounds = function(query) {
    	$scope.interview=$scope.info.interviewRounds;
		return $scope.interview; 
	};
	
	$scope.reset = function() {
		$scope.position = angular.copy($scope.master);
		$scope.position={};
	}
	
	$scope.submit = function() {
		if ($scope.position !== undefined) {

			$scope.position.jobcode = $scope.setJobCode($scope.position.designation,$scope.position.client,$scope.position.location);
			
			
			positionService.createPosition($scope.position)
				.then(successMsg)
				.catch(errorMsg);
			
			function successMsg(msg){
				$scope.sendNotification(msg,'recruitment/searchPosition');
			}
			
			function errorMsg(msg){
				$scope.message=msg;
				$scope.cls=appConstants.ERROR_CLASS;
			}
		}
	}

	
	$scope.setJobCode = function(designation,client,location){
		desg = angular.uppercase($filter('limitTo')(designation, 3, 0));
		sclient = angular.uppercase(client.replace(/\s+/g, '-'));
		sloc = angular.uppercase($filter('limitTo')(location, location.length<3? location.length:3, 0));
		var dt = new Date();
		var curr_date = dt.getDate();
	    var curr_month = dt.getMonth() + 1;
	    var curr_year = dt.getFullYear();
	    return desg + "_" + sclient + "_" + sloc + "_" + curr_date + curr_month + curr_year + "_" + ran;
	}
	
	
	$scope.myFunct = function(keyEvent) {
		  if (keyEvent.which === 13)
			  keyEvent.preventDefault();
	}
	
	$scope.$on('$stateChangeStart', function( event ) {
		if($scope.position_form.$dirty) {
		/*
		 * //var answer = confirm("You have unsaved changes, do you want to
		 * continue?") if (!answer) { event.preventDefault(); }
		 */
		}
	});
	
	document.querySelector('#numberOfPositions').addEventListener('input', function(){
	    var num = this.value.match(/^\d+$/);
	    if (num === null) {
	        this.value = "";
	    }
	}, false)
}]);
