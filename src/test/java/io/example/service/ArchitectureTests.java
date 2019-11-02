package io.example.service;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.Test;

import javax.enterprise.inject.Produces;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

public class ArchitectureTests {

    private final String BASE_PACKAGE = this.getClass().getPackage().getName();

    @Test
    public void onion_architecture_layers_are_respected() {
        JavaClasses importedClasses = new ClassFileImporter()
                .withImportOption(new ImportOption.DoNotIncludeTests()).importPackages(BASE_PACKAGE);
        ArchRule rule = layeredArchitecture()
                .layer("api").definedBy(BASE_PACKAGE + ".api..")
                .layer("repository").definedBy(BASE_PACKAGE + ".repository..")
                .layer("service").definedBy(BASE_PACKAGE + ".service..")
                .layer("adapter").definedBy(BASE_PACKAGE + ".adapter..")
                .whereLayer("api").mayNotBeAccessedByAnyLayer()
                .whereLayer("service").mayOnlyBeAccessedByLayers("api", "adapter", "repository")
                .whereLayer("repository").mayOnlyBeAccessedByLayers("service")
                .whereLayer("adapter").mayOnlyBeAccessedByLayers("service");
        rule.check(importedClasses);
    }

    @Test
    public void adapter_always_returns_completable_futures_for_public_methods() {
        JavaClasses importedClasses = new ClassFileImporter()
                .withImportOption(new ImportOption.DoNotIncludeTests()).importPackages(BASE_PACKAGE);
        ArchRule rule = methods()
                .that()
                .areDeclaredInClassesThat().resideInAPackage(BASE_PACKAGE + ".adapter..")
                .and().arePublic()
                .and().areDeclaredInClassesThat().haveSimpleNameEndingWith("Adapter")
                .should().haveRawReturnType(CompletableFuture.class);
        rule.check(importedClasses);
    }

    @Test
    public void service_always_returns_streams_for_public_methods() {
        JavaClasses importedClasses = new ClassFileImporter()
                .withImportOption(new ImportOption.DoNotIncludeTests()).importPackages(BASE_PACKAGE);
        ArchRule rule = methods()
                .that()
                .areDeclaredInClassesThat().resideInAPackage(BASE_PACKAGE + ".service..")
                .and().arePublic()
                .and().areDeclaredInClassesThat().haveSimpleNameEndingWith("Service")
                .should().haveRawReturnType(Stream.class);
        rule.check(importedClasses);
    }
}