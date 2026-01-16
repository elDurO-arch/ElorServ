package com.ElorServ.ElorServ.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class IkastetxeaDTO {

    @SerializedName("CCEN") 
    private Integer codigo;

    @SerializedName("NOME")
    private String izena;

    @SerializedName("DOMI")
    private String helbidea;

    @SerializedName("CPOS")
    private Integer postaKodea;

    @SerializedName("TEL1")
    private String telefonoa;

    @SerializedName("EMAIL")
    private String email;

    @SerializedName("PAGINA")
    private String webgunea;

    @SerializedName("DMUNIE")
    private String udalerria;

    @SerializedName("DTERRE")
    private String lurraldea;

    @SerializedName("DGENRE")
    private String mota;

    @SerializedName("LATITUD")
    private Double latitudea;

    @SerializedName("LONGITUD")
    private Double longitudea;
}