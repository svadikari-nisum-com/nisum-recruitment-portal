angular.module('erApp')
		   .service('designationService',['$http','$filter','$rootScope','$log','$q','$cacheFactory','appConstants',designationService]);

function designationService($http,$filter,$rootScope,$log,$q,$cacheFactory,appConstants) {
	return {
		getDesignation : getDesignation,
		addDesignation : addDesignation,
		updateDesignation : updateDesignation,
		removeDesignation : deleteDesignation
	};

	function getDesignation(){
		return $http.get('resources/designations')
			 .then(getDesignationData)
			 .catch(sendErrorDesignationMsg);
	}
	function addDesignation(designation){
		return $http.post('resources/designations', designation)
		.then(createDesignationSuccess)
		.catch(sendErrorCreate);
	}
	function updateDesignation(designation){
		return $http.put('resources/designations', designation)
		.then(updateDesignationSuccess)
		.catch(sendErrorupdate);
	}
	function deleteDesignation(designation){
		return $http.delete('resources/designations/'+designation)
		.then(deleteDesignationSuccess)
		.catch(sendErrorDesignationMsg);
	}
	
	function updateDesignationSuccess(response){
		return  response.config.data.designation +"Designation Updated successfully";
	}
	
	function createDesignationSuccess(response){
		return  response.config.data.designation +" Designation Created successfully";
	}
	function deleteDesignationSuccess(response){
		return  "Designation Deleted successfully";
	}
	function sendErrorupdate(response){
		 return $q.reject('Error in Updating Designation');
	}
	function sendErrorCreate(response){
		 return $q.reject('Error in Creating Designation');
	}
	function sendErrorDesignationMsg(response){
		 return $q.reject('Error retrieving Designation' + response.status + ')');
	}
	function getDesignationData(response){
		return response.data;
	}
}
