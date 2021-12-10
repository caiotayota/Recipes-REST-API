package com.caiotayota.recipes.service;

import com.caiotayota.recipes.exceptions.RecipeNotFoundException;
import com.caiotayota.recipes.exceptions.UserNotAllowedException;
import com.caiotayota.recipes.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.caiotayota.recipes.repository.RecipeRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepo;
    
    @Autowired
    public RecipeService(RecipeRepository recipeRepo) {
        this.recipeRepo = recipeRepo;
    }
    
    public Recipe getRecipeById(long id) {
        return recipeRepo.findById(id)
                .orElseThrow(RecipeNotFoundException::new);
    }

    public List<Recipe> getRecipeByCategory(String category) {
        List<Recipe> recipes = recipeRepo.findByCategoryIgnoreCaseOrderByDateDesc(category);
        return recipes != null ? recipes : Collections.emptyList();
    }

    public List<Recipe> getRecipeByName(String name) {
        List<Recipe> recipes = recipeRepo.findByNameIgnoreCaseContainingOrderByDateDesc(name);
        return recipes != null ? recipes : Collections.emptyList();
    }

    public long addRecipe(Recipe recipe) {
        recipe.setAuthor(getLoggedUser());
        return recipeRepo.save(recipe).getId();
    }

    public void updateRecipeById(long id, Recipe recipeUpdated) {
        Optional<Recipe> recipeFromDb = recipeRepo.findById(id);
        
        if (recipeFromDb.isEmpty()) throw new RecipeNotFoundException();
        
        if (!recipeFromDb.get().getAuthor().equals(getLoggedUser())) {
            throw new UserNotAllowedException();
        }
        
        recipeFromDb.get().setName(recipeUpdated.getName());
        recipeFromDb.get().setDescription(recipeUpdated.getDescription());
        recipeFromDb.get().setIngredients(recipeUpdated.getIngredients());
        recipeFromDb.get().setDirections(recipeUpdated.getDirections());
        recipeFromDb.get().setCategory(recipeUpdated.getCategory());
        recipeRepo.save(recipeFromDb.get());
    }

    public void deleteRecipeById(long id) {
        Optional<Recipe> recipeFromDb = recipeRepo.findById(id);
        if (recipeFromDb.isEmpty()) throw new RecipeNotFoundException();
        
        if (!recipeFromDb.get().getAuthor().equals(getLoggedUser())) {
            throw new UserNotAllowedException();
        }
        
        recipeRepo.deleteById(id);
    }
    
    public String getLoggedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getPrincipal().toString();
    }
}