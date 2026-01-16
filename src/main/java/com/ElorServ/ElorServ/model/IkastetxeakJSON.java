package com.ElorServ.ElorServ.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class IkastetxeakJSON {
	
	@SerializedName("CENTROS")
	private List<IkastetxeaDTO> listaCentros;
}

