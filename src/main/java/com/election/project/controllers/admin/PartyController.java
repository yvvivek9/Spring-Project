package com.election.project.controllers.admin;

import com.election.project.entity.Party;
import com.election.project.repository.PartyRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PartyController {
    private PartyRepository partyRepository;

    public PartyController(PartyRepository partyRepository) {
        this.partyRepository = partyRepository;
    }

    @GetMapping("/admin/parties")
    public String listParties(Model model) {
        List<Party> parties = partyRepository.findAll();
        model.addAttribute("parties", parties);
        return "/admin/list-parties";
    }

    @GetMapping("/admin/add-party")
    public String addParty(Model model, Party party) {
        model.addAttribute("party", party);
        return "/admin/add-party";
    }

    @PostMapping("/admin/add-party")
    public String addPartyPost(@ModelAttribute("party") Party party, Model model) {
        Party temp = partyRepository.findByName(party.getName());
        if (temp != null) {
            model.addAttribute("partyexists", party);
            return "/admin/add-party";
        }
        partyRepository.save(party);
        return "redirect:/admin/parties";
    }

    @PostMapping("/admin/delete-party")
    public String deleteParty(@RequestParam Long id) {
        partyRepository.deleteById(id);
        return "redirect:/admin/parties";
    }
}
