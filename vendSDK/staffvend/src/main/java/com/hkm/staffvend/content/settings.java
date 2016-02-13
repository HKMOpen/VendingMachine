package com.hkm.staffvend.content;

/**
 * Created by zJJ on 2/8/2016.
 */


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hkm.staffvend.R;
import com.hkm.staffvend.ui.SecAbout;
import com.hkm.staffvend.event.BS;
import com.hkm.staffvend.dialog.DialogTextInput;
import com.hkm.staffvend.dialog.TickeD;
import com.hkm.staffvend.dialog.TicketNumD;
import com.hkmvend.sdk.client.RestaurantPOS;
import com.hkmvend.sdk.storage.Bill.BillContainer;

/**
 * Created by hesk on 12/1/16.
 */
public class settings extends PreferenceFragment   implements DialogTextInput.OnEditItemListener{
    private ListPreference mListPreference;
    private BillContainer client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RestaurantPOS c = RestaurantPOS.getInstance(getActivity().getApplication());
        client = c.getBillContainer();
        addPreferencesFromResource(R.xml.setup_option_list);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //  mListPreference = (ListPreference) getPreferenceManager().findPreference("PREF_LIST_LANG");
        // mListPreference.setOnPreferenceChangeListener(check_preference);
        Preference button = findPreference("IMPORT_MENU");
        button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                BS.start_scanning(getActivity());
                return true;
            }
        });
        Preference tstart = findPreference("TICKET_START");
        tstart.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                TickeD.newInstance(settings.this).show(getFragmentManager(), TicketNumD.TAG);
                return true;
            }
        });

        Preference extrainfo = findPreference("EXTRA_INFO");
        extrainfo.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent i = new Intent(getActivity(), SecAbout.class);
                getActivity().startActivity(i);
                return true;
            }
        });

        return inflater.inflate(R.layout.content_setting, container, false);
    }


    private Preference.OnPreferenceChangeListener check_preference = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            if (preference.getKey().equalsIgnoreCase("PREF_LIST_LANG") && newValue instanceof String) {
                setSwitchExistingClientFromPreferencePanel((String) newValue);
                return true;
            }
            return false;
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(shared_preference_changes);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(shared_preference_changes);
    }

    private SharedPreferences.OnSharedPreferenceChangeListener shared_preference_changes = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        }
    };

    public void setSwitchExistingClientFromPreferencePanel(String mLanguage) {

   /*     if (mLanguage.equals("en")) {
            client.setLanguageBase(HBEditorialClient.BASE_EN);
        } else if (mLanguage.equals("zh_CN")) {
            client.setLanguageBase(HBEditorialClient.BASE_CN);
        } else if (mLanguage.equals("ja")) {
            client.setLanguageBase(HBEditorialClient.BASE_JP);
        } else if (mLanguage.equals("zh_TW")) {
            client.setLanguageBase(HBEditorialClient.BASE_CN);
        }*/
        //   HBUtil.setApplicationLanguageBase(mLanguage, getActivity().getApplication(), client);
        //   EBus.getInstance().post(new RenderTrigger());
    }

    @Override
    public void onFieldModified(long position, String newTitle) {
        Log.d("ca;;", newTitle);
        client.setMaunalLastestBillNumber(Integer.parseInt(newTitle));
    }

}