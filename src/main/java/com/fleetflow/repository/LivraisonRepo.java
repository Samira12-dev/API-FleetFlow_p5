package com.fleetflow.repository;

import com.fleetflow.entity.Livraison;
import com.fleetflow.entity.StatutLivraison;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LivraisonRepo extends JpaRepository<Livraison, Long> {
    Page<Livraison> findByStatut(StatutLivraison statut, Pageable pageable);

    Page<Livraison> findByClientId(Long clientId,Pageable pageable);

    @Query("select d from Livraison d where d.dateLivraison between :start and :end")
    Page<Livraison> findByDateLivraisonBetween(@Param("start") LocalDate start, @Param("end") LocalDate end, Pageable pageable);

    @Query("SELECT l FROM Livraison l WHERE l.adresseDestination LIKE %:ville%")
    Page<Livraison> findLivraisonsParVilleDestination(@Param("ville") String ville, Pageable pageable);

    @Query("select count(l) from Livraison l where l.chauffeur.id = ?1")
    long totalLivraisonsByChauffeur( Long chauffeurId);

    Page<Livraison> findByChauffeurId(Long chauffeurId,Pageable pageable);
}
