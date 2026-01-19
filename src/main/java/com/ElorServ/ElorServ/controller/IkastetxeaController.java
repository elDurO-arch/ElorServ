package com.ElorServ.ElorServ.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ElorServ.ElorServ.model.IkastetxeaDTO;
import com.ElorServ.ElorServ.service.IkastetxeaService;

@RestController
@RequestMapping("/api/ikastetxeak")
public class IkastetxeaController {

	@Autowired
	private IkastetxeaService ikastetxeaService;
	
	// GET http://localhost:8080/api/ikastetxeak
    @GetMapping
    public List<IkastetxeaDTO> lortuGuztiak() {
        return ikastetxeaService.lortuGuztiak();
    }
}
