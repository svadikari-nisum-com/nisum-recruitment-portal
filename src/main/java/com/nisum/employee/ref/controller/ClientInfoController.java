package com.nisum.employee.ref.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nisum.employee.ref.domain.ResponseVO;
import com.nisum.employee.ref.service.IClientInfoService;
import com.nisum.employee.ref.util.Constants;
import com.nisum.employee.ref.view.ClientInfoDTO;

@Controller
public class ClientInfoController {

	@Autowired(required = false)
	private IClientInfoService clientInfoService;

	@RequestMapping(value = "/clientNames", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<String>> getClients() {
		List<String> clients = clientInfoService.getClientNames();
		return new ResponseEntity<List<String>>(clients, HttpStatus.OK);
	}

	@RequestMapping(value = "/clientInfo", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getClientInfo(@RequestParam(value = "clientName", required = false) String clientName) {
		List<ClientInfoDTO> clients = null;
		if (clientName != null && !clientName.isEmpty()) {
			clients = clientInfoService.getClientDetailsByClient(clientName);
		} else {
			clients = clientInfoService.getClientDetails();
		}
		return !clients.isEmpty() ? new ResponseEntity<List<ClientInfoDTO>>(clients, HttpStatus.OK)
				: new ResponseEntity<String>(Constants.clientNotFound, HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/interviewerNames", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getInterviewerNames() {
		List<String> interviewerNames = clientInfoService.getInterviewerNames();
		return new ResponseEntity<List<String>>(interviewerNames, HttpStatus.OK);
	}

	@RequestMapping(value = "/getClientById", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getClientById(@RequestParam(value = "clientId", required = true) String clientId) {
		
		List<ClientInfoDTO> client = clientInfoService.getClientById(clientId);
		return new ResponseEntity<List<ClientInfoDTO>>(client, HttpStatus.OK);
		
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/clientInfo", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<?> deleteClient(@RequestParam(value = "clientId", required = true) String clientId) {
		
		clientInfoService.deleteClient(clientId);
		ResponseVO<String> response = new ResponseVO<String>();
		response.setMessage(clientId + " Id Client has been removed successfully.");
		return new ResponseEntity<ResponseVO<String>>(response, HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/clientInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ClientInfoDTO> createClient(@RequestBody ClientInfoDTO clientInfoDTO) {
		clientInfoService.createClient(clientInfoDTO);
		return new ResponseEntity<ClientInfoDTO>(clientInfoDTO,clientInfoDTO.hasErrors()?HttpStatus.BAD_REQUEST: HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/clientInfo", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<String> updateClient(@RequestBody ClientInfoDTO clientInfoDTO) {
		clientInfoService.updateClient(clientInfoDTO);
		return new ResponseEntity<String>("{\"msg\":\"Client Updated Successfully\"}", HttpStatus.ACCEPTED);
	}
}
