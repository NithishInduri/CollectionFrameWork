/*
 * package com.insurence.repository;
 * 
 * import static org.assertj.core.api.Assertions.assertThat;
 * 
 * import java.util.Optional;
 * 
 * import org.junit.jupiter.api.Test; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
 * 
 * import com.insurence.entity.PlanCategory;
 * 
 * @DataJpaTest public class PlanCategoryRepositoryTest {
 * 
 * @Autowired private PlanCategoryRepository planCategoryRepository;
 * 
 * @Test public void testSavePlanCategory() { // Create a new PlanCategory
 * object PlanCategory planCategory = new PlanCategory();
 * planCategory.setCategoryName("Health");
 * 
 * // Save the PlanCategory PlanCategory savedPlanCategory =
 * planCategoryRepository.save(planCategory);
 * 
 * // Verify the saved entity assertThat(savedPlanCategory).isNotNull();
 * assertThat(savedPlanCategory.getId()).isGreaterThan(0);
 * assertThat(savedPlanCategory.getCategoryName()).isEqualTo("Health"); }
 * 
 * @Test public void testFindPlanCategoryById() { // Create and save a
 * PlanCategory PlanCategory planCategory = new PlanCategory();
 * planCategory.setCategoryName("Life"); PlanCategory savedPlanCategory =
 * planCategoryRepository.save(planCategory);
 * 
 * // Find the saved entity Optional<PlanCategory> foundPlanCategory =
 * planCategoryRepository.findById(savedPlanCategory.getId());
 * 
 * // Verify the found entity assertThat(foundPlanCategory).isPresent();
 * assertThat(foundPlanCategory.get().getCategoryName()).isEqualTo("Life"); }
 * 
 * @Test public void testDeletePlanCategory() { // Create and save a
 * PlanCategory PlanCategory planCategory = new PlanCategory();
 * planCategory.setCategoryName("Vehicle"); PlanCategory savedPlanCategory =
 * planCategoryRepository.save(planCategory);
 * 
 * // Delete the PlanCategory
 * planCategoryRepository.deleteById(savedPlanCategory.getId());
 * 
 * // Verify the entity is deleted Optional<PlanCategory> deletedPlanCategory =
 * planCategoryRepository.findById(savedPlanCategory.getId());
 * assertThat(deletedPlanCategory).isNotPresent(); } }
 */