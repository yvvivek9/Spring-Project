package com.election.project.service;

import com.election.project.entity.Candidate;
import com.election.project.entity.Election;
import com.election.project.entity.Party;
import com.election.project.repository.ElectionRepository;
import com.election.project.repository.VoteRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ElectionService {
    @PersistenceContext
    private EntityManager entityManager;

    private ElectionRepository electionRepository;
    private VoteRepository voteRepository;

    public ElectionService(ElectionRepository electionRepository, VoteRepository voteRepository) {
        this.electionRepository = electionRepository;
        this.voteRepository = voteRepository;
    }

    public void startElection() {
        Election election = new Election();
        election.setStartDate(new Date());
        election.setStatus(Election.ElectionStatus.ACTIVE);
        electionRepository.save(election);
    }

    public void endElection(Long id) {
        Optional<Election> data = electionRepository.findById(id);
        if (data.isPresent()) {
            // calculate winners
            Party winParty = findPartyWithHighestVotes();
            Candidate winCandidate = findCandidateWithHighestVotesInParty(winParty);
            if (winParty == null || winCandidate == null) return;

            // Update election data
            Election election = data.get();
            election.setWinParty(winParty.getName());
            election.setWinCandidate(winCandidate.getName());
            election.setStatus(Election.ElectionStatus.ENDED);
            electionRepository.save(election);

            // Reset the votes table
            voteRepository.deleteAll();
        }
    }

    private Party findPartyWithHighestVotes() {
        // JPQL query to select the party with the highest total votes
        String jpql = "SELECT v.candidate.party " +
                "FROM Vote v " +
                "GROUP BY v.candidate.party " +
                "ORDER BY COUNT(v) DESC";

        List<Party> resultList = entityManager.createQuery(jpql, Party.class)
                .setMaxResults(1)
                .getResultList();

        // The first element of each array in resultList is the Party object
        if (!resultList.isEmpty()) {
            return resultList.getFirst();
        } else {
            return null; // No party found
        }
    }

    private Candidate findCandidateWithHighestVotesInParty(Party party) {
        // JPQL query to select the candidate with the highest votes in a specific party
        String jpql = "SELECT v.candidate " +
                "FROM Vote v " +
                "WHERE v.candidate.party = :party " +
                "GROUP BY v.candidate " +
                "ORDER BY COUNT(v) DESC";

        List<Candidate> resultList = entityManager.createQuery(jpql, Candidate.class)
                .setParameter("party", party)
                .setMaxResults(1)
                .getResultList();

        if (!resultList.isEmpty()) {
            return resultList.getFirst();
        } else {
            return null; // No candidate found in the given party
        }
    }
}
