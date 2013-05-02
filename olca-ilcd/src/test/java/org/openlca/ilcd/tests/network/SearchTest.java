package org.openlca.ilcd.tests.network;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openlca.ilcd.contacts.Contact;
import org.openlca.ilcd.descriptors.ContactDescriptor;
import org.openlca.ilcd.descriptors.DescriptorList;
import org.openlca.ilcd.descriptors.FlowDescriptor;
import org.openlca.ilcd.descriptors.FlowPropertyDescriptor;
import org.openlca.ilcd.descriptors.ProcessDescriptor;
import org.openlca.ilcd.descriptors.SourceDescriptor;
import org.openlca.ilcd.descriptors.UnitGroupDescriptor;
import org.openlca.ilcd.flowproperties.FlowProperty;
import org.openlca.ilcd.flows.Flow;
import org.openlca.ilcd.io.NetworkClient;
import org.openlca.ilcd.processes.Process;
import org.openlca.ilcd.sources.Source;
import org.openlca.ilcd.units.UnitGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchTest {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	private String baseUri = "http://localhost:8080/soda4LCA/resource";
	private NetworkClient client = new NetworkClient(baseUri);

	@Before
	public void setUp() throws Exception {
		DataSets.upload();
	}

	@Test
	public void testSearchProcess() throws Exception {
		String name = "ABS";
		log.debug("test: search process with name '{}'", name);
		DescriptorList list = client.search(Process.class, name);
		assertTrue(list.getDescriptors().size() > 0);
		for (Object obj : list.getDescriptors()) {
			assertTrue(obj instanceof ProcessDescriptor);
			ProcessDescriptor descriptor = (ProcessDescriptor) obj;
			log.debug("process found: id={}, name={}", descriptor.getUuid(),
					descriptor.getName().getValue());
		}
	}

	@Test
	public void testSearchFlow() throws Exception {
		String name = "glycidol";
		log.debug("test: search flow with name '{}'", name);
		DescriptorList list = client.search(Flow.class, name);
		assertTrue(list.getDescriptors().size() > 0);
		for (Object obj : list.getDescriptors()) {
			assertTrue(obj instanceof FlowDescriptor);
			FlowDescriptor descriptor = (FlowDescriptor) obj;
			log.debug("flow found: id={}, name={}", descriptor.getUuid(),
					descriptor.getName().getValue());
		}
	}

	@Test
	@Ignore
	public void testSearchFlowProperty() throws Exception {
		String name = "calorific";
		log.debug("test: search flow property with name '{}'", name);
		DescriptorList list = client.search(FlowProperty.class, name);
		assertTrue(list.getDescriptors().size() > 0);
		for (Object obj : list.getDescriptors()) {
			assertTrue(obj instanceof FlowPropertyDescriptor);
			FlowPropertyDescriptor descriptor = (FlowPropertyDescriptor) obj;
			log.debug("flow property found: id={}, name={}",
					descriptor.getUuid(), descriptor.getName().getValue());
		}
	}

	@Test
	public void testSearchUnitGroup() throws Exception {
		String name = "mass";
		log.debug("test: search unit group with name '{}'", name);
		DescriptorList list = client.search(UnitGroup.class, name);
		assertTrue(list.getDescriptors().size() > 0);
		for (Object obj : list.getDescriptors()) {
			assertTrue(obj instanceof UnitGroupDescriptor);
			UnitGroupDescriptor descriptor = (UnitGroupDescriptor) obj;
			log.debug("unit group found: id={}, name={}", descriptor.getUuid(),
					descriptor.getName().getValue());
		}
	}

	@Test
	@Ignore
	public void testSearchContact() throws Exception {
		String name = "Review";
		log.debug("test: search contact with name '{}'", name);
		DescriptorList list = client.search(Contact.class, name);
		assertTrue(list.getDescriptors().size() > 0);
		for (Object obj : list.getDescriptors()) {
			assertTrue(obj instanceof ContactDescriptor);
			ContactDescriptor descriptor = (ContactDescriptor) obj;
			log.debug("contact found: id={}, name={}", descriptor.getUuid(),
					descriptor.getName().getValue());
		}
	}

	@Test
	@Ignore
	public void testSearchSource() throws Exception {
		String name = "IMA-Europe_Plastic";
		log.debug("test: search source with name '{}'", name);
		DescriptorList list = client.search(Source.class, name);
		assertTrue(list.getDescriptors().size() > 0);
		for (Object obj : list.getDescriptors()) {
			assertTrue(obj instanceof SourceDescriptor);
			SourceDescriptor descriptor = (SourceDescriptor) obj;
			log.debug("contact found: id={}, name={}", descriptor.getUuid(),
					descriptor.getName().getValue());
		}
	}

}
