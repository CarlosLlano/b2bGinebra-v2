package co.b2bginebra.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the imagen database table.
 * 
 */
@Entity
@Table(name="imagen")
@NamedQuery(name="Imagen.findAll", query="SELECT i FROM Imagen i")
public class Imagen implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="IMAGEN_IDIMAGEN_GENERATOR", sequenceName="SEQ_IMAGEN",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="IMAGEN_IDIMAGEN_GENERATOR")
	@Column(name="id_imagen", unique=true, nullable=false, precision=7)
	private long idImagen;

	@Column(nullable=false)
	private byte[] imagen;

	//bi-directional many-to-one association to Negocio
	@ManyToOne
	@JoinColumn(name="negocio_id_negocio", nullable=false)
	private Negocio negocio;

	public Imagen() {
	}

	public long getIdImagen() {
		return this.idImagen;
	}

	public void setIdImagen(long idImagen) {
		this.idImagen = idImagen;
	}

	public byte[] getImagen() {
		return this.imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public Negocio getNegocio() {
		return this.negocio;
	}

	public void setNegocio(Negocio negocio) {
		this.negocio = negocio;
	}

}