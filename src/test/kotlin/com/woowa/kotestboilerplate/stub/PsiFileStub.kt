package com.woowa.kotestboilerplate.stub

import com.intellij.psi.PsiFile
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Key
import com.intellij.openapi.util.TextRange
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiFileSystemItem
import com.intellij.psi.PsiManager
import com.intellij.psi.PsiReference
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.PsiElementProcessor
import com.intellij.psi.search.SearchScope
import javax.swing.Icon
import com.intellij.lang.FileASTNode
import com.intellij.lang.Language
import com.intellij.navigation.ItemPresentation

class PsiFileStub : PsiFile {
    override fun <T : Any?> getUserData(key: Key<T>): T? {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> putUserData(key: Key<T>, value: T?) {
        TODO("Not yet implemented")
    }

    override fun getIcon(flags: Int): Icon {
        TODO("Not yet implemented")
    }

    override fun getProject(): Project {
        TODO("Not yet implemented")
    }

    override fun getLanguage(): Language {
        TODO("Not yet implemented")
    }

    override fun getManager(): PsiManager {
        TODO("Not yet implemented")
    }

    override fun getChildren(): Array<PsiElement> {
        TODO("Not yet implemented")
    }

    override fun getParent(): PsiDirectory? {
        TODO("Not yet implemented")
    }

    override fun getFirstChild(): PsiElement {
        TODO("Not yet implemented")
    }

    override fun getLastChild(): PsiElement {
        TODO("Not yet implemented")
    }

    override fun getNextSibling(): PsiElement {
        TODO("Not yet implemented")
    }

    override fun getPrevSibling(): PsiElement {
        TODO("Not yet implemented")
    }

    override fun getContainingFile(): PsiFile {
        TODO("Not yet implemented")
    }

    override fun getTextRange(): TextRange {
        TODO("Not yet implemented")
    }

    override fun getStartOffsetInParent(): Int {
        TODO("Not yet implemented")
    }

    override fun getTextLength(): Int {
        TODO("Not yet implemented")
    }

    override fun findElementAt(offset: Int): PsiElement? {
        TODO("Not yet implemented")
    }

    override fun findReferenceAt(offset: Int): PsiReference? {
        TODO("Not yet implemented")
    }

    override fun getTextOffset(): Int {
        TODO("Not yet implemented")
    }

    override fun getText(): String {
        TODO("Not yet implemented")
    }

    override fun textToCharArray(): CharArray {
        TODO("Not yet implemented")
    }

    override fun getNavigationElement(): PsiElement {
        TODO("Not yet implemented")
    }

    override fun getOriginalElement(): PsiElement {
        TODO("Not yet implemented")
    }

    override fun textMatches(text: CharSequence): Boolean {
        TODO("Not yet implemented")
    }

    override fun textMatches(element: PsiElement): Boolean {
        TODO("Not yet implemented")
    }

    override fun textContains(c: Char): Boolean {
        TODO("Not yet implemented")
    }

    override fun accept(visitor: PsiElementVisitor) {
        TODO("Not yet implemented")
    }

    override fun acceptChildren(visitor: PsiElementVisitor) {
        TODO("Not yet implemented")
    }

    override fun copy(): PsiElement {
        TODO("Not yet implemented")
    }

    override fun add(element: PsiElement): PsiElement {
        TODO("Not yet implemented")
    }

    override fun addBefore(element: PsiElement, anchor: PsiElement?): PsiElement {
        TODO("Not yet implemented")
    }

    override fun addAfter(element: PsiElement, anchor: PsiElement?): PsiElement {
        TODO("Not yet implemented")
    }

    override fun checkAdd(element: PsiElement) {
        TODO("Not yet implemented")
    }

    override fun addRange(first: PsiElement?, last: PsiElement?): PsiElement {
        TODO("Not yet implemented")
    }

    override fun addRangeBefore(first: PsiElement, last: PsiElement, anchor: PsiElement?): PsiElement {
        TODO("Not yet implemented")
    }

    override fun addRangeAfter(first: PsiElement?, last: PsiElement?, anchor: PsiElement?): PsiElement {
        TODO("Not yet implemented")
    }

    override fun delete() {
        TODO("Not yet implemented")
    }

    override fun checkDelete() {
        TODO("Not yet implemented")
    }

    override fun deleteChildRange(first: PsiElement?, last: PsiElement?) {
        TODO("Not yet implemented")
    }

    override fun replace(newElement: PsiElement): PsiElement {
        TODO("Not yet implemented")
    }

    override fun isValid(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isWritable(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getReference(): PsiReference? {
        TODO("Not yet implemented")
    }

    override fun getReferences(): Array<PsiReference> {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> getCopyableUserData(key: Key<T>): T? {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> putCopyableUserData(key: Key<T>, value: T?) {
        TODO("Not yet implemented")
    }

    override fun processDeclarations(
        processor: PsiScopeProcessor,
        state: ResolveState,
        lastParent: PsiElement?,
        place: PsiElement
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun getContext(): PsiElement? {
        TODO("Not yet implemented")
    }

    override fun isPhysical(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getResolveScope(): GlobalSearchScope {
        TODO("Not yet implemented")
    }

    override fun getUseScope(): SearchScope {
        TODO("Not yet implemented")
    }

    override fun getNode(): FileASTNode {
        TODO("Not yet implemented")
    }

    override fun isEquivalentTo(another: PsiElement?): Boolean {
        TODO("Not yet implemented")
    }

    override fun getName(): String {
        TODO("Not yet implemented")
    }

    override fun setName(name: String): PsiElement {
        TODO("Not yet implemented")
    }

    override fun checkSetName(name: String?) {
        TODO("Not yet implemented")
    }

    override fun navigate(requestFocus: Boolean) {
        TODO("Not yet implemented")
    }

    override fun canNavigate(): Boolean {
        TODO("Not yet implemented")
    }

    override fun canNavigateToSource(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getPresentation(): ItemPresentation? {
        TODO("Not yet implemented")
    }

    override fun isDirectory(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getVirtualFile(): VirtualFile {
        TODO("Not yet implemented")
    }

    override fun processChildren(processor: PsiElementProcessor<in PsiFileSystemItem>): Boolean {
        TODO("Not yet implemented")
    }

    override fun getContainingDirectory(): PsiDirectory {
        TODO("Not yet implemented")
    }

    override fun getModificationStamp(): Long {
        TODO("Not yet implemented")
    }

    override fun getOriginalFile(): PsiFile {
        TODO("Not yet implemented")
    }

    override fun getFileType(): FileType {
        TODO("Not yet implemented")
    }

    override fun getPsiRoots(): Array<PsiFile> {
        TODO("Not yet implemented")
    }

    override fun getViewProvider(): FileViewProvider {
        TODO("Not yet implemented")
    }

    override fun subtreeChanged() {
        TODO("Not yet implemented")
    }
}