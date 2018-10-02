package co.b2bginebra.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tipo_estado database table.
 * 
 */
@Entity
@Table(name="tipo_estado")
@NamedQuery(name="TipoEstado.findAll", query="SELECT t FROM TipoEstado t")
public class TipoEstado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TIPO_ESTADO_IDTIPOESTADO_GENERATOR", sequenceName="SEQ_TIPO_ESTADO",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TIPO_ESTADO_IDTIPOESTADO_GENERATOR")
	@Column(name="id_tipo_estado", unique=true, nullable=false, precision=3)
	private long idTipoEstado;

	@Column(nullable=false, length=50)
	private String descripcion;

	@Column(nullable=false, length=50)
	private String nombre;

	//bi-directional many-to-one association to Estado
	@OneToMany(mappedBy="tipoEstado")
	private List<Estado> estados;

	public TipoEstado() {
	}

	public long getIdTipoEstado() {
		return this.idTipoEstado;
	}

	public void setIdTipoEstado(long idTipoEstado) {
		this.idTipoEstado = idTipoEstado;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Estado> getEstados() {
		return this.estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public Estado addEstado(Estado estado) {
		getEstados().add(estado);
		estado.setTipoEstado(this);

		return estado;
	}

	public Estado removeEstado(Estado estado) {
		getEstados().remove(estado);
		estado.setTipoEstado(null);

		return estado;
	}

}