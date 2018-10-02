package co.b2bginebra.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the negocio database table.
 * 
 */
@Entity
@Table(name="negocio")
@NamedQuery(name="Negocio.findAll", query="SELECT n FROM Negocio n")
public class Negocio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="NEGOCIO_IDNEGOCIO_GENERATOR", sequenceName="SEQ_NEGOCIO",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="NEGOCIO_IDNEGOCIO_GENERATOR")
	@Column(name="id_negocio", unique=true, nullable=false, precision=6)
	private long idNegocio;

	@Column(nullable=false, length=50)
	private String correo;

	@Column(nullable=false, length=500)
	private String descripcion;

	@Column(nullable=false, length=50)
	private String direccion;

	@Column(name="img_principal", nullable=false)
	private byte[] imgPrincipal;

	@Column(name="razon_social", nullable=false, length=50)
	private String razonSocial;

	@Column(name="sitio_web", length=100)
	private String sitioWeb;

	@Column(nullable=false, length=50)
	private String telefono;

	//bi-directional many-to-one association to HorarioAtencion
	@OneToMany(cascade=CascadeType.ALL,mappedBy="negocio",orphanRemoval=true)
	private List<HorarioAtencion> horarioAtencions;

	//bi-directional many-to-one association to Imagen
	@OneToMany(cascade=CascadeType.ALL,mappedBy="negocio",orphanRemoval=true)
	private List<Imagen> imagens;

	//bi-directional many-to-one association to Estado
	@ManyToOne
	@JoinColumn(name="estado_id_estado", nullable=false)
	private Estado estado;

	//bi-directional many-to-one association to SolicitudReg
	@ManyToOne
	@JoinColumn(name="solicitud_reg_id_solicitud", nullable=true)
	private SolicitudReg solicitudReg;

	//bi-directional many-to-one association to TipoNegocio
	@ManyToOne
	@JoinColumn(name="tipo_negocio_id_tipo_negocio", nullable=false)
	private TipoNegocio tipoNegocio;

	//bi-directional many-to-one association to Usuario
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="usuario_id_usuario", nullable=false)
	private Usuario usuario;

	//bi-directional many-to-one association to Notificacion
	@OneToMany(mappedBy="negocio")
	private List<Notificacion> notificacions;

	//bi-directional many-to-one association to Oferta
	@OneToMany(cascade=CascadeType.ALL,fetch = FetchType.EAGER,mappedBy="negocio",orphanRemoval=true)
	private List<Oferta> ofertas;

	//bi-directional many-to-one association to SolicitudReg
	@OneToMany(mappedBy="negocio")
	private List<SolicitudReg> solicitudRegs;

	public Negocio() {
	}

	public long getIdNegocio() {
		return this.idNegocio;
	}

	public void setIdNegocio(long idNegocio) {
		this.idNegocio = idNegocio;
	}

	public String getCorreo() {
		return this.correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public byte[] getImgPrincipal() {
		return this.imgPrincipal;
	}

	public void setImgPrincipal(byte[] imgPrincipal) {
		this.imgPrincipal = imgPrincipal;
	}

	public String getRazonSocial() {
		return this.razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getSitioWeb() {
		return this.sitioWeb;
	}

	public void setSitioWeb(String sitioWeb) {
		this.sitioWeb = sitioWeb;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public List<HorarioAtencion> getHorarioAtencions() {
		return this.horarioAtencions;
	}

	public void setHorarioAtencions(List<HorarioAtencion> horarioAtencions) {
		this.horarioAtencions = horarioAtencions;
	}

	public HorarioAtencion addHorarioAtencion(HorarioAtencion horarioAtencion) {
		getHorarioAtencions().add(horarioAtencion);
		horarioAtencion.setNegocio(this);

		return horarioAtencion;
	}

	public HorarioAtencion removeHorarioAtencion(HorarioAtencion horarioAtencion) {
		getHorarioAtencions().remove(horarioAtencion);
		horarioAtencion.setNegocio(null);

		return horarioAtencion;
	}

	public List<Imagen> getImagens() {
		return this.imagens;
	}

	public void setImagens(List<Imagen> imagens) {
		this.imagens = imagens;
	}

	public Imagen addImagen(Imagen imagen) {
		getImagens().add(imagen);
		imagen.setNegocio(this);

		return imagen;
	}

	public Imagen removeImagen(Imagen imagen) {
		getImagens().remove(imagen);
		imagen.setNegocio(null);

		return imagen;
	}

	public Estado getEstado() {
		return this.estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public SolicitudReg getSolicitudReg() {
		return this.solicitudReg;
	}

	public void setSolicitudReg(SolicitudReg solicitudReg) {
		this.solicitudReg = solicitudReg;
	}

	public TipoNegocio getTipoNegocio() {
		return this.tipoNegocio;
	}

	public void setTipoNegocio(TipoNegocio tipoNegocio) {
		this.tipoNegocio = tipoNegocio;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Notificacion> getNotificacions() {
		return this.notificacions;
	}

	public void setNotificacions(List<Notificacion> notificacions) {
		this.notificacions = notificacions;
	}

	public Notificacion addNotificacion(Notificacion notificacion) {
		getNotificacions().add(notificacion);
		notificacion.setNegocio(this);

		return notificacion;
	}

	public Notificacion removeNotificacion(Notificacion notificacion) {
		getNotificacions().remove(notificacion);
		notificacion.setNegocio(null);

		return notificacion;
	}

	public List<Oferta> getOfertas() {
		return this.ofertas;
	}

	public void setOfertas(List<Oferta> ofertas) {
		this.ofertas = ofertas;
	}

	public Oferta addOferta(Oferta oferta) {
		getOfertas().add(oferta);
		oferta.setNegocio(this);

		return oferta;
	}

	public Oferta removeOferta(Oferta oferta) {
		getOfertas().remove(oferta);
		oferta.setNegocio(null);

		return oferta;
	}

	public List<SolicitudReg> getSolicitudRegs() {
		return this.solicitudRegs;
	}

	public void setSolicitudRegs(List<SolicitudReg> solicitudRegs) {
		this.solicitudRegs = solicitudRegs;
	}

	public SolicitudReg addSolicitudReg(SolicitudReg solicitudReg) {
		getSolicitudRegs().add(solicitudReg);
		solicitudReg.setNegocio(this);

		return solicitudReg;
	}

	public SolicitudReg removeSolicitudReg(SolicitudReg solicitudReg) {
		getSolicitudRegs().remove(solicitudReg);
		solicitudReg.setNegocio(null);

		return solicitudReg;
	}
	
	public String toString()
	{
		return "RAZON SOCIAL: " + razonSocial + ", DIRECCION: " + direccion + ", TELEFONO: " + telefono + ", CORREO: " + correo;
	}

}