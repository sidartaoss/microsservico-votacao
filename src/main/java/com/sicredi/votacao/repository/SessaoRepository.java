/*
 * @(#) SessaoRepository.java      1.00    29/05/2020
 * Copyrights (c) 2020 Sicredi.
 * Todos os direitos reservados.
 */
package com.sicredi.votacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sicredi.votacao.model.Sessao;

/**
 * Interface de repositório para a entidade {@link Sessao}.
 * 
 * @author Sidarta Silva (semprebono@gmail.com)
 * @version $Revision$
 */
@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Long> {

}
