/*
 * @(#) VotacaoRepository.java      1.00    29/05/2020
 * Copyrights (c) 2020 Sicredi.
 * Todos os direitos reservados.
 */
package com.sicredi.votacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sicredi.votacao.model.Sessao;
import com.sicredi.votacao.model.Votacao;
import com.sicredi.votacao.model.VotacaoPk;

/**
 * Interface de repositório para a entidade {@link Votacao}.
 * 
 * @author Sidarta Silva (semprebono@gmail.com)
 * @version $Revision$
 */
@Repository
public interface VotacaoRepository extends JpaRepository<Votacao, VotacaoPk> {

	/**
	 * Método responsável por buscar o total de votos Sim para a sessão informada.
	 * 
	 * @param sessaoId o id do objeto persistente {@link Sessao}.
	 * @return o total de votos Sim para o código da sessão informado.
	 */
	@Query("select count(v) from Votacao v where v.id.sessaoId = :sessaoId and v.voto = 'S'")
	Long findTotalVotosSimBySessaoId(@Param("sessaoId") Long sessaoId);
	
	/**
	 * Método responsável por buscar o total de votos Não para a sessão informada.
	 * 
	 * @param sessaoId o id do objeto persistente {@link Sessao}.
	 * @return o total de votos Não para o código da sessão informado.
	 */
	@Query("select count(v) from Votacao v where v.id.sessaoId = :sessaoId and v.voto = 'N'")
	Long findTotalVotosNaoBySessaoId(@Param("sessaoId") Long sessaoId);
}
