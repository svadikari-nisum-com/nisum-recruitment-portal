app.controller('searchProfileCtrl',['$scope', '$http','$q', '$window','jobCodeService1','$rootScope', '$filter', '$log','appConstants', 'uiGridConstants',
                                    function($scope, $http, $q, $window, jobCodeService1,$rootScope, $filter,$log,appConstants, uiGridConstants) {
	
	$scope.errorHide = true;
	$scope.numRows = 10;
	
	$scope.col=["Name","Email Id","Designation","Experience","Current Employer","Assigned Job Code"];
	
	$scope.att=["candidateName","emailId","designation","expYear","currentEmployer","jobcodeProfile"];
	$scope.att1=["jobcodeProfile"];
	for (i = 0; i< $rootScope.user.roles.length;i++){
    	if($rootScope.user.roles[i] == 'ROLE_USER'){
    		var URLL = 'resources/profile?profilecreatedBy=' + $rootScope.user.emailId;
    	}else{
    		var URLL = 'resources/profile';
    	}
    }
	$http.get(URLL).success(function(data, status, headers, config) {
		$scope.myData = data;
		
		$scope.gridOptions.data = data;
		$scope.gridOptions.totalItems = data.length;
		$scope.gridOptions.paginationPageSize = $scope.numRows;
		$scope.gridOptions.minRowsToShow = data.length < $scope.numRows ? data.length : $scope.numRows;
		
	}).error(function(data, status, headers, config) {
		$log.error("Failed To Get Prfiles! ---> "+data);
	});

	$scope.title = "Search";
	
		$scope.editProfile = function(data) {
			jobCodeService1.setprofileUserId(data.emailId);
			jobCodeService1.setjobCode(data.jobcodeProfile);
			location.href='#recruitment/viewProfile';
		};
		
		$scope.gridOptions = {
		    enableSorting: true,
		    enableColumnMenus: false,
			enablePaginationControls: false,
			enableHorizontalScrollbar : uiGridConstants.scrollbars.NEVER,
	        enableVerticalScrollbar   : uiGridConstants.scrollbars.NEVER,
			paginationCurrentPage: 1,
		    columnDefs: [
		      { field: 'candidateName', displayName:"Name", cellClass: 'ui-grid-align', cellTemplate: '<a style="padding-left: 5px;" ng-click="grid.appScope.editProfile(row.entity); $event.stopPropagation();" ui-sref="recruitment.viewProfile">{{row.entity.candidateName}} </a>'},
		      { field: 'emailId', displayName:"Email Id", cellClass: 'ui-grid-align'},
		      { field: 'designation', displayName:"Designation", width: 150, cellClass: 'ui-grid-align'},
		      { field: 'expYear', displayName:"Experience", width: 100, cellClass: 'ui-grid-align'},
		      { field: 'currentEmployer', displayName:"Current Employer", width: 200, cellClass: 'ui-grid-align'},
		      { field: 'jobcodeProfile', displayName:"Assigned Job Code", cellFilter: 'stringArrayFilter', cellClass: 'ui-grid-align'}
		    ],
		    onRegisterApi: function( gridApi ) {
		      $scope.grid1Api = gridApi;
		    }
		  };
		
	}]);
