package com.nisum.employee.ref.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import com.nisum.employee.ref.view.ClientInfoDTO;

@RequestMapping("/clients")
@Controller
public class ClientInfoController {

	@Autowired(required = false)
	private IClientInfoService clientInfoService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<ClientInfoDTO>> getClientInfo(@RequestParam(value = "clientId", required = false) String clientId) {
		List<ClientInfoDTO> clients = null;
		if (StringUtils.isNotEmpty(clientId)) {
			clients = clientInfoService.getClientById(clientId);
		} else {
			clients = clientInfoService.getClientDetails();
		}
		return new ResponseEntity<List<ClientInfoDTO>>(clients, HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<ResponseVO<String>> deleteClient(@RequestParam(value = "clientId", required = true) String clientId) {
		
		clientInfoService.deleteClient(clientId);
		ResponseVO<String> response = new ResponseVO<String>();
		response.setMessage(clientId + " Id Client has been removed successfully.");
		return new ResponseEntity<ResponseVO<String>>(response, HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ClientInfoDTO> createClient(@RequestBody ClientInfoDTO clientInfoDTO) {
		clientInfoService.createClient(clientInfoDTO);
		return new ResponseEntity<ClientInfoDTO>(clientInfoDTO,clientInfoDTO.hasErrors()?HttpStatus.BAD_REQUEST: HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<String> updateClient(@RequestBody ClientInfoDTO clientInfoDTO) {
		clientInfoService.updateClient(clientInfoDTO);
		return new ResponseEntity<String>("{\"msg\":\"Client Updated Successfully\"}", HttpStatus.ACCEPTED);
	}
}
