/*package org.adarsh.jutils.actions;

import java.net.URISyntaxException;

import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jdt.core.IAnnotatable;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.ITypeRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.IAnnotationBinding;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IMemberValuePairBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.NodeFinder;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.QualifiedType;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.StructuralPropertyDescriptor;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.internal.ui.infoviews.JavadocView;
import org.eclipse.jdt.internal.ui.viewsupport.JavaElementLinks;
import org.eclipse.jdt.ui.JavaElementLabels;
import org.eclipse.jdt.ui.SharedASTProvider;
import org.eclipse.jface.text.IRegion;

public class Snippet {
	private static void addAnnotation(StringBuffer buf, IJavaElement element,
	        IAnnotationBinding annotation) throws URISyntaxException {
		String uri = JavaElementLinks.createURI("eclipse-javadoc", annotation
		        .getAnnotationType().getJavaElement());
		buf.append('@');
		addLink(buf, uri, annotation.getName());

		IMemberValuePairBinding[] mvPairs = annotation
		        .getDeclaredMemberValuePairs();
		if (mvPairs.length > 0) {
			buf.append('(');
			for (int j = 0; j < mvPairs.length; j++) {
				if (j > 0) {
					buf.append(JavaElementLabels.COMMA_STRING);
				}
				IMemberValuePairBinding mvPair = mvPairs[j];
				String memberURI = JavaElementLinks.createURI(
				        "eclipse-javadoc", mvPair.getMethodBinding()
				                .getJavaElement());
				addLink(buf, memberURI, mvPair.getName());
				buf.append('=');
				addValue(buf, element, mvPair.getValue());
			}
			buf.append(')');
		}
	}

	private static void addValue(StringBuffer buf, IJavaElement element,
            Object value) {
	    // TODO Auto-generated method stub
	    
    }

	private static void addLink(StringBuffer buf, String memberURI, String name) {
    }



	public static void addAnnotations(StringBuffer buf, IJavaElement element,
	        ITypeRoot editorInputElement, IRegion hoverRegion) {
		if ((element instanceof IAnnotatable)) try {
			String annotationString = getAnnotations(element,
			        editorInputElement, hoverRegion);
			if (annotationString != null) {
				buf.append("<div style='margin-bottom: 5px;'>");
				buf.append(annotationString);
				buf.append("</div>");
			}
		} catch (JavaModelException localJavaModelException) {
			buf.append("<br>");
		} catch (URISyntaxException localURISyntaxException) {
			buf.append("<br>");
		}
	}

	private static String getAnnotations(IJavaElement element,
	        ITypeRoot editorInputElement, IRegion hoverRegion)
	        throws URISyntaxException, JavaModelException {
		if (!(element instanceof IAnnotatable)) {
			return null;
		}
		if (((IAnnotatable) element).getAnnotations().length == 0) {
			return null;
		}

		ASTNode node = getHoveredASTNode(editorInputElement, hoverRegion);
		IBinding binding;
		if (node == null) {
			ASTParser p = ASTParser.newParser(3);
			p.setProject(element.getJavaProject());
			try {
				binding = p
				        .createBindings(new IJavaElement[] { element }, null)[0];
			} catch (OperationCanceledException localOperationCanceledException) {
				IBinding binding1;
				return null;
			}
		} else {
			binding = resolveBinding(node);
		}

		if (binding == null) {
			return null;
		}
		IAnnotationBinding[] annotations = binding.getAnnotations();
		if (annotations.length == 0) {
			return null;
		}
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < annotations.length; i++) {
			addAnnotation(buf, element, annotations[i]);
			buf.append("<br>");
		}

		return buf.toString();
	}

	private static ASTNode getHoveredASTNode(ITypeRoot editorInputElement,
	        IRegion hoverRegion) {
		if (editorInputElement == null) {
			return null;
		}
		CompilationUnit unit = SharedASTProvider.getAST(editorInputElement,
		        SharedASTProvider.WAIT_ACTIVE_ONLY, null);
		if (unit == null) {
			return null;
		}
		return NodeFinder.perform(unit, hoverRegion.getOffset(), hoverRegion
		        .getLength());
	}

	private static IBinding resolveBinding(ASTNode node) {
		if ((node instanceof SimpleName)) {
			SimpleName simpleName = (SimpleName) node;
			StructuralPropertyDescriptor loc = simpleName.getLocationInParent();
			while ((loc == QualifiedType.NAME_PROPERTY) ||
			       (loc == QualifiedName.NAME_PROPERTY) ||
			       (loc == SimpleType.NAME_PROPERTY) ||
			       (loc == ParameterizedType.TYPE_PROPERTY)) {
				node = node.getParent();
				loc = node.getLocationInParent();
			}
			if (loc == ClassInstanceCreation.TYPE_PROPERTY) {
				ClassInstanceCreation cic = (ClassInstanceCreation) node
				        .getParent();
				IMethodBinding constructorBinding = cic
				        .resolveConstructorBinding();
				if (constructorBinding == null) return null;
				ITypeBinding declaringClass = constructorBinding
				        .getDeclaringClass();
				if (!declaringClass.isAnonymous()) return constructorBinding;
				ITypeBinding superTypeDeclaration = declaringClass
				        .getSuperclass().getTypeDeclaration();
				return resolveSuperclassConstructor(superTypeDeclaration,
				        constructorBinding);
			}
			return simpleName.resolveBinding();
		}
		if ((node instanceof SuperConstructorInvocation)) return ((SuperConstructorInvocation) node)
		        .resolveConstructorBinding();
		if ((node instanceof ConstructorInvocation)) {
			return ((ConstructorInvocation) node).resolveConstructorBinding();
		}
		return null;
	}

	private static IBinding resolveSuperclassConstructor(
            ITypeBinding superTypeDeclaration, IMethodBinding constructorBinding) {
	    return null;
    }
}
*/