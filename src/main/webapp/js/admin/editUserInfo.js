app.controller("editUserInfoCtrl",['$scope','$http', '$filter', '$timeout','$q','$state', 'sharedDataService','appConstants', '$log', '$rootScope','$location','clientService','userService','$mdDialog', 
                               	function($scope, $http, $filter, $timeout, $q, $state, sharedDataService,appConstants,$log,$rootScope,$location,clientService,userService,$mdDialog) {
	
	$scope.info = $rootScope.info;
	$scope.showMsg = false;
	$scope.clientList=[];
	$scope.calendar = false;
	$scope.hideCal = true;
	$scope.hideDetails = true;
	$scope.hideRoles = true;
	$scope.hideTimeSlot = false;
	$scope.closeP = true;
	$scope.hideAddOne = false;
	$scope.hideCreate = true;
	$scope.disableOk = true;
	$scope.interviewRoundsAllocation = {};
	$scope.hideDevisionAllocation = false;
	
	
	$scope.col=["Name","Email Id","Roles","Client"];
	
	$scope.att=["name","emailId","roles","clientName"];
	$scope.att1=["roles"];
	
	$scope.functionalGroups = angular.copy( $scope.info.FunctionalTeam);
	
	if(sharedDataService.getData() == undefined) {
		location.href="#admin/users";
	}
	
	$scope.userToEdit = sharedDataService.getData();
	
	
	if($scope.userToEdit.emailId == sessionStorage.userId )
	{
		$scope.hideTimeSlot = true;
	}
	
	$scope.message = sharedDataService.getmessage();
	$scope.adminCls = sharedDataService.getClass();
	$scope.days = [
	   			"Sunday","Monday","Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
	   	];
	   	$scope.hours = [
	   	   			"1", "1.5", "2", "2.5", "3"
	   	   	];
	$scope.status = {
		    isFirstOpen: true,
		    open: true 
		  };
	
	clientService.getClientInfo().then(setClientList);

	function setClientList(data){
		angular.forEach(data, function(client){
			$scope.clientList.push(client.clientName);
		})
	}
	
	$scope.update = function(){
		
		var validate=$scope.validateSave($scope.userToEdit);
		if(validate && $scope.validateSlotTimings()){
		userService.updateUser($scope.userToEdit)
			.then(function(msg){
				 $scope.sendSharedMessage(msg,'/admin/users');
			})
			.catch(function(msg){
				sharedDataService.setClass(appConstants.ERROR_CLASS);
				sharedDataService.setmessage(msg);
				$timeout( function(){ $scope.alHide(); }, 5000);
			})
		}
		else{
			
			if(validate)
			{
				sharedDataService.showAlertPopUp("Time slots are conflicting. Please change",$mdDialog);
				
				
			}else
			{
				sharedDataService.showAlertPopUp("Please fill Mandatory fields",$mdDialog);
				
			}
			
		}
	}
	
	$scope.alHide =  function(){
	    $scope.message = "";
	    $scope.message1 = "";
	    $scope.cls = '';
	}
	$scope.validateSave = function(userToEdit){
		if(userToEdit.name!=null && userToEdit.name!="Click Here To Edit"){
			if(userToEdit.mobileNumber!=null && userToEdit.mobileNumber!="Click Here To Edit"){
				if(userToEdit.skypeId!=null && userToEdit.skypeId!="Click Here To Edit"){
					return true;
				}	
			}	
		}
		return false;
	}


$scope.validateUserInfo = function() {

	if (!angular.isUndefined($scope.userToEdit) && $scope.validateField($scope.userToEdit.name) 
			&& $scope.validateField($scope.userToEdit.mobileNumber) && $scope.validateField($scope.userToEdit.skypeId)&& $scope.validateField($scope.userToEdit.roles)) {
		
		$scope.isUserFormValid = false;
	} else
		$scope.isUserFormValid = true;
	return true;
};

$scope.validateField = function(data) {
	if (angular.isUndefined(data) || data === null || data.length == 0  ) {
		return false;
	} else
		return true;
};
	
	$scope.validateSlotTimings = function(){
		
		if($scope.userToEdit.timeSlots && $scope.userToEdit.timeSlots.length > 0) {
			for(i in $scope.userToEdit.timeSlots) {
				var ts = $scope.userToEdit.timeSlots[i];
				
				if(ts.isNotAvailable) {
					if(ts.fromDate == null || ts.fromDate.length == 0 || ts.toDate == null || ts.toDate.length == 0) {
						return false;
					}
				}
			}
		}
		
		if( $scope.userToEdit.timeSlots &&  $scope.userToEdit.timeSlots != null)
		{
		for(var initTSIndex = 0; initTSIndex < $scope.userToEdit.timeSlots.length; initTSIndex++) 
		{
			for (var innerTSIndex = initTSIndex + 1; innerTSIndex < $scope.userToEdit.timeSlots.length; innerTSIndex++) 
			{
				
				if( $scope.userToEdit.timeSlots[initTSIndex].day == $scope.userToEdit.timeSlots[innerTSIndex].day)
				{
					var initTSDate = new Date($scope.userToEdit.timeSlots[initTSIndex].time);
					var toCompareDate = new Date($scope.userToEdit.timeSlots[innerTSIndex].time);
					
					if(toCompareDate.getHours() == initTSDate.getHours() )
					{
						return false;
					}else if ( (initTSDate.getHours() + parseInt($scope.userToEdit.timeSlots[initTSIndex].hour)) == toCompareDate.getHours() 
								&& initTSDate.getMinutes() >  toCompareDate.getMinutes() )
					{
						return false;
					}
					else if ( (toCompareDate.getHours() + parseInt($scope.userToEdit.timeSlots[innerTSIndex].hour)) == initTSDate.getHours() 
							&& toCompareDate.getMinutes() >  initTSDate.getMinutes() )
					{
						return false;
					}else if ( 	initTSDate.getHours() <  toCompareDate.getHours() && 
								(initTSDate.getHours() + parseInt($scope.userToEdit.timeSlots[initTSIndex].hour)) > toCompareDate.getHours() )
					{
						return false;
					}
					else if ( toCompareDate.getHours() <  initTSDate.getHours() && 
							(toCompareDate.getHours() + parseInt($scope.userToEdit.timeSlots[innerTSIndex].hour)) > initTSDate.getHours())
					{
						return false;
					}
				}
			}
		}
		}
		return true;
	}
	$scope.validateDate =  function(){
		var date=new Date();
		if(date<$scope.userToEdit.dob){
			$scope.message = "select Proper Date";
			$scope.adminCls = appConstants.ERROR_CLASS;
			$timeout( function(){ $scope.alHide(); }, 5000);
		}
	}
	
	$scope.editRoles = function(){
		$scope.hideRoles = false;
		$scope.hideView = true;
		$scope.tempRoles=$scope.userToEdit.roles;
	}
	
	$scope.openCal = function(){
		$scope.calendar = true;
		$scope.hideCal = false;
		$scope.maxDate = new Date();
		$scope.minDate = new Date().setFullYear(new Date().getFullYear() - 100);
	}
	
	
	$scope.openFromToCal = function(value, slot){
		if(value == 'from') {
			slot.hideFromCal = true;
			slot.fromCalendar = true;
		} else {
			slot.hideToCal = true;
			slot.toCalendar = true;
		}
		
		$scope.timeSlotMinDate = new Date();
	}
	
	
	$scope.resetNotAvailble = function(slot) {
		if(!slot.isNotAvailable) {
			$scope.hideFromCal = false;
			$scope.hideToCal = false;
			$scope.fromCalendar = false;
			$scope.toCalendar = false;
		}
		slot.fromDate = "";
		slot.toDate = "";
	}
	
	$scope.closeFromToCal = function(value, slot) {
		if(value == 'from') {
			slot.fromCalendar = false;
			slot.hideFromCal = false;
		} else {
			slot.toCalendar = false;
			slot.hideToCal = false;
		}
	}
	
	$scope.closeCal = function(){
		$scope.calendar = false;
		$scope.hideCal = true;
	}
	
	$scope.hideEdit = function(){
		if($scope.userToEdit.roles=== undefined)
		{
			$scope.message="Select at least one Role";
			$scope.cls=appConstants.ERROR_CLASS;
			  $timeout( function(){ $scope.alHide(); }, 5000);
			  $scope.userToEdit.roles = $scope.tempRoles;
			return;
		}
		if( _.contains($scope.userToEdit.roles,"ROLE_INTERVIEWER") )
		{
			$scope.hideCreate = false;
		}else
		{
			$scope.hideCreate = true;
		}
		$scope.hideDetails =  _.contains($scope.userToEdit.roles,"ROLE_INTERVIEWER");
		
		$scope.hideRoles = true;
		$scope.hideView = false;	
	}

	$scope.today = function() {
	    $scope.dt = new Date();
	  };

	  $scope.clear = function () {
	    $scope.dt = null;
	  };

	  $scope.dateOptions = {
	    formatYear: 'yy',
	    startingDay: 1,
	    showWeeks: false
	  };

	$scope.disabled = function(date, mode) {
	    return ( mode === 'day' && ( date.getDay() === 0 || date.getDay() === 6 ) );
	};

	$scope.addSlot = function(){
		if(angular.isUndefined($scope.userToEdit.timeSlots) || $scope.userToEdit.timeSlots === null){
			$scope.userToEdit.timeSlots = [];
		}
		var currentDateTime = new Date();
		var setMin = currentDateTime.getMinutes() + (10 - (currentDateTime.getMinutes() % 10));
		if(setMin >= 60)
		{
			currentDateTime.setMinutes("50");
		}else
		{
			currentDateTime.setMinutes(setMin);
		}
		$scope.userToEdit.timeSlots.push({
			day : $scope.days[currentDateTime.getDay()],
			time : currentDateTime,
			hour: "1"
		});
	}
	
	$scope.removeSlot = function (index) {
		$scope.userToEdit.timeSlots.splice(index, 1);
    }
	
	$scope.validateChar = function(data) {
		if (/^[a-zA-Z _]*$/.test(data)) {
			return true;
		} else
			return "Enter valid name..";
	};
	$scope.validateLocation = function(data) {
		if (/^[a-zA-Z _]*$/.test(data)) {
			return true;
		} else
			return "Enter valid Location...";
	};
	$scope.validatePhNo = function(data) {
		if (/^\+?\d{10,12}$/.test(data)) {
			return true;
		} else
			return "Enter valid mobile number..";
	};
		
	$scope.hidecreate = function() {
		
		$scope.hideAddOne = true;
		$scope.hideCreate = false;
		
	}
	$scope.validateAlphanumeric = function(data) {
		if (/^[a-zA-Z0-9]+$/.test(data)) {
			return true;
		} else
			return "Enter valid Skype Id..";
	};
	
	$scope.addFunctionalRounds = function ()
	{
		
		if(!$scope.userToEdit.interviewRoundsAllocation )
		{
			$scope.userToEdit.interviewRoundsAllocation = [];
		}
		$scope.userToEdit.interviewRoundsAllocation.push({'department':$scope.functionalGroups[$scope.divisionIdx],"interviewRounds":$scope.interviewRoundsAllocation.selectedRounds});
		$scope.functionalGroups.splice($scope.divisionIdx,1);
		if($scope.functionalGroups.length == 0)
		{
			$scope.hideDevisionAllocation = true;
		}
		$scope.divisionIdx=undefined;
		$scope.interviewRoundsAllocation.selectedRounds = "";
		$scope.disableOk = true;
		
	}
	$scope.deleteInterviewRound = function(index)
	{
		$scope.functionalGroups.push($scope.userToEdit.interviewRoundsAllocation[index].department);
		$scope.userToEdit.interviewRoundsAllocation.splice(index,1);
		$scope.hideDevisionAllocation = false;
		
	}
	$scope.disableAdd = function() {
		
		if(angular.isDefined($scope.divisionIdx) && angular.isDefined($scope.interviewRoundsAllocation.selectedRounds) && $scope.interviewRoundsAllocation.selectedRounds.length > 0 )
		{
			$scope.disableOk = false;
		}else
		{
			$scope.disableOk = true;
		}
	};
	
	$scope.init = function() {
		
		if(_.contains($scope.userToEdit.roles,"ROLE_INTERVIEWER"))
		{
			$scope.hideCreate = false ;
		}
		$scope.interviewFunctionalRounds = angular.copy( $scope.info.interviewRounds);
		$scope.interviewFunctionalRounds.splice($scope.interviewFunctionalRounds.indexOf("Hr Round"), 1);
		$scope.interviewFunctionalRounds.splice($scope.interviewFunctionalRounds.indexOf("Manager Round"), 1);
		
		angular.forEach($scope.userToEdit.interviewRoundsAllocation, function(object , index){
			$scope.functionalGroups.splice($scope.functionalGroups.indexOf(object.department),1);
		});
		
		
		if($scope.functionalGroups.length == 0)
		{
			$scope.hideDevisionAllocation = true;
		}
		
		
		
	};
	$scope.init();
	
	
}]);
