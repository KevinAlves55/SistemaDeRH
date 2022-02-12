package com.AppRH.AppRH.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.AppRH.AppRH.models.Empresa;

public interface EmpresaRepository extends CrudRepository<Empresa, String> {
		
	Empresa findByCodigo(long codigo);
	List<Empresa> findByNome(String nome);

}
