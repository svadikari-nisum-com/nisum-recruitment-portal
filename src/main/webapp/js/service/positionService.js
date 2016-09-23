angular.module('erApp')
		   .service('positionService',['$http','$filter','$rootScope','appConstants','$q', '$timeout',	positionService]);

function positionService($http,$filter,$rootScope,$timeout,$log,appConstants) {
	return {
		createPosition : addPosition,
		updatePosition : updatePosition,
		getPosition: getPosition,
		getPositionByDesignation : getPositionByDesignation,
		getPositionByJobcode : getPositionByJobcode,
		getPositionBylocation : getPositionBylocation,
		getClients : getClients,
		updatePositionStatus:updatePositionStatus
	};
	
	function addPosition(positionObj){
		return $http.post('resources/positions', positionObj)
		.then(createPositionSuccess)
		.catch(createPositionError);
	}
	
	function createPositionSuccess(response){
		return response.data.jobcode + " Position Created Successfully!";
	}
	
	function createPositionError(response){
		return "Failed To Create Position! Response Status: " + response.status;
	}
	
	function updatePosition(positionObj){
		return $http.put('resources/positions', positionObj)
		.then(updatePositionSuccess)
		.catch(updatePositionError);
	}
	function updatePositionStatus(positionObj){
		return $http.put('resources/positions/updatePositionStatus?jobCode='+positionObj.jobcode+'&status='+positionObj.status)
		.then(updatePositionSuccess)
		.catch(updatePositionError);
	}
	function updatePositionSuccess(response){
		return " Position updated Successfully!";
	}
	
	function updatePositionError(response){
		return "Failed To update Position! Response Status: " + response.status;
	}
	
	function getPosition(isManager){
		
		var positionURL = "resources/positions";
		if(typeof isManager !== 'undefined')
		{
			positionURL = "resources/positions?searchKey=hiringManager&searchValue="+isManager;
		}
		
		return $http.get(positionURL)
		.then(getPositionSuccess)
		.catch(getPositionError);
	}
	function getPositionByDesignation(designation){
		return $http.get('resources/positions?searchKey=designation&searchValue='+designation)
		.then(getPositionSuccess)
		.catch(getPositionError);
	}
	
	function getPositionByJobcode(jobcode){
		return $http.get('resources/positions?searchKey=jobcode&searchValue='+jobcode)
		.then(function(response) {
			return data = response.data;
		})
		.catch(getPositionError);
	}
	function getPositionBylocation(location){
		return $http.get('resources/positions?searchKey=location&searchValue='+location)
		.then(getPositionSuccess)
		.catch(getPositionError);
	}
	
	function getPositionSuccess(response){
		return response.data;
	}
	function getPositionError(response){
		return "Failed To Get Position! Response";
	}
	function getClients(){
		return $http.get('resources/client')
		.then(getClientsSuccess)
		.catch(getClientError);
	}
	
	function getClientsSuccess(response){
		return response.data;
	}
	
	function getClientError(response){
		return "Failed To Get Clients! Response";
	}
}