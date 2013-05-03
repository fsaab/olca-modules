package org.openlca.ilcd.util;

import java.util.Collections;
import java.util.List;

import org.openlca.ilcd.commons.Class;
import org.openlca.ilcd.commons.ClassificationInformation;
import org.openlca.ilcd.sources.DataSetInformation;
import org.openlca.ilcd.sources.Source;

public class SourceBag implements IBag<Source> {

	private Source source;

	public SourceBag(Source source) {
		this.source = source;
	}

	@Override
	public Source getValue() {
		return source;
	}

	@Override
	public String getId() {
		DataSetInformation info = getDataSetInformation();
		if (info != null)
			return info.getUUID();
		return null;
	}

	public String getShortName() {
		DataSetInformation info = getDataSetInformation();
		if (info != null)
			return LangString.getLabel(info.getShortName());
		return null;
	}

	public String getComment() {
		DataSetInformation info = getDataSetInformation();
		if (info != null)
			return LangString.getFreeText(info.getSourceDescriptionOrComment());
		return null;
	}

	public String getSourceCitation() {
		DataSetInformation info = getDataSetInformation();
		if (info != null)
			return info.getSourceCitation();
		return null;
	}

	public List<Class> getSortedClasses() {
		DataSetInformation info = getDataSetInformation();
		if (info != null) {
			ClassificationInformation classInfo = info
					.getClassificationInformation();
			return ClassList.sortedList(classInfo);
		}
		return Collections.emptyList();
	}

	private DataSetInformation getDataSetInformation() {
		if (source.getSourceInformation() != null)
			return source.getSourceInformation().getDataSetInformation();
		return null;
	}

}