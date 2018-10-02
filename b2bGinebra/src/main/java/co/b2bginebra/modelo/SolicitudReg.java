package co.b2bginebra.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the solicitud_reg database table.
 * 
 */
@Entity
@Table(name="solicitud_reg")
@NamedQuery(name="SolicitudReg.findAll", query="SELECT s FROM SolicitudReg s")
public class SolicitudReg implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SOLICITUD_REG_IDSOLICITUD_GENERATOR", sequenceName="SEQ_SOLICITUD_REG",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SOLICITUD_REG_IDSOLICITUD_GENERATOR")
	@Column(name="id_solicitud", unique=true, nullable=false, precision=6)
	private long idSolicitud;

	@Column(nullable=false, length=500)
	private String descripcion;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_atencion")
	private Date fechaAtencion;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_creacion", nullable=false)
	private Date fechaCreacion;

	@Column(length=500)
	private String respuesta;

	//bi-directional many-to-one association to Negocio
	@OneToMany(mappedBy="solicitudReg")
	private List<Negocio> negocios;

	//bi-directional many-to-one association to Estado
	@ManyToOne
	@JoinColumn(name="estado_id_estado", nullable=false)
	private Estado estado;

	//bi-directional many-to-one association to Negocio
	@ManyToOne
	@JoinColumn(name="negocio_id_negocio", nullable=false)
	private Negocio negocio;

	public SolicitudReg() {
	}

	public long getIdSolicitud() {
		return this.idSolicitud;
	}

	public void setIdSolicitud(long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaAtencion() {
		return this.fechaAtencion;
	}

	public void setFechaAtencion(Date fechaAtencion) {
		this.fechaAtencion = fechaAtencion;
	}

	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getRespuesta() {
		return this.respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public List<Negocio> getNegocios() {
		return this.negocios;
	}

	public void setNegocios(List<Negocio> negocios) {
		this.negocios = negocios;
	}

	public Negocio addNegocio(Negocio negocio) {
		getNegocios().add(negocio);
		negocio.setSolicitudReg(this);

		return negocio;
	}

	public Negocio removeNegocio(Negocio negocio) {
		getNegocios().remove(negocio);
		negocio.setSolicitudReg(null);

		return negocio;
	}

	public Estado getEstado() {
		return this.estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Negocio getNegocio() {
		return this.negocio;
	}

	public void setNegocio(Negocio negocio) {
		this.negocio = negocio;
	}

}