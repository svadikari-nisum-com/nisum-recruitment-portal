app.controller('offerManagementCtrl',['$scope', '$http','$q', '$window','$state', '$timeout','$filter','$log','appConstants','infoService','offerService', 'uiGridConstants',
                                      function($scope, $http, $q, $window, $state, $timeout,$filter,$log,appConstants,infoService, offerService, uiGridConstants) {
	
	$scope.col=["Name","Email Id","Client","Status"];
	
	$scope.att=["candidateName","emailId","client","status"];
	
	$scope.numRows = 10;
	
	$http.get('resources/profile').success(function(data, status, headers, config) {
		$scope.myData = data;
		
		$scope.gridOptions.data = data;
		$scope.gridOptions.totalItems = data.length;
		$scope.gridOptions.paginationPageSize = $scope.numRows;
		$scope.gridOptions.minRowsToShow = data.length < $scope.numRows ? data.length : $scope.numRows;
	}).error(function(data, status, headers, config) {
		$log.error("Failed To Get Profiles! ---> "+data);
	});
	
	$scope.selectCandidate = function(profile) {
		offerService.setData(profile);
		$state.go("offer.createOffer");
	}

	$scope.title = "Offer";
	
	$scope.gridOptions = {
	    enableSorting: true,
	    enableColumnMenus: false,
		enablePaginationControls: false,
		enableHorizontalScrollbar : uiGridConstants.scrollbars.NEVER,
        enableVerticalScrollbar   : uiGridConstants.scrollbars.NEVER,
		paginationCurrentPage: 1,
	    columnDefs: [
	      { field: 'candidateName', displayName:"Name", cellClass: 'ui-grid-align', cellTemplate: '<a style="padding-left: 5px;" ng-click="grid.appScope.selectCandidate(row.entity); $event.stopPropagation();" ui-sref="offer.createOffer">{{row.entity.candidateName}} </a>'},
	      { field: 'emailId', displayName:"Email Id", cellClass: 'ui-grid-align'},
	      { field: 'clinet', displayName:"Client", cellClass: 'ui-grid-align'},
	      { field: 'status', displayName:"Status", cellClass: 'ui-grid-align'}
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