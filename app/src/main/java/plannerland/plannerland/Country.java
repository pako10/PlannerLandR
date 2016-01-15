package plannerland.plannerland;

import java.util.Map;
import java.util.UUID;

/**
 * Created by Paco on 10/08/2015.
 */
public class Country {
    public UUID idPais;
    public Language Idioma;
    public Map<UUID, PayLineBusiness> EmpresasPagoEnLinea;
    public String CodigoAlfa2;
    public String CodigoAlfa3;
    public String CodigoNumerico;
    public String NombreEspanyol;
    public String NombreIngles;
    public String NombrePortugues;
    public String NombreFrances;
    public String NombreAleman;
    public int Latitud;
    public int Longitud;
    public int Altitud;
    public int Azimut;
    public boolean Activate;
    public String TimeZone;
    public char Estatus;
    public String UserName;
    public String ApplicationName;
    public String FechaUTCCreacion;
    public String FechaUTCModificacion;
    public String SystemTimeZone;
}
