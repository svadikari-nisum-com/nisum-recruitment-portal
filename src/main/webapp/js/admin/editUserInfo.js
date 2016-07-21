app.controller("editUserInfoCtrl",['$scope','$http', '$filter', '$timeout','$q','$state', 'sharedDataService','appConstants', '$log', '$rootScope','$location','clientService','userService','$mdDialog', 
                               	function($scope, $http, $filter, $timeout, $q, $state, sharedDataService,appConstants,$log,$rootScope,$location,clientService,userService,$mdDialog) {
	
	$scope.info = $rootScope.info;
	$scope.showMsg = false;
	$scope.clientList=[];
	$scope.calendar = false;
	$scope.hideCal = true;
	$scope.hideDetails = true;
	$scope.hideRoles = true;
	
	$scope.col=["Name","Email Id","Roles","Client"];
	
	$scope.att=["name","emailId","roles","clientName"];
	$scope.att1=["roles"];
	
	if(sharedDataService.getData() == undefined) {
		location.href="#admin/users";
	}
	
	$scope.userToEdit = sharedDataService.getData();
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
					if(userToEdit.skills!=null && userToEdit.skills!="Click Here To Edit"){
						return true;
					}	
				}	
			}	
		}
		return false;
	}
	
	$scope.validateSlotTimings = function(){
		
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

	$scope.validatePhNo = function(data) {
		if (/^\+?\d{10,12}$/.test(data)) {
			return true;
		} else
			return "Enter valid mobile number..";
	};
		
	$scope.validateAlphanumeric = function(data) {
		if (/^[a-zA-Z0-9]+$/.test(data)) {
			return true;
		} else
			return "Enter valid Skype Id..";
	};
	
}]);
