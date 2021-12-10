package com.caiotayota.recipes.controller;

import com.caiotayota.recipes.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.caiotayota.recipes.service.RecipeService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recipe")
class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}")
    public Recipe getRecipeById(@PathVariable long id) {
        return recipeService.getRecipeById(id);
    }

    @GetMapping(value = "/search/", params = "category")
    public List<Recipe> getRecipeByCategory(@RequestParam @Valid String category) {
        return recipeService.getRecipeByCategory(category);
    }

    @GetMapping(value = "/search/", params = "name")
    public List<Recipe> getRecipeByName(@RequestParam @Valid String name) {
        return recipeService.getRecipeByName(name);
    }

    @PostMapping("/new")
    public Map<String, Long> createNewRecipe(@RequestBody @Valid Recipe recipe) {
        long id = recipeService.addRecipe(recipe);
        return Map.of("id", id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRecipe(@PathVariable long id, @RequestBody @Valid Recipe recipe) {
        recipeService.updateRecipeById(id, recipe);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecipeById(@PathVariable long id) {
        recipeService.deleteRecipeById(id);
    }

}


