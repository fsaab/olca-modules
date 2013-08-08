package org.openlca.io;

import org.openlca.core.model.Category;
import org.openlca.util.Strings;

/**
 * A simple pair of a parent category and a sub-category. Often this is
 * appropriate for the display of elementary flow categories (= compartment and
 * sub-compartment.
 */
public class CategoryPair implements Comparable<CategoryPair> {

	private String category;
	private String subCategory;

	public CategoryPair(String category, String subCategory) {
		initVals(category, subCategory);
	}

	public CategoryPair(Category category) {
		if (category == null)
			initVals(null, null);
		else if (category.getParentCategory() == null)
			initVals(category.getName(), null);
		else
			initVals(category.getParentCategory().getName(), category.getName());
	}

	private void initVals(String category, String subCategory) {
		this.category = category == null ? "" : category;
		this.subCategory = subCategory == null ? "" : subCategory;
	}

	public String getCategory() {
		return category;
	}

	public String getSubCategory() {
		return subCategory;
	}

	@Override
	public int compareTo(CategoryPair other) {
		if (other == null)
			return 1;
		int c = Strings.compare(this.category, other.category);
		if (c != 0)
			return c;
		return Strings.compare(this.subCategory, other.subCategory);
	}

}