package co.b2bginebra.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the notificacion database table.
 * 
 */
@Entity
@Table(name="notificacion")
@NamedQuery(name="Notificacion.findAll", query="SELECT n FROM Notificacion n")
public class Notificacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="NOTIFICACION_IDNOTIFICACION_GENERATOR", sequenceName="SEQ_NOTIFICACION",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="NOTIFICACION_IDNOTIFICACION_GENERATOR")
	@Column(name="id_notificacion", unique=true, nullable=false, precision=7)
	private long idNotificacion;

	@Column(nullable=false, length=500)
	private String descripcion;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_creacion", nullable=false)
	private Date fechaCreacion;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_terminacion", nullable=false)
	private Date fechaTerminacion;

	@Column(nullable=false)
	private byte[] imagen;

	@Column(nullable=false, length=50)
	private String nombre;

	@Column(nullable=false, precision=5)
	private BigDecimal visitas;

	//bi-directional many-to-one association to CategoriaProd
	@ManyToOne
	@JoinColumn(name="categoria_prod_id_categoria", nullable=false)
	private CategoriaProd categoriaProd;

	//bi-directional many-to-one association to Negocio
	@ManyToOne
	@JoinColumn(name="negocio_id_negocio", nullable=false)
	private Negocio negocio;

	//bi-directional many-to-one association to TipoNot
	@ManyToOne
	@JoinColumn(name="tipo_not_id_tipo_not", nullable=false)
	private TipoNot tipoNot;

	public Notificacion() {
	}

	public long getIdNotificacion() {
		return this.idNotificacion;
	}

	public void setIdNotificacion(long idNotificacion) {
		this.idNotificacion = idNotificacion;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaTerminacion() {
		return this.fechaTerminacion;
	}

	public void setFechaTerminacion(Date fechaTerminacion) {
		this.fechaTerminacion = fechaTerminacion;
	}

	public byte[] getImagen() {
		return this.imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getVisitas() {
		return this.visitas;
	}

	public void setVisitas(BigDecimal visitas) {
		this.visitas = visitas;
	}

	public CategoriaProd getCategoriaProd() {
		return this.categoriaProd;
	}

	public void setCategoriaProd(CategoriaProd categoriaProd) {
		this.categoriaProd = categoriaProd;
	}

	public Negocio getNegocio() {
		return this.negocio;
	}

	public void setNegocio(Negocio negocio) {
		this.negocio = negocio;
	}

	public TipoNot getTipoNot() {
		return this.tipoNot;
	}

	public void setTipoNot(TipoNot tipoNot) {
		this.tipoNot = tipoNot;
	}

}