package co.b2bginebra.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the negocio_registrado database table.
 * 
 */
@Entity
@Table(name="negocio_registrado")
@NamedQuery(name="NegocioRegistrado.findAll", query="SELECT n FROM NegocioRegistrado n")
public class NegocioRegistrado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="NEGOCIO_REGISTRADO_IDNEGOCIOREGIST_GENERATOR", sequenceName="SEQ_NEGOCIO_REGISTRADO",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="NEGOCIO_REGISTRADO_IDNEGOCIOREGIST_GENERATOR")
	@Column(name="id_negocio_regist", unique=true, nullable=false, precision=7)
	private long idNegocioRegist;

	@Column(name="doc_repr", nullable=false, length=50)
	private String docRepr;

	@Column(name="razon_social", nullable=false, length=50)
	private String razonSocial;

	public NegocioRegistrado() {
	}

	public long getIdNegocioRegist() {
		return this.idNegocioRegist;
	}

	public void setIdNegocioRegist(long idNegocioRegist) {
		this.idNegocioRegist = idNegocioRegist;
	}

	public String getDocRepr() {
		return this.docRepr;
	}

	public void setDocRepr(String docRepr) {
		this.docRepr = docRepr;
	}

	public String getRazonSocial() {
		return this.razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

}