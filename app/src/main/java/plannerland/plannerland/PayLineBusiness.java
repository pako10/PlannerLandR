package plannerland.plannerland;

import java.util.Map;
import java.util.UUID;

/**
 * Created by Paco on 10/08/2015.
 */
public class PayLineBusiness {
    public UUID idEmpresaPagoLinea;
    public String Empresa;
    public Map<UUID, Country> Paises;
    public char Estatus;
    public String UserName;
    public String ApplicationName;
    public String FechaUTCCreacion;
    public String FechaUTCModificacion;
    public String SystemTimeZone;
}
