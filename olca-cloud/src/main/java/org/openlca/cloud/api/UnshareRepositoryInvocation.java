package org.openlca.cloud.api;

import org.openlca.cloud.util.Strings;
import org.openlca.cloud.util.Valid;
import org.openlca.cloud.util.WebRequests;
import org.openlca.cloud.util.WebRequests.Type;
import org.openlca.cloud.util.WebRequests.WebRequestException;

/**
 * Invokes a webservice call to cancel sharing the specified repository
 */
class UnshareRepositoryInvocation {

	private static final String PATH = "/access/unshare";
	String baseUrl;
	String sessionId;
	String repositoryName;
	String unshareWithUser;

	/**
	 * Cancels sharing of the specified repository with the specified user
	 * 
	 * @throws WebRequestException
	 *             if repository or user does not exist
	 */
	void execute() throws WebRequestException {
		Valid.checkNotEmpty(baseUrl, "base url");
		Valid.checkNotEmpty(sessionId, "session id");
		Valid.checkNotEmpty(repositoryName, "repository name");
		Valid.checkNotEmpty(unshareWithUser, "unshare with user");
		String url = Strings.concat(baseUrl, PATH, "/", repositoryName, "/", unshareWithUser);
		WebRequests.call(Type.POST, url, sessionId);
	}

}
