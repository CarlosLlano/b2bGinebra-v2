package co.b2bginebra.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@Table(name="usuario")
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USUARIO_IDUSUARIO_GENERATOR", sequenceName="SEQ_USUARIO",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USUARIO_IDUSUARIO_GENERATOR")
	@Column(name="id_usuario", unique=true, nullable=false, precision=6)
	private long idUsuario;

	@Column(nullable=false, length=50)
	private String correo;

	@Column(nullable=false, length=50)
	private String direccion;

	@Column(nullable=false, length=50)
	private String identificacion;

	@Column(nullable=false, length=50)
	private String nombre;

	@Column(nullable=false, length=50)
	private String password;

	@Column(nullable=false, length=50)
	private String telefono;

	//bi-directional many-to-one association to Negocio
	@OneToMany(fetch = FetchType.EAGER,mappedBy="usuario")
	private List<Negocio> negocios;

	//bi-directional many-to-one association to Estado
	@ManyToOne
	@JoinColumn(name="estado_id_estado", nullable=false)
	private Estado estado;

	public Usuario() {
	}

	public long getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getCorreo() {
		return this.correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getIdentificacion() {
		return this.identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public List<Negocio> getNegocios() {
		return this.negocios;
	}

	public void setNegocios(List<Negocio> negocios) {
		this.negocios = negocios;
	}

	public Negocio addNegocio(Negocio negocio) {
		getNegocios().add(negocio);
		negocio.setUsuario(this);

		return negocio;
	}

	public Negocio removeNegocio(Negocio negocio) {
		getNegocios().remove(negocio);
		negocio.setUsuario(null);

		return negocio;
	}

	public Estado getEstado() {
		return this.estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	public String toString()
	{
		return "NOMBRE: " + nombre + ", IDENTIFICACION: " + identificacion + ", TELEFONO: " + telefono + ", DIRECCION: " + direccion + ", CORREO: " + correo;
	}
	
}