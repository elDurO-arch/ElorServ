package com.ElorServ.ElorServ;

public class ElorServController {

}
/*package com.Arik.zereginak;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zereginak")
public class ZereginaController {
	
	private ArrayList<Zeregina> zereginLista = new ArrayList<>();
	private int kontagailua=1;
	
	//Eraikitzailea, hasierako zereginekin
	public ZereginaController() {
		zereginLista.add(new Zeregina(kontagailua++, "SpringBoot ikasi", false));
		zereginLista.add(new Zeregina(kontagailua++, "Kotxea garbitu", true));
	}
	
	//ENDPOINTak
	//Lortu zeregin guztiak 
	@GetMapping
	public ArrayList<Zeregina> getZereginak(){
		return zereginLista;
	}
	
	@PostMapping
	public Zeregina postZeregina(@RequestBody Zeregina zeregina) {
		zeregina.setId(kontagailua++);
		zereginLista.add(zeregina);
		return zeregina;
	}
	
	@DeleteMapping("/{id}")
	public String deleteZeregina(@PathVariable int id) {
		
			for(Zeregina z: zereginLista) {
				if(z.getId()==id) {
					zereginLista.remove(z);
			return "Zeregina ezabatuta";
				}
			}
			return "Zeregina ID horrekin ez dago: " + id;
		}
	
	@PutMapping("/{id}")
	public String updateOsatuta(@PathVariable int id) {
		
		for(Zeregina z: zereginLista) {
			if(z.getId()==id) {
				z.setOsatuta(true);
				return "Eguneratu da zeregina";
			}
		}
		return "Zeregina ID horrekin ez dago: " + id;
	}
}
*/
