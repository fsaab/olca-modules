package org.openlca.ecospold2;

import org.jdom2.Element;

public class Property {

	private String id;
	private double amount;
	private String unitId;
	private String name;
	private String unitName;
	private String variableName;
	private String mathematicalRelation;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getVariableName() {
		return variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}

	public String getMathematicalRelation() {
		return mathematicalRelation;
	}

	public void setMathematicalRelation(String mathematicalRelation) {
		this.mathematicalRelation = mathematicalRelation;
	}

	static Property fromXml(Element e) {
		if (e == null)
			return null;
		Property p = new Property();
		p.amount = In.decimal(e.getAttributeValue("amount"));
		p.id = e.getAttributeValue("propertyId");
		p.name = In.childText(e, "name");
		p.unitId = e.getAttributeValue("unitId");
		p.unitName = In.childText(e, "unitName");
		p.mathematicalRelation = e.getAttributeValue("mathematicalRelation");
		p.variableName = e.getAttributeValue("variableName");
		return p;
	}

	Element toXml() {
		Element e = new Element("property", IO.NS);
		e.setAttribute("amount", Double.toString(amount));
		e.setAttribute("propertyId", id);
		e.setAttribute("unitId", unitId);
		if (mathematicalRelation != null)
			e.setAttribute("mathematicalRelation", mathematicalRelation);
		if (variableName != null)
			e.setAttribute("variableName", variableName);
		Out.addChild(e, "name", name);
		Out.addChild(e, "unitName", unitName);
		return e;
	}

}
