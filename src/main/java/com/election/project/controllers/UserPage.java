package com.election.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class UserPage {

    @GetMapping("/user-profile")
    public String showUserProfile(Model model) {
        // Create an example user object with details
        User user = new User();
        user.setVoterId("100001");
        user.setName("Jane Smith");
        user.setAge(28);
        user.setGender("Female");
        user.setCaste("SC-ST");
        user.setConstituency("Constituency 1");
        user.setState("State A");
        user.setEligibleToVote(true);
        user.setBiometricsVerified(true);

        // Add the user object to the model
        model.addAttribute("user", user);

        // Example announcements
        List<String> announcements = Arrays.asList(
                "Upcoming elections will be conducted in the month of January, 2024.",
                "Please check your respective polling booth at least a week before election day, once they are released.",
                "Please check your Voter eligibility and Biometric's verification before Dec, 2023."
        );

        // Add the announcements list to the model
        model.addAttribute("announcements", announcements);

        // Return the Thymeleaf template name
        return "user-page";
    }

    // Example User class (could be a separate file)
    public static class User {
        private String voterId;
        private String name;
        private int age;
        private String gender;
        private String caste;
        private String constituency;
        private String state;
        private boolean eligibleToVote;
        private boolean biometricsVerified;

        // Getters
        public String getVoterId() {
            return voterId;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public String getGender() {
            return gender;
        }

        public String getCaste() {
            return caste;
        }

        public String getConstituency() {
            return constituency;
        }

        public String getState() {
            return state;
        }

        public boolean isEligibleToVote() {
            return eligibleToVote;
        }

        public boolean isBiometricsVerified() {
            return biometricsVerified;
        }

        // Setters
        public void setVoterId(String voterId) {
            this.voterId = voterId;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public void setCaste(String caste) {
            this.caste = caste;
        }

        public void setConstituency(String constituency) {
            this.constituency = constituency;
        }

        public void setState(String state) {
            this.state = state;
        }

        public void setEligibleToVote(boolean eligibleToVote) {
            this.eligibleToVote = eligibleToVote;
        }

        public void setBiometricsVerified(boolean biometricsVerified) {
            this.biometricsVerified = biometricsVerified;
        }
    }
}