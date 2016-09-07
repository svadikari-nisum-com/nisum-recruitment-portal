 app.controller('searchReportCntrl',['$scope', '$http','jobCodeService1','userService', '$timeout','$filter','$q', '$log', '$rootScope','blockUI','positionService','profileService','interviewService','reportService','uiGridConstants','appConstants', function($scope, $http, jobCodeService1,userService, $timeout,$filter, $q, $log, $rootScope,blockUI,positionService,profileService,interviewService,reportService,uiGridConstants,appConstants) {
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
	$scope.numRows = 10;
	$scope.functionalGroups = ["DEV","QA","NOC","SUPPORT"];
	$scope.positionStatus=["Draft","Approved","Open","Closed"];
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
	$scope.getUsers = function(){
	
	
		userService.getUsers().then(function (data){
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
		for(var i=0;i<user.roles.length;i++){
			if(user.roles[i]=="ROLE_MANAGER"){
				$scope.repManagers.push(user.name);
				$scope.reportingMngr.push({"emailId":user.emailId,"name":user.name});			
				
			}else if(user.roles[i]=="ROLE_HR"){		
				$scope.hr.push(user.name);
				
			}
			else if(user.roles[i]=="ROLE_RECRUITER"){
				$scope.recruiters.push(user.name);
			}
		}
			})
			
		});
	
	}
	$scope.init=function(){
		
		$scope.getUsers();
//		$scope.repManagers = $scope.getUsers("ROLE_MANAGER");
//	
//		$scope.hr = $scope.getUsers("ROLE_HR");
//		$scope.recruiters = $scope.getUsers("ROLE_RECRUITER");
		
		
	}
	$scope.searchReport=function(){
	
//			var reportsURL = 'resources/reports/hiringManager?hiringManager=hhh';
		var reportsURL='resources/reports?hiringManager='+$scope.report.repManager;
			$http.get(reportsURL).success(function(data, status, headers, config) {
		
				$scope.gridOptions.data = data;
			
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
		      { field: 'functionalGrp', displayName:"Functional Group", cellClass: 'ui-grid-align'},
		      { field: 'profilesInTechnicalRound1', displayName:"# Positions in Technical Round1", cellClass: 'ui-grid-align'},	
		      { field: 'profilesInTechnicalRound2', displayName:"# Positions in Technical Round2", cellClass: 'ui-grid-align'},
		      { field: 'profilesInManagerRound', displayName:"# Positions in Manager Round", cellClass: 'ui-grid-align'},
		      { field: 'profilesInHRRound', displayName:"# Positions in HR Round", cellClass: 'ui-grid-align'},
		      { field: 'offered', displayName:"Offered",  cellClass: 'ui-grid-align'},
		      { field: 'closed', displayName:"Closed",  cellClass: 'ui-grid-align'},
		      { field: 'noOfOpenPositions', displayName:"No of Positions", cellClass: 'ui-grid-align'},
		   
		     
		     
		    ],

		  };
   
}]);