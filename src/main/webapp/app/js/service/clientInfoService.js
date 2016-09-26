angular.module('erApp')
		   .service('clientService',['$http','$q','$timeout','$rootScope','appConstants',	clientInfoService]);	

function clientInfoService($http,$q,$timeout,appConstants){
		return{
			getClientInfo : getClientInfo,
			getClientName : getClientName,
			getClientByName : getClientByName,
			createClient : addClient,
			updateClient : updateClient,
			removeClient : deleteClient
		};
	
	
	function getClientInfo(){
		return $http.get('resources/clients')
		 .then(getData)
		 .catch(sendGetClientError);	
	}
	function getClientName(){
		return $http.get('resources/clients')
		 .then(getData)
		 .catch(sendGetClientError);	
	}
	function getClientByName(clientNames){
		return $http.get('resources/clients?clientId='+clientNames)
		 .then(getData)
		 .catch(sendGetClientError);	
	}
	function addClient(clientInfo){
		return $http.post('resources/clients', clientInfo)
					.then(clientAddedSuccess)
					.catch(sendCreateClientError);
	}
	
	function updateClient(clientInfo){
		return $http.put('resources/clients', clientInfo)
					.then(clientUpdatedSuccess)
					.catch(sendGetClientError);
	}
	
	function deleteClient(clientId){
		return $http.delete('resources/clients?clientId='+clientId)
					.then(clientDeletedSuccess)
					.catch("sdsdfsfsdfsd")
	}
	
	function getData(response){
		return response.data;
	}
	
	function clientUpdatedSuccess(response){
		return  response.config.data.clientName +" Client has been updated successfully";
	}
	
	function clientAddedSuccess(response){
		return  response.config.data.clientName +" Client added successfully";
	}
	
	function clientDeletedSuccess(response){
		return  "Client has been deleted successfully";
	}
	
	function sendGetClientError(response){
		 return $q.reject('Error retrieving book(s). (HTTP status: ' + response.status + ')');
	}
	
	function sendCreateClientError(response){
		// return $q.reject('Error retrieving book(s). (HTTP status: ' + response.status + ')');
		var showContent = response.data.errors[0].desc;
		return $q.reject(showContent);
	}
	
	};
