app.controller("userCtrl", ['$scope', '$http', '$filter', '$timeout','$q','$mdDialog','$state', 'sharedDataService','appConstants', '$log', '$rootScope','$location','userService', 'uiGridConstants',
                            	function($scope, $http, $filter, $timeout, $q,$mdDialog, $state, sharedDataService,appConstants,$log,$rootScope,$location,userService, uiGridConstants) {
	
	$scope.info = $rootScope.info;
	$scope.numRows = 10;
	
	$scope.showMsg = false;
	
	$scope.message = sharedDataService.getmessage();
	$scope.adminCls = sharedDataService.getClass();

	userService.getUsers().then(setUserData).catch(getUserError);
	
	function setUserData(data){
		$scope.myData = data;
		$scope.gridOptions.data = data;
		$scope.gridOptions.totalItems = data.length;
		$scope.gridOptions.paginationPageSize = $scope.numRows;
		$scope.gridOptions.minRowsToShow = data.length < $scope.numRows ? data.length : $scope.numRows;
		
		$timeout( function(){ $scope.message = ""; $scope.cls = ''; sharedDataService.setmessage("");sharedDataService.getClass("");}, 5000);
	}
	
	function getUserError(msg){
		$log.error("Failed!! ---> "+msg);
	}
	
	
	$scope.editUser = function(user) {
		sharedDataService.setmessage(''); 
		sharedDataService.setData(user);
		$scope.userToEdit = user;
		$log.info(angular.toJson($scope.userToEdit));
		location.href="#admin/users/edit";
	};
	
	$scope.alHide =  function(){
	    $scope.message = "";
	    $scope.cls = '';
	}
	
	$scope.gridOptions = {
	    enableSorting: true,
	    enableColumnMenus: false,
		enablePaginationControls: false,
		enableHorizontalScrollbar : uiGridConstants.scrollbars.NEVER,
        enableVerticalScrollbar   : uiGridConstants.scrollbars.NEVER,
		paginationCurrentPage: 1,
	    columnDefs: [
	      { field: 'name', cellClass: 'ui-grid-align'},
	      { field: 'emailId', cellClass: 'ui-grid-align', cellTemplate: '<a style="padding-left: 5px;" ng-click="grid.appScope.editUser(row.entity); $event.stopPropagation();" ui-sref="admin.users.edit">{{row.entity.emailId}} </a>' },
	      { field: 'roles', cellClass: 'ui-grid-align', cellFilter: 'stringArrayFilter' },
	      { field: 'clientName', cellClass: 'ui-grid-align'},
	      { field: 'edit', enableSorting: false , cellTemplate: '<a ng-click="grid.appScope.editUser(row.entity)" ui-sref="admin.users.edit" class="glyphicon glyphicon-edit"></a>'},
	      { field: 'delete', enableSorting: false, cellTemplate: '<a class="glyphicon glyphicon-remove" ng-click="grid.appScope.deleteUser(row.entity)"></a>' }

	      ],
	    onRegisterApi: function( gridApi ) {
	      $scope.grid1Api = gridApi;
	    }
	  };
	$scope.deleteUser = function(rowEntity) {
        
        sharedDataService.showConformPopUp("Are you sure you want to delete?","Delete User",$mdDialog).then(function(){
        	$scope.myData.splice($scope.myData.indexOf(rowEntity), 1);
        	userService.deleteUser(rowEntity).then(function(msg){
	        	$scope.message = rowEntity.name+ "  Deleted Successfully";
	        	$scope.cls = appConstants.SUCCESS_CLASS;
	        	$timeout(function() { $scope.alHide();},3000);
    		}).catch(function(deleteMessage){    			
    			$log.error("Failed! ---> "+deleteMessage);
    		});
        });
        
    }
}]);
