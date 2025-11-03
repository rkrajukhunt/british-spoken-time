package com.britishspokentime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Basic Spring Boot application context test.
 */
@SpringBootTest
class BritishSpokenTimeApplicationTests {

  @Autowired
  private ApplicationContext applicationContext;

  @Test
  void contextLoads() {
    assertThat(applicationContext).isNotNull();
  }

  @Test
  void main_shouldStartApplication() {
    // Act & Assert - verifies main method doesn't throw exception
    BritishSpokenTimeApplication.main(new String[]{});
  }

  @Test
  void applicationContext_shouldContainRequiredBeans() {
    // Assert - verify key beans are loaded
    assertThat(applicationContext.containsBean("timeController")).isTrue();
    assertThat(applicationContext.containsBean("britishTimeConverter")).isTrue();
    assertThat(applicationContext.containsBean("numberToWordConverter")).isTrue();
  }

  @Test
  void applicationContext_shouldHaveApplicationName() {
    // Assert
    String applicationName = applicationContext.getEnvironment().getProperty("spring.application.name");
    assertThat(applicationName).isNotNull();
  }

  @Test
  void applicationContext_shouldHaveEnvironment() {
    // Assert
    assertThat(applicationContext.getEnvironment()).isNotNull();
  }

  @Test
  void applicationContext_shouldHaveBeanDefinitions() {
    // Assert
    assertThat(applicationContext.getBeanDefinitionCount()).isGreaterThan(0);
  }

  @Test
  void applicationContext_shouldHaveDisplayName() {
    // Assert
    assertThat(applicationContext.getDisplayName()).isNotNull();
  }

  @Test
  void applicationContext_shouldHaveId() {
    // Assert
    assertThat(applicationContext.getId()).isNotNull();
  }
}
