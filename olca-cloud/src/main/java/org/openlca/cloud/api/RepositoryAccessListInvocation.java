package org.openlca.cloud.api;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.openlca.cloud.util.Strings;
import org.openlca.cloud.util.Valid;
import org.openlca.cloud.util.WebRequests;
import org.openlca.cloud.util.WebRequests.Type;
import org.openlca.cloud.util.WebRequests.WebRequestException;

import com.sun.jersey.api.client.ClientResponse;

/**
 * Invokes a web service call to retrieve the list of users that have access to
 * the specified repository
 */
class RepositoryAccessListInvocation {

	private static final String PATH = "/access/shared";
	String baseUrl;
	String sessionId;
	String repositoryId;

	/**
	 * Loads the list of users that have access to the specified repository
	 * 
	 * @return list of users with access
	 * @throws WebRequestException
	 *             if the specified repository did not exist
	 */
	List<String> execute() throws WebRequestException {
		Valid.checkNotEmpty(baseUrl, "base url");
		Valid.checkNotEmpty(sessionId, "session id");
		Valid.checkNotEmpty(repositoryId, "repository id");
		String url = Strings.concat(baseUrl, PATH, "/", repositoryId);
		ClientResponse response = WebRequests.call(Type.GET, url, sessionId);
		return new Gson().fromJson(response.getEntity(String.class),
				new TypeToken<List<String>>() {
				}.getType());
	}

}
