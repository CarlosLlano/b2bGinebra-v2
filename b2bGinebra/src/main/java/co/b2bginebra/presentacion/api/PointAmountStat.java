package co.b2bginebra.presentacion.api;

/**
 *	Clase utilizada para generar estadisticas
 *
 */
public class PointAmountStat implements Comparable<PointAmountStat>
{

	private String nombre;
	private int cantidad;
	
	
	public PointAmountStat()
	{
		
	}
	
	public PointAmountStat(String nombre, int cantidad) {
        this.nombre = nombre;
        this.cantidad = cantidad;
    }
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public int compareTo(PointAmountStat o) 
	{
		int cantidad=o.getCantidad();
 
	    return cantidad-this.cantidad;

	}

	
	
	
	
	
	
}
