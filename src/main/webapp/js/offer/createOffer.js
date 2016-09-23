
app.directive('number', function () {
    return {
        link: function (scope, el, attr) {
            el.bind("keydown keypress", function (event) {
                //ignore all characters that are not numbers, except backspace, delete, left arrow and right arrow
                if ((event.keyCode < 48 || event.keyCode > 57) && event.keyCode != 8 && event.keyCode != 46 && event.keyCode != 37 && event.keyCode != 39) {
                    event.preventDefault();
                }
            });
        }
    };
});

app.controller('createOfferCtrl',['$scope','$state','$http','$upload','$q','$timeout','$filter','$log','appConstants','infoService','offerService','userService','designationService',
    function($scope, $state, $http, $upload, $q, $timeout,$filter,$log,appConstants,infoService, offerService, userService ,designationService) {

	if(offerService.getData() == undefined) {
		$state.go('offer.list');
	}

	$scope.profile = offerService.getData();
	// console.log(angular.toJson($scope.profile));	
	$scope.pageName = "Create Offer";
	//$scope.designations = [];
	$scope.candidate = {};
	$scope.candidate.emailId = $scope.profile.emailId;
	if($scope.profile.mobileNo && $scope.profile.mobileNo != null)
	{
		$scope.candidate.mobileNo = $scope.profile.mobileNo;
	}
	$scope.candidate.jobcodeProfile = angular.copy($scope.profile.jobcodeProfile[0]);
	$scope.candidate.candidateName = $scope.profile.candidateName;
	$scope.candidate.project = "";
	$scope.candidate.reportingManager = "";
	$scope.candidate.imigrationStatus = "";
	$scope.candidate.offerLetterName = "";
	$scope.candidate.relocationAllowance = "";
	$scope.candidate.singInBonus = "";
	$scope.candidate.ctc = "";
	$scope.candidate.designation = "";
	$scope.candidate.comments = "";
	$scope.candidate.joiningDate = "";
	$scope.candidate.location = "";
	$scope.managers = [];
	$scope.iStatus = ["B-1/B-2 VISITOR FOR BUSINESS/PLEASURE",
	                  "WB (WAIVER OF BUSINESS)",
	                  "WT (WAIVER OF TOURIST)",
	                  "F-1 STUDENT",
	                  "J-1 EXCHANGE VISITOR(STUDENTS)",
	                  "J-1 EXCHANGE VISITOR (PROFESSORS AND RESEARCHERS)",
	                  "H-1B TEMPORARY WORKER",
	                  "O-1",
	                  "TN"];
	$scope.allowances = ["$100","$150","$200"];
	$scope.bonus = ["$200","$400","$600"];
	$scope.isDisableOfferSave = true;
	//if($scope.candidate.jobcodeProfile=="")
		 $scope.candidate.status = "NOTINITIATED";
	// else
	//	 $scope.candidate.status = "INITIATED";
	var offerLetterFile = null;
	$scope.invalidFile = true;
	
	
	userService.getUsers().then(
			function(data){
				angular.forEach(data,function(userInfo) {
				if(_.contains(userInfo.roles, "ROLE_MANAGER")){
					$scope.managers.push(userInfo);
				}
			});
		}).catch();
	
	var GET_POSTION_DETAILS='resources/positions?searchKey=jobcode&searchValue='+$scope.profile.jobcodeProfile;
	var RELEASE_OFFER='resources/save-offer';
	
	$http.get(GET_POSTION_DETAILS).success(function(data2, status, headers, config) {
		$scope.candidate.client = data2.client;
		$scope.candidate.reportingManager = data2.hiringManager;
		// console.log(angular.toJson(data2));
	}).error(function(data, status, headers, config) {
		$log.error(data);
	});
	
	$scope.getNextStatus = function(){
		offerService.getNextStatuses($scope.candidate.status).then(function(data){
			$scope.offerData = data;
		}).catch(function(msg){
			$log.error(msg);
		});
	}
	
	/*$scope.validateStatus = function(){
		if($scope.candidate.status === 'CLOSED'){
			$scope.statusClosed = true;
		}
	}*/

	$scope.saveOffer = function() {
		//Removed this functionality as we are generating offer letter when the status is "RELEASED"
		//$scope.uploadFileIntoDB($scope.offerLetterFile);
		$http.post('resources/offers', $scope.candidate).success(function(data, status) {
			$log.info("saved offer...");
			$scope.sendNotification("Offer Saved Successfully",'/offer');
		  }).error(function(data) {
			$log.error("error saving offer..." + data);
		});
	};
	
	  
	$scope.validateOffer = function() {
		if (!angular.isUndefined($scope.candidate)
				&& $scope.validateField($scope.candidate.project) && $scope.validateField($scope.candidate.reportingManager) 
				&& $scope.validateField($scope.candidate.imigrationStatus) && $scope.validateField($scope.candidate.joiningDate)
				&& $scope.validateField($scope.candidate.designation) && $scope.validateField($scope.candidate.ctc)
				&& $scope.validateField($scope.candidate.relocationAllowance) && $scope.validateField($scope.candidate.singInBonus)
				&& $scope.validateField($scope.candidate.status)) {
			
			$scope.isDisableOfferSave = false;
		} else
			$scope.isDisableOfferSave = true;
	};
	
	$scope.validateReportingManager = function(){
		var managersArray = $scope.managers;
		var value = false;
		for (index = 0; index < managersArray.length; ++index) {
			if(managersArray[index].name == $scope.candidate.reportingManager){
				value = true;
				$scope.saveOffer();
			}
		}
		
		if(!value){
			 $scope.message=$scope.candidate.reportingManager + "is not a valid manager.";
			 $scope.candidate.reportingManager = undefined;
			 $scope.cls=appConstants.ERROR_CLASS;
			 $timeout( function(){ $scope.alHide(); }, 4000);
		}
	}
	
	$scope.alHide =  function(){
	    $scope.message = "";
	    $scope.cls = '';
	}
	
	$scope.validateField = function(data) {
		if (angular.isUndefined(data) || data === null || data.length == 0  ) {
			return false;
		} else
			return true;
	};
	
	$scope.uploadFileIntoDB = function (files) {
        if (files && ( files.length==1 )) {
            for (var i = 0; i < files.length; i++) {
                var file = files[0];
                $upload.upload({
                    url: 'resources/'+ $scope.candidate.emailId +'/upload-offer-letter',
                    file: file
                }).progress(function (evt) {
                }).success(function (data, status) {
                	$log.info("Offer Letter Saved...");
                }).error(function (data, status) {
                	$log.error("Uploading Offer Letter Failed ! ---> " + data);
                });
            }
        }
        
	};

	$scope.upload = function (files) {
		if(files[0].name.toLowerCase().includes(".pdf") || files[0].name.toLowerCase().includes(".doc") || files[0].name.toLowerCase().includes(".docs") || files[0].name.toLowerCase().includes(".docx")){
			$scope.offerLetterFile = files;
			$scope.candidate.offerLetterName = $scope.candidate.emailId + "_" + files[0].name;
			$scope.invalidFile = false;
			$scope.fileError = false;
		}else{
			$scope.fileError = true;
			document.getElementById("uploadFile").value = "";
		}
		document.getElementById("uploadFile").onchange = function() {
		    if(uploadFile.value) {
		        document.getElementById("submit").disabled = false; 
		        $scope.invalidFile = false;
		    }  
		}
	};
	
	$scope.today = function() {
	    $scope.dt = new Date();
	  };
	  
    $scope.today();

	$scope.clear = function () {
	    $scope.dt = null;
	};
	
	// Disable weekend selection
	$scope.disabled = function(date, mode) {
	   return ( mode === 'day' && ( date.getDay() === 0 || date.getDay() === 6 ) );
	};
	  
	$scope.toggleMin = function() {
	   $scope.minDate = $scope.minDate ? null : new Date();
	};
	$scope.toggleMin();

	$scope.open = function($event) {
	   $event.preventDefault();
	   $event.stopPropagation();
      $scope.opened = true;
	};

	$scope.dateOptions = {
	   formatYear: 'yy',
	   startingDay: 1
	};
	  
	$scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
	$scope.format = $scope.formats[0];
	
	$scope.cancel = function() {
	    $state.go('recruitment.interviewManagement');
	};
	
	var init = function () {
		
		designationService.getDesignation().then(function(data){
			$scope.designations=data;
		}).catch(function(msg){
			$scope.message=msg;
			 $scope.cls=appConstants.ERROR_CLASS;
			 $timeout( function(){ $scope.alHide(); }, 5000);
		});
		
		$http.get("resources/offers/"+offerService.getData().emailId).success(function(data, status)
		{
			if(data != null)
			{
				$scope.candidate = data;
				$scope.pageName = "Update Offer";
			}
			
			$scope.getNextStatus();
		}).catch(function(status){
			$scope.getNextStatus();
			$log.error(status);
		});
		
		
	};
	
	init();
	
	
	
	
}]);