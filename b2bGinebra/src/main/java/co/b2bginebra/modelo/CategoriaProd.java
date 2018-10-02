package co.b2bginebra.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the categoria_prod database table.
 * 
 */
@Entity
@Table(name="categoria_prod")
@NamedQuery(name="CategoriaProd.findAll", query="SELECT c FROM CategoriaProd c")
public class CategoriaProd implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CATEGORIA_PROD_IDCATEGORIA_GENERATOR", sequenceName="SEQ_CATEGORIA_PROD",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CATEGORIA_PROD_IDCATEGORIA_GENERATOR")
	@Column(name="id_categoria", unique=true, nullable=false, precision=2)
	private long idCategoria;

	@Column(nullable=false, length=50)
	private String nombre;

	//bi-directional many-to-one association to TipoNegocio
	@ManyToOne
	@JoinColumn(name="tipo_negocio_id_tipo_negocio", nullable=false)
	private TipoNegocio tipoNegocio;

	//bi-directional many-to-one association to Notificacion
	@OneToMany(mappedBy="categoriaProd")
	private List<Notificacion> notificacions;

	//bi-directional many-to-one association to Oferta
	@OneToMany(mappedBy="categoriaProd")
	private List<Oferta> ofertas;

	public CategoriaProd() {
	}

	public long getIdCategoria() {
		return this.idCategoria;
	}

	public void setIdCategoria(long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public TipoNegocio getTipoNegocio() {
		return this.tipoNegocio;
	}

	public void setTipoNegocio(TipoNegocio tipoNegocio) {
		this.tipoNegocio = tipoNegocio;
	}

	public List<Notificacion> getNotificacions() {
		return this.notificacions;
	}

	public void setNotificacions(List<Notificacion> notificacions) {
		this.notificacions = notificacions;
	}

	public Notificacion addNotificacion(Notificacion notificacion) {
		getNotificacions().add(notificacion);
		notificacion.setCategoriaProd(this);

		return notificacion;
	}

	public Notificacion removeNotificacion(Notificacion notificacion) {
		getNotificacions().remove(notificacion);
		notificacion.setCategoriaProd(null);

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
		oferta.setCategoriaProd(this);

		return oferta;
	}

	public Oferta removeOferta(Oferta oferta) {
		getOfertas().remove(oferta);
		oferta.setCategoriaProd(null);

		return oferta;
	}

}