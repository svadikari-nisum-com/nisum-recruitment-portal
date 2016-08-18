 app.controller('searchReportCntrl',['$scope', '$http', '$window','jobCodeService1','userService', '$timeout','$filter','$q', '$log', '$rootScope','blockUI','positionService','profileService','interviewService','reportService','uiGridConstants', function($scope, $http,$window, jobCodeService1,userService, $timeout,$filter, $q, $log, $rootScope,blockUI,positionService,profileService,interviewService,reportService,uiGridConstants) {
	$scope.data = {};
	$scope.positions = {};
	$scope.candidate = {};
	$scope.report={};
	 $scope.profileData = [];
	$scope.calendar = false;
	$scope.hideCal = true;
	$scope.noOfPositions = "";
	
	$scope.hr=[];
	$scope.reportingMngr=[];
	$scope.recruiters=[];
	$scope.repManagers = [];
	$scope.skills=$rootScope.info.skills;
	$scope.functionalGroups = ["DEV","QA","NOC","SUPPORT"];
	$scope.positionStatus=["Draft","Approved","Open","Closed"];
	var position_URL = 'resources/position';
	$scope.getUsers = function(role){
		
	
		userService.getUserByRole(role).then(function (data){
			if(angular.isUndefined($scope.repManagers)){
			$scope.repManagers = [];
			$scope.reportingMngr=[];
			}
			if(angular.isUndefined($scope.hr)){
			$scope.hr=[];
			}
			if(angular.isUndefined($scope.recruiters)){
				$scope.recruiters=[];
				}
			angular.forEach(data,function(user){
				
			if(role=="ROLE_MANAGER"){
				$scope.repManagers.push(user.name);
				$scope.reportingMngr.push(user.name);			
				
			}else if(role=="ROLE_HR"){		
				$scope.hr.push(user.name);
				
			}
			else if(role=="ROLE_RECRUITER"){
				$scope.recruiters.push(user.name);
			}
			})
			
		});
	
	}
	$scope.init=function(){
		
		
		$scope.repManagers = $scope.getUsers("ROLE_MANAGER");
	
		$scope.hr = $scope.getUsers("ROLE_HR");
		$scope.recruiters = $scope.getUsers("ROLE_RECRUITER");
		
		
	}
	$scope.searchReport=function(){
		alert($scope.report.repManager)
			var InterviewDetailsURL = 'resources/getReportByManager?repManager='+$scope.report.repManager;
			$http.get(InterviewDetailsURL).success(function(data, status, headers, config) {
				$scope.report = data;
			}).error(function(data, status, headers, config) {
				
				$log.error(data);
			})
			
	};
	$scope.init();
	$scope.gridOptions = {
		    enableSorting: true,
		    enableColumnMenus: false,
			enablePaginationControls: false,
			enableHorizontalScrollbar : uiGridConstants.scrollbars.NEVER,
	        enableVerticalScrollbar   : uiGridConstants.scrollbars.NEVER,
			paginationCurrentPage: 1,
		    columnDefs: [
		      { field: 'functionalGroup', displayName:"Functional Group", cellClass: 'ui-grid-align', cellTooltip:'jhjsdgjsd'},
		      { field: 'positionsTechnical1', displayName:"# Positions in Technical Round1", cellClass: 'ui-grid-align'},	
		      { field: 'positionsTechnical2', displayName:"# Positions in Technical Round2", cellClass: 'ui-grid-align'},
		      { field: 'positionsManager', displayName:"# Positions in Manager Round", cellClass: 'ui-grid-align'},
		      { field: 'positionsHR', displayName:"# Positions in HR Round", cellClass: 'ui-grid-align'},
		      { field: 'offered', displayName:"Offered", width: 100, cellClass: 'ui-grid-align'},
		      { field: 'closed', displayName:"Closed", width: 100, cellClass: 'ui-grid-align'},
		      { field: 'totPositions', displayName:"No of Positions", cellClass: 'ui-grid-align'},
		   
		     
		     
		    ],
		    onRegisterApi: function( gridApi ) {
		    	$scope.gridApi = gridApi;
	 	        $scope.gridApi.grid.registerRowsProcessor($scope.singleFilter, 200);
		    }
		  };
   
}]);