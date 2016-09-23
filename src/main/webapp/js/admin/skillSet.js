app.run(['$anchorScroll', function($anchorScroll) {
    $anchorScroll.yOffset = 50; // always scroll by 50 extra pixels
}])
app.controller('skillSet', ['$scope', '$http', '$q', '$timeout', '$filter','$mdDialog', 'appConstants','sharedDataService', 'infoService', '$location', '$anchorScroll', 'convertArray2Json', 'uiGridConstants',
    function($scope, $http, $q, $timeout, $filter, $mdDialog, appConstants, sharedDataService, infoService, $location, $anchorScroll, convertArray2Json, uiGridConstants) {
	
	$scope.numRows = 10;
	
        $scope.status = {
            isFirstOpen: true,
            isFirstDisabled: false
        };

        $scope.skills = [];
        $scope.dis2 = true;
        $scope.dis = false;
        $scope.skills1 = {};
        $scope.newSkill = "";
        $scope.message = "";
        $scope.hideError = true;
        
        infoService.getInfoById('skills').then(getSkills).catch(getUserError);

        function getSkills(skills) {
            $scope.skills1 = skills;
            $scope.skills = skills.value;
            
            $scope.gridOptions.data = convertArray2Json.convertArrayOfStringsToGridFriendlyJSON("skills", skills.value);
    		$scope.gridOptions.totalItems = skills.length;
    		$scope.gridOptions.paginationPageSize = $scope.numRows;
    		$scope.gridOptions.minRowsToShow = skills.length < $scope.numRows ? skills.length : $scope.numRows;
            
        }
        
        function getUserError(msg){
    		$log.error("Failed!! ---> "+msg);
    	}
        
        $scope.save = function() {
            if ($scope.newSkill == "" || $scope.newSkill == null || $scope.newSkill == undefined) {
                $scope.hideError = false;
            } else {
                var ck = $scope.checkSkillSet();
                if (ck) {
                    $scope.skills1.value.push($scope.newSkill);

                    infoService.updateInformation($scope.skills1).then(function(msg) {
                        sendSharedMessage(msg, appConstants.SUCCESS_CLASS);
                        infoService.getInfoById('skills').then(getSkills).catch(getUserError);
                        $timeout(function() { $scope.alHide(); }, 5000);
                        $scope.newSkill = "";
                    }).catch(function(msg) {
                        sendSharedMessage(msg, appConstants.ERROR_CLASS);
                        $timeout(function() { $scope.alHide(); }, 5000);
                    })
                }
                $scope.dis = false;
                $scope.dis2 = true;

            }
        }

        $scope.checkSkillSet = function() {
            var flag = true;
            angular.forEach($scope.skills, function(sk) {
                if ($scope.areEquals($scope.newSkill, sk)) {
                    $scope.message = "Skill Already Exists";
                    $scope.cls = appConstants.ERROR_CLASS;
                    $timeout(function() { $scope.alHide(); }, 5000);
                    $scope.newSkill = "";
                    flag = false;
                }
            });
            return flag;
        }

        $scope.deleteSkill = function(index, skill) {
           
            sharedDataService.showConformPopUp("Are you sure you want to delete?","Delete Skill",$mdDialog).then(function(){
            	$scope.skills1.value.splice(index, 1);

                infoService.removeInformation($scope.skills1).then(function(msg) {
                    sendSharedMessage(msg, appConstants.SUCCESS_CLASS);
                    infoService.getInfoById('skills').then(getSkills).catch(getUserError);
                    $timeout(function() { $scope.alHide(); }, 5000);
                    
                }).catch(function(msg) {
                    sendSharedMessage(msg, appConstants.ERROR_CLASS);
                    $timeout(function() { $scope.alHide(); }, 5000);
                })

                $scope.dis = false;
                $scope.dis2 = true;
            });
        }

        $scope.Skills1 = function() {
            $scope.dis = true;
            $scope.dis2 = false;
        }

        $scope.alHide = function() {
            $scope.message = "";
            $scope.cls = '';
        }

        function sendSharedMessage(msg, msgCls) {
            $scope.showMsg = true;
            $scope.message = msg;
            $scope.cls = msgCls;
            $scope.gotoAnchor();
        }

        $scope.gotoAnchor = function() {
            var newHash = 'top';
            if ($location.hash() !== newHash) {
                $location.hash('top');
            } else {
                $anchorScroll();
            }
        };



        //--------------

        $scope.itemsPerPage = appConstants.ITEMS_PER_PAGE;
        $scope.currentPage = 0;

        $scope.changePage = function() {
            $scope.currentPage = 0;
        }

        $scope.range = function(start) {
            var pageCnt = $scope.pageCount();
            var ret = [];

            if (start + 1 == pageCnt && pageCnt == 1) {
                ret.push(0);
                return ret;
            }
            if ((start + 2 >= pageCnt)) {
                while (start + 2 >= pageCnt)
                    start--;
            }
            if (start < 0)
                start = 0;
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
			if (!$scope.skills1) { return; }
            return Math.ceil($scope.skills.length / $scope.itemsPerPage);
        };

        $scope.nextPage = function() {
            if ($scope.currentPage > $scope.pageCount()) {
                $scope.currentPage++;
            }
        };

        $scope.setPage = function() {
            $scope.currentPage = this.n;
        };
        $scope.cancel = function() {
            $scope.dis = false;
            $scope.dis2 = true;
        }

        $scope.gridOptions = {
            enableSorting: true,
            enableColumnMenus: false,
            enablePaginationControls: false,
            paginationCurrentPage: 1,
            enableHorizontalScrollbar : uiGridConstants.scrollbars.NEVER,
            enableVerticalScrollbar   : uiGridConstants.scrollbars.NEVER,
            columnDefs: [
                { field: 'skills', cellClass: 'ui-grid-align'},
                { field: 'delete', width:150, enableSorting: false, cellTemplate: '<a class="glyphicon glyphicon-remove" ng-click="grid.appScope.deleteSkill(row.entity.index,skill)"></a>' }
            ],
            onRegisterApi: function(gridApi) {
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
    	        ['skills'].forEach(function(field) {
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
