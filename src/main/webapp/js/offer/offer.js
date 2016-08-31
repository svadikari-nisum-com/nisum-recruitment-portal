app.controller('offerManagementCtrl',['$scope', '$http','$q', '$state', '$timeout','$filter','$log','appConstants','infoService','offerService',
                                      function($scope, $http, $q, $state, $timeout,$filter,$log,appConstants,infoService, offerService) {
	
	$scope.col=["Name","Email Id","Client","Status"];
	
	$scope.att=["candidateName","emailId","client","status"];
	
	$http.get('resources/offers').success(function(data, status, headers, config) {
		$scope.myData = data;
	}).error(function(data, status, headers, config) {
		$log.error("Failed To Get Profiles! ---> "+data);
	});
	
	$scope.selectCandidate = function(offer) {
		offerService.setData(offer);
		$state.go("offer.createOffer");
	}

	$scope.title = "Offer";

}]);