package com.enigma.food.repository;

import com.enigma.food.model.Recipes;

import com.enigma.food.repository.RecipesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class RecipesRepositoryTest {
    @Autowired
    private RecipesRepository repository;

    @Test
    public void RecipesRepository_Create_ReturnCreatedRecipes(){
        Recipes recipes = Recipes.builder()
                .name("recipe 1")
                .description("Ikan, Minyak")
                .method("masak ikan terlebih dahulu")
                .price(5000)
                .imageUrl("https://akcdn.detik.net.id/visual/2020/03/20/48ffe775-d603-4b25-9440-270906e01b9e_43.jpeg?w=720&q=90")
                .build();
        repository.save(recipes);

        Recipes foundRecipes = repository.findById(recipes.getId()).orElse(null);

        assertThat(foundRecipes).isNotNull();
    }
    @Test
    public void RecipesRepository_GetAll_ReturnAllCustomer(){
        Recipes recipes1 = Recipes.builder()
                .name("recipe 1")
                .description("Ikan, Minyak")
                .method("masak ikan terlebih dahulu")
                .price(5000)
                .imageUrl("https://akcdn.detik.net.id/visual/2020/03/20/48ffe775-d603-4b25-9440-270906e01b9e_43.jpeg?w=720&q=90")
                .build();
        Recipes recipes2 = Recipes.builder()
                .name("recipe 2")
                .description("Ikan, Minyak")
                .method("masak ikan terlebih dahulu")
                .price(5000)
                .imageUrl("https://akcdn.detik.net.id/visual/2020/03/20/48ffe775-d603-4b25-9440-270906e01b9e_43.jpeg?w=720&q=90")
                .build();
        repository.save(recipes1);
        repository.save(recipes2);

        List<Recipes> recipes = repository.findAll();
        assertThat(recipes).isNotNull();
        assertThat(recipes).hasSize(2);
    }
    @Test
    public void RecipesRepository_GetById_ReturnAllRecipes() {
        Recipes recipes = Recipes.builder()
                .name("recipe 1")
                .description("Ikan, Minyak")
                .method("masak ikan terlebih dahulu")
                .price(5000)
                .imageUrl("https://akcdn.detik.net.id/visual/2020/03/20/48ffe775-d603-4b25-9440-270906e01b9e_43.jpeg?w=720&q=90")
                .build();
        repository.save(recipes);

        Recipes recipes1 = repository.findById(recipes.getId()).orElse(null);
        assertThat(recipes1).isNotNull();
    }
    @Test
    public void RecipesRepository_UpdateById_ReturnUpdatedRecipes() {
        Recipes recipes = Recipes.builder()
                .name("recipe 1")
                .description("Ikan, Minyak")
                .method("masak ikan terlebih dahulu")
                .price(5000)
                .imageUrl("https://akcdn.detik.net.id/visual/2020/03/20/48ffe775-d603-4b25-9440-270906e01b9e_43.jpeg?w=720&q=90")
                .build();
        repository.save(recipes);
        Recipes foundrecipes =repository.findById(recipes.getId()).orElse(null);
        foundrecipes.setName("recipes 2");
        foundrecipes.setDescription("Ayam, Minyak");
        foundrecipes.setMethod("Masak Ayam terlebih dahulu");
        foundrecipes.setPrice(4000);
        foundrecipes.setImageUrl("https://d1vbn70lmn1nqe.cloudfront.net/prod/wp-content/uploads/2023/07/28042323/Resep-Olahan-Ayam-yang-Lebih-Padat-Nutrisi-dan-Bergizi-.jpg.webp");
        Recipes updateRecipes = repository.save(foundrecipes);

        assertThat(updateRecipes).isNotNull();
        assertThat(updateRecipes.getName()).isEqualTo("recipes 2");
        assertThat(updateRecipes.getDescription()).isEqualTo("Ayam, Minyak");
        assertThat(updateRecipes.getMethod()).isEqualTo("Masak Ayam terlebih dahulu");
        assertThat(updateRecipes.getPrice()).isEqualTo(4000);
        assertThat(updateRecipes.getImageUrl()).isEqualTo("https://d1vbn70lmn1nqe.cloudfront.net/prod/wp-content/uploads/2023/07/28042323/Resep-Olahan-Ayam-yang-Lebih-Padat-Nutrisi-dan-Bergizi-.jpg.webp");
    }
    @Test
    public void RecipesRepository_DeleteById_ReturnNull() {
        Recipes recipes = Recipes.builder()
                .name("recipe 1")
                .description("Ikan, Minyak")
                .method("masak ikan terlebih dahulu")
                .price(5000)
                .imageUrl("https://akcdn.detik.net.id/visual/2020/03/20/48ffe775-d603-4b25-9440-270906e01b9e_43.jpeg?w=720&q=90")
                .build();
        repository.save(recipes);
        repository.deleteById(recipes.getId());
        Recipes foundRecipes = repository.findById(recipes.getId()).orElse(null);
        assertThat(foundRecipes).isNull();
    }

}
