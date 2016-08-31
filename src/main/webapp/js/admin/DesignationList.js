app.run(['$anchorScroll', function($anchorScroll) {
    $anchorScroll.yOffset = 50;   // always scroll by 50 extra pixels
}])
app.controller('DesignationListCtrl',['$scope','$rootScope', '$http','$q', '$timeout','$filter','$log','appConstants','infoService','$location','$mdDialog', '$anchorScroll','designationService','jobCodeService1','sharedDataService','$state', 'uiGridConstants',
                                      function($scope,$rootScope, $http, $q, $timeout,$filter,$log,appConstants,infoService,$location,$mdDialog,$anchorScroll,designationService,jobCodeService1,sharedDataService, $state, uiGridConstants ) {
	
	$scope.designation1 = {};
	$scope.hideSkills = true;
	$scope.dis2 = false;
	$scope.designation={};
	$scope.hideError = true;
	$scope.pskills=$rootScope.info.skills;
	$scope.expYear=$rootScope.info.expYears;
	$scope.numRows = 10;
	
	$scope.cls = sharedDataService.getClass();
	$scope.message = sharedDataService.getmessage();
	
	$scope.newDesig="";
	
	$scope.col=["Designations","Skills","Min Exp","Max Exp","Delete"];
	
	$scope.att=["designation","skills","minExpYear","maxExpYear"];
	$scope.att1=["skills"];
	
	$scope.init = function() {
		designationService.getDesignation().then(function(data){
			$scope.designation1=data;
			$scope.gridOptions.data = data;
			$scope.gridOptions.totalItems = data.length;
			$scope.gridOptions.paginationPageSize = $scope.numRows;
			$scope.gridOptions.minRowsToShow = data.length < $scope.numRows ? data.length : $scope.numRows;
			$timeout( function(){ $scope.message = ""; $scope.cls = ''; sharedDataService.setmessage("");sharedDataService.getClass("");}, 5000);
		}).catch(function(msg){
			$scope.message=msg;
			 $scope.cls=appConstants.ERROR_CLASS;
			 $scope.gotoAnchor();
			 $timeout( function(){ $scope.alHide(); }, 5000);
		});
	}
	$scope.init();
	
	$scope.editDesign = function(data) {
		jobCodeService1.setDesignation(data.designation);
		location.href='#admin/designation/edit';
	};

	$scope.save = function(){
		if($scope.checkDesignation()){
		  designationService.addDesignation($scope.designation).then(function(msg){
			  $scope.sendSharedMessage(msg,'/admin/designation');
		  }).catch(function(msg){ 
			  $scope.message=msg;
		      $scope.cls=appConstants.ERROR_CLASS; $scope.gotoAnchor(); 
		  })
		}
	}
	
	
	$scope.checkDesignation = function(){
		$scope.checkDes = true;
		angular.forEach($scope.designation1, function(des){
			if($scope.designation.designation.toUpperCase() == des.designation.toUpperCase()){
				 $scope.message="Designation is  Already Exists";
				 $scope.cls=appConstants.ERROR_CLASS;
				 $scope.checkDes = false;
			}});
		return  $scope.checkDes;
	}		
	
	$scope.skills = function(){
		$scope.hideSkills = false;
		$scope.dis2 = true;
	}
	$scope.skills1 = function(){
		$scope.hideSkills = true;
		$scope.dis2 = false;
	}
	$scope.alHide =  function(){
	    $scope.message = "";
	    $scope.cls = '';
	}
	$scope.validate =  function(){
		$scope.alHide();		
	    if(parseInt($scope.designation.maxExpYear)<parseInt($scope.designation.minExpYear)){
	    	$scope.message="maxExpYear should be gretter than minExpYear";
		    $scope.cls=appConstants.ERROR_CLASS;
		    $scope.designation.maxExpYear="";
		    $timeout( function(){ $scope.alHide(); }, 5000);
	    }
	}
	$scope.myFunct = function(keyEvent) {
        if (keyEvent.which === 13)
                keyEvent.preventDefault();
	}
	$scope.gotoAnchor = function() {
	       var newHash = 'top';
	       if ($location.hash() !== newHash) {
	         $location.hash('top');
	       } else {
	         $anchorScroll();
	       }
	};
	
	$scope.gridOptions = {
	    enableSorting: true,
	    enableColumnMenus: false,
		enablePaginationControls: false,
		paginationCurrentPage: 1,
		enableHorizontalScrollbar : uiGridConstants.scrollbars.NEVER,
        enableVerticalScrollbar   : uiGridConstants.scrollbars.NEVER,
	    columnDefs: [
	      { field: 'designation', displayName:"Designations", cellClass: 'ui-grid-align', 
	    	  cellTemplate: '<a style="padding-left: 5px;" ng-click="grid.appScope.editDesign(row.entity); $event.stopPropagation();" ui-sref="admin.designation.edit">{{row.entity.designation}} </a>'},
	      { field: 'skills', displayName:"Skills", cellClass: 'ui-grid-align', cellFilter: 'stringArrayFilter'},
	      { field: 'minExpYear', displayName:"Min Exp", cellClass: 'ui-grid-align'},
	      { field: 'maxExpYear', displayName:"Max Exp", cellClass: 'ui-grid-align'},
	      { field: 'delete', enableSorting: false, cellTemplate: '<a class="glyphicon glyphicon-remove" ng-click="grid.appScope.deleteDesignation(row.entity)"></a>' }
	    ],
	    onRegisterApi: function( gridApi ) {
	      $scope.gridApi = gridApi;
	      $scope.gridApi.grid.registerRowsProcessor($scope.singleFilter, 200);
	    }
	  };
		$scope.deleteDesignation = function(rowEntity) {
			
		sharedDataService.showConformPopUp("Are you sure you want to delete?","Delete Designation",$mdDialog).then(function(){	
			$scope.designation1.splice($scope.designation1.indexOf(rowEntity), 1);
        	
        	designationService.removeDesignation(rowEntity.designation).then(function(msg){
	        	$scope.message=rowEntity.designation+ " " + msg;
	        	$scope.cls = appConstants.SUCCESS_CLASS;
	        	$timeout(function() { $scope.alHide();},3000);
    		}).catch(function(deleteMessage){
    			sendSharedMessage(msg, appConstants.ERROR_CLASS);
                $timeout(function() { $scope.alHide(); }, 5000);
    		});   
        	
            $scope.dis = false;
            $scope.dis2 = true;
		});
       
    }
	
	
	
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
	        ['designation'].forEach(function(field) {
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