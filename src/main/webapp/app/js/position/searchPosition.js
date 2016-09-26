app.controller('searchPositionCtrl',['$scope', '$http','$q','jobCodeService1','$filter', '$log','positionService','appConstants', 'uiGridConstants',
                                     function($scope, $http, $q, jobCodeService1,$filter, $log,positionService,appConstants, uiGridConstants) {

	$scope.approveBtnDisable = true;
	$scope.errorHide = true;
	$scope.data = {};
	$scope.message = "";
	$scope.numRows = 10;
	
	$scope.title = "Search";
	$scope.managerId = undefined;
	
	if($scope.hasRole('ROLE_MANAGER'))
	{
		$scope.managerId = $scope.user.emailId;
	}
	
	positionService.getPosition($scope.managerId).then(function(data){
		$scope.position=data;
		
		$scope.gridOptions.data = data;
		$scope.gridOptions.totalItems = data.length;
		$scope.gridOptions.paginationPageSize = $scope.numRows;
		$scope.gridOptions.minRowsToShow = data.length < $scope.numRows ? data.length : $scope.numRows;

	}).catch(function(msg){
   	  $log.error("Failed To Load Data! ---> "+msg);
   	  $scope.errorHide = false;
   	  $scope.errorMsg = msg;
     })
	
	$scope.itemsPerPage = appConstants.ITEMS_PER_PAGE;
	$scope.currentPage = 0;
	$scope.changePage = function(){
		$scope.currentPage = 0;
	}
	$scope.range = function (start) {
		var pageCnt = $scope.pageCount();
        var ret = [];

		if (start + 1 == pageCnt && pageCnt==1) {
			ret.push(0);
			return ret;
		}
		if ((start + 2 >= pageCnt)) {
			while (start + 2 >= pageCnt)
				start--;
		}
		if(start<0)
			start=0;
		for (var i = start; i < pageCnt; i++) {
			ret.push(i);
			if (i == start + 2)
				break;
		}
		return ret;
    };

		  $scope.prevPage = function() {
		    if ($scope.currentPage > 0) {
		      $scope.currentPage--;
		    }
		  };

		  $scope.pageCount = function() {
			if (!$scope.position) { return; }
		    return Math.ceil($scope.position.length/$scope.itemsPerPage);
		  };

		  $scope.nextPage = function() {
		    if ($scope.currentPage > $scope.pageCount()) {
		      $scope.currentPage++;
		    }
		  };

		  $scope.setPage = function() {
		    $scope.currentPage = this.n;
		  };
	
	
	$scope.editPosition = function(jobcodeProfile) {
		jobCodeService1.setjobCode(jobcodeProfile);
		location.href='#recruitment/viewPosition';
	};
	
	$scope.shareContent = function(jobcodeProfile)  {
		$scope.position
		positionService.getPositionByJobcode(jobcodeProfile).then(function(data) {
			$scope.position = data;
		})
		
		    var payload = { 
		      "comment": $scope.position[0].jobProfile, 
		      "visibility": { 
		        "code": "anyone"
		      } 
		    };
		    
		    IN.API.Raw("/people/~/shares?format=json")
		      .method("POST")
		      .body(JSON.stringify(payload))
		      .result($scope.onSuccess)
		      .error($scope.onError);
	}
	
	
	$scope.onSuccess = function(data) {
		$scope.message = "shared successfully";
	  }

	  // Handle an error response from the API call
	$scope.onError = function(error) {
		$scope.message = error.message;
	  }
	
	$scope.gridOptions = {
	    enableSorting: true,
	    enableColumnMenus: false,
		enablePaginationControls: false,
		enableHorizontalScrollbar : uiGridConstants.scrollbars.NEVER,
        enableVerticalScrollbar   : uiGridConstants.scrollbars.NEVER,
		paginationCurrentPage: 1,
	    columnDefs: [
	      { field: 'jobcode', displayName:"Job code", cellClass: 'ui-grid-align', cellTemplate: '<div class="text-wrap"><a style="padding-left: 5px;" ng-click="grid.appScope.editPosition(row.entity.jobcode); $event.stopPropagation();">{{row.entity.jobcode}}<md-tooltip>  {{row.entity.jobcode}} </md-tooltip> </a></div>'},
	      { field: 'designation', displayName:"Role", cellClass: 'ui-grid-align'},
	      { field: 'minExpYear', displayName:"Min-Exp", width: 100, cellClass: 'ui-grid-align'},
	      { field: 'maxExpYear', displayName:"Max-Exp", width: 100, cellClass: 'ui-grid-align'},
	      { field: 'location', displayName:"Location", cellClass: 'ui-grid-align'},
	      { field: 'client', displayName:"Client", cellClass: 'ui-grid-align'}
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
	        ['jobcode'].forEach(function(field) {
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

app.filter('offset', function() {
	  return function(input, start) {
	  start = parseInt(start, 10);
  return input.slice(start);
};
});