/*
 * package com.insurence.repository;
 * 
 * import static org.junit.jupiter.api.Assertions.assertEquals; import static
 * org.mockito.Mockito.times; import static org.mockito.Mockito.verify; import
 * static org.mockito.Mockito.when;
 * 
 * import java.util.Arrays; import java.util.List; import java.util.Optional;
 * 
 * import org.junit.jupiter.api.BeforeEach; import org.junit.jupiter.api.Test;
 * import org.mockito.Mock; import org.mockito.MockitoAnnotations;
 * 
 * import com.insurence.entity.Plan;
 * 
 * public class PlanRepositoryTest {
 * 
 * @Mock private PlanRepository planRepository;
 * 
 * private Plan plan1; private Plan plan2;
 * 
 * @BeforeEach void setUp() { // Initialize Mockito annotations
 * MockitoAnnotations.openMocks(this);
 * 
 * // Create sample plan objects plan1 = new Plan(); plan1.setPlanId(1);
 * plan1.setPlanName("Health Plan A");
 * plan1.setPlanDescription("Description A");
 * 
 * plan2 = new Plan(); plan2.setPlanId(2); plan2.setPlanName("Health Plan B");
 * plan2.setPlanDescription("Description B"); }
 * 
 * @Test public void testFindById() { // Arrange: Mock the repository's findById
 * behavior when(planRepository.findById(1)).thenReturn(Optional.of(plan1));
 * 
 * // Act: Call the repository's method Optional<Plan> result =
 * planRepository.findById(1);
 * 
 * // Assert: Check that the result is as expected assertEquals("Health Plan A",
 * result.get().getPlanName()); assertEquals("Description A",
 * result.get().getPlanDescription());
 * 
 * // Verify that the method was called once verify(planRepository,
 * times(1)).findById(1); }
 * 
 * @Test public void testSavePlan() { // Arrange: Mock the repository's save
 * behavior when(planRepository.save(plan1)).thenReturn(plan1);
 * 
 * // Act: Call the save method Plan savedPlan = planRepository.save(plan1);
 * 
 * // Assert: Check that the saved plan is correct assertEquals("Health Plan A",
 * savedPlan.getPlanName());
 * 
 * // Verify that the save method was called verify(planRepository,
 * times(1)).save(plan1); }
 * 
 * @Test public void testFindAllPlans() { // Arrange: Mock the findAll behavior
 * List<Plan> planList = Arrays.asList(plan1, plan2);
 * when(planRepository.findAll()).thenReturn(planList);
 * 
 * // Act: Call the repository's findAll method List<Plan> result =
 * planRepository.findAll();
 * 
 * // Assert: Check the number of plans retrieved assertEquals(2,
 * result.size()); assertEquals("Health Plan A", result.get(0).getPlanName());
 * assertEquals("Health Plan B", result.get(1).getPlanName());
 * 
 * // Verify that the findAll method was called once verify(planRepository,
 * times(1)).findAll(); }
 * 
 * @Test public void testDeleteById() { // Act: Call the deleteById method
 * planRepository.deleteById(1);
 * 
 * // Verify that the deleteById method was called verify(planRepository,
 * times(1)).deleteById(1); } }
 */