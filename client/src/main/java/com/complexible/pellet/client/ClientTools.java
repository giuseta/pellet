package com.complexible.pellet.client;

import java.io.IOException;

import com.google.common.base.Throwables;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Set of static tools to work with client API
 *
 * @author edgar
 */
public final class ClientTools {

	public static <O> O executeCall(final Call<O> theCall) {
		try {
			Response<O> aResp = theCall.execute();

			if (!aResp.isSuccessful()) {
				throw new RuntimeException(String.format("Request call failed: [%d] %s",
				                                         aResp.code(), aResp.message()));
			}

			return aResp.body();
		}
		catch (IOException e) {
			throw Throwables.propagate(e);
		}
	}
}
