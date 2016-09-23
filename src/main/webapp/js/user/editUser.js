app.controller('editUserCtrl',['$scope', '$http','$q', '$timeout', '$log', '$rootScope','appConstants','userService',
                               function($scope, $http, $q, $timeout, $log, $rootScope,appConstants, userService) {
	
	$scope.info = $rootScope.info;
	$scope.user = $rootScope.user;
	$scope.isOpen = false;
	$scope.calendar = false;
	$scope.hideCal = true;
	$scope.hideDetails = true;
	$scope.isDisableUserSave = true;
	$scope.days = [
	   			"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
	   	];
	   	$scope.hours = [
	   	   			"1", "1.5", "2", "2.5", "3"
	   	   	];
	$scope.status = {
		    isFirstOpen: true,
		    open: true
		  };
	$scope.hideDetails =  _.contains($scope.user.roles,"ROLE_INTERVIEWER");
	
	$scope.successHide = true;
	
	$scope.update = function(){
		$scope.user.emailId=sessionStorage.userId;
		data = userService.updateUser($scope.user).then(function(msg){
			$scope.sendNotification(msg,'/viewUser');
		}).catch(function(msg){
			$scope.message=msg;
			$scope.cls=appConstants.ERROR_CLASS;
		});
		$scope.isDisableUserSave = true;
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
		if(angular.isUndefined($scope.user.timeSlots) || $scope.user.timeSlots === null){
			$scope.user.timeSlots = [];
		}
		$scope.user.timeSlots.push({
			day : "",
			time : "",
			hour: ""
		});
	}
	
	$scope.removeSlot = function (index) {
		$scope.user.timeSlots.splice(index, 1);
    }
	$scope.validatePhNo = function(data) {
		if (/^\+?\d{10,10}$/.test(data)) {
			return true;
		} else
			return "Enter valid mobile number..";
	};
	$scope.validateSkypeId = function(data) {
		if (/^[a-zA-Z0-9]+$/.test(data)) {
			return true;
		} else
			return "Enter valid SkypeId..";
	};

	$scope.validateText = function(data) {
		if (angular.isUndefined(data) || data === null || data.length == 0  ) {
			
			return "Please enter valid data";
		} else
			return true;
	};

	$scope.validateField = function(data) {
		if (angular.isUndefined(data) || data === null || data.length == 0  ) {
			
			return false;
		} else
			return true;
	};
	
	$scope.validateProfile = function() {
		
		if (!angular.isUndefined($scope.user) && $scope.validateField($scope.user.name) && $scope.validateField($scope.user.dob) && $scope.validateField($scope.user.mobileNumber) ) {
			
			$scope.isDisableUserSave = false;
		} else
			$scope.isDisableUserSave = true;
	};
	
	
}]);