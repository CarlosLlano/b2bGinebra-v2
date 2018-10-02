package co.b2bginebra.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the horario_atencion database table.
 * 
 */
@Entity
@Table(name="horario_atencion")
@NamedQuery(name="HorarioAtencion.findAll", query="SELECT h FROM HorarioAtencion h")
public class HorarioAtencion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="HORARIO_ATENCION_IDHORARIO_GENERATOR", sequenceName="SEQ_HORARIO_ATENCION",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="HORARIO_ATENCION_IDHORARIO_GENERATOR")
	@Column(name="id_horario", unique=true, nullable=false, precision=6)
	private long idHorario;

	@Column(nullable=false, length=10)
	private String dia;

	@Column(name="fecha_fin", nullable=false, length=10)
	private String fechaFin;

	@Column(name="fecha_inicio", nullable=false, length=10)
	private String fechaInicio;

	//bi-directional many-to-one association to Negocio
	@ManyToOne
	@JoinColumn(name="negocio_id_negocio", nullable=false)
	private Negocio negocio;

	public HorarioAtencion() {
	}

	public long getIdHorario() {
		return this.idHorario;
	}

	public void setIdHorario(long idHorario) {
		this.idHorario = idHorario;
	}

	public String getDia() {
		return this.dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public String getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Negocio getNegocio() {
		return this.negocio;
	}

	public void setNegocio(Negocio negocio) {
		this.negocio = negocio;
	}

}