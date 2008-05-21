/*******************************************************************************
 * Copyright (c) 2007 Exadel, Inc. and Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Exadel, Inc. and Red Hat, Inc. - initial API and implementation
 ******************************************************************************/ 
package org.jboss.tools.jsf.vpe.richfaces.template;

import java.util.List;

import org.jboss.tools.jsf.vpe.richfaces.ComponentUtil;
import org.jboss.tools.jsf.vpe.richfaces.HtmlComponentUtil;
import org.jboss.tools.vpe.editor.context.VpePageContext;
import org.jboss.tools.vpe.editor.template.VpeAbstractTemplate;
import org.jboss.tools.vpe.editor.template.VpeChildrenInfo;
import org.jboss.tools.vpe.editor.template.VpeCreationData;
import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsIDOMNodeList;
import org.mozilla.xpcom.XPCOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class RichFacesPanelTemplate extends VpeAbstractTemplate {


	public VpeCreationData create(VpePageContext pageContext, Node sourceNode, nsIDOMDocument visualDocument) {

	   
		Element sourceElement = (Element)sourceNode;

		nsIDOMElement div = visualDocument.createElement("div");

		
		VpeCreationData creationData = new VpeCreationData(div);
		

		ComponentUtil.setCSSLink(pageContext, "panel/panel.css", "richFacesPanel");
		String styleClass = sourceElement.getAttribute("styleClass");
		div.setAttribute("class", "dr-pnl rich-panel " + (styleClass==null?"":styleClass));
		String style = sourceElement.getAttribute("style");
		if(style!=null && style.length()>0) {
			div.setAttribute("style", style);
		}

		// Encode Header
		Node header = ComponentUtil.getFacet(sourceElement, "header", true);
		if(header!=null) {
		    	nsIDOMElement headerDiv = visualDocument.createElement("div");
			div.appendChild(headerDiv);
			String headerClass = sourceElement.getAttribute("headerClass");
			headerDiv.setAttribute("class", "dr-pnl-h rich-panel-header " + (headerClass==null?"":headerClass));
			headerDiv.setAttribute("style", ComponentUtil.getHeaderBackgoundImgStyle());

			VpeChildrenInfo headerInfo = new VpeChildrenInfo(headerDiv);
			headerInfo.addSourceChild(header);
			creationData.addChildrenInfo(headerInfo);
		}

		// Encode Body
		nsIDOMElement bodyDiv = visualDocument.createElement("div");
		div.appendChild(bodyDiv);
		String bodyClass = sourceElement.getAttribute("bodyClass");
		bodyDiv.setAttribute("class", "dr-pnl-b rich-panel-body " + (bodyClass==null?"":bodyClass));

		List<Node> children = ComponentUtil.getChildren(sourceElement, true);
		VpeChildrenInfo bodyInfo = new VpeChildrenInfo(bodyDiv);
		for (Node child : children) {
			bodyInfo.addSourceChild(child);
		}
		creationData.addChildrenInfo(bodyInfo);

		return creationData;
	}

	/* (non-Javadoc)
	 * @see org.jboss.tools.vpe.editor.template.VpeAbstractTemplate#validate(org.jboss.tools.vpe.editor.context.VpePageContext, org.w3c.dom.Node, org.mozilla.interfaces.nsIDOMDocument, org.jboss.tools.vpe.editor.template.VpeCreationData)
	 */
	@Override
	public void validate(VpePageContext pageContext, Node sourceNode,
			nsIDOMDocument visualDocument, VpeCreationData data) {
		// FIX for JBIDE-1213 (Max Areshkau)
		if(data.getNode()!=null) {
			String bodyClass = ((Element)sourceNode).getAttribute("bodyClass");
			applyStylesToTable(data.getNode(), bodyClass);
		}
	}
	
    private void applyStylesToTable(nsIDOMNode node,String sourceClass) {

    	try {
    	    nsIDOMNodeList list = node.getChildNodes();
    	    nsIDOMElement element = (nsIDOMElement) node
    		    .queryInterface(nsIDOMElement.NS_IDOMELEMENT_IID);

    	    if (node.getNodeName().equalsIgnoreCase(
    		    HtmlComponentUtil.HTML_TAG_TABLE)){
    	    	element.setAttribute("class", "dr-pnl-b rich-panel-body " + (sourceClass==null?"":sourceClass));
    	    }
    	    for (int i = 0; i < list.getLength(); i++) {
    	    applyStylesToTable(list.item(i),sourceClass);
    	    }
    	} catch (XPCOMException e) {
    	    //Ignore
    	    return;
    	}
        }
}