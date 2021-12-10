package com.caiotayota.recipes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "Name must not be blank.")
    private String name;

    @NotBlank(message = "Description must not be blank.")
    private String description;

    @NotBlank(message = "Category must not be blank.")
    private String category;

    @ElementCollection
    @NotEmpty(message = "Recipe must contain at least one ingredient.")
    private List<String> ingredients;

    @ElementCollection
    @NotEmpty(message = "Recipe must contain at least one direction.")
    private List<String> directions;
    
    @JsonIgnore
    private String author;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    Date date;

}
