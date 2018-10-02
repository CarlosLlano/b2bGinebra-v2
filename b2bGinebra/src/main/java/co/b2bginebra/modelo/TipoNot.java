package co.b2bginebra.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tipo_not database table.
 * 
 */
@Entity
@Table(name="tipo_not")
@NamedQuery(name="TipoNot.findAll", query="SELECT t FROM TipoNot t")
public class TipoNot implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TIPO_NOT_IDTIPONOT_GENERATOR", sequenceName="SEQ_TIPO_NOT",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TIPO_NOT_IDTIPONOT_GENERATOR")
	@Column(name="id_tipo_not", unique=true, nullable=false, precision=2)
	private long idTipoNot;

	@Column(nullable=false, length=100)
	private String descripcion;

	@Column(nullable=false, length=50)
	private String nombre;

	//bi-directional many-to-one association to Notificacion
	@OneToMany(mappedBy="tipoNot")
	private List<Notificacion> notificacions;

	public TipoNot() {
	}

	public long getIdTipoNot() {
		return this.idTipoNot;
	}

	public void setIdTipoNot(long idTipoNot) {
		this.idTipoNot = idTipoNot;
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

	public List<Notificacion> getNotificacions() {
		return this.notificacions;
	}

	public void setNotificacions(List<Notificacion> notificacions) {
		this.notificacions = notificacions;
	}

	public Notificacion addNotificacion(Notificacion notificacion) {
		getNotificacions().add(notificacion);
		notificacion.setTipoNot(this);

		return notificacion;
	}

	public Notificacion removeNotificacion(Notificacion notificacion) {
		getNotificacions().remove(notificacion);
		notificacion.setTipoNot(null);

		return notificacion;
	}

}