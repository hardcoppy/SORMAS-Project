package de.symeda.sormas.app.rest;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import de.symeda.sormas.api.caze.CaseDataDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Martin Wahnschaffe on 07.06.2016.
 */
public interface CaseFacadeRetro {

    @GET("cases/{uuid}")
    Call<CaseDataDto> getByUuid(@Path("uuid") String uuid);

    @GET("cases/all/{since}")
    Call<List<CaseDataDto>> getAllCases(@Path("since") long since);

}
