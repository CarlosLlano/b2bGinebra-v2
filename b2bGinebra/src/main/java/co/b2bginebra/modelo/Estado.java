package co.b2bginebra.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the estado database table.
 * 
 */
@Entity
@Table(name="estado")
@NamedQuery(name="Estado.findAll", query="SELECT e FROM Estado e")
public class Estado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ESTADO_IDESTADO_GENERATOR", sequenceName="SEQ_ESTADO",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ESTADO_IDESTADO_GENERATOR")
	@Column(name="id_estado", unique=true, nullable=false, precision=2)
	private long idEstado;

	@Column(nullable=false, length=50)
	private String descripcion;

	@Column(nullable=false, length=50)
	private String nombre;

	//bi-directional many-to-one association to TipoEstado
	@ManyToOne
	@JoinColumn(name="tipo_estado_id_tipo_estado", nullable=false)
	private TipoEstado tipoEstado;

	//bi-directional many-to-one association to Negocio
	@OneToMany(mappedBy="estado")
	private List<Negocio> negocios;

	//bi-directional many-to-one association to SolicitudReg
	@OneToMany(mappedBy="estado")
	private List<SolicitudReg> solicitudRegs;

	//bi-directional many-to-one association to Usuario
	@OneToMany(mappedBy="estado")
	private List<Usuario> usuarios;

	public Estado() {
	}

	public long getIdEstado() {
		return this.idEstado;
	}

	public void setIdEstado(long idEstado) {
		this.idEstado = idEstado;
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

	public TipoEstado getTipoEstado() {
		return this.tipoEstado;
	}

	public void setTipoEstado(TipoEstado tipoEstado) {
		this.tipoEstado = tipoEstado;
	}

	public List<Negocio> getNegocios() {
		return this.negocios;
	}

	public void setNegocios(List<Negocio> negocios) {
		this.negocios = negocios;
	}

	public Negocio addNegocio(Negocio negocio) {
		getNegocios().add(negocio);
		negocio.setEstado(this);

		return negocio;
	}

	public Negocio removeNegocio(Negocio negocio) {
		getNegocios().remove(negocio);
		negocio.setEstado(null);

		return negocio;
	}

	public List<SolicitudReg> getSolicitudRegs() {
		return this.solicitudRegs;
	}

	public void setSolicitudRegs(List<SolicitudReg> solicitudRegs) {
		this.solicitudRegs = solicitudRegs;
	}

	public SolicitudReg addSolicitudReg(SolicitudReg solicitudReg) {
		getSolicitudRegs().add(solicitudReg);
		solicitudReg.setEstado(this);

		return solicitudReg;
	}

	public SolicitudReg removeSolicitudReg(SolicitudReg solicitudReg) {
		getSolicitudRegs().remove(solicitudReg);
		solicitudReg.setEstado(null);

		return solicitudReg;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario addUsuario(Usuario usuario) {
		getUsuarios().add(usuario);
		usuario.setEstado(this);

		return usuario;
	}

	public Usuario removeUsuario(Usuario usuario) {
		getUsuarios().remove(usuario);
		usuario.setEstado(null);

		return usuario;
	}

}