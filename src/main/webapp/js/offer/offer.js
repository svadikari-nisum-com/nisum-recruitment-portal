app.controller('offerManagementCtrl',['$scope', '$http','$q', '$state', '$timeout','$filter','$log','appConstants','infoService','offerService',
                                      function($scope, $http, $q, $state, $timeout,$filter,$log,appConstants,infoService, offerService) {
	$scope.useremailId = sessionStorage.userId;
	$scope.col=["Name","Email Id","Client","Status"];
	
	$scope.att=["candidateName","emailId","client","status"];
	$http.get('resources/user?emailId='+$scope.useremailId).success(function(data, status, headers, config) {
	
		$scope.userRoles = data[0].roles;
		$scope.userEmailId=data[0].emailId;
	
	if(_.contains($scope.userRoles, "ROLE_HR") || _.contains($scope.userRoles, "ROLE_RECRUITER") || _.contains($scope.userRoles, "ROLE_ADMIN")){
	$http.get('resources/offers').success(function(data, status, headers, config) {
		$scope.myData = data;
	}).error(function(data, status, headers, config) {
		$log.error("Failed To Get Profiles! ---> "+data);
	});
	}else if(_.contains($scope.userRoles, "ROLE_MANAGER")){
	
	
		$http.get('resources/offers?managerEmail='+$scope.userEmailId).success(function(data, status, headers, config) {
			$scope.myData = data;
		}).error(function(data, status, headers, config) {
			$log.error("Failed To Get Profiles! ---> "+data);
		});
		
		
	}
	
	
	});
	
	
	$scope.selectCandidate = function(offer) {
		offerService.setData(offer);
		$state.go("offer.createOffer");
	}

	$scope.title = "Offer";

}]);