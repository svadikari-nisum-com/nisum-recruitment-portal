angular.module('erApp')
.service('dashboardService',['$http','$filter','$rootScope','appConstants','$q', '$timeout', dashboardService]);

function dashboardService($http,$filter,$rootScope,$timeout,appConstants,$q) {
	return {
		getPositionData : getPositionData,
		getScheduleData : getScheduleData,
		getScheduleDataInterviewer : getScheduleDataInterviewer
	};
	
	function getPositionData(obj){
		return $http.get('resources/positions/positionsByAggregation')
		.then(getPositionDataSuccess)
		.catch(getPositionDataError);
	}
	
	function getScheduleDataInterviewer(emailId){
		return $http.get('resources/interviews?interviewerEmail='+emailId)
		.then(getScheduleDataSuccess)
		.catch(getScheduleDataError);
	}
	
	function getScheduleData(emailId){
		return $http.get('resources/interviews')
		.then(getScheduleDataSuccess)
		.catch(getScheduleDataError);
	}
	
	function getPositionDataSuccess(response){
		return response.data;
	}
	
	function getPositionDataError(response){
		return "Failed To Get Positions!";
	}
	
	function getScheduleDataSuccess(response){
		data = response.data;
		var showScheduleData = [];
		var today = new Date();
		var tomorrow = new Date(today);
		tomorrow.setDate(today.getDate()+4);
		angular.forEach(data, function(obj){
			angular.forEach(obj.rounds, function(obj2){
				var dbDate = new Date(obj2.interviewSchedule.interviewDateTime);
				if(obj2.interviewSchedule != null && obj2.interviewFeedback == null ){
				showScheduleData.push({"cname":obj.candidateName,"currentPositionId":obj.currentPositionId,"email":obj.candidateEmail, "round":obj2.interviewSchedule.roundName, "date":dbDate, "interviewId":obj.interviewerId,"interviewerEmail":obj.interviewerEmail});
				}
			})
		});
		return showScheduleData;
	}
	
	function getScheduleDataError(response){
		return q.reject("Failed To Get Interview Details!");
	}
}