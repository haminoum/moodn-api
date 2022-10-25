package com.hero.moodn/*

import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition
import io.github.whiskeysierra.archunit.hexagonal.matching

@AnalyzeClasses(packagesOf = [App::class], importOptions = [DoNotIncludeTests::class])
@Tag("architecture-test")
object ArchitectureTest {


    TODO
     @ArchTest
    val packages: ArchRule =
        Architectures.layeredArchitecture()
            .layer("application").definedBy("..application..")
            .layer("domain.api").definedBy("..domain.api..")
            .layer("domain.spi").definedBy("..domain.spi..")
            .layer("domain.model").definedBy("..domain.model..")
            .layer("domain.logic").definedBy("..domain.logic..")
            .layer("infrastructure").definedBy("..infrastructure..")
            .whereLayer("application").mayNotBeAccessedByAnyLayer()
            .whereLayer("domain.api").mayOnlyBeAccessedByLayers("domain.logic", "application")
            .whereLayer("domain.spi").mayOnlyBeAccessedByLayers("domain.logic", "infrastructure")
            .whereLayer("domain.model").mayOnlyBeAccessedByLayers("domain.api", "domain.spi", "domain.logic", "application", "infrastructure")
            .whereLayer("domain.logic").mayNotBeAccessedByAnyLayer()
            .whereLayer("infrastructure").mayNotBeAccessedByAnyLayer()

    @ArchTest
    val driverAdapters: ArchRule =
        SlicesRuleDefinition.slices()
            .matching("..application.(**)")
            .namingSlices("$1")
            .should().notDependOnEachOther()

    @ArchTest
    val domainApis: ArchRule =
        SlicesRuleDefinition.slices()
            .matching(
                "..application.(**)",
                "..domain.api.(**)",
                "..domain.logic.(**)"
            )
            .namingSlices("$1")
            .should().notDependOnEachOther()

    @ArchTest
    val domainLogics: ArchRule =
        SlicesRuleDefinition.slices()
            .matching("..domain.logic.(**)")
            .namingSlices("$1")
            .should().notDependOnEachOther()

    @ArchTest
    val domainSpis: ArchRule =
        SlicesRuleDefinition.slices()
            .matching(
                "..domain.spi.(**)",
                "..infrastructure.(**)"
            )
            .namingSlices("$1")
            .should().notDependOnEachOther()

    @ArchTest
    val drivenAdapters: ArchRule =
        SlicesRuleDefinition.slices()
            .matching("..infrastructure.(**)")
            .namingSlices("$1")
            .should().notDependOnEachOther()
}
*/
