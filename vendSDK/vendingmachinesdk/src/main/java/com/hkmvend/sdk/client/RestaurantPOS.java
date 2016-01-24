package com.hkmvend.sdk.client;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.hkmvend.sdk.model.gdata.ApiWrapGTable;
import com.hkmvend.sdk.model.gdata.Row;
import com.hkmvend.sdk.model.sheetsu.ResponseSheetsu;
import com.hkmvend.sdk.model.sheetsu.apiEntryRow;
import com.hkmvend.sdk.storage.EntryContainer;
import com.hkmvend.sdk.storage.MenuEntry;
import com.squareup.okhttp.Request;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import retrofit.Call;
import retrofit.Response;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by zJJ on 1/23/2016.
 */
public class RestaurantPOS extends retrofitClientBasic {
    private static RestaurantPOS static_instance;

    public RestaurantPOS(Application c) {
        super(c);
        registerJsonAdapter();
        container = EntryContainer.getInstnce(c);
    }

    @Override
    protected Request.Builder universal_header(Request.Builder chain) {
        chain.addHeader("Content-type", "text/plain; charset=utf-8");
        chain.addHeader("Accept-Language", "en-US,en;q=0.8");
        chain.addHeader("Accept-Encoding", "gzip, deflate, sdch");
        chain.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.111 Safari/537.36");
        chain.addHeader("Accept", "*/*");
        return chain;
    }

    public static RestaurantPOS newInstance(Application ctx) {
        return new RestaurantPOS(ctx);
    }

    public static RestaurantPOS getInstance(Application context) {
        if (static_instance == null) {
            static_instance = newInstance(context);
            return static_instance;
        } else {
            static_instance.setContext(context);
            return static_instance;
        }
    }

    //https://spreadsheets.google.com/feeds/list/17ahMMUjnfKYg1hVsFKUzR1weoSZU3kP-Id0xmvn8154/od6/public/values?alt=json-in-script&callback=cds
    //https://developers.google.com/chart/interactive/docs/querylanguage
    private interface workerService {
   /*     @GET("/feeds/list/{doc_id}/od6/public/values?alt=json-in-script&callback=cds")
        Call<String> getMenu(
                @Path("doc_id") final String document_id,
                @Query("tqx") final String extra,
                @Query("REGION") final String region);*/

        @GET("/apis/{doc_key}")
        Call<ResponseSheetsu> getMenuSheetsu(@Path("doc_key") final String document_key);
    }

    public interface DataConfigCB {
        void success(List<MenuEntry> list);

        void failure(String error_cause);
    }

    private workerService createService() {
        return api.create(workerService.class);
    }

    @Override
    protected String getBaseEndpoint() {
        //  return "https://spreadsheets.google.com/";
        return "https://sheetsu.com";
    }


    private String ensureJsonData(String result) {
        int start = result.indexOf("{", result.indexOf("{") + 1);
        int end = result.lastIndexOf("}");
        String jsonResponse = result.substring(start, end);
        return jsonResponse;
    }


    public RestaurantPOS setCB(DataConfigCB cb) {
        call_back = cb;
        return this;
    }

    public RestaurantPOS setDatabaseId(String restaurant_menu_db_id) {
        this.restaurant_menu_db_id = restaurant_menu_db_id;
        container.saveRestaurantMenuId(restaurant_menu_db_id);
        return this;
    }

    public void runType() throws Exception {
        boot_load_sync f = new boot_load_sync();
        if (call_back == null) throw new Exception("call back is not setup");
        if (!container.isMenuExisted() && restaurant_menu_db_id == null)
            throw new Exception("menu id is not setup");
        final String id = restaurant_menu_db_id == null ? container.loadRestaurantMenuId() : restaurant_menu_db_id;
        f.execute(id);
    }

    private String restaurant_menu_db_id;
    private DataConfigCB call_back;
    private EntryContainer container;
  /*  private static final int
            ENTRY_ID = 0,
            NAME_CN = 1,
            NAME_EN = 2,
            CATE = 3,
            TIME = 4,
            PRICE = 5;
*/

    private class boot_load_sync extends AsyncTask<String, Void, Void> {
        private ApiWrapGTable stored_object;
        boolean need_to_login_first = false;
        String failure = "", plain_json = "";


        @Override
        protected Void doInBackground(String[] params) {
            try {
                final String doc_id = params[0];
            /*    if (container.isConfigurationStored()) {
                    plain_json = container.loadConfigFile();
                } else {*/
                Call<ResponseSheetsu> service = createService().getMenuSheetsu(doc_id);
                Response<ResponseSheetsu> response = service.execute();
                if (!response.isSuccess()) {
                    throw new IOException("server maybe down");
                }
                if (response.code() != 200) {
                    throw new IOException("server " + response.code());
                }
                ResponseSheetsu body = response.body();
                // Log.i("TAGService", body);
                // plain_json = ensureJsonData(body);
                //  Log.i("TAGService", plain_json);
                container.saveConfigFile(plain_json);
                //}

                savePlainFileToRealm(gsonsetup.toJson(body));
            } catch (IOException e) {
                failure = e.getCause().getMessage();
                cancel(true);
            } catch (Exception e) {
                failure = e.getMessage();
                cancel(true);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            call_back.success(container.getAllRecords());
        }

        @Override
        protected void onCancelled(Void aVoid) {
            super.onCancelled(aVoid);
            call_back.failure(failure);
        }
    }

    private void savePlainFileToRealm(String file) {
        ResponseSheetsu stored_object = gsonsetup.fromJson(file, ResponseSheetsu.class);
        List<apiEntryRow> row = stored_object.result;
        container.flushRecords();
        Iterator<apiEntryRow> ri = row.iterator();
        while (ri.hasNext()) {
            apiEntryRow H = ri.next();

            boolean success = container. addNewRecord(
                    MenuEntry.valueOf(H.cate),
                    H.entryId,
                    H.chinese,
                    H.english,
                    H.price
                    );

        }
    }
}
