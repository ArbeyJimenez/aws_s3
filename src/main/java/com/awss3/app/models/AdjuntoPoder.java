package com.awss3.app.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "sga_adjunto_poder")
public class AdjuntoPoder implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "cod_ee_operacion")
	private Integer cod_asamblea;
	
	@Column(name = "id_usuario")
	private String identificaUsuario;	
	
	@Column(name = "estado")
	private String estado;
	
	@Column(name = "ruta_bucket")
	private String rutaBucket;
	
	@Column(name = "fecha_envio")
	private Date fechaEnvio;
}