package com.election.project.controllers;

import com.election.project.entity.Candidate;
import com.election.project.entity.Party;
import com.election.project.repository.CandidateRepository;
import com.election.project.repository.PartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

    private PartyRepository partyRepository;
    private CandidateRepository candidateRepository;

    public HomeController(PartyRepository partyRepository, CandidateRepository candidateRepository) {
        this.partyRepository = partyRepository;
        this.candidateRepository = candidateRepository;
    }

    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping("/home")
    public String home(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("userdetail", userDetails);
        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            model.addAttribute("isAdmin", true);
        }
        return "home";
    }

    @GetMapping("/parties")
    public String listParties(Model model) {
        List<Party> parties = partyRepository.findAll();
        model.addAttribute("parties", parties);
        return "list-parties";
    }

    @GetMapping("/candidates")
    public String listCandidates(Model model) {
        List<Candidate> candidates = candidateRepository.findAll();
        model.addAttribute("candidates", candidates);
        return "list-candidates";
    }
}
