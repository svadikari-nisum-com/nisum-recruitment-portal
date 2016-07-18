app.controller('clientCtrl',['$scope','$rootScope','$http','$q', '$window', '$timeout', '$log','$location', '$state', 'jobCodeService1','appConstants','sharedDataService','clientService', 
                             function($scope,$rootScope, $http, $q, $window, $timeout, $log, $location, $state, jobCodeService1,appConstants,sharedDataService,clientService) {
	
	$scope.client = {};
	$scope.clients = {};

	$scope.col=["Clients","Location"];
	
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
		if($scope.checkClients()){
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
	}
	
	$scope.deleteClient = function(clientId){
		clientService.removeClient(clientId)
		 .then(successMsg)
		 .catch(errorMsg);
		
	function successMsg(msg) { 
			$scope.sendSharedMessage(msg,'/admin/client');
      };
      
  	function errorMsg(msg) { 
  		$scope.message=msg;
		$scope.cls=appConstants.ERROR_CLASS;  	};

	}
	
	$scope.checkClients = function(){
		angular.forEach($scope.clients, function(cl){
			if($scope.client.clientName == cl.clientName){
				 $scope.message="Client Already Exists";
				 $scope.cls=appConstants.ERROR_CLASS;
				 $scope.checkCl = false;
		}});
		return true;
	}
	
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
		paginationCurrentPage: 1,
	    columnDefs: [
	      { field: 'clientName', displayName:"Clients", cellClass: 'ui-grid-align', cellTemplate: '<a style="padding-left: 5px;" ng-click="grid.appScope.editUser(row.entity); $event.stopPropagation();" ui-sref="admin.client.editClient">{{row.entity.clientName}} </a>'},
	      { field: 'locations', displayName:"Location", cellClass: 'ui-grid-align'}
	    ],
	    onRegisterApi: function( gridApi ) {
	      $scope.grid1Api = gridApi;
	    }
	  };
	
}]);