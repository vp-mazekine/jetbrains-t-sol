package me.serce.solidity.lang.core.resolve

import me.serce.solidity.lang.psi.SolNamedElement

class SolImportResolveTest : SolResolveTestBase() {
  fun testImportPathResolve() {
    val file1 = InlineFile(
      code = "contract a {}",
      name = "Ownable.tsol"
    )

    InlineFile("""
          import "./Ownable.tsol";
                      //^

          contract b {}
    """)

    val (refElement) = findElementAndDataInEditor<SolNamedElement>("^")

    val resolved = checkNotNull(refElement.reference?.resolve()) {
      "Failed to resolve ${refElement.text}"
    }

    assertEquals(file1.name, resolved.containingFile.name)
  }

  fun testImportPathResolveNpm() {
    val file1 = myFixture.configureByFile("node_modules/util/contracts/TestImport.tsol")
    myFixture.configureByFile("contracts/ImportUsage.tsol")

    val (refElement) = findElementAndDataInEditor<SolNamedElement>("^")

    val resolved = checkNotNull(refElement.reference?.resolve()) {
      "Failed to resolve ${refElement.text}"
    }

    assertEquals(file1.name, resolved.containingFile.name)
  }

  fun testImportPathResolveEthPM() {
    val file1 = myFixture.configureByFile("installed_contracts/util/contracts/TestImport.tsol")
    myFixture.configureByFile("contracts/ImportUsageEthPM.tsol")

    val (refElement) = findElementAndDataInEditor<SolNamedElement>("^")

    val resolved = checkNotNull(refElement.reference?.resolve()) {
      "Failed to resolve ${refElement.text}"
    }

    assertEquals(file1.name, resolved.containingFile.name)
  }

  fun testImportPathResolveFoundry() {
    val file1 = myFixture.configureByFile("lib/util/src/TestImport.tsol")
    myFixture.configureByFile("contracts/ImportUsageFoundry.tsol")

    val (refElement) = findElementAndDataInEditor<SolNamedElement>("^")

    val resolved = checkNotNull(refElement.reference?.resolve()) {
      "Failed to resolve ${refElement.text}"
    }

    assertEquals(file1.name, resolved.containingFile.name)
  }

  fun testResolveFrom() {
    val file1 = InlineFile(
      code = "contract A {}",
      name = "Ownable.tsol"
    )

    InlineFile("""
          import A from "./Ownable.tsol";
               //^

          contract b {}
    """)

    val (refElement) = findElementAndDataInEditor<SolNamedElement>("^")

    val resolved = checkNotNull(refElement.reference?.resolve()) {
      "Failed to resolve ${refElement.text}"
    }

    assertEquals(file1.name, resolved.containingFile.name)
  }

  override fun getTestDataPath() = "src/test/resources/fixtures/import/"
}
