app.controller("adminCtrl", ['$scope', '$http', '$filter', '$timeout','$q','$state', '$location', function($scope, $http, $filter, $timeout, $q, $state, $location) {

	$scope.showErrorMsg=false;
    $scope.showSuccessMsg= false;
    $scope.message = "";
    $scope.oneAtATime = true;
    
    if($state.is("admin"))
    	$state.go("admin.users.list");

    $scope.tabs = [
                   { heading: "Users", route:"admin.users.list", roles: "ROLE_ADMIN"},
                   { heading: "Client", route:"admin.client.list", roles: "ROLE_ADMIN"},
                   { heading: "Designation", route:"admin.designation.list", roles: "ROLE_ADMIN"},
                   { heading: "Skill-Set", route:"admin.skillSet", roles: "ROLE_ADMIN,ROLE_HR,ROLE_RECRUITER"},
                   { heading: "Interview Rounds", route:"admin.interviewRound.list", roles: "ROLE_ADMIN,ROLE_HR,ROLE_RECRUITER"}
               ];
 
	$scope.filterOptions = {
        filterText: "",
        useExternalFilter: true
    };
	
	$scope.status = {
		isFirstOpen: true,			    
		open:true
	};
	
	$scope.isActive = function (url) {
		return $state.includes(url.replace(".list",""));
    };
	
}]);