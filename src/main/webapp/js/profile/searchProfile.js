app.controller('searchProfileCtrl',['$scope', '$http','$q','jobCodeService1','$rootScope','userService', '$filter', '$log','appConstants', 'uiGridConstants',
                                    function($scope, $http, $q, jobCodeService1,$rootScope,userService, $filter,$log,appConstants, uiGridConstants) {
	
	$scope.errorHide = true;
	$scope.numRows = 10;
	$scope.interviewTemplate = '<a href="" id="" class="interview-schedule"  ng-click="grid.appScope.schedule(row.entity.jobcodeProfile,row.entity.emailId)" >'
        +'<span class="glyphicon glyphicon-plus" ng-show='+$scope.hasRole("ROLE_RECRUITER") + ' ng-hide='+$scope.hasRole("ROLE_HR,ROLE_MANAGER")+' style="position: relative; top: 1px;"  />'
        +'<span class="glyphicon glyphicon-envelope" ng-show='+$scope.hasRole("ROLE_HR,ROLE_MANAGER")+' ng-hide='+$scope.hasRole("ROLE_RECRUITER,ROLE_ADMIN")+' style="position: relative; top: 1px;"  />';

	$scope.col=["Name","Email Id","Role","Experience","Recrutier","Interview Status"];
	$scope.recruitmentData = {};
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
		userService.getUserByRole("ROLE_RECRUITER").then(function (userdata){
		    angular.forEach(userdata,function(user) {
			      
		    	$scope.recruitmentData[user.emailId] = user.name;
		    });
		    angular.forEach(data,function(profileRec)
    		{
    			profileRec.hrAssigned = $scope.recruitmentData[profileRec.hrAssigned] ? $scope.recruitmentData[profileRec.hrAssigned] : profileRec.hrAssigned;
    		});
		    		
    		
		    
		    
		}).catch(function(message) {
			$log.error(message)
		});
		
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
			rowTemplate: '<div <div ng-repeat="col in colContainer.renderedColumns track by col.colDef.name"  class="ui-grid-cell" ui-grid-cell><md-tooltip>{{row.entity.jobcodeProfile[0]}}</md-tooltip></div></div>',
		    columnDefs: [
		      { field: 'candidateName', displayName:"Name",width: 100, cellClass: 'ui-grid-align', cellTemplate: '<a style="padding-left: 5px;" ng-click="grid.appScope.editProfile(row.entity); $event.stopPropagation();" ui-sref="recruitment.viewProfile">{{row.entity.candidateName}} </a>'},
		      { field: 'emailId', displayName:"Email Id",width: 150, cellClass: 'ui-grid-align'},
		      { field: 'designation', displayName:"Role", width: 100, cellClass: 'ui-grid-align'},
		      { field: 'expYear', displayName:"Experience", width: 50, cellClass: 'ui-grid-align'},
		      { field: 'hrAssigned', displayName:"Recrutier", width: 150, cellClass: 'ui-grid-align'},
		      { field: 'interview', displayName:"Interview", width: 100, cellClass: 'ui-grid-align',cellTemplate: $scope.interviewTemplate },
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
        
		 $scope.schedule = function(positionId, candidateEmail) {
				jobCodeService1.setprofileUserId(candidateEmail);
				jobCodeService1.setjobCode(positionId);
				jobCodeService1.setPreviousPage("recruitment.searchProfile");
				for (i = 0; i< $rootScope.user.roles.length;i++) {
			    	if ($rootScope.user.roles[i] == 'ROLE_RECRUITER'){
			    		location.href='#recruitment/scheduleInterview';
			    	} else if ($rootScope.user.roles[i] == 'ROLE_ADMIN') {
			    		location.href='#recruitment/interviewManagement';
			    	} else  {
			    		location.href='#recruitment/interviewFeedback';
			    	}
			    }
				
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
