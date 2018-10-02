package co.b2bginebra.modelo;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the tipo_negocio database table.
 * 
 */
@Entity
@Table(name="tipo_negocio")
@NamedQuery(name="TipoNegocio.findAll", query="SELECT t FROM TipoNegocio t")
public class TipoNegocio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TIPO_NEGOCIO_IDTIPONEGOCIO_GENERATOR", sequenceName="SEQ_TIPO_NEGOCIO",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TIPO_NEGOCIO_IDTIPONEGOCIO_GENERATOR")
	@Column(name="id_tipo_negocio", unique=true, nullable=false, precision=2)
	private long idTipoNegocio;

	@Column(nullable=false, length=50)
	private String nombre;

	//bi-directional many-to-one association to CategoriaProd
	@OneToMany(fetch = FetchType.EAGER,mappedBy="tipoNegocio")
	private List<CategoriaProd> categoriaProds;

	//bi-directional many-to-one association to Negocio
	@OneToMany(mappedBy="tipoNegocio")
	private List<Negocio> negocios;

	public TipoNegocio() {
	}

	public long getIdTipoNegocio() {
		return this.idTipoNegocio;
	}

	public void setIdTipoNegocio(long idTipoNegocio) {
		this.idTipoNegocio = idTipoNegocio;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<CategoriaProd> getCategoriaProds() {
		return this.categoriaProds;
	}

	public void setCategoriaProds(List<CategoriaProd> categoriaProds) {
		this.categoriaProds = categoriaProds;
	}

	public CategoriaProd addCategoriaProd(CategoriaProd categoriaProd) {
		getCategoriaProds().add(categoriaProd);
		categoriaProd.setTipoNegocio(this);

		return categoriaProd;
	}

	public CategoriaProd removeCategoriaProd(CategoriaProd categoriaProd) {
		getCategoriaProds().remove(categoriaProd);
		categoriaProd.setTipoNegocio(null);

		return categoriaProd;
	}

	public List<Negocio> getNegocios() {
		return this.negocios;
	}

	public void setNegocios(List<Negocio> negocios) {
		this.negocios = negocios;
	}

	public Negocio addNegocio(Negocio negocio) {
		getNegocios().add(negocio);
		negocio.setTipoNegocio(this);

		return negocio;
	}

	public Negocio removeNegocio(Negocio negocio) {
		getNegocios().remove(negocio);
		negocio.setTipoNegocio(null);

		return negocio;
	}

}