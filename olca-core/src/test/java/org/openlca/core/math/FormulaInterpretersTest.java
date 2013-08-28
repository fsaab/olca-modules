package org.openlca.core.math;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openlca.core.TestSession;
import org.openlca.core.database.IDatabase;
import org.openlca.core.model.Parameter;
import org.openlca.core.model.ParameterRedef;
import org.openlca.core.model.ParameterScope;
import org.openlca.core.model.Process;
import org.openlca.expressions.FormulaInterpreter;
import org.openlca.expressions.InterpreterException;
import org.openlca.expressions.Scope;

public class FormulaInterpretersTest {

	private IDatabase database = TestSession.getDefaultDatabase();
	private Parameter globalParam;
	private Process process;
	private FormulaInterpreter interpreter;

	@Before
	@SuppressWarnings("serial")
	public void setUp() throws Exception {
		globalParam = new Parameter();
		globalParam.setName("fi_tests_global");
		globalParam.setInputParameter(true);
		globalParam.setScope(ParameterScope.GLOBAL);
		globalParam.setValue(32);
		database.createDao(Parameter.class).insert(globalParam);
		process = new Process();
		Parameter localParam = new Parameter();
		localParam.setName("fi_tests_local");
		localParam.setFormula("fi_tests_global + 10");
		localParam.setInputParameter(false);
		localParam.setScope(ParameterScope.PROCESS);
		process.getParameters().add(localParam);
		database.createDao(Process.class).insert(process);
		interpreter = FormulaInterpreters.build(database, new HashSet<Long>() {
			{
				add(process.getId());
			}
		});
	}

	@After
	public void tearDown() throws Exception {
		database.createDao(Parameter.class).delete(globalParam);
		database.createDao(Process.class).delete(process);
	}

	@Test
	public void testEvalLocal() throws Exception {
		Scope scope = interpreter.getScope(process.getId());
		Assert.assertEquals(42, scope.eval("fi_tests_local"), 1e-16);
	}

	@Test
	public void testEvalGlobal() throws Exception {
		Assert.assertEquals(32, interpreter.eval("fi_tests_global"), 1e-16);
	}

	@Test(expected = InterpreterException.class)
	public void testEvalLocalInGlobalFails() throws Exception {
		Assert.assertEquals(42, interpreter.eval("fi_tests_local"), 1e-16);
	}

	@Test
	public void testRedefine() throws Exception {
		List<ParameterRedef> redefs = new ArrayList<>();
		redefs.add(new ParameterRedef() {
			{
				setName("fi_tests_global");
				setValue(3.1);
			}
		});
		redefs.add(new ParameterRedef() {
			{
				setName("fi_tests_local");
				setValue(1.3);
				setProcessId(process.getId());
			}
		});
		FormulaInterpreters.apply(redefs, interpreter);
		Assert.assertEquals(3.1, interpreter.eval("fi_tests_global"), 1e-16);
		Scope scope = interpreter.getScope(process.getId());
		Assert.assertEquals(1.3, scope.eval("fi_tests_local"), 1e-16);
	}
}