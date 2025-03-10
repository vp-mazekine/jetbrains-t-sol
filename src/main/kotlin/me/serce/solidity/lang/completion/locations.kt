package me.serce.solidity.lang.completion

import com.intellij.patterns.ElementPattern
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.patterns.StandardPatterns
import com.intellij.psi.PsiElement
import me.serce.solidity.lang.core.SolElementType
import me.serce.solidity.lang.core.SolidityFile
import me.serce.solidity.lang.core.SolidityTokenTypes
import me.serce.solidity.lang.psi.SolFunctionCallArguments
import me.serce.solidity.lang.psi.SolFunctionCallExpression
import me.serce.solidity.lang.psi.SolMapExpression
import me.serce.solidity.lang.psi.SolPragmaDirective
import me.serce.solidity.lang.psi.SolPrimaryExpression
import me.serce.solidity.lang.psi.SolRevertStatement


fun emitStartStatement() =
  psiElement(SolidityTokenTypes.IDENTIFIER)
    .afterLeaf(psiElement(SolidityTokenTypes.EMIT))

fun revertStartStatement() =
  psiElement(SolidityTokenTypes.IDENTIFIER)
    .inside(SolRevertStatement::class.java)
    .afterLeaf("revert")

fun stateVarInsideContract() =
  psiElement(SolidityTokenTypes.IDENTIFIER)
    .inside(psiElement(SolPrimaryExpression::class.java))
    .inside(SolidityFile::class.java)

fun rootDeclaration(): ElementPattern<PsiElement> = psiElement()
  .withSuperParent(2, SolPrimaryExpression::class.java)
  .withSuperParent(3, SolidityFile::class.java)

fun expression(): ElementPattern<PsiElement> =
  StandardPatterns.or(
    functionCall(), primaryExpression(), functionCallArguments()
  )

fun functionCall(): ElementPattern<PsiElement> =
  psiElement(SolidityTokenTypes.IDENTIFIER).inside(SolFunctionCallExpression::class.java)

fun primaryExpression(): ElementPattern<PsiElement> =
  psiElement(SolidityTokenTypes.IDENTIFIER).inside(SolPrimaryExpression::class.java)

fun functionCallArguments(): ElementPattern<PsiElement> =
  psiElement(SolidityTokenTypes.IDENTIFIER).inside(SolFunctionCallArguments::class.java)

fun pragma(): ElementPattern<PsiElement> =
  psiElement(SolidityTokenTypes.IDENTIFIER).inside(SolPragmaDirective::class.java)

fun mapExpression(): ElementPattern<PsiElement> =
  psiElement(SolidityTokenTypes.IDENTIFIER).inside(SolMapExpression::class.java)

