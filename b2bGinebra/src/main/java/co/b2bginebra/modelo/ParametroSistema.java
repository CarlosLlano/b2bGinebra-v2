package co.b2bginebra.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the parametro_sistema database table.
 * 
 */
@Entity
@Table(name="parametro_sistema")
@NamedQuery(name="ParametroSistema.findAll", query="SELECT p FROM ParametroSistema p")
public class ParametroSistema implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PARAMETRO_SISTEMA_IDPARAMETRO_GENERATOR", sequenceName="SEQ_PARAMETRO_SISTEMA",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PARAMETRO_SISTEMA_IDPARAMETRO_GENERATOR")
	@Column(name="id_parametro", unique=true, nullable=false, precision=2)
	private long idParametro;

	@Column(nullable=false, length=50)
	private String nombre;

	@Column(nullable=false, length=500)
	private String valor;

	public ParametroSistema() {
	}

	public long getIdParametro() {
		return this.idParametro;
	}

	public void setIdParametro(long idParametro) {
		this.idParametro = idParametro;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getValor() {
		return this.valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

}