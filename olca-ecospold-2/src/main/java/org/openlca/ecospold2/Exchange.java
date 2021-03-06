package org.openlca.ecospold2;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.Element;

public abstract class Exchange {

	public String id;
	public String unitId;

	/**
	 * Amount is a reference type because it is optional as exchanges are also
	 * used in the master data
	 */
	public Double amount;
	public String name;
	public String unitName;
	public String variableName;
	public String mathematicalRelation;
	public String casNumber;
	public String comment;
	public Uncertainty uncertainty;
	public Integer outputGroup;
	public Integer inputGroup;

	public final List<Property> properties = new ArrayList<>();

	protected void readValues(Element element) {
		this.amount = In.optionalDecimal(element.getAttributeValue("amount"));
		this.id = element.getAttributeValue("id");
		this.mathematicalRelation = element
				.getAttributeValue("mathematicalRelation");
		this.variableName = element.getAttributeValue("variableName");
		this.name = In.childText(element, "name");
		this.unitName = In.childText(element, "unitName");
		this.comment = In.childText(element, "comment");
		this.unitId = element.getAttributeValue("unitId");
		this.uncertainty = Uncertainty
				.fromXml(In.child(element, "uncertainty"));
		this.casNumber = element.getAttributeValue("casNumber");
		List<Element> propElems = In.childs(element, "property");
		for (Element propElem : propElems) {
			Property property = Property.fromXml(propElem);
			if (property != null)
				properties.add(property);
		}

		String inGroup = In.childText(element, "inputGroup");
		if (inGroup != null)
			this.inputGroup = In.integer(inGroup);
		else {
			String outGroup = In.childText(element, "outputGroup");
			this.outputGroup = In.integer(outGroup);
		}
	}

	protected void writeValues(Element element) {
		if (id != null)
			element.setAttribute("id", id);
		if (unitId != null)
			element.setAttribute("unitId", unitId);
		if (amount != null)
			element.setAttribute("amount", amount.toString());
		if (mathematicalRelation != null)
			element.setAttribute("mathematicalRelation", mathematicalRelation);
		if (variableName != null)
			element.setAttribute("variableName", variableName);
		if (casNumber != null)
			element.setAttribute("casNumber", casNumber);
		if (name != null)
			Out.addChild(element, "name", name);
		if (unitName != null)
			Out.addChild(element, "unitName", unitName);
		if (comment != null)
			Out.addChild(element, "comment", comment);
		if (uncertainty != null)
			element.addContent(uncertainty.toXml());
		for (Property property : properties)
			element.addContent(property.toXml());
	}

	protected void writeInputOutputGroup(Element element) {
		if (inputGroup != null)
			Out.addChild(element, "inputGroup").setText(inputGroup.toString());
		else if (outputGroup != null)
			Out.addChild(element, "outputGroup")
					.setText(outputGroup.toString());
	}

}
