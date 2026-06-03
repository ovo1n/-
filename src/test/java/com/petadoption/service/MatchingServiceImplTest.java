package com.petadoption.service;

import com.petadoption.model.entity.Pet;
import com.petadoption.model.entity.User;
import com.petadoption.service.impl.MatchingServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MatchingServiceImplTest {

    private MatchingServiceImpl service = new MatchingServiceImpl();

    private User makeUser(Integer residenceType, Integer petExperience, Integer familySize,
                          Integer agreeScientificCare, Integer hasBalcony) {
        User u = new User();
        u.setResidenceType(residenceType);
        u.setPetExperience(petExperience);
        u.setFamilySize(familySize);
        u.setAgreeScientificCare(agreeScientificCare);
        u.setHasBalcony(hasBalcony);
        return u;
    }

    private Pet makePet(Integer size, Integer breedingDifficulty, Integer healthStatus,
                        Integer isVaccinated, Integer isNeutered, String personality) {
        Pet p = new Pet();
        p.setSize(size);
        p.setBreedingDifficulty(breedingDifficulty);
        p.setHealthStatus(healthStatus);
        p.setIsVaccinated(isVaccinated);
        p.setIsNeutered(isNeutered);
        p.setPersonality(personality);
        return p;
    }

    @Test
    public void testCalculateMatchingScore_HighMatch() {
        User user = makeUser(2, 4, 4, 1, 1); // good conditions
        Pet pet = makePet(1, 1, 1, 1, 1, "温和 友好");

        Double score = service.calculateMatchingScore(user, pet);
        System.out.println("HighMatch score=" + score);
        Assertions.assertNotNull(score);
        Assertions.assertTrue(score >= 85.0, "Expected high match score >=85 but was " + score);
    }

    @Test
    public void testCalculateMatchingScore_LowMatch() {
        User user = makeUser(1, 1, 1, 0, 0); // poor conditions
        Pet pet = makePet(3, 3, 3, 0, 0, "倔强");

        Double score = service.calculateMatchingScore(user, pet);
        System.out.println("LowMatch score=" + score);
        Assertions.assertNotNull(score);
        Assertions.assertTrue(score <= 60.0, "Expected low match score <=60 but was " + score);
    }
}
