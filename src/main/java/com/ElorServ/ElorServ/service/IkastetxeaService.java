package com.ElorServ.ElorServ.service;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.io.Reader;

import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import jakarta.annotation.PostConstruct;

import com.ElorServ.ElorServ.model.IkastetxeaDTO;
import com.ElorServ.ElorServ.model.IkastetxeakJSON;


@Service
public class IkastetxeaService {

	private List<IkastetxeaDTO> ikastetxeaZerrenda = new ArrayList<>();
	
	public IkastetxeaService() {
		// cuando spring crea la clase
		System.out.println("spring ha encontrado IkastetxeaService");
	}
	
	@PostConstruct
	public void kargatuDatuak() {
		Gson gson = new Gson();
		
		try {
			File jsonFile = new File("EuskadiLatLon.json");
			
			if(jsonFile.exists()) {
				try(Reader reader = new FileReader(jsonFile)){
					IkastetxeakJSON wrapper = gson.fromJson(reader, IkastetxeakJSON.class);
					this.ikastetxeaZerrenda = wrapper.getListaCentros();
					System.out.println("Gson kargatu da " + ikastetxeaZerrenda.size() + " ikastetxe.");
					
				}
			}else {
				System.out.println("Ezin izan da EuskadiLatLon.json fitxategia aurkitu.");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	
	public List<IkastetxeaDTO> lortuGuztiak() {
		return ikastetxeaZerrenda;	
	}
}
