package co.b2bginebra.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the oferta database table.
 * 
 */
@Entity
@Table(name="oferta")
@NamedQuery(name="Oferta.findAll", query="SELECT o FROM Oferta o")
public class Oferta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OFERTA_IDOFERTA_GENERATOR", sequenceName="SEQ_OFERTA",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OFERTA_IDOFERTA_GENERATOR")
	@Column(name="id_oferta", unique=true, nullable=false, precision=6)
	private long idOferta;

	@Column(nullable=false, length=500)
	private String descripcion;

	//bi-directional many-to-one association to CategoriaProd
	@ManyToOne
	@JoinColumn(name="categoria_prod_id_categoria", nullable=false)
	private CategoriaProd categoriaProd;

	//bi-directional many-to-one association to Negocio
	@ManyToOne
	@JoinColumn(name="negocio_id_negocio", nullable=false)
	private Negocio negocio;

	public Oferta() {
	}

	public long getIdOferta() {
		return this.idOferta;
	}

	public void setIdOferta(long idOferta) {
		this.idOferta = idOferta;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

}