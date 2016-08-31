app.controller('clientCtrl',['$scope','$rootScope','$http','$q', '$timeout', '$log','$location', '$state', '$mdDialog', 'jobCodeService1','appConstants','sharedDataService','clientService', 'uiGridConstants',
                             function($scope,$rootScope, $http, $q, $timeout, $log, $location, $state, $mdDialog,jobCodeService1,appConstants,sharedDataService,clientService, uiGridConstants) {
	
	$scope.client = {};
	$scope.clients = {};

	$scope.col=["Clients","Location","Delete"];
	
	$scope.att=["clientName","locations"];
	//$scope.att1=["roles"];
	$scope.clientCls = sharedDataService.getClass();
	$scope.message = sharedDataService.getmessage();
	$scope.client.interviewers = {"technicalRound1": [], "technicalRound2": []};
	
    $scope.locations = $rootScope.info.locations;
	$scope.client.locations="";
	clientService.getClientInfo()
	 .then(setClients);
	
	$scope.numRows = 10;

	function setClients(data){
			$scope.clients = data;
			$scope.gridOptions.data = data;
			$scope.gridOptions.totalItems = data.length;
			$scope.gridOptions.paginationPageSize = $scope.numRows;
			$scope.gridOptions.minRowsToShow = data.length < $scope.numRows ? data.length : $scope.numRows;
			$timeout( function(){ $scope.message = ""; $scope.cls = ''; sharedDataService.setmessage("");sharedDataService.getClass("");}, 3000);
		}
	
	$scope.submit = function(){
			$scope.client.clientId = $scope.client.clientName.toUpperCase().replace(/\s/g, '');
			clientService.createClient($scope.client)
						 .then(function(msg) { 
							 $scope.sendSharedMessage(msg,'/admin/client');
						 })
						 .catch(function (msg) {
							 $scope.message=msg;
							 $scope.cls=appConstants.ERROR_CLASS;
						});
	}
	
	/*$scope.deleteClient = function(rowEntity){
		clientService.removeClient(clientId)
		 .then(successMsg)
		 .catch(errorMsg);
		
	function successMsg(msg) { 
			$scope.sendSharedMessage(msg,'/admin/client');
      };
      
  	function errorMsg(msg) { 
  		$scope.message=msg;
		$scope.cls=appConstants.ERROR_CLASS;  	};

	}*/
	
/*	$scope.checkClients = function(){
		angular.forEach($scope.clients, function(cl){
			if($scope.client.clientName == cl.clientName){
				 //$scope.message="Client Already Exists";
				 $scope.cls=appConstants.ERROR_CLASS;
				 $scope.checkCl = false;
		}});
		return true;
	}*/
	
	$scope.editClient = function(data){
		jobCodeService1.setclientId(data.clientId);
		jobCodeService1.setclientName(data.clientName);
		//location.href='#admin/client/editClient';
		$state.go('admin.client.editClient');
	}
	
	$scope.status1 = {
			isFirstOpen: true,			    
			open1:true
	};
	
	$scope.gridOptions = {
	    enableSorting: true,
	    enableColumnMenus: false,
		enablePaginationControls: false,
		enableHorizontalScrollbar : uiGridConstants.scrollbars.NEVER,
        enableVerticalScrollbar   : uiGridConstants.scrollbars.NEVER,
		paginationCurrentPage: 1,
	    columnDefs: [
	      { field: 'clientName', displayName:"Clients", cellClass: 'ui-grid-align', cellTemplate: '<a style="padding-left: 5px;" ng-click="grid.appScope.editClient(row.entity); $event.stopPropagation();" ui-sref="admin.client.editClient">{{row.entity.clientName}} </a>'},
	      { field: 'locations', displayName:"Location", cellClass: 'ui-grid-align'},
	      { field: 'delete', enableSorting: false, cellTemplate: '<a class="glyphicon glyphicon-remove" ng-click="grid.appScope.deleteClient(row.entity)"></a>' }
	    ],
	    onRegisterApi: function( gridApi ) {
	      $scope.gridApi = gridApi;
	      $scope.gridApi.grid.registerRowsProcessor($scope.singleFilter, 200);
	    }
	  };
	
	$scope.searchFilter = function() {
		$scope.gridApi.grid.refresh();
	};
	$scope.deleteClient = function(rowEntity) {
		sharedDataService.showConformPopUp("Are you sure you want to delete?","Delete Client",$mdDialog).then(function(){
			$scope.clients.splice($scope.clients.indexOf(rowEntity), 1);
        	clientService.removeClient(rowEntity.clientId).then(function(msg){
	        	$scope.message = rowEntity.clientName+ " " + msg;
	        	$scope.cls = appConstants.SUCCESS_CLASS;
	        	$timeout(function() { $scope.alHide();},3000);
    		}).catch(function(deleteMessage){
    			sendSharedMessage(msg, appConstants.ERROR_CLASS);
                $timeout(function() { $scope.alHide(); }, 5000);
    		}); 
		});
    }
	$scope.singleFilter = function(renderableRows) {
		var searchValue = "";
		if($scope.filterValue){
			searchValue = $scope.filterValue.toUpperCase()
		}
	    var matcher = new RegExp(searchValue);
	    renderableRows.forEach(function(row) {
	        var match = false;
	        ['clientName'].forEach(function(field) {
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