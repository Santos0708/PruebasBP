package ec.fin.bp.test.jpa.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.fin.bp.test.model.entity.Persona;

@Repository
public interface IPersonaDao extends CrudRepository<Persona, Integer> {


}
