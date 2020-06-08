/*
 * @(#) PautaRepository.java      1.00    29/05/2020
 * Copyrights (c) 2020 Sicredi.
 * Todos os direitos reservados.
 */
package com.sicredi.votacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sicredi.votacao.model.Pauta;

/**
 * Interface de repositório para a entidade {@link Pauta}.
 * 
 * @author Sidarta Silva (semprebono@gmail.com)
 * @version $Revision$
 */
@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {

}
