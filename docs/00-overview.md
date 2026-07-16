# Spring From Scratch

> [1. Stage One: Setting Up Our Spring Container](#1-stage-one-setting-up-our-spring-container)

## 1. Stage One: Setting Up Our Spring Container

> **Stage result:** `Test.main()` can create `OrcaApplicationContext` using `AppConfig.class`. We also defined `@ComponentScan` and `@Component`, but the context cannot scan packages or create beans yet.

1. **Create the program entry point.**

   Create `Test` with a `main()` method. This is where we will start our Spring-like container.

2. **Create the configuration class.**

   Create an empty `AppConfig` class. This class will tell the container how the application should be configured.

3. **Create the application context.**

   Create `OrcaApplicationContext`. Add a `configClass` field and a constructor that accepts the configuration class:

   ```java
   public OrcaApplicationContext(Class configClass) {
       this.configClass = configClass;
   }
   ```

4. **Start the context from `Test`.**

   Return to `Test`, import `OrcaApplicationContext`, and create it by passing `AppConfig.class`:

   ```java
   OrcaApplicationContext applicationContext =
           new OrcaApplicationContext(AppConfig.class);
   ```

   `AppConfig.class` gives the context the configuration class itself so the context can inspect its annotations later.

5. **Create the component-scanning annotation.**

   Create `@ComponentScan` as a runtime annotation for classes. Its `value` stores the package that should be scanned.

6. **Configure the package to scan.**

   Add `@ComponentScan("com.orca.service")` to `AppConfig`. The context will later read this annotation and scan that package.

7. **Create and use the component annotation.**

   Create `@Component` to mark managed classes. Add `@Component("userService")` to `UserService` so its bean name is `userService`.

8. **Create the bean lookup shell.**

   Add `getBean(String beanName)` to `OrcaApplicationContext`, then call `getBean("userService")` from `Test`. The method currently returns `null` because bean creation and storage have not been implemented.

> **Current status:** We have created the main container class, its configuration, the two annotations, and an example component. Package scanning, component discovery, bean creation, bean storage, bean retrieval, and dependency injection are not implemented yet.
