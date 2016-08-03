app.controller('searchProfileCtrl',['$scope', '$http','$q', '$window','jobCodeService1','$rootScope', '$filter', '$log','appConstants', 'uiGridConstants',
                                    function($scope, $http, $q, $window, jobCodeService1,$rootScope, $filter,$log,appConstants, uiGridConstants) {
	
	$scope.errorHide = true;
	$scope.numRows = 10;
	
	$scope.col=["Name","Email Id","Designation","Experience","Recrutier","Interview Status"];
	
	$scope.att=["candidateName","emailId","designation","expYear","hrAssigned","interviewProgress"];
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
			rowTemplate: '<div <div ng-repeat="col in colContainer.renderedColumns track by col.colDef.name"  class="ui-grid-cell" ui-grid-cell><md-tooltip>{{row.entity.jobcodeProfile}}</md-tooltip></div></div>',
		    columnDefs: [
		      { field: 'candidateName', displayName:"Name", cellClass: 'ui-grid-align', cellTemplate: '<a style="padding-left: 5px;" ng-click="grid.appScope.editProfile(row.entity); $event.stopPropagation();" ui-sref="recruitment.viewProfile">{{row.entity.candidateName}} </a>'},
		      { field: 'emailId', displayName:"Email Id", cellClass: 'ui-grid-align'},
		      { field: 'designation', displayName:"Designation", width: 150, cellClass: 'ui-grid-align'},
		      { field: 'expYear', displayName:"Experience", width: 100, cellClass: 'ui-grid-align'},
		      { field: 'hrAssigned', displayName:"Recrutier", width: 200, cellClass: 'ui-grid-align'},
		      { field: 'interviewProgress', displayName:"Interview Status", cellClass: 'ui-grid-align'}
		    ],
		    onRegisterApi: function( gridApi ) {
		    	$scope.gridApi = gridApi;
	 	        $scope.gridApi.grid.registerRowsProcessor($scope.singleFilter, 200);
		    }
		  };
		
		$scope.searchFilter = function() {
			$scope.gridApi.grid.refresh();
		};

		$scope.singleFilter = function(renderableRows) {
			var searchValue = "";
			if($scope.filterValue){
				searchValue = $scope.filterValue.toUpperCase()
			}
		    var matcher = new RegExp(searchValue);
		    renderableRows.forEach(function(row) {
		        var match = false;
		        ['candidateName'].forEach(function(field) {
		            if (row.entity[field] && row.entity[field].toUpperCase().match(matcher)) {
		                match = true;
		            }
		        });
		        if (!match) {
		            row.visible = false;
		        }
		    });
		    return renderableRows;
		};
		
	}]);
